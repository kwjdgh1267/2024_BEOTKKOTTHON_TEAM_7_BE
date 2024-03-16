package groomthon.studymate.service;

import groomthon.studymate.dto.StudyRequestDto;
import groomthon.studymate.dto.StudyResponseDto;
import groomthon.studymate.dto.UserDto;
import groomthon.studymate.entity.Study;
import groomthon.studymate.entity.User;
import groomthon.studymate.entity.UserStudy;
import groomthon.studymate.repository.StudyRepository;
import groomthon.studymate.repository.UserRepository;
import groomthon.studymate.repository.UserStudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyService {
    private final StudyRepository studyRepository;
    private final UserRepository userRepository;
    private final UserStudyRepository userStudyRepository;
    private final UserStudyService userStudyService;

    public StudyResponseDto createStudy(Authentication authentication, StudyRequestDto studyRequestDto) {
        UserDto userDto = (UserDto) authentication.getPrincipal();
        //스터디 생성
        Study newStudy= new Study(studyRequestDto.getTitle(),studyRequestDto.getContents(),
                studyRequestDto.getSubject(),studyRequestDto.getRecruitNum(),false,studyRequestDto.getFrequency(), userDto.getEmail());
        newStudy.setNowNum(1);
        Study savedStudy = studyRepository.save(newStudy);
        //로그인한 정보로 현재 유저 불러오기
        User foundUser= userRepository.findByEmail(userDto.getEmail()).get();

        //중간 테이블 생성, 저장
        UserStudy userStudy= new UserStudy(newStudy,foundUser);
        UserStudy savedUserStudy = userStudyRepository.save(userStudy);

        //서로 연관관계 매핑
        userStudy.setStudy(newStudy);
        userStudy.setUser(foundUser);


        List<User> users = userStudyService.getUserListByStudyId(savedStudy.getId());

        return changer(savedStudy,users);

    }

    public List<StudyResponseDto> findAllStudy(boolean cond) {//0은 모집 덜된거 1은 다된거임
        List<Study> studies = studyRepository.findAllByComp(false);
        List<StudyResponseDto> temp = new ArrayList<>();
        for(Study tempStudy:studies){
            List<User> users = userStudyService.getUserListByStudyId(tempStudy.getId());
            temp.add(changer(tempStudy,users));
        }
        return temp;
    }

    public StudyResponseDto findOneStudy(Long studyId) {
        Study foundStudy = studyRepository.findById(studyId).orElse(null);
        List<User> users = userStudyService.getUserListByStudyId(foundStudy.getId());

        return changer(foundStudy,users);

    }

    public StudyResponseDto changer(Study study,List<User> users){
        return new StudyResponseDto(study.getId(), study.getTitle(), study.getContents(),
                study.getSubject(),study.getRecruitNum(), study.isComplete(), study.getFrequency(), study.getWriter(), study.getNowNum(),
                users.stream().map(user -> User.toUserResDto(user)).collect(Collectors.toList()));
    }


}

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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyService {
    private final StudyRepository studyRepository;
    private final UserRepository userRepository;
    private final UserStudyRepository userStudyRepository;

    public StudyResponseDto createStudy(Authentication authentication, StudyRequestDto studyRequestDto) {
        UserDto userDto = (UserDto) authentication.getPrincipal();
        //스터디 생성
        Study newStudy= new Study(studyRequestDto.getTitle(),studyRequestDto.getContents(),
                studyRequestDto.getSubject(),studyRequestDto.getRecruitNum(),false,studyRequestDto.getFrequency(), userDto.getEmail());
        Study savedStudy = studyRepository.save(newStudy);
        //로그인한 정보로 현재 유저 불러오기
        User foundUser= userRepository.findByEmail(userDto.getEmail()).get();

        //중간 테이블 생성, 저장
        UserStudy userStudy= new UserStudy(newStudy,foundUser);
        UserStudy savedUserStudy = userStudyRepository.save(userStudy);

        //서로 연관관계 매핑
        userStudy.setStudy(newStudy);
        userStudy.setUser(foundUser);



        //나중에 서비스로 옮길거임
        //UserStudy 테이블에서 해당 스터디 아이디에 맞는거를 다 가져오고
        List<UserStudy> found = userStudyRepository.findByStudyId(savedStudy.getId());
        List<User> users= new ArrayList<>();
        for(UserStudy tempUserStudy: found){
            users.add(userRepository.findById(tempUserStudy.getUser().getId()).get());
        }

        return new StudyResponseDto(savedStudy.getId(), savedStudy.getTitle(), savedStudy.getContents(),
                savedStudy.getSubject(),savedStudy.getRecruitNum(), savedStudy.isComplete(), savedStudy.getFrequency(), savedStudy.getWriter(),
                users.stream().map(user -> User.toUserResDto(user)).collect(Collectors.toList()));

    }
}

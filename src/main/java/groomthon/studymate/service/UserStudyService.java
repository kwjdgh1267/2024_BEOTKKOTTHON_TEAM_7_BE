package groomthon.studymate.service;

import groomthon.studymate.dto.UserDto;
import groomthon.studymate.entity.Study;
import groomthon.studymate.entity.User;
import groomthon.studymate.entity.UserStudy;
import groomthon.studymate.repository.StudyRepository;
import groomthon.studymate.repository.UserRepository;
import groomthon.studymate.repository.UserStudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserStudyService {
    private final UserStudyRepository userStudyRepository;
    private final UserRepository userRepository;
    private final StudyRepository studyRepository;

    //스터디 아이디로 참가자 조회
    public List<User> getUserListByStudyId(Long studyId){
        List<UserStudy> userStudies = userStudyRepository.findByStudyId(studyId);
        List<User> users= new ArrayList<>();
        for(UserStudy tempUserStudy: userStudies){
            users.add(userRepository.findById(tempUserStudy.getUser().getId()).get());
        }
        return users;
    }

    public String joinStudy(Authentication authentication, Long studyId) {
        UserDto userDto = (UserDto) authentication.getPrincipal();
        User foundUser = userRepository.findByEmail(userDto.getEmail()).orElse(null);
        Study foundStudy= studyRepository.findById(studyId).orElse(null);

        List<UserStudy> userStudies = userStudyRepository.findByStudyId(studyId);
        int check=0;
        for(UserStudy userStudy: userStudies){
            if(userStudy.getUser().getId()==foundUser.getId()){// 나중에 유저로 비교해보기
                check=1;
                break;
            }
        }
        if(check==0){
            if(foundStudy.getNowNum()< foundStudy.getRecruitNum()){
                if(foundStudy.getNowNum()+1==foundStudy.getRecruitNum()){// 이미 회원이면 안되도록 처리해야함. 완료
                    foundStudy.setComplete(true);
                }
                foundStudy.setNowNum(foundStudy.getNowNum()+1);
                UserStudy newUserStudy = new UserStudy();
                newUserStudy.setUser(foundUser);
                newUserStudy.setStudy(foundStudy);
                userStudyRepository.save(newUserStudy);
            }else{
                return "최대 인원입니다.";// 바람직하지 않다. 예외처리에 대해 알아보자
            }
            return "가입 완료";
        }else {
            return "이미 가입한 스터디입니다.";
        }
    }
}

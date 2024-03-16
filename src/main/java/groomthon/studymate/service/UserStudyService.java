package groomthon.studymate.service;

import groomthon.studymate.entity.User;
import groomthon.studymate.entity.UserStudy;
import groomthon.studymate.repository.UserRepository;
import groomthon.studymate.repository.UserStudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserStudyService {
    private final UserStudyRepository userStudyRepository;
    private final UserRepository userRepository;

    //스터디 아이디로 참가자 조회
    public List<User> getUserListByStudyId(Long studyId){
        List<UserStudy> userStudies = userStudyRepository.findByStudyId(studyId);
        List<User> users= new ArrayList<>();
        for(UserStudy tempUserStudy: userStudies){
            users.add(userRepository.findById(tempUserStudy.getUser().getId()).get());
        }
        return users;
    }
}

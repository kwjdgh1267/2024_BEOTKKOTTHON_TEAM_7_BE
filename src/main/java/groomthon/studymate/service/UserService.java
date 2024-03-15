package groomthon.studymate.service;

import groomthon.studymate.config.auth.jwt.PrincipalDetails;
import groomthon.studymate.dto.UserDto;
import groomthon.studymate.entity.Role;
import groomthon.studymate.entity.User;
import groomthon.studymate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public String findMyInfo(Authentication authentication) {//정상 작동되면 dto 대신 principal써보자
        UserDto userDto = (UserDto) authentication.getPrincipal();
        return userDto.getEmail()+userDto.getPicture()+userDto.getRole().getKey();
    }
    @Transactional
    public User createUser(UserDto dto){
        return userRepository.save(User.builder().email(dto.getEmail()).name(dto.getName()).picture(dto.getPicture()).role(Role.MENTEE).build());
    }
    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }
}

package groomthon.studymate.controller;

import groomthon.studymate.entity.User;
import groomthon.studymate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final UserRepository userRepository;
    @GetMapping("/test")
    public String test(){
        return "test입니다";
    }

    @GetMapping("/getimage")
    public String getimage(@AuthenticationPrincipal OAuth2User user){
        return user.getAttributes().toString();
    }




}

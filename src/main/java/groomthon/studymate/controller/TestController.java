package groomthon.studymate.controller;

import groomthon.studymate.entity.User;
import groomthon.studymate.repository.UserRepository;
import groomthon.studymate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final UserRepository userRepository;
    private final UserService userService;
    @GetMapping("/test")
    public String test(Authentication authentication){
        return "test입니다";
    }

    @GetMapping("/getimage")
    public String getimage( Authentication authentication){
        return authentication.getPrincipal().toString();
    }
    @GetMapping("/myinfo")
    public String findUser(Authentication authentication){

        return userService.findMyInfo(authentication);
    }
    @GetMapping("/mytest/findbyemail")
    public String findUser(@RequestParam(name = "email") String email){

        return userService.findByEmail(email).getEmail();
    }


//    @GetMapping("https://www.googleapis.com/userinfo/v2/me")
//    ResponseEntity<String> get


    @GetMapping("/oauth/google")
    public void googleCallback(@RequestParam String code){
        System.out.println(code);
    }


}

package groomthon.studymate.controller;

import groomthon.studymate.config.auth.jwt.Token;
import groomthon.studymate.dto.IdTokenRequestDto;
import groomthon.studymate.dto.UserDto;
import groomthon.studymate.entity.User;
import groomthon.studymate.repository.UserRepository;
import groomthon.studymate.service.AccountService;
import groomthon.studymate.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class TestController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final AccountService accountService;
    @GetMapping("/test")
    public String test(){
        return "test입니다";
    }

    @GetMapping("/user/test")
    public String test1(Authentication authentication){
        return "usertest입니다";
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


//    @PostMapping("/login/oauth2/code")
//    public String googleCall(@RequestBody String str){
//        System.out.println(str);
//        return str;
//    }
    @PostMapping("/login/oauth2/code")
    public ResponseEntity LoginWithGoogleOauth2(@RequestBody IdTokenRequestDto requestBody, HttpServletResponse response) {
        //내가 수정함
        UserDto userDto=accountService.accessTokenToUserDto(requestBody);
        Token authToken = accountService.loginOAuthGoogle(userDto);
        final ResponseCookie cookie = ResponseCookie
                .from("AUTH-TOKEN", authToken.getToken())
                .httpOnly(true)
                .maxAge(7 * 24 * 3600)//7일
                .path("/")
                .secure(false)
                .build();
        final ResponseCookie refreshCookie = ResponseCookie
                .from("REFRESH-TOKEN", authToken.getRefreshToken())
                .httpOnly(true)
                .maxAge(90 * 24 * 3600)//90일
                .path("/")
                .secure(false)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
        return ResponseEntity.ok().build();
    }



}

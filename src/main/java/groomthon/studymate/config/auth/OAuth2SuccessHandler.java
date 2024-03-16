package groomthon.studymate.config.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import groomthon.studymate.config.auth.jwt.Token;
import groomthon.studymate.config.auth.jwt.TokenService;
import groomthon.studymate.dto.UserDto;
import groomthon.studymate.entity.User;
import groomthon.studymate.service.AccountService;
import groomthon.studymate.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final TokenService tokenService;
    private final UserRequestMapper userRequestMapper;
    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final AccountService accountService;//과제 7팀에 제출

    @Override//
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        UserDto userDto = userRequestMapper.toDto(oAuth2User);

        // 최초 로그인이라면 회원가입 처리를 한다.
        User foundUser= accountService.createUser(userDto);
// 기존코드
//        User foundUser = userService.findByEmail(userDto.getEmail());
//        if(foundUser ==null){
//            User temp=userService.createUser(userDto);
//        }
        Token token =accountService.loginOAuthGoogle(userDto);
// 기존 코드
// Token token = tokenService.generateToken(userDto.getEmail(), "USER");
        log.info("{}", token);

        writeTokenResponse(response, token);
    }

    private void writeTokenResponse(HttpServletResponse response, Token token)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        response.addHeader("Auth", token.getToken());
        response.addHeader("Refresh", token.getRefreshToken());
        response.setContentType("application/json;charset=UTF-8");

        var writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(token));
        writer.flush();
    }
}
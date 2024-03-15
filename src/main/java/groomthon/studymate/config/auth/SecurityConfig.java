package groomthon.studymate.config.auth;

import groomthon.studymate.config.auth.jwt.JwtAuthFilter;
import groomthon.studymate.config.auth.jwt.TokenService;
import groomthon.studymate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler successHandler;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer.disable());
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
        http.sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        http.headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()));
        http.authorizeHttpRequests(
                authorize -> authorize
                        .requestMatchers(
                                "/favicon.ico",
                                "/error",
                                "/swagger-ui/**",
                                "/swagger-resources/**",
                                "/v3/api-docs/**","/token/**","/login/**","/oauth/google").permitAll()
                        .requestMatchers("/mytest/**").permitAll()
                        .anyRequest().authenticated()
        );
//        http.oauth2Login((oa->oa.loginPage("/token/expired")));
//        http.oauth2Login(oa->oa.defaultSuccessUrl("/test"));
        http.oauth2Login(oa->oa.successHandler(successHandler));
        http.oauth2Login(oa->oa.userInfoEndpoint(uif->uif.userService(oAuth2UserService)));

        http.addFilterBefore(new JwtAuthFilter(tokenService,userRepository), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
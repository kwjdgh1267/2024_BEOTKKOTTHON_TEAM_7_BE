package groomthon.studymate.config.auth.jwt;

import groomthon.studymate.entity.User;
import groomthon.studymate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByEmail(username).orElse(null);
        //     System.out.println("1111");
        return new PrincipalDetails(userEntity);

        /*username 으로 받는게 디폴트인데 나는 폰넘버로 받겠다*/
    }
}
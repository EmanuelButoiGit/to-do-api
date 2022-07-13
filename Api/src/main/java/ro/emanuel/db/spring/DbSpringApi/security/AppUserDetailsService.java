package ro.emanuel.db.spring.DbSpringApi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.emanuel.db.spring.DbSpringApi.entities.UserEntity;
import ro.emanuel.db.spring.DbSpringApi.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        final UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("can't find user with name " + username));
        return new AppUserDetails(userEntity.getUsername(), userEntity.getPassword(), userEntity.getRole());
    }

}

package pro.hexa.backend.main.api.common.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pro.hexa.backend.domain.user.repository.UserRepository;
import pro.hexa.backend.main.api.common.config.security.dto.CustomUserDetails;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String userId) {
    // 찾은 유저를 권한에 따라 인가함.
//        return userRepository.findById(userId).map(u -> new CustomUserDetails(u, Collections.singleton(new SimpleGrantedAuthority(u.getRole().getValue())))).orElseThrow(() -> new UserNotFoundException(userId));;
    }

}
package pro.hexa.backend.main.api.common.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pro.hexa.backend.main.api.common.config.security.dto.CustomUserDetails;

@RequiredArgsConstructor
@Log4j2
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;




    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        // AuthenticaionFilter에서 생성된 토큰으로부터 아이디와 비밀번호를 조회함
        String username = token.getName();
        String userPw = (String) token.getCredentials();
        // UserDetailsService를 통해 DB에서 아이디로 사용자 조회
        CustomUserDetails customUserDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);

        if(!passwordEncoder.matches(userPw, customUserDetails.getPassword())){
            throw new BadCredentialsException(customUserDetails.getUsername() + "Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(customUserDetails, userPw, customUserDetails.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(CustomAuthentication.class);
    }

}
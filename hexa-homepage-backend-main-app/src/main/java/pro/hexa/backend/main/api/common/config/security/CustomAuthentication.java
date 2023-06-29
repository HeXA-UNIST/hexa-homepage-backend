package pro.hexa.backend.main.api.common.config.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import pro.hexa.backend.main.api.common.config.security.dto.CustomUserDetails;


public class CustomAuthentication extends UsernamePasswordAuthenticationToken {
    // principal, credentials,
    // 인증 전 생성자
    public CustomAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }
    private CustomUserDetails getCustomUserDetails(){
        return (CustomUserDetails) this.getPrincipal();
    }

    public String getId(){
        return getCustomUserDetails().getUsername();
    }

    public String getPassword(){
        return getCustomUserDetails().getPassword();
    }

}

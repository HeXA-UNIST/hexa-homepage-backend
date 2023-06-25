package pro.hexa.backend.main.api.domain.login.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequestDto {
    private String username;
    private String password;
}

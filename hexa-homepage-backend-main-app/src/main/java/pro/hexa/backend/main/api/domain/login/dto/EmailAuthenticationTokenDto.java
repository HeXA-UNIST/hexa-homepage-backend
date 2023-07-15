package pro.hexa.backend.main.api.domain.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailAuthenticationTokenDto {

    private String accessToken;

    private String refreshToken;
}

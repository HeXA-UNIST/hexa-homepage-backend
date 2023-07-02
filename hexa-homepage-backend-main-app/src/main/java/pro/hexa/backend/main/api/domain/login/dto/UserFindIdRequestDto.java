package pro.hexa.backend.main.api.domain.login.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFindIdRequestDto {
    @Schema(description = "이름")
    private String name;

    @Schema(description = "e-mail")
    private String email;

    @Schema(description = "인증번호")
    private String verificationCode;
}

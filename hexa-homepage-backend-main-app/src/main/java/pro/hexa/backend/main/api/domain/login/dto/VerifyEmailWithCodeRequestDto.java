package pro.hexa.backend.main.api.domain.login.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class VerifyEmailWithCodeRequestDto {

    @Schema(description = "유저 이름")
    private String name;

    @Schema(description = "이메일")
    private String email;

    @Schema(description = "인증번호")
    private String code;
}

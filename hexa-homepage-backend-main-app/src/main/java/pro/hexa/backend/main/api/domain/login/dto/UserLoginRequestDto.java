package pro.hexa.backend.main.api.domain.login.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequestDto {
    @Schema(description = "유저 이름")
    private String id;
    @Schema(description = "비밀번호")
    private String password;
}

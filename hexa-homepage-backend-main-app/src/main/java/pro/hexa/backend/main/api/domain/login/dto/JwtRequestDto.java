package pro.hexa.backend.main.api.domain.login.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequestDto {
    @Schema(description = "이름")
    private String userName;

    @Schema(description = "비번")
    private String password;
}

package pro.hexa.backend.main.api.domain.login.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequestDto {
    @Schema(description = "유저 이름")
    private String userName;

    @Schema(description = "이름?", example = "김선욱")
    private String name;

    @Schema(description = "비번1")
    private String password1;

    @Schema(description = "비번2...??")
    private String password2;
}

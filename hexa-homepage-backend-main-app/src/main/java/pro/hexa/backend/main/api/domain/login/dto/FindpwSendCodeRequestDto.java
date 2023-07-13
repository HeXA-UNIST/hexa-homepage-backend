package pro.hexa.backend.main.api.domain.login.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class FindpwSendCodeRequestDto {

    @Schema(description = "사용자 id")
    private String id;

    @Schema(description = "이름")
    private String name;

    @Schema(description = "이메일")
    private String email;
}

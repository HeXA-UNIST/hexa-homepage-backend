package pro.hexa.backend.main.api.domain.login.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFindPasswordSecondRequestDto {
    // this Dto is used for Authentication By Email
    @Schema(description = "이름")
    private String name;
    @Schema(description = "이메일")
    private String email;
    @Schema(description = "인증 번호")
    private String authenticationNumbers;
}

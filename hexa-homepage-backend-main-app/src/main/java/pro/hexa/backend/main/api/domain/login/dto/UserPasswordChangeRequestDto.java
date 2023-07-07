package pro.hexa.backend.main.api.domain.login.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPasswordChangeRequestDto {
    @Schema(description = "아이디")
    private String id;

    @Schema(description = "새 비밀번호")
    private String password_1;

    @Schema(description = "비밀번호 확인")
    private String password_2;
}

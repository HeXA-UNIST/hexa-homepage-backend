package pro.hexa.backend.main.api.domain.login.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class FindpwChangePwRequestDto {

    @Schema(description = "사용자 id")
    private String id;
    @Schema(description = "새 비밀번호")
    private String newPw;
    @Schema(description = "새 비밀번호 확인")
    private String newPwConfirm;
    @Schema(description = "인증 토큰")
    private String token;
}

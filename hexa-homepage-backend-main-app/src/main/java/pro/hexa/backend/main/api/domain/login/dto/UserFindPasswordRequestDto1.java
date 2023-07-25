package pro.hexa.backend.main.api.domain.login.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFindPasswordRequestDto1 {
    @Schema(description = "아이디")
    private String id;
}

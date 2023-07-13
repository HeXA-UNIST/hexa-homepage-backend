package pro.hexa.backend.main.api.domain.login.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class FindpwRequestDto {

    @Schema(description = "사용자 id")
    private String id;
}

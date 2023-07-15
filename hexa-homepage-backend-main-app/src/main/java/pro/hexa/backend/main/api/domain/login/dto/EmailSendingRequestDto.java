package pro.hexa.backend.main.api.domain.login.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EmailRequestDto {
    @Schema(description = "이름")
    private String name;
    @Email
    @Schema(description =  "이메일")
    private String email;
}

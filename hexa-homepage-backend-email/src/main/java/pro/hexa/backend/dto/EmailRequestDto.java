package pro.hexa.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmailRequestDto {
    private String sendTo;
    private String name;
    private String content;
}

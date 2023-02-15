package pro.hexa.backend.main.api.domain.seminar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pro.hexa.backend.domain.attachment.domain.Attachment;

@Getter
@NoArgsConstructor
public class SeminarAttatchmentDto {
    @Schema(description = "첨부파일 url")
    private String url;
    private String name;
    private Long size;

    public static SeminarAttatchmentDto fromSeminarAttatchment(Attachment seminarAttatchement) {
        SeminarAttatchmentDto seminarAttatchmentDto = new SeminarAttatchmentDto();
        seminarAttatchmentDto.url = seminarAttatchement.getLocation();
        seminarAttatchmentDto.name = seminarAttatchement.getName();
        seminarAttatchmentDto.size = seminarAttatchement.getSize();
        return seminarAttatchmentDto;
    }

}
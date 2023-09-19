package pro.hexa.backend.main.api.domain.seminar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminSeminarDto {

    @Schema(description = "세미나 id")
    private Long seminarId;

    @Schema(description = "세미나 제목")
    private String title;

    @Schema(description = "세미나 날짜")
    private Date date;

    @Schema(description = "첨부파일 개수")
    private int attachmentsCount;
}

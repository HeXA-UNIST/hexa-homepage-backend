package pro.hexa.backend.main.api.domain.seminar.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminSeminarDetailResponse {

    @Schema(description = "세미나 제목")
    private String title;

    @Schema(description = "세미나 내용")
    private String content;

    @Schema(description = "세미나 날짜")
    private Date date;

    @Schema(description = "세미나 첨부파일 리스트")
    private List<AdminSeminarAttachmentDto> attachments;
}

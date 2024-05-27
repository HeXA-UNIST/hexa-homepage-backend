package pro.hexa.backend.main.api.domain.seminar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pro.hexa.backend.domain.seminar.domain.Seminar;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminSeminarDetailResponse {

    @Schema(description = "세미나 제목")
    private String title;

    @Schema(description = "세미나 내용")
    private String content;

    @Schema(description = "세미나 날짜")
    private String date;

    @Schema(description = "세미나 첨부파일 리스트")
    private List<AdminSeminarAttachmentDto> attachments;

    public void fromSeminar(Seminar seminar) {
        this.title = seminar.getTitle();
        this.content = seminar.getContent();
        this.date = seminar.getDate().toString();
        if (seminar.getAttachments() != null && !seminar.getAttachments().isEmpty()) {
            this.attachments = seminar.getAttachments().stream()
                    .map(attachment -> new AdminSeminarAttachmentDto(
                            attachment.getId(),
                            attachment.getName(),
                            attachment.getSize()
                    ))
                    .collect(Collectors.toList());
        } else {
            this.attachments = new ArrayList<>();
        }
    }
}

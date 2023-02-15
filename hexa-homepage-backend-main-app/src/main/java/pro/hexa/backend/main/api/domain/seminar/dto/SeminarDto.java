package pro.hexa.backend.main.api.domain.seminar.dto;

import static pro.hexa.backend.main.api.common.utils.DateUtils.YYYY_MM_DD;
import static pro.hexa.backend.main.api.common.utils.DateUtils.toFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pro.hexa.backend.domain.seminar.domain.Seminar;

@Getter
@NoArgsConstructor
public class SeminarDto {
    @Schema(description = "id")
    protected Long seminarId;

    @Schema(description = "제목")
    protected String title;

    @Schema(description = "날짜", example = "2022.01.01")
    protected String date;

    @Schema(description = "유저 아이디")
    protected String writerUserId;

    @Schema(description = "유저 이름")
    protected String writerName;

    @Schema(description = "컨텐츠")
    protected String content;

    @Schema(description = "첨부파일")
    protected List<SeminarAttatchmentDto> attachment;

    public void fromSeminar(Seminar seminar) {
        this.seminarId = seminar.getId();
        this.title = seminar.getTitle();
        this.date = toFormat(seminar.getDate(), YYYY_MM_DD);
        this.writerUserId = seminar.getUser().getId();
        this.writerName = seminar.getUser().getName();
        this.content = seminar.getContent();
        this.attachment = seminar.getAttachments().stream()
                .map(SeminarAttatchmentDto::fromSeminarAttatchment)
                .collect(Collectors.toList());
    }
}
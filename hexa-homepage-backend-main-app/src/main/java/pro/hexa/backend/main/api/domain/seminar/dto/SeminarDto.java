package pro.hexa.backend.main.api.domain.seminar.dto;

import static pro.hexa.backend.main.api.common.utils.DateUtils.YYYY_MM_DD;
import static pro.hexa.backend.main.api.common.utils.DateUtils.toFormat;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "유저 이름")
    protected String writer;

    @Schema(description = "컨텐츠")
    protected String content;

    @Schema(description = "첨부파일")
    protected boolean hasAttachment;

    public void fromSeminar(Seminar seminar) {
        this.seminarId = seminar.getId();
        this.title = seminar.getTitle();
        this.date = toFormat(seminar.getDate(), YYYY_MM_DD);
        this.content = seminar.getContent();
        this.hasAttachment = (seminar.getAttachments()!=null&&!seminar.getAttachments().isEmpty());
    }
}
package pro.hexa.backend.main.api.domain.news.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminCreateNewsRequestDto {

    @Schema(description = "뉴스 종류", required = true)
    private String newsType;

    @Schema(description = "뉴스 제목", required = true)
    private String title;

    @Schema(description = "뉴스 날짜", required = true)
    private Date date;

    @Schema(description = "뉴스 내용", required = true)
    private String content;
}

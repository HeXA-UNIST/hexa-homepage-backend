package pro.hexa.backend.main.api.domain.news.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pro.hexa.backend.domain.news.model.NEWS_TYPE;

@Getter
@AllArgsConstructor
public class NewsDetailResponse {

    @Schema(description = "뉴스 종류")
    private NEWS_TYPE newsType;

    @Schema(description = "뉴스 제목")
    private String title;

    @Schema(description = "뉴스 날짜")
    private LocalDate date;

    @Schema(description = "뉴스 내용")
    private String content;


}

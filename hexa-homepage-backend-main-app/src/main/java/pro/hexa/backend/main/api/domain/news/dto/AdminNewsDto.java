package pro.hexa.backend.main.api.domain.news.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pro.hexa.backend.domain.news.domain.News;
import pro.hexa.backend.domain.news.model.NEWS_TYPE;

@Getter
@NoArgsConstructor
public class AdminNewsDto {

    @Schema(description = "뉴스 id")
    private Long newsId;

    @Schema(description = "뉴스 종류")
    private NEWS_TYPE newsType;

    @Schema(description = "뉴스 제목")
    private String title;

    @Schema(description = "뉴스 날짜")
    private LocalDate date;
    public void fromNews(News news) {
        this.newsId = news.getId();
        this.newsType = news.getNewsType();
        this.title = news.getTitle();
        this.date = news.getDate();
    }
}

package pro.hexa.backend.main.api.domain.main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import pro.hexa.backend.domain.news.model.NEWS_TYPE;

@Getter
@Builder
public class MainPageNewsDto {

    @Schema(description = "id")
    private Long newsId;

    @Schema(description = "뉴스 타입")
    private NEWS_TYPE newsType;

    @Schema(description = "제목")
    private String title;

    @Schema(description = "날짜", example = "2022.01.04")
    private String date;
}

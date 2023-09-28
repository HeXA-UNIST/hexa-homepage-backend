package pro.hexa.backend.main.api.domain.news.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.Date;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pro.hexa.backend.domain.news.domain.News;
import pro.hexa.backend.domain.news.model.NEWS_TYPE;
import pro.hexa.backend.domain.project.domain.Project;
import pro.hexa.backend.domain.project_tech_stack.domain.ProjectTechStack;
import pro.hexa.backend.main.api.domain.project.dto.ProjectMemberDto;

import static pro.hexa.backend.main.api.common.utils.DateUtils.YYYY_MM_DD;
import static pro.hexa.backend.main.api.common.utils.DateUtils.toFormat;

@Getter
@NoArgsConstructor
public class AdminNewsDetailResponse {

    @Schema(description = "뉴스 종류")
    private NEWS_TYPE newsType;

    @Schema(description = "뉴스 제목")
    private String title;

    @Schema(description = "뉴스 날짜")
    private LocalDate date;

    @Schema(description = "뉴스 내용")
    private String content;

    public void fromNews(News news) {
        this.newsType = news.getNewsType();
        this.title = news.getTitle();
        this.date = news.getDate();
        this.content = news.getContent();
    }

}

package pro.hexa.backend.main.api.domain.project.dto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminProjectDetailResponse {

    @Schema(description = "프로젝트 제목")
    private String title;

    @Schema(description = "프로젝트 시작 날짜")
    private Date startDate;

    @Schema(description = "프로젝트 종료 날짜")
    private Date endDate;

    @Schema(description = "프로젝트 기술 스택 리스트")
    private List<String> projectTechStacks;

    @Schema(description = "프로젝트 상태")
    private String state;

    @Schema(description = "프로젝트 내용")
    private String content;

    @Schema(description = "썸네일 이미지 파일 id")
    private Long thumbnail;
}

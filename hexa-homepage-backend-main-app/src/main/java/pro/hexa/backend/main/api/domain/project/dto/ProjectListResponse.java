package pro.hexa.backend.main.api.domain.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectListResponse {

    @Schema(description = "총 페이지 수")
    private Integer totalPage;

    @Schema(description = "프로젝트 목록")
    private List<ProjectDto> projects;

}

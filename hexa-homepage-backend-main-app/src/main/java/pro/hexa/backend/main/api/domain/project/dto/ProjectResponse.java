package pro.hexa.backend.main.api.domain.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectResponse {
    @Schema(description = "프로젝트")
    private ProjectDto project;
}

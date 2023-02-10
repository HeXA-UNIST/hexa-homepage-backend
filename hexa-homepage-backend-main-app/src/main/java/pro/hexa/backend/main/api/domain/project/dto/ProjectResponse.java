package pro.hexa.backend.main.api.domain.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pro.hexa.backend.domain.project.domain.Project;

@Getter
@NoArgsConstructor
public class ProjectResponse extends ProjectDto {

    @Schema(description = "내용")
    private String content;

    @Override
    public void fromProject(Project project) {
        super.fromProject(project);
        this.content = project.getContent();
    }
}

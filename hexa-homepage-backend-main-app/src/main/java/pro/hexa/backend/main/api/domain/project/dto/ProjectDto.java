package pro.hexa.backend.main.api.domain.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pro.hexa.backend.domain.project.domain.Project;
import pro.hexa.backend.domain.project.model.STATE_TYPE;

@Getter
@NoArgsConstructor
public class ProjectDto {

    @Schema(description = "id")
    protected Long projectId;

    @Schema(description = "제목")
    protected String title;

    @Schema(description = "썸네일 id")
    protected Long thumbnail;

    @Schema(description = "상태")
    protected STATE_TYPE state;

    @Schema(description = "간단한 소개")
    protected String description;

    public void fromProject(Project project) {
        this.projectId = project.getId();
        this.title = project.getTitle();
        this.thumbnail = (project.getThumbnail()!=null)?project.getThumbnail().getId():null;
        this.state = project.getState();
        this.description = project.getDescription();
    }
}

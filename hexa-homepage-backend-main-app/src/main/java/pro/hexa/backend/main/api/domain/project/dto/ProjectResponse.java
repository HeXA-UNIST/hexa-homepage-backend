package pro.hexa.backend.main.api.domain.project.dto;

import static pro.hexa.backend.main.api.common.utils.DateUtils.YYYY_MM_DD;
import static pro.hexa.backend.main.api.common.utils.DateUtils.toFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pro.hexa.backend.domain.project.domain.Project;
import pro.hexa.backend.domain.project_tech_stack.domain.ProjectTechStack;

@Getter
@NoArgsConstructor
public class ProjectResponse{

    private String title;

    private String startDate;

    private String endDate;

    private List<String> projectTechStacks;

    // private somewhat members;

    private String state;

    @Schema(description = "내용")
    private String content;

    private String description;

    private Long thumbnail;


    public void fromProject(Project project) {
        this.title = project.getTitle();
        this.startDate = toFormat(project.getStartDate(), YYYY_MM_DD);
        this.endDate = toFormat(project.getEndDate(), YYYY_MM_DD);
        this.projectTechStacks = project.getProjectTechStacks().stream()
            .map(ProjectTechStack::getContent)
            .collect(Collectors.toList());
        this.description = project.getDescription();
        this.content = project.getContent();
        this.state = project.getState().getValue();
        this.thumbnail = project.getThumbnail().getId();
    }
}

package pro.hexa.backend.main.api.domain.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pro.hexa.backend.domain.project_member.domain.ProjectMember;
import pro.hexa.backend.domain.user.domain.User;

@Getter
@NoArgsConstructor
public class ProjectMemberDto {

    @Schema(description = "사용자 id")
    private String userId;
    private String name;
    private String profileUrl;

    public static ProjectMemberDto fromProjectMember(ProjectMember projectMember) {
        User user = projectMember.getUser();

        ProjectMemberDto projectMemberDto = new ProjectMemberDto();
        projectMemberDto.userId = user.getId();
        projectMemberDto.name = user.getName();
        projectMemberDto.profileUrl = user.getProfileImage().getLocation();
        return projectMemberDto;
    }
}

package pro.hexa.backend.main.api.domain.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import pro.hexa.backend.domain.project.model.STATE_TYPE;
import pro.hexa.backend.domain.project_member.domain.ProjectMember;
import pro.hexa.backend.domain.project_member.model.AUTHORIZATION_TYPE;
import pro.hexa.backend.domain.project_tech_stack.domain.ProjectTechStack;

import java.util.List;

@Getter
@Builder
public class ProjectDto {
    @Schema(description = "id")
    private Long projectId;

    @Schema(description = "제목")
    private String title;

    @Schema(description = "썸네일 url")
    private String thumbnailUrl;

    @Schema(description = "시작 날짜", example = "2022.01.01")
    private String startDate;

    @Schema(description = "종료 날짜", defaultValue ="", example = "2022.01.01")
    private String endDate;

    @Schema(description = "기술스택리스트")
    private List<ProjectTechStack> techStackList;

    @Schema(description = "멤버 정보 리스트")
    private List<ProjectMember> memberList;

    @Schema(description = "상태")
    private STATE_TYPE status;

    @Schema(description = "공개")
    private AUTHORIZATION_TYPE public_status;

    @Schema(description = "내용")
    private String content;
}

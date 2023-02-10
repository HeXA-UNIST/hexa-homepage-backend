package pro.hexa.backend.main.api.domain.project.service;

import static pro.hexa.backend.main.api.common.utils.constant.yyyy_MM_dd;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.hexa.backend.domain.project.domain.Project;
import pro.hexa.backend.domain.project.repository.ProjectRepository;
import pro.hexa.backend.main.api.domain.project.dto.ProjectDto;
import pro.hexa.backend.main.api.domain.project.dto.ProjectListResponse;
import pro.hexa.backend.main.api.domain.project.dto.ProjectResponse;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectPageService {

    private ProjectRepository projectRepository;

    public ProjectListResponse getProjectListResponse() {
        List<Project> projectList = projectRepository.findForProjectListByQuery();
        List<ProjectDto> projects = projectList.stream()
                .map(this::transformProjectToProjectListDto)
                .collect(Collectors.toList());

        return ProjectListResponse.builder()
                .projects(projects)
                .page(2)    // 임시로 넣어놨습니다
                .maxPage(3) // 임시로 넣어놨습니다
                .build();
    }

    private ProjectDto transformProjectToProjectListDto(Project project) {
        return ProjectDto.builder()
                .projectId(project.getId())
                .title(project.getTitle())
                .thumbnailUrl(project.getThumbnail().getLocation())
                .startDate(project.getStartDate().format(DateTimeFormatter.ofPattern(yyyy_MM_dd)))
                .endDate(project.getEndDate().format(DateTimeFormatter.ofPattern(yyyy_MM_dd)))
                .techStackList(project.getProjectTechStacks())
                .memberList(project.getMembers())
                .status(project.getState())
                .public_status(project.getAuthorization())
                .build();
    }

    public ProjectResponse getProjectResponse() {
        Project _project = projectRepository.findForProjectByQuery();
        ProjectDto project = transformProjectToProjectDto(_project);
        return ProjectResponse.builder()
                .project(project)
                .build();
    }

    private ProjectDto transformProjectToProjectDto(Project project) {
        return ProjectDto.builder()
                .projectId(project.getId())
                .title(project.getTitle())
                .thumbnailUrl(project.getThumbnail().getLocation())
                .startDate(project.getStartDate().format(DateTimeFormatter.ofPattern(yyyy_MM_dd)))
                .endDate(project.getEndDate().format(DateTimeFormatter.ofPattern(yyyy_MM_dd)))
                .techStackList(project.getProjectTechStacks())
                .memberList(project.getMembers())
                .status(project.getState())
                .public_status(project.getAuthorization())
                .content(project.getContent())
                .build();
    }
}

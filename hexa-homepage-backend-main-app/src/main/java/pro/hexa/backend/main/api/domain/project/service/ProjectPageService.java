package pro.hexa.backend.main.api.domain.project.service;

import java.util.List;
import java.util.Optional;
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

    private final ProjectRepository projectRepository;

    public ProjectListResponse getProjectListResponse() {
        List<Project> projectList = projectRepository.findForProjectListByQuery();
        List<ProjectDto> projects = projectList.stream()
                .map(project -> {
                    ProjectDto projectDto = new ProjectDto();
                    projectDto.fromProject(project);
                    return projectDto;
                })
                .collect(Collectors.toList());

        return ProjectListResponse.builder()
                .projects(projects)
                .page(2)    // 임시로 넣어놨습니다
                .maxPage(3) // 임시로 넣어놨습니다
                .build();
    }

    public ProjectResponse getProjectResponse(Long projectId) {
        ProjectResponse projectResponse = new ProjectResponse();

        Optional.ofNullable(projectRepository.findForProjectByQuery(projectId))
            .ifPresent(projectResponse::fromProject);

        return projectResponse;
    }
}

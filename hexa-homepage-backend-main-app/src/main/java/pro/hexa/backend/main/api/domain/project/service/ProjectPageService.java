package pro.hexa.backend.main.api.domain.project.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.hexa.backend.domain.attachment.domain.Attachment;
import pro.hexa.backend.domain.attachment.repository.AttachmentRepository;
import pro.hexa.backend.domain.project.domain.Project;
import pro.hexa.backend.domain.project.model.STATE_TYPE;
import pro.hexa.backend.domain.project.repository.ProjectRepository;
import pro.hexa.backend.domain.project_tech_stack.domain.ProjectTechStack;
import pro.hexa.backend.domain.project_tech_stack.repository.ProjectTechStackRepository;
import pro.hexa.backend.main.api.common.exception.BadRequestException;
import pro.hexa.backend.main.api.common.exception.BadRequestType;
import pro.hexa.backend.main.api.common.utils.DateUtils;
import pro.hexa.backend.main.api.domain.project.dto.AdminCreateProjectRequestDto;
import pro.hexa.backend.main.api.domain.project.dto.AdminModifyProjectRequestDto;
import pro.hexa.backend.main.api.domain.project.dto.AdminProjectDetailResponse;
import pro.hexa.backend.main.api.domain.project.dto.AdminProjectDto;
import pro.hexa.backend.main.api.domain.project.dto.AdminProjectListResponse;
import pro.hexa.backend.main.api.domain.project.dto.ProjectDto;
import pro.hexa.backend.main.api.domain.project.dto.ProjectListResponse;
import pro.hexa.backend.main.api.domain.project.dto.ProjectResponse;
import pro.hexa.backend.main.api.domain.project.dto.ProjectTechStackResponse;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectPageService {

    private final ProjectRepository projectRepository;
    private final ProjectTechStackRepository projectTechStackRepository;
    private final AttachmentRepository attachmentRepository;

    public ProjectListResponse getProjectListResponse(String searchText, List<String> status, String sort, List<String> includeTechStack,
        List<String> excludeTechStack, Integer year, Integer pageNum, Integer perPage) {
        List<Project> projectList = projectRepository.findAllByQuery(searchText, status, sort, includeTechStack, excludeTechStack, year,
            pageNum, perPage);
        List<ProjectDto> projects = projectList.stream().map(project -> {
            ProjectDto projectDto = new ProjectDto();
            projectDto.fromProject(project);
            return projectDto;
        }).collect(Collectors.toList());

        int maxPage = projectRepository.getMaxPage(searchText, status, sort, includeTechStack, excludeTechStack, year, perPage);

        return ProjectListResponse.builder().projects(projects).page(perPage).maxPage(maxPage).build();
    }

    public ProjectResponse getProjectResponse(Long projectId) {
        ProjectResponse projectResponse = new ProjectResponse();

        Optional.ofNullable(projectRepository.findByQuery(projectId)).ifPresent(projectResponse::fromProject);

        return projectResponse;
    }

    public ProjectTechStackResponse getProjectTechStackResponse() {
        List<ProjectTechStack> techStackList = projectTechStackRepository.findTechStackByQuery();

        return ProjectTechStackResponse.builder()
            .techStackList(techStackList.stream().map(ProjectTechStack::getContent).collect(Collectors.toList())).build();
    }

    public AdminProjectListResponse getAdminProjectList(Integer pageNum, Integer perPage) {
        int maxPage = projectRepository.getAdminMaxPage(perPage);

        List<Project> projectList = projectRepository.findAllInAdminPage(pageNum, perPage);
        List<AdminProjectDto> projects = projectList.stream().map(project -> {
            AdminProjectDto adminProjectDto = new AdminProjectDto();
            adminProjectDto.fromProject(project);
            return adminProjectDto;
        }).collect(Collectors.toList());

        return AdminProjectListResponse.builder().totalPage(maxPage).list(projects).build();
    }

    public AdminProjectDetailResponse getAdminProjectDetail(Long projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isEmpty()) {
            throw new BadRequestException(BadRequestType.PROJECT_NOT_FOUND);
        }

        AdminProjectDetailResponse adminProjectDetailResponse = new AdminProjectDetailResponse();
        adminProjectDetailResponse.fromProject(project.get());
        return adminProjectDetailResponse;
    }

    @Transactional
    public void adminCreateProject(AdminCreateProjectRequestDto adminCreateProjectRequestDto) {
        List<ProjectTechStack> projectTechStackList = getProjectTechStackListFromTheContentList(
            adminCreateProjectRequestDto.getProjectTechStacks());
        Attachment thumbnail = attachmentRepository.findById(adminCreateProjectRequestDto.getThumbnail()).orElse(null);
        Project project = Project.create(adminCreateProjectRequestDto.getTitle(),
            DateUtils.dateToLocalDateTime(adminCreateProjectRequestDto.getStartDate()),
            DateUtils.dateToLocalDateTime(adminCreateProjectRequestDto.getEndDate()), projectTechStackList, null, null,
            STATE_TYPE.valueOf(adminCreateProjectRequestDto.getState()), adminCreateProjectRequestDto.getContent(), thumbnail);

        projectRepository.save(project);
    }

    @Transactional
    protected List<ProjectTechStack> getProjectTechStackListFromTheContentList(List<String> contentList) {
        Stream<ProjectTechStack> presentingProjectTechStackList = projectTechStackRepository.getTechStackByContentList(contentList)
            .stream();
        List<String> presentingProjectTechStackContentList = presentingProjectTechStackList.map(ProjectTechStack::getContent)
            .collect(Collectors.toList());
        Stream<ProjectTechStack> newlyCreatedProjectTechStackList = contentList.stream()
            .filter(content -> !presentingProjectTechStackContentList.contains(content))
            .map(ProjectTechStack::create)
            .map(projectTechStackRepository::save);
        return Stream.concat(presentingProjectTechStackList, newlyCreatedProjectTechStackList)
            .collect(Collectors.toList());
    }

    @Transactional
    public void adminModifyProject(AdminModifyProjectRequestDto adminModifyProjectRequestDto) {
        Optional<Project> optionalProject = projectRepository.findById(adminModifyProjectRequestDto.getProjectId());
        if (optionalProject.isEmpty()) {
            throw new BadRequestException(BadRequestType.PROJECT_NOT_FOUND);
        }
        Project project = optionalProject.get();

        if (adminModifyProjectRequestDto.getTitle() != null) {
            project.setTitle(adminModifyProjectRequestDto.getTitle());
        }

        if (adminModifyProjectRequestDto.getStartDate() != null) {
            project.setStartDate(DateUtils.dateToLocalDateTime(adminModifyProjectRequestDto.getStartDate()));
        }

        if (adminModifyProjectRequestDto.getEndDate() != null) {
            project.setEndDate(DateUtils.dateToLocalDateTime(adminModifyProjectRequestDto.getEndDate()));
        }

        if (adminModifyProjectRequestDto.getProjectTechStacks() != null) {
            List<ProjectTechStack> projectTechStacks = adminModifyProjectRequestDto.getProjectTechStacks().stream()
                .map(ProjectTechStack::create).collect(Collectors.toList());
            project.setProjectTechStacks(projectTechStacks);
        }

        if (adminModifyProjectRequestDto.getState() != null) {
            STATE_TYPE state = STATE_TYPE.valueOf(adminModifyProjectRequestDto.getState());
            project.setState(state);
        }

        if (adminModifyProjectRequestDto.getContent() != null) {
            project.setContent(adminModifyProjectRequestDto.getContent());
        }

        if (adminModifyProjectRequestDto.getThumbnail() != null) {
            Attachment thumbnail = attachmentRepository.findById(adminModifyProjectRequestDto.getThumbnail())
                .orElseThrow(() -> new BadRequestException(BadRequestType.ATTACHMENT_NOT_EXIST));
            project.setThumbnail(thumbnail);
        }
    }

    @Transactional
    public void adminDeleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }
}

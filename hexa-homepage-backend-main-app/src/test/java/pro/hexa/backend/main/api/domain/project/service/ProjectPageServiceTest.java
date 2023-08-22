package pro.hexa.backend.main.api.domain.project.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import pro.hexa.backend.domain.attachment.domain.Attachment;
import pro.hexa.backend.domain.project.domain.Project;
import pro.hexa.backend.domain.project_tech_stack.domain.ProjectTechStack;
import pro.hexa.backend.domain.project.repository.ProjectRepository;
import pro.hexa.backend.domain.project_tech_stack.repository.ProjectTechStackRepository;
import pro.hexa.backend.domain.seminar.domain.Seminar;
import pro.hexa.backend.domain.user.domain.User;
import pro.hexa.backend.main.api.domain.project.dto.ProjectListResponse;
import pro.hexa.backend.main.api.domain.project.dto.ProjectResponse;
import pro.hexa.backend.main.api.domain.project.dto.ProjectTechStackResponse;

import java.util.Collections;
import java.util.Optional;


class ProjectPageServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectTechStackRepository projectTechStackRepository;

    @InjectMocks
    private ProjectPageService projectPageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProjectListResponse() {
        // given
        String searchText = "test";
        List<String> status = List.of("승인중");
        String sort = "asc";
        List<String> includeTechStack = List.of("Java", "Spring");
        List<String> excludeTechStack = List.of("Python");
        Integer year = 2023;
        Integer pageNum = 110;
        int page = 10;

        List<Project> mockedProjects = createMockedProjects(pageNum);

        // Mock repository
        when(projectRepository.findAllByQuery(
                searchText, status, sort, includeTechStack, excludeTechStack, year, pageNum, page
        ))
                .thenReturn(mockedProjects);

        when(projectRepository.getMaxPage(searchText, status, sort, includeTechStack, excludeTechStack, year, pageNum, page))
                .thenReturn(1);

        // When
        ProjectListResponse response = projectPageService.getProjectListResponse(
                searchText, status, sort,  includeTechStack, excludeTechStack, year, pageNum, page
        );

        // Then
        assertNotNull(response);
        assertEquals(page, response.getPage());
        assertEquals(1, response.getMaxPage());
    }


    private List<Project> createMockedProjects(int count) {
        List<Project> projects = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Project project = new Project();
            project.setId((long) i);
            Attachment attachment = new Attachment();
            attachment.setLocation("path/to/thumbnail" + i);
            attachment.setName("thumbnail" + i + ".jpg");
            attachment.setSize(100L); // 예시로 임의의 값 설정

            project.setThumbnail(attachment);
            project.setStartDate(LocalDateTime.of(2023,3,3,3,3));
            project.setEndDate(LocalDateTime.of(2023,4,3,3,3));

            projects.add(project);
        }

        return projects;
    }

    @Test
    void testGetProjectResponse1() {
        // Given
        Long projectId = 1L;
        Project mockedProject = createMockedProject(projectId); // Create a mocked Project instance

        // Mock repository
        when(projectRepository.findByQuery(projectId)).thenReturn(mockedProject);

        // When
        ProjectResponse response = projectPageService.getProjectResponse(projectId);

        // Then
        assertNotNull(response);
        assertEquals(projectId, response.getProjectId());
    }
    @Test
    void testGetProjectResponse2() {
        // Given
        Long projectId = 1L;
         // Create a mocked Project instance

        // Mock repository
        when(projectRepository.findByQuery(projectId)).thenReturn(null);

        // When
        ProjectResponse response = projectPageService.getProjectResponse(projectId);

        // Then
        assertNotNull(response);
        assertNull(response.getProjectId());
    }

    private Project createMockedProject(Long projectId) {

        Project project = new Project();
        project.setId(projectId);

        Attachment attachment = new Attachment();
        attachment.setLocation("path/to/thumbnail");
        attachment.setName("thumbnail.jpg");
        attachment.setSize(100L); // 예시로 임의의 값 설정

        project.setThumbnail(attachment);
        project.setStartDate(LocalDateTime.of(2023,3,3,3,3));
        project.setEndDate(LocalDateTime.of(2023,4,3,3,3));

        return project;
    }

    @Test
    void getProjectTechStackResponse() {
        // Given
        List<ProjectTechStack> mockedProjectTechStackList = createMockedProjectTechStacks(25);


        when(projectTechStackRepository.findTechStackByQuery()).thenReturn(mockedProjectTechStackList);
        ProjectTechStackResponse response = projectPageService.getProjectTechStackResponse();

        // Then
        assertNotNull(response);
        assertEquals(25, response.getTechStackList().size());

    }

    // Helper method to create mocked tech stack list
    private List<ProjectTechStack> createMockedProjectTechStacks(int count) {
        List<ProjectTechStack> projectTechStacks = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ProjectTechStack projecttechstack = new ProjectTechStack();
            projecttechstack.setId((long) (127+i));
            projecttechstack.setContent("내용" + Integer.toString(i));

            Project project = new Project();
            project.setId(127L);

            Attachment attachment = new Attachment();
            attachment.setLocation("path/to/thumbnail");
            attachment.setName("thumbnail.jpg");
            attachment.setSize(100L); // 예시로 임의의 값 설정

            project.setThumbnail(attachment);
            project.setStartDate(LocalDateTime.of(2023,3,3,3,3));
            project.setEndDate(LocalDateTime.of(2023,4,3,3,3));


            projecttechstack.setProject(project);
            projectTechStacks.add(projecttechstack);
        }

        return projectTechStacks;
    }


}
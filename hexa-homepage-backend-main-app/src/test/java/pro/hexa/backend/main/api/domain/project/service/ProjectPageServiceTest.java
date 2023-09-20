package pro.hexa.backend.main.api.domain.project.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pro.hexa.backend.domain.attachment.domain.Attachment;
import pro.hexa.backend.domain.project.domain.Project;
import pro.hexa.backend.domain.project.model.STATE_TYPE;
import pro.hexa.backend.domain.project.repository.ProjectRepository;
import pro.hexa.backend.domain.project_member.domain.ProjectMember;
import pro.hexa.backend.domain.project_member.model.AUTHORIZATION_TYPE;
import pro.hexa.backend.domain.project_tech_stack.domain.ProjectTechStack;
import pro.hexa.backend.domain.project_tech_stack.repository.ProjectTechStackRepository;
import pro.hexa.backend.domain.user.domain.User;
import pro.hexa.backend.main.api.domain.project.dto.ProjectListResponse;
import pro.hexa.backend.main.api.domain.project.dto.ProjectResponse;
import pro.hexa.backend.main.api.domain.project.dto.ProjectTechStackResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;


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
        List<STATE_TYPE> status = List.of(STATE_TYPE.승인중);
        String sort = "asc";
        List<String> includeTechStack = List.of("Java", "Spring");
        List<String> excludeTechStack = List.of("Python");
        Integer year = 2023;
        Integer pageNum = 110;
        int perPage = 10;

        List<Project> mockedProjects = createMockedProjects(pageNum);

        // Mock repository
        when(projectRepository.findAllByQuery(
                searchText, status, sort, includeTechStack, excludeTechStack, year, pageNum, perPage
        ))
                .thenReturn(mockedProjects);

        when(projectRepository.getMaxPage(searchText, status, sort, includeTechStack, excludeTechStack, year, perPage))
                .thenReturn(1);

        // When
        ProjectListResponse response = projectPageService.getProjectListResponse(
                searchText, status, sort,  includeTechStack, excludeTechStack, year, pageNum, perPage
        );

        // Then
        assertNotNull(response);
        assertEquals(perPage, response.getPage());
        assertEquals(1, response.getMaxPage());
    }


    private List<Project> createMockedProjects(int count) {
        List<Project> projects = new ArrayList<>();
        ProjectTechStack projectTechStack1 = ProjectTechStack.create(128L, "Java");
        ProjectTechStack projectTechStack3 = ProjectTechStack.create(1L, "Python");

        Attachment attachment = new Attachment();
        attachment.setLocation("/path/to/file.jpg");
        attachment.setName("file.jpg");
        attachment.setSize(2048L);

        User user1 = User.testcreate("testId",
                "hexapro",
                attachment
                );
        ProjectMember projectMember1 = ProjectMember.create("k", user1, AUTHORIZATION_TYPE.Member);

        Attachment attachment2 = new Attachment();
        attachment.setLocation("path/to/thumbnail");
        attachment.setName("thumbnail.jpg");
        attachment.setSize(100L);

        for (int i = 0; i < count; i++) {

            Project project = Project.create((long)i,
                    "title",
                    LocalDateTime.of(2023,3,3,3,3),
                    LocalDateTime.of(2023,4,3,3,3),
                    List.of(projectTechStack1, projectTechStack3), List.of(projectMember1),
                    AUTHORIZATION_TYPE.Member,
                    STATE_TYPE.승인중,
                    "This is a project about web development",
                    attachment2);

            projects.add(project);
        }

        return projects;
    }

    @Test
    void testGetProjectResponse1() {
        // Given
        Long projectId = 1L;
        Project mockedProject = createMockedProject(projectId);

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

        // Mock repository
        when(projectRepository.findByQuery(projectId)).thenReturn(null);

        // When
        ProjectResponse response = projectPageService.getProjectResponse(projectId);

        // Then
        assertNotNull(response);
        assertNull(response.getProjectId());
    }

    private Project createMockedProject(Long projectId) {

        ProjectTechStack projectTechStack1 = ProjectTechStack.create(128L, "Java");
        Attachment attachment1 = new Attachment();
        attachment1.setLocation("/path/to/file.jpg");
        attachment1.setName("file.jpg");
        attachment1.setSize(2048L);

        User user1 = User.testcreate("testId",
                "hexapro",
                attachment1
        );

        ProjectMember projectMember1 = ProjectMember.create("k", user1, AUTHORIZATION_TYPE.Member);

        Attachment attachment = new Attachment();
        attachment.setLocation("path/to/thumbnail");
        attachment.setName("thumbnail.jpg");
        attachment.setSize(100L);

        Project project = Project.create(projectId,
                "title",
                LocalDateTime.of(2023,3,3,3,3),
                LocalDateTime.of(2023,4,3,3,3),
                List.of(projectTechStack1), List.of(projectMember1),
                AUTHORIZATION_TYPE.Member,
                STATE_TYPE.승인중,
                "This is a project about web development",
                attachment);

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

    private List<ProjectTechStack> createMockedProjectTechStacks(int count) {
        List<ProjectTechStack> projectTechStacks = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ProjectTechStack projecttechstack = ProjectTechStack.create((long) (127+i), "내용" + Integer.toString(i));

            projectTechStacks.add(projecttechstack);
        }

        return projectTechStacks;
    }


}

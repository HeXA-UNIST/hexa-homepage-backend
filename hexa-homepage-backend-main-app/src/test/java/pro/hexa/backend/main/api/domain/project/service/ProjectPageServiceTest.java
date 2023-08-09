package pro.hexa.backend.main.api.domain.project.service;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import pro.hexa.backend.domain.project.domain.Project;
import pro.hexa.backend.domain.project_tech_stack.domain.ProjectTechStack;
import pro.hexa.backend.domain.project.repository.ProjectRepository;
import pro.hexa.backend.domain.project_tech_stack.repository.ProjectTechStackRepository;
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
        Integer pageNum = 0;
        int page = 10;

        // Mock repository
        when(projectRepository.findAllByQuery(
                searchText, status, sort, includeTechStack, excludeTechStack, year, pageNum, page
        ))
                .thenReturn(Collections.singletonList(new Project()));

        when(projectRepository.getMaxPage(searchText, status, sort, includeTechStack, excludeTechStack, year, pageNum, page))
                .thenReturn(1);

        // When
        ProjectListResponse response = projectPageService.getProjectListResponse(
                searchText, Collections.emptyList(), sort, Collections.emptyList(),
                Collections.emptyList(), year, pageNum, page
        );

        // Then
        assertNotNull(response);
        assertEquals(page, response.getPage());
        assertEquals(0, response.getMaxPage());
    }

    @Test
    void getProjectResponse() {
        // Given
        Long projectId = 1L;

        // When
        ProjectResponse response = projectPageService.getProjectResponse(projectId);

        // Then
        assertNotNull(response);
    }

    @Test
    void getProjectTechStackResponse() {
        // Mock repository
        when(projectTechStackRepository.findTechStackByQuery())
                .thenReturn(Collections.singletonList(new ProjectTechStack()));

        // When
        ProjectTechStackResponse response = projectPageService.getProjectTechStackResponse();

        // Then
        assertNotNull(response);
        assertFalse(response.getTechStackList().isEmpty());
    }
}
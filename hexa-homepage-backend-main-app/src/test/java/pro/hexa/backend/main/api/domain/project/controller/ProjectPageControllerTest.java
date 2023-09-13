package pro.hexa.backend.main.api.domain.project.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import pro.hexa.backend.domain.project.domain.Project;
import pro.hexa.backend.domain.project.model.STATE_TYPE;
import pro.hexa.backend.domain.project.repository.ProjectRepository;
import pro.hexa.backend.domain.project_member.domain.ProjectMember;
import pro.hexa.backend.domain.project_member.model.AUTHORIZATION_TYPE;
import pro.hexa.backend.domain.project_member.repository.ProjectMemberRepository;
import pro.hexa.backend.domain.project_tech_stack.domain.ProjectTechStack;
import pro.hexa.backend.domain.project_tech_stack.repository.ProjectTechStackRepository;
import pro.hexa.backend.main.api.domain.project.dto.ProjectListResponse;
import pro.hexa.backend.main.api.domain.project.dto.ProjectResponse;
import pro.hexa.backend.main.api.domain.project.dto.ProjectTechStackResponse;

@SpringBootTest
class ProjectPageControllerTest {
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectTechStackRepository projectTechStackRepository;

    @Autowired
    ProjectMemberRepository projectMemberRepository;

    @Autowired
    ProjectPageController projectPageController;

    @Test
    void getProjectListResponse() {
        ProjectTechStack projectTechStack1 = ProjectTechStack.create(128L, "Java");
        ProjectTechStack projectTechStack2 = ProjectTechStack.create(12L, "Spring");
        ProjectTechStack projectTechStack3 = ProjectTechStack.create(1L, "Python");
        ProjectTechStack projectTechStack4 = ProjectTechStack.create(128L, "C++");

        ProjectMember projectMember1 = ProjectMember.create("k");
        ProjectMember projectMember2 = ProjectMember.create("p");
        ProjectMember projectMember3 = ProjectMember.create("s");
        ProjectMember projectMember4 = ProjectMember.create("i");
        ProjectMember projectMember5 = ProjectMember.create("o");

        projectTechStackRepository.save(projectTechStack1);
        projectTechStackRepository.save(projectTechStack2);
        projectTechStackRepository.save(projectTechStack3);
        projectTechStackRepository.save(projectTechStack4);

        projectMemberRepository.save(projectMember1);
        projectMemberRepository.save(projectMember2);
        projectMemberRepository.save(projectMember3);
        projectMemberRepository.save(projectMember4);
        projectMemberRepository.save(projectMember5);

        List<ProjectTechStack> projectTechStacks1= List.of(projectTechStack1, projectTechStack3);
        List<ProjectTechStack> projectTechStacks2 = List.of(projectTechStack2);
        List<ProjectTechStack> projectTechStacks3 = List.of(projectTechStack4,projectTechStack2);
        List<ProjectMember> projectmembers1 = List.of(projectMember1, projectMember2);
        List<ProjectMember> projectmembers2 = List.of(projectMember3);
        List<ProjectMember> projectmembers3 = List.of(projectMember4, projectMember5);



        Project project1 = Project.create(
                        1L,
                        "Web Development",
                        LocalDateTime.of(2023, 1, 1, 0, 0),
                        LocalDateTime.of(2023, 12, 31, 0, 0),
                        projectTechStacks1, // 기술 스택 목록을 여기에 추가해주세요.
                        projectmembers1,
                        AUTHORIZATION_TYPE.Member,
                        STATE_TYPE.승인중,
                "This is a project about web development"
        );

        // 두 번째 프로젝트 예시
        Project project2 = Project.create(
                2L,
                "Mobile App Development",
                LocalDateTime.of(2023, 2, 1, 0, 0),
                LocalDateTime.of(2023, 11, 30, 0, 0),
                projectTechStacks2,
                projectmembers2,
                AUTHORIZATION_TYPE.Pro,
                STATE_TYPE.모집완료,
                "This is a project about mobile app development"
        );

        // 세 번째 프로젝트 예시
        Project project3 = Project.create(
                3L,
                "Machine Learning Research",
                LocalDateTime.of(2023, 3, 1, 0, 0),
                LocalDateTime.of(2023, 10, 31, 0, 0),
                projectTechStacks3, // 기술 스택 목록을 여기에 추가해주세요.
                projectmembers3,
                AUTHORIZATION_TYPE.Admin,
                STATE_TYPE.진행완료,
                "This is a project about ML research"
        );

        projectRepository.save(project1);
        projectRepository.save(project2);
        projectRepository.save(project3);

        ResponseEntity<ProjectListResponse> response = projectPageController.getProjectListResponse(
                "This",
                List.of("승인중"),
                "sort",
                List.of("Spring"),
                List.of("Python"),
                2023,
                1,
                10
        );

        ProjectListResponse dto = response.getBody();
        assertEquals(dto.getProjects().size(), 3);
        assertEquals(dto.getPage(), 10);
        assertEquals(dto.getMaxPage(), 2);

        ResponseEntity<ProjectResponse> response2 = projectPageController.getProjectResponse(127L);
        assertNotNull(response2.getBody().getContent());

        ResponseEntity<ProjectTechStackResponse> response3 = projectPageController.getProjectTechStackResponse();
        ProjectTechStackResponse dto2 = response3.getBody();
        assertEquals(dto2.getTechStackList().size(), 5);
    }





}

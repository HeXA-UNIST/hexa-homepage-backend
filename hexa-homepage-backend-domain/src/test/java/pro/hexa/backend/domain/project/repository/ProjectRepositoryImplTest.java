package pro.hexa.backend.domain.project.repository;

import java.util.stream.Collectors;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.mockito.MockitoAnnotations;

import pro.hexa.backend.domain.project.domain.Project;
import pro.hexa.backend.domain.project.domain.QProject;
import pro.hexa.backend.domain.project.model.STATE_TYPE;
import pro.hexa.backend.domain.project_member.domain.QProjectMember;
import pro.hexa.backend.domain.project_tech_stack.domain.QProjectTechStack;


public class ProjectRepositoryImplTest {

    @Mock
    private JPAQueryFactory queryFactory;

    @Mock
    private JPAQuery<Project> jpaQuery;

    @InjectMocks
    private ProjectRepositoryImpl projectRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllByQueryP() {
        String searchText = "test";
        List<String> status = List.of("승인중");
        String sort = "asc";
        List<String> includeTechStack = List.of("Java", "Spring");
        List<String> excludeTechStack = List.of("Python");
        Integer year = 2023;
        Integer pageNum = 0;
        Integer page = 10;

        // 가상의 프로젝트 목록 생성
        List<Project> mockProjectList = createMockProjectList();

        // QueryDSL에서 사용할 가상의 QProject 객체 생성
        QProject project = QProject.project;
        QProjectMember projectMember = QProjectMember.projectMember;
        QProjectTechStack projectTechStack = QProjectTechStack.projectTechStack;
        List<STATE_TYPE> stats = status.stream().map(STATE_TYPE::valueOf).collect(Collectors.toList());

        // QueryDSL가 findAllByQuery 메서드 호출 시 예상되는 결과를 설정
        when(queryFactory.selectFrom(project)).thenReturn(jpaQuery);
        when(jpaQuery.leftJoin(project.projectTechStacks, projectTechStack)).thenReturn(jpaQuery);
        when(jpaQuery.fetchJoin()).thenReturn(jpaQuery);
        when(jpaQuery.leftJoin(project.members, projectMember)).thenReturn(jpaQuery);
        when(jpaQuery.fetchJoin()).thenReturn(jpaQuery);
        when(jpaQuery.where(project.title.contains(searchText),
                project.state.in(stats),
                projectTechStack.content.in(includeTechStack),
                projectTechStack.content.notIn(excludeTechStack)
                // year는 무엇을 기준으로 할지 제대로 정의 되어있지 않음
        )).thenReturn(jpaQuery);
        when(jpaQuery.orderBy((sort == "asc") ? project.title.asc() : project.title.desc())).thenReturn(jpaQuery);
        when(jpaQuery.offset(pageNum)).thenReturn(jpaQuery);
        when(jpaQuery.limit(page)).thenReturn(jpaQuery);
        when(jpaQuery.fetch()).thenReturn(mockProjectList);

        // findAllByQuery 메서드 호출
        List<Project> result = projectRepository.findAllByQuery(searchText, status, sort, includeTechStack, excludeTechStack, year, pageNum, page);

        // 결과 검증
        assertNotNull(result);
        assertEquals(20, result.size()); // 예상되는 페이지 크기와 일치하는지 확인
    }

    @Test
    void getMaxPageC() {
        // 가상의 데이터 생성
        String searchText = "test";
        List<String> status = List.of("승인중");
        String sort = "asc";
        List<String> includeTechStack = List.of("Java", "Spring");
        List<String> excludeTechStack = List.of("Python");
        Integer year = 2023;
        Integer pageNum = 0;
        int page = 10;

        // 가상의 프로젝트 목록 생성
        List<Project> mockProjectList = createMockProjectList();

        // QueryDSL에서 사용할 가상의 QProject 객체 생성
        QProject project = QProject.project;
        QProjectMember projectMember = QProjectMember.projectMember;
        QProjectTechStack projectTechStack = QProjectTechStack.projectTechStack;
        List<STATE_TYPE> stats = status.stream().map(STATE_TYPE::valueOf).collect(Collectors.toList());

        // QueryDSL가 findAllByQuery 메서드 호출 시 예상되는 결과를 설정
        when(queryFactory.selectFrom(project)).thenReturn(jpaQuery);
        when(jpaQuery.leftJoin(project.projectTechStacks, projectTechStack)).thenReturn(jpaQuery);
        when(jpaQuery.fetchJoin()).thenReturn(jpaQuery);
        when(jpaQuery.leftJoin(project.members, projectMember)).thenReturn(jpaQuery);
        when(jpaQuery.fetchJoin()).thenReturn(jpaQuery);
        when(jpaQuery.where(project.title.contains(searchText),
                project.state.in(stats),
                projectTechStack.content.in(includeTechStack),
                projectTechStack.content.notIn(excludeTechStack)
                // year는 무엇을 기준으로 할지 제대로 정의 되어있지 않음
        )).thenReturn(jpaQuery);
        when(jpaQuery.fetch()).thenReturn(mockProjectList);

        // 결과 검증
        assertEquals(20, projectRepository.getMaxPage(searchText, status, sort, includeTechStack, excludeTechStack, year, pageNum, page));
    }

    @Test
    void findByQueryP() {
        // 가상의 데이터 생성
        Long id=1255L;

        // 가상의 프로젝트 목록 생성
        List<Project> mockProjectList = createMockProjectList();

        // QueryDSL에서 사용할 가상의 QProject 객체 생성
        QProject project = QProject.project;
        QProjectMember projectMember = QProjectMember.projectMember;
        QProjectTechStack projectTechStack = QProjectTechStack.projectTechStack;

        // QueryDSL가 findAllByQuery 메서드 호출 시 예상되는 결과를 설정
        when(queryFactory.selectFrom(project)).thenReturn(jpaQuery);
        when(jpaQuery.leftJoin(project.members, projectMember)).thenReturn(jpaQuery);
        when(jpaQuery.fetchJoin()).thenReturn(jpaQuery);
        when(jpaQuery.leftJoin(project.projectTechStacks, projectTechStack)).thenReturn(jpaQuery);
        when(jpaQuery.fetchJoin()).thenReturn(jpaQuery);
        when(jpaQuery.where(project.id.eq(id))).thenReturn(jpaQuery);
        when(jpaQuery.fetchOne()).thenReturn(mockProjectList.get(0));
        // 결과 검증
        assertEquals(mockProjectList.get(0), projectRepository.findByQuery(1255L));
    }

    // 가상의 프로젝트 목록 생성 (테스트에 사용할 데이터를 가정하여 생성)
    private List<Project> createMockProjectList() {
        List<Project> projects = new ArrayList<>();

        // 가상의 프로젝트 객체들 생성하여 리스트에 추가
        for (int i = 0; i < 20; i++) {
            Project project = new Project();
            project.setId((long) i);
            project.setTitle("Project " + i);
            project.setStartDate(LocalDateTime.of(2023, 7, i + 1, 0, 0));
            project.setEndDate(LocalDateTime.of(2023, 8, i + 1, 0, 0));
            project.setState(STATE_TYPE.승인중);

            projects.add(project);
        }

        return projects;
    }

}
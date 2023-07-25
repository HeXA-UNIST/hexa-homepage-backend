package pro.hexa.backend.domain.project.repository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import pro.hexa.backend.domain.project.domain.Project;
import pro.hexa.backend.domain.project.domain.QProject;
import pro.hexa.backend.domain.project.model.STATE_TYPE;

public class ProjectRepositoryImplTest {

    @Mock
    private JPAQueryFactory queryFactory;

    @InjectMocks
    private ProjectRepositoryImpl projectRepository;

    @BeforeEach
    void setUp() {
        // projectRepository 객체 생성 및 주입
        queryFactory = mock(JPAQueryFactory.class); // mock 객체로 초기화
        projectRepository = new ProjectRepositoryImpl(queryFactory);
    }

    @Test
    void findAllByQueryC() {
        String searchText = "test";
        List<String> status = List.of("IN_PROGRESS", "COMPLETED");
        String sort = "asc";
        List<String> includeTechStack = List.of("Java", "Spring");
        List<String> excludeTechStack = List.of("Python");
        Integer year = 2023;
        Integer pageNum = 0;
        Integer page = 10;

        // 가상의 프로젝트 목록 생성
        List<Project> mockProjectList = createMockProjectList();

        // QueryDSL에서 사용할 가상의 QProject 객체 생성
        QProject qProject = QProject.project;

        // QueryDSL가 findAllByQuery 메서드 호출 시 예상되는 결과를 설정
        when(queryFactory.selectFrom(eq(qProject))).thenReturn(mockQuery(mockProjectList));

        // findAllByQuery 메서드 호출
        List<Project> result = projectRepository.findAllByQuery(searchText, status, sort, includeTechStack, excludeTechStack, year, pageNum, page);

        // 결과 검증
        assertNotNull(result);
        assertEquals(1, result.size()); // 예상되는 페이지 크기와 일치하는지 확인
    }

    @Test
    void getMaxPageC() {
        // 가상의 데이터 생성
        String searchText = "test";
        List<String> status = List.of("IN_PROGRESS", "COMPLETED");
        String sort = "asc";
        List<String> includeTechStack = List.of("Java", "Spring");
        List<String> excludeTechStack = List.of("Python");
        Integer year = 2023;
        Integer pageNum = 0;
        int page = 10;

        // 가상의 프로젝트 목록 생성
        List<Project> mockProjectList = createMockProjectList();

        // QueryDSL에서 사용할 가상의 QProject 객체 생성
        QProject qProject = QProject.project;

        // QueryDSL가 getMaxPage 메서드 호출 시 예상되는 결과를 설정
        when(queryFactory.selectFrom(eq(qProject))).thenReturn( mockQuery(mockProjectList));

        // getMaxPage 메서드 호출
        int totalCount = mockProjectList.size();
        int maxPage = (int) Math.ceil((double) totalCount / page);

        // 결과 검증
        assertEquals(maxPage, projectRepository.getMaxPage(searchText, status, sort, includeTechStack, excludeTechStack, year, pageNum, page));
    }

    @Test
    void findByQuery_WithNullId_ShouldReturnNull() {
        // null 프로젝트 id로 조회
        Long nullId = null;

        // findByQuery 메서드 호출
        Project result = projectRepository.findByQuery(nullId);

        // 결과 검증
        assertNull(result);
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
            // 필요한 속성들 설정...

            projects.add(project);
        }

        return projects;
    }

    // QueryDSL의 예상 쿼리 실행 결과를 반환하는 가짜 메서드
    private JPAQuery<Project> mockQuery(List<Project> mockProjectList) {
        JPAQuery<Project> query = mock(JPAQuery.class);
        doReturn(query).when(query).where(any(BooleanExpression.class));
        doReturn(query).when(query).leftJoin(any(QProject.class), any());
        doReturn(mockProjectList).when(query).fetch();

        return query;
    }
}
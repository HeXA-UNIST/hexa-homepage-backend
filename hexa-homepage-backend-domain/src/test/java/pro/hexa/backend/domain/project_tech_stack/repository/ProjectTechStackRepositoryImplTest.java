package pro.hexa.backend.domain.project_tech_stack.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.querydsl.jpa.impl.JPAQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.querydsl.jpa.impl.JPAQueryFactory;
import pro.hexa.backend.domain.project_tech_stack.domain.ProjectTechStack;
import pro.hexa.backend.domain.project_tech_stack.domain.QProjectTechStack;

public class ProjectTechStackRepositoryImplTest {

    @Mock
    private JPAQueryFactory queryFactory;

    @InjectMocks
    private ProjectTechStackRepositoryImpl projectTechStackRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findTechStackByQuery() {
        // 가상의 테크스택 데이터 생성
        List<ProjectTechStack> mockTechStackList = createMockTechStackList();

        // QueryDSL에서 사용할 가상의 QProjectTechStack 객체 생성
        QProjectTechStack qProjectTechStack = QProjectTechStack.projectTechStack;

        // QueryDSL가 findTechStackByQuery 메서드 호출 시 예상되는 결과를 설정
        when(queryFactory.selectFrom(eq(qProjectTechStack))).thenReturn(mockQuery(mockTechStackList));

        // findTechStackByQuery 메서드 호출
        List<ProjectTechStack> result = projectTechStackRepository.findTechStackByQuery();

        // 결과 검증
        assertEquals(mockTechStackList, result);
    }

    // 가상의 테크스택 데이터 생성 (테스트에 사용할 데이터를 가정하여 생성)
    private List<ProjectTechStack> createMockTechStackList() {
        List<ProjectTechStack> techStacks = new ArrayList<>();

        // 가상의 테크스택 객체들 생성하여 리스트에 추가
        for (int i = 0; i < 5; i++) {
            ProjectTechStack techStack = new ProjectTechStack();
            techStack.setId((long) i);
            techStack.setContent("Tech Stack " + i);
            // 필요한 속성들 설정...

            techStacks.add(techStack);
        }

        return techStacks;
    }

    // QueryDSL의 예상 쿼리 실행 결과를 반환하는 가짜 메서드
    private JPAQuery<ProjectTechStack> mockQuery(List<ProjectTechStack> mockTechStackList) {
        JPAQuery<ProjectTechStack> query = mock(JPAQuery.class);
        doReturn(mockTechStackList).when(query).fetch();

        return query;
    }
}
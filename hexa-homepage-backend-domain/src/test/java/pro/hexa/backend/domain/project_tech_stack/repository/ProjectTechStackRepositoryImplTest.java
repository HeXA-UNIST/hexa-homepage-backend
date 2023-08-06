package pro.hexa.backend.domain.project_tech_stack.repository;


import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pro.hexa.backend.domain.attachment.domain.QAttachment;
import pro.hexa.backend.domain.project_tech_stack.domain.ProjectTechStack;
import pro.hexa.backend.domain.project_tech_stack.domain.QProjectTechStack;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ProjectTechStackRepositoryImplTest {

    @Mock
    private JPAQueryFactory queryFactory;

    @Mock
    private JPAQuery<ProjectTechStack> jpaQuery;

    @InjectMocks
    private ProjectTechStackRepositoryImpl projectTechStackRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void findAllByQueryC() {

        // 가상의 기술스택 목록 생성
        List<ProjectTechStack> mockProjectTechStackList = createMockSeminarList();

        // QueryDSL에서 사용할 가상의 QProjectTechStack 객체 생성
        QProjectTechStack projectTechStack = QProjectTechStack.projectTechStack;
        QAttachment attatchment = QAttachment.attachment;

        // QueryDSL가 findTechStackByQuery 메서드 호출 시 예상되는 결과를 설정
        when(queryFactory.selectFrom(projectTechStack)).thenReturn(jpaQuery);
        when(jpaQuery.fetch()).thenReturn(mockProjectTechStackList);

        // findAllByQuery 메서드 호출
        List<ProjectTechStack> result = projectTechStackRepository.findTechStackByQuery();
        assertNotNull(result);
        assertEquals(1, result.size()); // 예상되는 페이지 크기와 일치하는지 확인
    }

    // 가상의 기술스택 목록 생성 (테스트에 사용할 데이터를 가정하여 생성)
    private List<ProjectTechStack> createMockSeminarList() {
        List<ProjectTechStack> projectTechStacks = new ArrayList<>();

        // 가상의 세미나 객체들 생성하여 리스트에 추가
        ProjectTechStack projectTechStack1 = new ProjectTechStack();
        projectTechStack1.setId(17827L);
        projectTechStacks.add(projectTechStack1);

        return projectTechStacks;
    }
}
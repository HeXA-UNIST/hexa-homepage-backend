package pro.hexa.backend.domain.project.repository;

import java.util.List;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import pro.hexa.backend.domain.project.domain.Project;
import pro.hexa.backend.domain.project.domain.QProject;

@RequiredArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Project> findForProjectListByQuery() {
        QProject project = QProject.project;
        return queryFactory.selectFrom(project)
                .orderBy(project.startDate.desc())
                .limit(3)
                .fetch();
    }

    @Override
    public Project findForProjectByQuery() {
        QProject project = QProject.project;
        return queryFactory.selectFrom(project)
                .fetchOne();    // 이게 맞나요?
    }
}

package pro.hexa.backend.domain.project.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import pro.hexa.backend.domain.project.domain.Project;
import pro.hexa.backend.domain.project.domain.QProject;
import pro.hexa.backend.domain.project_member.domain.QProjectMember;
import pro.hexa.backend.domain.project_tech_stack.domain.QProjectTechStack;

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
    public Project findForProjectByQuery(Long id) {
        if (id == null) {
            return null;
        }

        QProject project = QProject.project;
        QProjectMember projectMember = QProjectMember.projectMember;
        QProjectTechStack projectTechStack = QProjectTechStack.projectTechStack;

        return queryFactory.selectFrom(project)
            .leftJoin(project.members, projectMember).fetchJoin()
            .leftJoin(project.projectTechStacks, projectTechStack).fetchJoin()
            .where(project.id.eq(id))
            .fetchOne();
    }
}

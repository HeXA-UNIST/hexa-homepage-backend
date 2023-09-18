package pro.hexa.backend.domain.project.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import pro.hexa.backend.domain.project.domain.Project;
import pro.hexa.backend.domain.project.domain.QProject;
import pro.hexa.backend.domain.project.model.STATE_TYPE;
import pro.hexa.backend.domain.project_member.domain.QProjectMember;
import pro.hexa.backend.domain.project_tech_stack.domain.QProjectTechStack;

@RequiredArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Project> findAllByQuery(String searchText, List<String> status, String sort, List<String> includeTechStack,
        List<String> excludeTechStack, Integer year, Integer pageNum, Integer perPage) {
        QProject project = QProject.project;
        QProjectMember projectMember = QProjectMember.projectMember;
        QProjectTechStack projectTechStack = QProjectTechStack.projectTechStack;
        List<STATE_TYPE> stats = status.stream().map(STATE_TYPE::valueOf).collect(Collectors.toList());

        return queryFactory.selectFrom(project)
            .leftJoin(project.projectTechStacks, projectTechStack).fetchJoin()
            .leftJoin(project.members, projectMember).fetchJoin()
            .where(project.title.contains(searchText),
                project.state.in(stats),
                projectTechStack.content.in(includeTechStack),
                projectTechStack.content.notIn(excludeTechStack)
            )
            .orderBy((sort == "asc") ? project.title.asc() : project.title.desc())    // 임시로 title로 둠
            .offset((long)pageNum * perPage)
            .limit(perPage)
            .fetch();
    }


    @Override
    public int getMaxPage(String searchText, List<String> status, String sort, List<String> includeTechStack,
                          List<String> excludeTechStack, Integer year, Integer perPage) {
        QProject project = QProject.project;
        QProjectMember projectMember = QProjectMember.projectMember;
        QProjectTechStack projectTechStack = QProjectTechStack.projectTechStack;


        BooleanExpression whereQuery = project.createdAt.isNotNull();

        if (StringUtils.isNotBlank(searchText)) {
            whereQuery = whereQuery.and(project.title.contains(searchText));
        }

        if (status != null && !status.isEmpty()) {
            List<STATE_TYPE> stats = status.stream().map(STATE_TYPE::valueOf).collect(Collectors.toList());
            whereQuery = whereQuery.and(project.state.in(stats));
        }

        if (includeTechStack != null && !includeTechStack.isEmpty()) {
            whereQuery = whereQuery.and(project.projectTechStacks.any().content.in(includeTechStack));
        }

        if (excludeTechStack != null && !excludeTechStack.isEmpty()) {
            whereQuery = whereQuery.and(project.projectTechStacks.any().content.notIn(excludeTechStack));
        }

        if (year != null) {
            LocalDateTime standardDateForYear = LocalDateTime.of(year, 1, 1, 0, 0);
            whereQuery = whereQuery.and(project.updatedAt.between(standardDateForYear, standardDateForYear.plusYears(1)));
        }

        return Math.toIntExact(queryFactory.select(project.id.count().divide(perPage))
                .from(project)
                .leftJoin(project.projectTechStacks, projectTechStack).fetchJoin()
                .leftJoin(project.members, projectMember).fetchJoin()
                .where(whereQuery)
                .fetchFirst());
    }


    @Override
    public Project findByQuery(Long id) {
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
package pro.hexa.backend.domain.project.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import pro.hexa.backend.domain.project.domain.Project;
import pro.hexa.backend.domain.project.domain.QProject;
import pro.hexa.backend.domain.project.model.STATE_TYPE;
import pro.hexa.backend.domain.project_member.domain.QProjectMember;
import pro.hexa.backend.domain.project_tech_stack.domain.QProjectTechStack;

@RequiredArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Project> findForProjectListByQuery(String searchText, List<String> status, String sort, List<String> includeTechStack,
                                                   List<String> excludeTechStack, Integer year, Integer pageNum, Integer page) {
        QProject project = QProject.project;
        QProjectMember projectMember = QProjectMember.projectMember;
        QProjectTechStack projectTechStack = QProjectTechStack.projectTechStack;
        List<STATE_TYPE> stats = status.stream().map(STATE_TYPE::valueOf).collect(Collectors.toList());

        List<Project> content = queryFactory.selectFrom(project)
                .leftJoin(project.projectTechStacks, projectTechStack).fetchJoin()
                .leftJoin(project.members, projectMember).fetchJoin()
                .where(project.title.contains(searchText),
                        project.state.in(stats),
                        projectTechStack.content.in(includeTechStack),
                        projectTechStack.content.notIn(excludeTechStack)
                        // year는 무엇을 기준으로 할지 제대로 정의 되어있지 않음
                )
                .orderBy((sort == "asc") ? project.title.asc():project.title.desc())    // 임시로 title로 둠
                .offset(pageNum)
                .limit(page)
                .fetch();

        int totalsize = queryFactory.selectFrom(project)
                .leftJoin(project.projectTechStacks, projectTechStack).fetchJoin()
                .leftJoin(project.members, projectMember).fetchJoin()
                .where(project.title.contains(searchText),
                        project.state.in(stats),
                        projectTechStack.content.in(includeTechStack),
                        projectTechStack.content.notIn(excludeTechStack)
                        // year는 무엇을 기준으로 할지 제대로 정의 되어있지 않음
                )
                .fetch().size();
        
        return content; // totalsize도 같이 넘겨야됨
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

    @Override
    public List<String> findTechStackByQuery() {
        QProjectTechStack projectTechStack = QProjectTechStack.projectTechStack;
        List<String> techStackList = new ArrayList<String>();
        return techStackList;
    }
}

package pro.hexa.backend.domain.project_tech_stack.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProjectTechStackRepositoryImpl implements ProjectTechStackRepositoryCustom {
    private final JPAQueryFactory queryFactory;
}

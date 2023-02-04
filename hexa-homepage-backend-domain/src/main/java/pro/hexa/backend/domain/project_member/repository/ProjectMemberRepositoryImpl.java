package pro.hexa.backend.domain.project_member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProjectMemberRepositoryImpl implements ProjectMemberRepositoryCustom {
    private final JPAQueryFactory queryFactory;
}

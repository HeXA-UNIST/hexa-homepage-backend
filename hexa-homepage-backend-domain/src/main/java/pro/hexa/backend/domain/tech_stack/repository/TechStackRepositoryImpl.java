package pro.hexa.backend.domain.tech_stack.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TechStackRepositoryImpl implements TechStackRepositoryCustom {

    private final JPAQueryFactory queryFactory;
}

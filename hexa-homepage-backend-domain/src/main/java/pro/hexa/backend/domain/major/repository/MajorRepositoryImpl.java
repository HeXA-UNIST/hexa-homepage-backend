package pro.hexa.backend.domain.major.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MajorRepositoryImpl implements MajorRepositoryCustom {

    private final JPAQueryFactory queryFactory;
}

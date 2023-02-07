package pro.hexa.backend.domain.seminar.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SeminarRepositoryImpl implements SeminarRepositoryCustom {
    private final JPAQueryFactory queryFactory;
}

package pro.hexa.backend.domain.service.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ServiceRepositoryImpl implements ServiceRepositoryCustom {
    private final JPAQueryFactory queryFactory;
}

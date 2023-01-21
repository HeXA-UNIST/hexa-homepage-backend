package pro.hexa.backend.domain.sns.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SNSRepositoryImpl implements SNSRepositoryCustom {

    private final JPAQueryFactory queryFactory;
}

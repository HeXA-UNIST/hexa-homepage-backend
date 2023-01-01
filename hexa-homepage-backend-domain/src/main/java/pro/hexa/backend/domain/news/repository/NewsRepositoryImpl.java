package pro.hexa.backend.domain.news.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NewsRepositoryImpl implements NewsRepositoryCustom {

    private JPAQueryFactory queryFactory;
}

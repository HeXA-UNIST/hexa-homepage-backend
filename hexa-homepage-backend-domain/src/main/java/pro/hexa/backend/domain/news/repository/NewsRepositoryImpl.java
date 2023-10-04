package pro.hexa.backend.domain.news.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import pro.hexa.backend.domain.news.domain.News;
import pro.hexa.backend.domain.news.domain.QNews;

import java.util.List;
import java.util.Optional;



@RequiredArgsConstructor
public class NewsRepositoryImpl implements NewsRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<News> findForMainPageByQuery() {
        QNews news = QNews.news;
        return queryFactory.selectFrom(news)
            .orderBy(news.date.desc())
            .limit(3)
            .fetch();
    }

    @Override
    public List<News> findAllWithPaging(Integer pageNum, Integer perPage) {
        QNews news = QNews.news;

        return queryFactory.selectFrom(news)
                .offset((long) pageNum * perPage)
                .limit(perPage)
                .fetch();
    }

    @Override
    public Optional<News> findNewsByQuery(Long id) {
        if (id == null) {
            return Optional.empty();
        }

        QNews news = QNews.news;

        return Optional.ofNullable(
                queryFactory.selectFrom(news)
                .where(news.id.eq(id))
                .fetchOne()
        );
    }

    @Override
    public int getMaxPage(Integer perPage) {
        QNews news = QNews.news;

        BooleanExpression whereQuery = news.createdAt.isNotNull();


        return Math.toIntExact(queryFactory.select(news.id.count())
                .from(news)
                .where(whereQuery)
                .fetchFirst()) / perPage;
    }
}

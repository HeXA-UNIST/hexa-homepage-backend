package pro.hexa.backend.domain.news.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import pro.hexa.backend.domain.news.domain.News;
import pro.hexa.backend.domain.news.domain.QNews;


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
    public List<News> findAllByQuery(Integer pageNum, Integer perPage) {
        QNews news = QNews.news;

        return queryFactory.selectFrom(news)
                .offset((long) pageNum * perPage)
                .limit(perPage)
                .fetch();
    }

    @Override
    public News findByQuery(Long id) {
        if (id == null) {
            return null;
        }

        QNews news = QNews.news;

        return queryFactory.selectFrom(news)
                .where(news.id.eq(id))
                .fetchOne();
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

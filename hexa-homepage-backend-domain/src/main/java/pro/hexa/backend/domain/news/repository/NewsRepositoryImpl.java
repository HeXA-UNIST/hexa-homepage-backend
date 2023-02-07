package pro.hexa.backend.domain.news.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import pro.hexa.backend.domain.news.domain.News;
import pro.hexa.backend.domain.news.domain.QNews;

@RequiredArgsConstructor
public class NewsRepositoryImpl implements NewsRepositoryCustom {

    private JPAQueryFactory queryFactory;

    @Override
    public List<News> findForMainPageByQuery() {
        QNews news = QNews.news;
        return queryFactory.selectFrom(news)
            .orderBy(news.date.desc())
            .limit(3)
            .fetch();
    }
}

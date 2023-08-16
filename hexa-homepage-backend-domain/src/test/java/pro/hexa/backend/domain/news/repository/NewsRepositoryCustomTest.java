package pro.hexa.backend.domain.news.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pro.hexa.backend.domain.news.domain.News;
import pro.hexa.backend.domain.news.domain.QNews;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@Nested
@DisplayName("")
class NewsRepositoryCustomTest {

    @Mock
    private NewsRepositoryImpl newsRepository;

    private JPAQueryFactory queryFactory;
    private EntityManager entityManager;
    private JPAQuery<News> jpaQuery;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("쿼리 테스트")
    void TestFindForMainPageByQuery() {
        // given
        QNews news = QNews.news;

        String expectedQuery = "select news from News news order by news.date desc limit 3";
        when(queryFactory.selectFrom(news)).thenReturn(jpaQuery);
        when(jpaQuery.orderBy(news.date.desc())).thenReturn(jpaQuery);
        when(jpaQuery.limit(3)).thenReturn(jpaQuery);
        when(jpaQuery.limit(3).toString()).thenReturn(expectedQuery);


        // when
        String testedQuery = queryFactory.selectFrom(news)
                .orderBy(news.date.desc())
                .limit(3)
                .toString();
        // then
        assertEquals(testedQuery, expectedQuery);
    }

    @Test
    @DisplayName("Title 쿼리 테스트")
    void TestFindByTitle() {
        // given
        QNews news = QNews.news;
        String testingTitle = "hello";
        String expectedQuery = "select news from News news where title=" + testingTitle;
        when(queryFactory.selectFrom(news)).thenReturn(jpaQuery);
        when(jpaQuery.where(news.title.eq(testingTitle)).toString()).thenReturn(expectedQuery);

        // when
        String testedQuery = queryFactory.selectFrom(news)
                        .where(news.title.eq(testingTitle)).toString();

        // then
        assertEquals(testedQuery, expectedQuery);

    }
}
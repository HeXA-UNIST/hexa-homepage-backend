package pro.hexa.backend.domain.news.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pro.hexa.backend.domain.news.domain.News;
import pro.hexa.backend.domain.news.model.NEWS_TYPE;
import pro.hexa.backend.domain.user.domain.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;


@Nested
@DisplayName("")
class NewsRepositoryCustomTest {
    @Mock
    private NewsRepository newsRepository;

    @Nested
    @DisplayName("쿼리 테스트")
    class TestFindForMainPageByQuery {
        /*
        task1
         최대 3개의 뉴스를 가져와야 함. => length > 3 => exception
        task2
         가져온 News의 date가 desc인지 확인해야 함.
        task 3
         List에 null이 들어갔다면 exception을 내놓아야 함.
        */
        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);

            newsRepository.deleteAll();
            LocalDate nowDate = LocalDate.now();
            News news1 = News.create(NEWS_TYPE.공지, "HeXA OT", nowDate, "Come to the Club Room");
            News news2 = News.create(NEWS_TYPE.공지, "HeXA Graduation", nowDate.minusMonths(3), "congratulation");
            News news3 = News.create(NEWS_TYPE.공지, "HeXA MT", nowDate.plusDays(3), "Let's Go to the Busan");
            News news4 = News.create(NEWS_TYPE.수상, "HeXA Top Coder contest", nowDate.plusMonths(3), "student A is top coder");
            News news5 = News.create(NEWS_TYPE.공지, "HeXA Meeting", nowDate.plusDays(10), "meeting about project A");
            List<News> newsList = new ArrayList<>(Arrays.asList(news1, news2, news3, news4, news5));
            when(newsRepository.saveAll(anyList())).then(AdditionalAnswers.returnsFirstArg());

            newsRepository.saveAll(newsList);
            when(newsRepository.findAll()).thenReturn(newsList);

            // 만약 findForMainPageByQuery()를 사용한다면 news4, 5, 3 순으로 가지는 list를 가져와야 한다.
            when(newsRepository.findForMainPageByQuery()).thenReturn(new ArrayList<>(Arrays.asList(news4, news5, news3)));

        }

        @Test
        @DisplayName("뉴스 개수 3개 이하인지 확인")
        void checkNumOfNews() {
            // when
            List<News> newsList = newsRepository.findForMainPageByQuery();
            // task 1, then
            assertEquals(true, newsList.size() <= 3);
        }

        @Test
        @DisplayName("Date가 내림차순인지 확인")
        void checkDescent() {
            // when
            List<News> newsList = newsRepository.findForMainPageByQuery();

            // task 2
            List<LocalDate> sortedLocalDateList = newsList.stream()
                    .map(News::getDate)
                    .sorted((date1, date2) -> date2.compareTo(date1))
                    .collect(Collectors.toList());
            // then
            assertEquals(sortedLocalDateList.stream(), newsList.stream().map(News::getDate));
        }

        @Test
        @DisplayName("newsList가 null인지 check")
        void checkNullityOfNewsList() {
            // when
            List<News> newsList = newsRepository.findForMainPageByQuery();

            // task 3, then
            assertNotNull(newsList);
        }


    }

    @Nested
    @DisplayName("Title find query method checking")
    class TestFindByTitle {
        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);

            newsRepository.deleteAll();
            LocalDate nowDate = LocalDate.now();
            News news1 = News.create(NEWS_TYPE.공지, "HeXA OT", nowDate, "Come to the Club Room");
            News news2 = News.create(NEWS_TYPE.공지, "HeXA Graduation", nowDate.minusMonths(3), "congratulation");
            News news3 = News.create(NEWS_TYPE.공지, "HeXA MT", nowDate.plusDays(3), "Let's Go to the Busan");
            News news4 = News.create(NEWS_TYPE.수상, "HeXA Top Coder contest", nowDate.plusMonths(3), "student A is top coder");
            News news5 = News.create(NEWS_TYPE.공지, "HeXA Meeting", nowDate.plusDays(10), "meeting about project A");
            List<News> newsList = new ArrayList<>(Arrays.asList(news1, news2, news3, news4, news5));
            when(newsRepository.saveAll(anyList())).then(AdditionalAnswers.returnsFirstArg());

            newsRepository.saveAll(newsList);

            when(newsRepository.findAll()).thenReturn(newsList);

            when(newsRepository.findByTitle("HeXA OT")).thenReturn(Optional.of(news1));
            when(newsRepository.findByTitle("HeXA Graduation")).thenReturn(Optional.of(news2));
            when(newsRepository.findByTitle("HeXA MT")).thenReturn(Optional.of(news3));
            when(newsRepository.findByTitle("HeXA Top Coder contest")).thenReturn(Optional.of(news4));
            when(newsRepository.findByTitle("HeXA Meeting")).thenReturn(Optional.of(news5));
        }

        @Test
        @DisplayName("Title이 Null인지 check")
        void checkNullityOfTitle(){
            assertFalse(newsRepository.findByTitle("HeXA OT").isEmpty());
            assertFalse(newsRepository.findByTitle("HeXA Graduation").isEmpty());
            assertFalse(newsRepository.findByTitle("HeXA MT").isEmpty());
            assertFalse(newsRepository.findByTitle("HeXA Top Coder contest").isEmpty());
            assertFalse(newsRepository.findByTitle("HeXA Meeting").isEmpty());
        }

        @Test
        @DisplayName("각 Title이 맞는지 확인")
        void checkTitleIsCorrect(){
            LocalDate nowDate = LocalDate.now();
            News news1 = News.create(NEWS_TYPE.공지, "HeXA OT", nowDate, "Come to the Club Room");
            News news2 = News.create(NEWS_TYPE.공지, "HeXA Graduation", nowDate.minusMonths(3), "congratulation");
            News news3 = News.create(NEWS_TYPE.공지, "HeXA MT", nowDate.plusDays(3), "Let's Go to the Busan");
            News news4 = News.create(NEWS_TYPE.수상, "HeXA Top Coder contest", nowDate.plusMonths(3), "student A is top coder");
            News news5 = News.create(NEWS_TYPE.공지, "HeXA Meeting", nowDate.plusDays(10), "meeting about project A");

            // when
            News predictedUser1 = newsRepository.findByTitle("HeXA OT").get();
            News predictedUser2 = newsRepository.findByTitle("HeXA Graduation").get();
            News predictedUser3 = newsRepository.findByTitle("HeXA MT").get();
            News predictedUser4 = newsRepository.findByTitle("HeXA Top Coder contest").get();
            News predictedUser5 = newsRepository.findByTitle("HeXA Meeting").get();

            // then
            assertEquals(predictedUser1, news1);
            assertEquals(predictedUser2, news2);
            assertEquals(predictedUser3, news3);
            assertEquals(predictedUser4, news4);
            assertEquals(predictedUser5, news5);
        }

    }
}
package pro.hexa.backend.domain.news.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pro.hexa.backend.domain.news.domain.News;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@DataJpaTest
class NewsRepositoryCustomTest {
    @Autowired
    NewsRepositoryImpl newsRepository;

    @Test
    void TestFindForMainPageByQuery() {
        /*
        task1
         최대 3개의 뉴스를 가져와야 함. => length > 3 => exception
        task2
         가져온 News의 date가 desc인지 확인해야 함.
        task 3
         List에 null이 들어갔다면 exception을 내놓아야 함.

        */
        List<News> newsList = newsRepository.findForMainPageByQuery();
        // task 1
        assertEquals(true, newsList.size() <= 3);
        // task 2
        List<LocalDate> sortedLocalDateList = newsList.stream()
                .map(News::getDate)
                .sorted((date1, date2) -> date2.compareTo(date1))
                .collect(Collectors.toList());
        assertEquals(sortedLocalDateList.stream(), newsList.stream().map(News::getDate));
        // task 3
        assertNotNull(newsList);
    }
}
package pro.hexa.backend.main.api.domain.news.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pro.hexa.backend.domain.news.domain.News;
import pro.hexa.backend.domain.news.model.NEWS_TYPE;
import pro.hexa.backend.domain.news.repository.NewsRepository;
import pro.hexa.backend.main.api.common.exception.BadRequestException;
import pro.hexa.backend.main.api.domain.news.dto.AdminCreateNewsRequestDto;
import pro.hexa.backend.main.api.domain.news.dto.AdminNewsDto;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class NewsAdminPageServiceTest {
    @Mock
    private NewsRepository newsRepository;


    @InjectMocks
    private NewsAdminPageService newsAdminPageService;


    @Test
    @DisplayName("뉴스의 리스트를 받아오는 메서드")
    void getAdminNewsList() {
        // given
        int pageNum = 1;
        int perPage = 3;

        Mockito.when(newsRepository.findAllWithPaging(pageNum, perPage)).thenReturn(makeNewsListForTest().subList(0, 3));
        Mockito.when(newsRepository.getMaxPage(perPage)).thenReturn(3);

        List<AdminNewsDto> resultList = newsAdminPageService.getAdminNewsList(pageNum, perPage).getList();
        List<AdminNewsDto> resultForTest = makeNewsListForTest().subList(0, 3).stream().map(n -> {
            AdminNewsDto adminNewsDto = new AdminNewsDto();
            adminNewsDto.fromNews(n);
            return adminNewsDto;
        }).collect(Collectors.toList());

        int resultTotalPage = newsAdminPageService.getAdminNewsList(pageNum, perPage).getTotalPage();
        int resultTotalPageForTest = newsRepository.getMaxPage(perPage);
        // when & then
        for(int i = 0; i < resultList.size(); i++){
            AdminNewsDto result = resultList.get(i);
            AdminNewsDto resultOfListForTest = resultForTest.get(i);
            Assertions.assertEquals(result.getNewsId(), resultOfListForTest.getNewsId());
            Assertions.assertEquals(result.getNewsType(), resultOfListForTest.getNewsType());
            Assertions.assertEquals(result.getDate(), resultOfListForTest.getDate());
            Assertions.assertEquals(result.getTitle(), resultOfListForTest.getTitle());
        }
        Assertions.assertEquals(resultTotalPage, resultTotalPageForTest);
    }

    @Test
    void getAdminNewsDetail() {
        // given
        Long newsId = 5L;
        List<News> testList = makeNewsListForTest();
        Mockito.when(newsRepository.findById(newsId)).thenReturn(Optional.of(testList.get(5)));

        String resultTitle = newsAdminPageService.getAdminNewsDetail(newsId).getTitle();
        LocalDate resultDate = newsAdminPageService.getAdminNewsDetail(newsId).getDate();
        String resultContent = newsAdminPageService.getAdminNewsDetail(newsId).getContent();

        String resultTitleForTest = testList.get(5).getTitle();
        LocalDate resultDateForTest = testList.get(5).getDate();
        String resultContentForTest = testList.get(5).getContent();
        // when & then
        Assertions.assertNotEquals(resultTitle, null);
        Assertions.assertNotEquals(resultDate, null);
        Assertions.assertNotEquals(resultContent, null);

        Assertions.assertEquals(resultTitle, resultTitleForTest);
        Assertions.assertEquals(resultContent, resultContentForTest);
        Assertions.assertEquals(resultDate, resultDateForTest);
    }

    @Test
    void adminCreateNews() {
        // given

        Long createdId1 = 10L;
        Long createdId2 = 11L;
        Long createdId3 = 12L;
        Long createdId4 = 13L;
        // case 1
        News invalidNews1 = News.create(createdId1, null, "Award From Big Data Contest", LocalDate.now().plusDays(3),
            "very brilliant HeXA member get the awards");
        AdminCreateNewsRequestDto adminCreateNewsRequestDto1 = new AdminCreateNewsRequestDto(invalidNews1.getNewsType(),
            invalidNews1.getTitle(), invalidNews1.getDate(), invalidNews1.getContent());
        Mockito.when(newsRepository.save(invalidNews1)).thenReturn(invalidNews1);

        // case 2
        News invalidNews2 = News.create(createdId2, NEWS_TYPE.수상, "", LocalDate.now().plusDays(3),
            "very brilliant HeXA member get the awards");
        AdminCreateNewsRequestDto adminCreateNewsRequestDto2 = new AdminCreateNewsRequestDto(invalidNews2.getNewsType(),
            invalidNews2.getTitle(), invalidNews2.getDate(), invalidNews2.getContent());
        Mockito.when(newsRepository.save(invalidNews2)).thenReturn(invalidNews2);

        // case 3
        News invalidNews3 = News.create(createdId3, NEWS_TYPE.수상, "Award From Big Data Contest", null,
            "very brilliant HeXA member get the awards");
        AdminCreateNewsRequestDto adminCreateNewsRequestDto3 = new AdminCreateNewsRequestDto(invalidNews3.getNewsType(),
            invalidNews3.getTitle(), invalidNews3.getDate(), invalidNews3.getContent());
        Mockito.when(newsRepository.save(invalidNews3)).thenReturn(invalidNews3);

        // case 4
        News invalidNews4 = News.create(createdId4, NEWS_TYPE.수상, "Award From Big Data Contest", LocalDate.now().plusDays(3),
            "");
        AdminCreateNewsRequestDto adminCreateNewsRequestDto4 = new AdminCreateNewsRequestDto(invalidNews4.getNewsType(),
            invalidNews4.getTitle(), invalidNews4.getDate(), invalidNews4.getContent());
        Mockito.when(newsRepository.save(invalidNews4)).thenReturn(invalidNews4);
        // when & then
        Assertions.assertThrows(BadRequestException.class, () -> newsAdminPageService.adminCreateNews(adminCreateNewsRequestDto1));
        Assertions.assertThrows(BadRequestException.class, () -> newsAdminPageService.adminCreateNews(adminCreateNewsRequestDto2));
        Assertions.assertThrows(BadRequestException.class, () -> newsAdminPageService.adminCreateNews(adminCreateNewsRequestDto3));
        Assertions.assertThrows(BadRequestException.class, () -> newsAdminPageService.adminCreateNews(adminCreateNewsRequestDto4));
    }


    private List<News> makeNewsListForTest() {
        List<News> newsList = new ArrayList<>();
        newsList.add(News.create(0L, NEWS_TYPE.공지, "graduation", LocalDate.now(), "congratulation"));
        newsList.add(News.create(1L, NEWS_TYPE.공지, "enroll of UNIST", LocalDate.now().minusDays(5), "welcome to UNIST"));
        newsList.add(News.create(2L, NEWS_TYPE.공지, "midterm of data structure", LocalDate.now().minusDays(3), "its so hard"));
        newsList.add(News.create(3L, NEWS_TYPE.공지, "final exam of computer architecture", LocalDate.now().minusDays(1), "please professor MJ"));
        newsList.add(News.create(4L, NEWS_TYPE.공지, "gogi tock tock", LocalDate.now().minusMonths(2), "delicious meat"));
        newsList.add(News.create(5L, NEWS_TYPE.공지, "donut time", LocalDate.now().minusDays(10), "nice to meet you"));
        newsList.add(News.create(6L, NEWS_TYPE.공지, "new server open", LocalDate.now().minusWeeks(2), "please use carefully"));

        return newsList;
    }

}
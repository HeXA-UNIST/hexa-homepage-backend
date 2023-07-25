package pro.hexa.backend.main.api.domain.main.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pro.hexa.backend.domain.news.domain.News;
import pro.hexa.backend.domain.news.repository.NewsRepository;
import pro.hexa.backend.main.api.domain.main.dto.MainPageNewsDto;
import pro.hexa.backend.main.api.domain.main.dto.MainPageResponse;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static pro.hexa.backend.main.api.common.utils.constant.yyyy_MM_dd;

class MainPageServiceTest {
    @Mock
    private NewsRepository newsRepository;

    @InjectMocks
    private MainPageService mainPageService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void TestGetMainPageResponse() {
        MainPageResponse mainPageResponse = mainPageService.getMainPageResponse();
        /*
         * task 1 : mainPageResponse가 null인지 check
         * task 2 : mainPageResponse의 News 들 중에서 id값이 공통인 게 없는지 check
         * task 3 : mainPageResponse의 News 들의 date가 format에 맞는지 check
         *   task 3 - 1 : + date가 null이 아닌지 check
         * task 4 : mainPageResponse의 News 들의 title이 null이 아닌지 check
         * */
        // task1
        assertNotNull(mainPageResponse);
        // task 2
        Stream<Long> distinctIds = mainPageResponse.getNewsList()
                .stream()
                .map(MainPageNewsDto::getNewsId)
                .distinct();
        assertEquals(mainPageResponse.getNewsList()
                .stream()
                .map(MainPageNewsDto::getNewsId), distinctIds);
        // task 3
        final String dateRegex = "(0-9)*.(0-9)*.(0-9)*";
        assertEquals(true, mainPageResponse.getNewsList()
                .stream()
                .map(MainPageNewsDto::getDate)
                .allMatch(str -> str.matches(dateRegex)));
        // task 3 -1
        assertEquals(true, mainPageResponse.getNewsList()
                .stream()
                .map(MainPageNewsDto::getDate)
                .allMatch(Objects::nonNull));

        // task 4
        assertEquals(true, mainPageResponse.getNewsList()
                .stream()
                .map(MainPageNewsDto::getTitle)
                .allMatch(Objects::nonNull));
    }
}
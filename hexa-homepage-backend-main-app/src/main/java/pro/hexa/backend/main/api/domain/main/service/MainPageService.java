package pro.hexa.backend.main.api.domain.main.service;

import static pro.hexa.backend.main.api.common.utils.constant.yyyy_MM_dd;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.hexa.backend.domain.news.domain.News;
import pro.hexa.backend.domain.news.repository.NewsRepository;
import pro.hexa.backend.main.api.domain.main.dto.MainPageNewsDto;
import pro.hexa.backend.main.api.domain.main.dto.MainPageResponse;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MainPageService {

    private final NewsRepository newsRepository;

    public MainPageResponse getMainPageResponse() {
        List<News> news = newsRepository.findForMainPageByQuery();
        List<MainPageNewsDto> newsList = news.stream()
            .map(this::transformNewsToMainPageNewsDto)
            .collect(Collectors.toList());

        return MainPageResponse.builder()
            .newsList(newsList)
            .build();
    }

    private MainPageNewsDto transformNewsToMainPageNewsDto(News news) {
        return MainPageNewsDto.builder()
            .newsId(news.getId())
            .newsType(news.getNewsType())
            .title(news.getTitle())
            .date(news.getDate().format(DateTimeFormatter.ofPattern(yyyy_MM_dd)))
            .build();
    }
}

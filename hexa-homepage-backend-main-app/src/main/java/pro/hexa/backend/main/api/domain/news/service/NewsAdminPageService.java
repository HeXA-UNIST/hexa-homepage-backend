package pro.hexa.backend.main.api.domain.news.service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.hexa.backend.domain.news.domain.News;
import pro.hexa.backend.domain.news.repository.NewsRepository;
import pro.hexa.backend.main.api.domain.news.dto.AdminCreateNewsRequestDto;
import pro.hexa.backend.main.api.domain.news.dto.AdminModifyNewsRequestDto;
import pro.hexa.backend.main.api.domain.news.dto.AdminNewsDetailResponse;
import pro.hexa.backend.main.api.domain.news.dto.AdminNewsListResponse;

import static pro.hexa.backend.main.api.common.utils.constant.yyyy_MM_dd;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NewsAdminPageService {
    private final NewsRepository newsRepository;

    public AdminNewsListResponse getAdminNewsList(
            Integer pageNum, Integer perPage
    ) {
        return null;
    }

    public AdminNewsDetailResponse getAdminNewsDetail(Long newsId) {
        return null;

    }

    public void adminCreateNews(AdminCreateNewsRequestDto adminCreateNewsRequestDto) {
    }

    public void adminModifyNews(AdminModifyNewsRequestDto adminModifyNewsRequestDto) {

    }

    public void adminDeleteNews(Long newsId) {
        Optional<News> news = newsRepository.findById(newsId);
        News findNews = news.orElseThrow();
        newsRepository.delete(findNews);

    }
}

/*
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

*/


package pro.hexa.backend.main.api.domain.news.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.hexa.backend.domain.news.domain.News;
import pro.hexa.backend.domain.news.repository.NewsRepository;
import pro.hexa.backend.main.api.domain.news.dto.NewsDetailResponse;
import pro.hexa.backend.main.api.domain.news.dto.NewsDto;
import pro.hexa.backend.main.api.domain.news.dto.NewsListResponse;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NewsPageService {

    private final NewsRepository newsRepository;
    public NewsListResponse getNewsList(Integer pageNum, Integer perPage) {

        List<News> newsList = newsRepository.findAllWithPaging(pageNum, perPage);
        List<NewsDto> newsDtoList = newsList.stream().map(news -> {
            NewsDto newsDto = new NewsDto();
            newsDto.fromNews(news);
            return newsDto;
        }).collect(Collectors.toList());

        int maxPage = newsRepository.getMaxPage(perPage);

        return new NewsListResponse(maxPage, newsDtoList);

    }

    public NewsDetailResponse getNewsDetail(Long newsId) {
        News news = newsRepository.findById(newsId).orElseThrow();

        return new NewsDetailResponse(
            news.getNewsType(),
            news.getTitle(),
            news.getDate(),
            news.getContent()
        );
    }
}

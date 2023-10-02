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
import pro.hexa.backend.main.api.domain.news.dto.*;

import static pro.hexa.backend.main.api.common.utils.constant.yyyy_MM_dd;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class NewsAdminPageService {
    private final NewsRepository newsRepository;

    public AdminNewsListResponse getAdminNewsList(
            Integer pageNum, Integer perPage
    ) {
        List<News> newsList = newsRepository.findAllByQuery(pageNum, perPage);
        List<AdminNewsDto> adminNewsDtos = newsList.stream()
                .map(news -> {
                    AdminNewsDto adminNewsDto = new AdminNewsDto();
                    adminNewsDto.fromNews(news);
                    return adminNewsDto;
                })
                .collect(Collectors.toList());

        int maxPage = newsRepository.getMaxPage(perPage);

        return AdminNewsListResponse.builder()
                .totalPage(maxPage)
                .list(adminNewsDtos)
                .build();
    }

    public AdminNewsDetailResponse getAdminNewsDetail(Long newsId) {
        AdminNewsDetailResponse adminNewsDetailResponse = new AdminNewsDetailResponse();

        Optional.ofNullable(newsRepository.findByQuery(newsId))
                .ifPresent(adminNewsDetailResponse::fromNews);

        return adminNewsDetailResponse;

    }

    public void adminCreateNews(AdminCreateNewsRequestDto adminCreateNewsRequestDto) {
        News createdNews = News.create(null,
                adminCreateNewsRequestDto.getNewsType(),
                adminCreateNewsRequestDto.getTitle(),
                adminCreateNewsRequestDto.getDate(),
                adminCreateNewsRequestDto.getContent());

        newsRepository.save(createdNews);
    }

    public void adminModifyNews(AdminModifyNewsRequestDto adminModifyNewsRequestDto) {
        News foundNews = Optional.ofNullable(newsRepository.findByQuery(adminModifyNewsRequestDto.getNewsId()))
                            .orElseThrow();

        Optional.ofNullable(adminModifyNewsRequestDto.getNewsType())
                .ifPresent(newsType -> foundNews.setNewsType(newsType));
        Optional.ofNullable(adminModifyNewsRequestDto.getTitle())
                .ifPresent(title -> foundNews.setTitle(title));
        Optional.ofNullable(adminModifyNewsRequestDto.getDate())
                .ifPresent(date -> foundNews.setDate(date));
        Optional.ofNullable(adminModifyNewsRequestDto.getContent())
                .ifPresent(content -> foundNews.setContent(content));

        newsRepository.save(foundNews);
    }

    public void adminDeleteNews(Long newsId) {
        Optional.ofNullable(newsRepository.findByQuery(newsId))
                .ifPresent(newsRepository::delete);
    }
}


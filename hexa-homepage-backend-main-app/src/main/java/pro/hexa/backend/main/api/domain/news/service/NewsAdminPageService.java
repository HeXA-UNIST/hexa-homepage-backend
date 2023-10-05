package pro.hexa.backend.main.api.domain.news.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.hexa.backend.domain.news.domain.News;
import pro.hexa.backend.domain.news.repository.NewsRepository;
import pro.hexa.backend.main.api.common.exception.BadRequestException;
import pro.hexa.backend.main.api.common.exception.BadRequestType;
import pro.hexa.backend.main.api.domain.news.dto.AdminCreateNewsRequestDto;
import pro.hexa.backend.main.api.domain.news.dto.AdminModifyNewsRequestDto;
import pro.hexa.backend.main.api.domain.news.dto.AdminNewsDetailResponse;
import pro.hexa.backend.main.api.domain.news.dto.AdminNewsDto;
import pro.hexa.backend.main.api.domain.news.dto.AdminNewsListResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NewsAdminPageService {
    private final NewsRepository newsRepository;

    public AdminNewsListResponse getAdminNewsList(
            Integer pageNum, Integer perPage
    ) {
        List<News> newsList = newsRepository.findAllWithPaging(pageNum, perPage);
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

        News foundNews = newsRepository.findById(newsId)
                .orElseThrow((() -> new BadRequestException(BadRequestType.NEWS_NOT_FOUND)));
        adminNewsDetailResponse.fromNews(foundNews);

        return adminNewsDetailResponse;
    }

    @Transactional
    public void adminCreateNews(AdminCreateNewsRequestDto adminCreateNewsRequestDto) {
        News createdNews = News.create(null,
                adminCreateNewsRequestDto.getNewsType(),
                adminCreateNewsRequestDto.getTitle(),
                adminCreateNewsRequestDto.getDate(),
                adminCreateNewsRequestDto.getContent());

        newsRepository.save(createdNews);
    }

    @Transactional
    public void adminModifyNews(AdminModifyNewsRequestDto adminModifyNewsRequestDto) {
        Long newsId = adminModifyNewsRequestDto.getNewsId();
        News foundNews = newsRepository.findById(newsId)
                            .orElseThrow((() -> new BadRequestException(BadRequestType.NEWS_NOT_FOUND)));

        Optional.ofNullable(adminModifyNewsRequestDto.getNewsType())
                .ifPresent(newsType -> foundNews.updateNewsType(newsType));
        Optional.ofNullable(adminModifyNewsRequestDto.getTitle())
                .ifPresent(title -> foundNews.updateTitle(title));
        Optional.ofNullable(adminModifyNewsRequestDto.getDate())
                .ifPresent(date -> foundNews.updateDate(date));
        Optional.ofNullable(adminModifyNewsRequestDto.getContent())
                .ifPresent(content -> foundNews.updateContent(content));
    }

    @Transactional
    public void adminDeleteNews(Long newsId) {
        newsRepository.deleteById(newsId);
    }
}


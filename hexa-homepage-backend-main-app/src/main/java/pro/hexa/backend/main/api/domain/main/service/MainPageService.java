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
import pro.hexa.backend.domain.service.repository.ServiceRepository;
import pro.hexa.backend.main.api.domain.main.dto.MainPageNewsDto;
import pro.hexa.backend.main.api.domain.main.dto.MainPageResponse;
import pro.hexa.backend.main.api.domain.main.dto.MainPageServiceDto;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MainPageService {

    private final NewsRepository newsRepository;
    private final ServiceRepository serviceRepository;

    public MainPageResponse getMainPageResponse() {
        List<News> news = newsRepository.findForMainPageByQuery();
        List<MainPageNewsDto> newsList = news.stream()
            .map(this::transformNewsToMainPageNewsDto)
            .collect(Collectors.toList());

        List<pro.hexa.backend.domain.service.domain.Service> services = serviceRepository.findForMainPageByQuery();
        List<MainPageServiceDto> serviceList = services.stream()
            .map(this::transformServiceToMainPageServiceDto)
            .collect(Collectors.toList());

        return MainPageResponse.builder()
            .newsList(newsList)
            .serviceList(serviceList)
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

    private MainPageServiceDto transformServiceToMainPageServiceDto(pro.hexa.backend.domain.service.domain.Service service){
        return MainPageServiceDto.builder()
            .serviceId(service.getId())
            .thumbnail(service.getThumbnail().getId())
            .title(service.getTitle())
            .build();
    }
}

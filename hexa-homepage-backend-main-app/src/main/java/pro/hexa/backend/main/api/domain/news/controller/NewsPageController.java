package pro.hexa.backend.main.api.domain.news.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.hexa.backend.main.api.domain.news.dto.NewsDetailResponse;
import pro.hexa.backend.main.api.domain.news.dto.NewsListResponse;
import pro.hexa.backend.main.api.domain.news.service.NewsPageService;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsPageController {

    private final NewsPageService newsPageService;

    @GetMapping("/list")
    public ResponseEntity<NewsListResponse> getNewsList(
        @RequestParam(required = false, defaultValue = "0") Integer pageNum,
        @RequestParam(required = false, defaultValue = "20")Integer perPage
    ){
        return ResponseEntity.ok(newsPageService.getNewsList(pageNum, perPage));
    }

    @GetMapping("/detail")
    public ResponseEntity<NewsDetailResponse> getNewsDetail(Long newsId){
        return ResponseEntity.ok(newsPageService.getNewsDetail(newsId));
    }
}

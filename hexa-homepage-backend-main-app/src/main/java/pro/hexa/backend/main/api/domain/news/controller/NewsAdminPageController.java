package pro.hexa.backend.main.api.domain.news.controller;

import io.swagger.v3.oas.annotations.Operation;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.hexa.backend.main.api.domain.news.dto.AdminCreateNewsRequestDto;
import pro.hexa.backend.main.api.domain.news.dto.AdminModifyNewsRequestDto;
import pro.hexa.backend.main.api.domain.news.dto.AdminNewsDetailResponse;
import pro.hexa.backend.main.api.domain.news.dto.AdminNewsListResponse;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class NewsAdminPageController {

    @Operation(description = "뉴스 리스트 조회")
    @GetMapping("/newsList")
    public ResponseEntity<AdminNewsListResponse> getAdminNewsList(
        @RequestParam(required = false) Integer pageNum,
        @RequestParam(required = false) @Valid @Min(value = 1) Integer perPage
    ) {
    }

    @Operation(description = "뉴스 수정 창에서 보여줄 정보 조회")
    @GetMapping("/newsDetail")
    public ResponseEntity<AdminNewsDetailResponse> getAdminNewsDetail(
        @RequestParam() Long newsId
    ) {
    }

    @Operation(description = "뉴스 생성 요청")
    @PostMapping("/createNews")
    public ResponseEntity<Void> adminCreateNews(@RequestBody AdminCreateNewsRequestDto adminCreateNewsRequestDto) {
    }

    @Operation(description = "뉴스 수정 요청")
    @PostMapping("/modifyNews")
    public ResponseEntity<Void> adminModifyNews(@RequestBody AdminModifyNewsRequestDto adminModifyNewsRequestDto) {
    }

    @Operation(description = "뉴스 삭제")
    @DeleteMapping("/deleteNews")
    public ResponseEntity<Void> adminDeleteNews(
        @RequestParam() Long newsId
    ) {
    }
}

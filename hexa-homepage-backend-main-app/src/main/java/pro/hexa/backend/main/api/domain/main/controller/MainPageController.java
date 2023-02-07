package pro.hexa.backend.main.api.domain.main.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.hexa.backend.main.api.domain.main.dto.MainPageResponse;
import pro.hexa.backend.main.api.domain.main.service.MainPageService;


@RestController
@RequestMapping("/mainpage")
@RequiredArgsConstructor
public class MainPageController {

    private final MainPageService mainPageService;

    @Operation(description = "main page 조회")
    @GetMapping("/data")
    public ResponseEntity<MainPageResponse> getMainPageResponse() {
        return new ResponseEntity<>(mainPageService.getMainPageResponse(), HttpStatus.OK);
    }
}

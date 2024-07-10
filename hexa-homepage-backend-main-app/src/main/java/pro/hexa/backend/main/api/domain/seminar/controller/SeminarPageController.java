package pro.hexa.backend.main.api.domain.seminar.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.hexa.backend.main.api.domain.seminar.dto.SeminarDetailResponse;
import pro.hexa.backend.main.api.domain.seminar.dto.SeminarListResponse;
import pro.hexa.backend.main.api.domain.seminar.service.SeminarPageService;

@RestController
@RequestMapping("/seminar")
@RequiredArgsConstructor
public class SeminarPageController {

    private final SeminarPageService seminarPageService;

    @Operation(description = "세미나 리스트 조회")
    @GetMapping("/list")
    public ResponseEntity<SeminarListResponse> getSeminarListResponse(
        @RequestParam(required = false, defaultValue = "") String searchText,
        @RequestParam(required = false) Integer year,
        @RequestParam(required = false, defaultValue = "0") Integer pageNum,
        @RequestParam(required = false, defaultValue = "20") Integer perPage
    ) {
        return new ResponseEntity<>(seminarPageService.getSeminarListResponse(searchText, year, pageNum, perPage), HttpStatus.OK);
    }

    @GetMapping("/detail")
    public ResponseEntity<SeminarDetailResponse> getSetminalDetail(@RequestParam Long seminarId){
        return ResponseEntity.ok(seminarPageService.getSeminarDetail(seminarId));
    }
}

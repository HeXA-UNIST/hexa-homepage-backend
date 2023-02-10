package pro.hexa.backend.main.api.domain.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.hexa.backend.main.api.domain.project.dto.ProjectListResponse;
import pro.hexa.backend.main.api.domain.project.dto.ProjectResponse;
import pro.hexa.backend.main.api.domain.project.service.ProjectPageService;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectPageController {

    private final ProjectPageService projectPageService;

    @Operation(description = "project 리스트를 조회")
    @GetMapping("/query")
    public ResponseEntity<ProjectListResponse> getProjectListResponse(@RequestParam(required = false, defaultValue = "") String searchText,
                                                                      @RequestParam(required = false) String status,
                                                                      @RequestParam(required = false) String sort,
                                                                      @RequestParam(required = false) String includeTechStack,
                                                                      @RequestParam(required = false) String excludeTechStack,
                                                                      @RequestParam(required = false) Integer year,
                                                                      @RequestParam(required = false) Integer pageNum,
                                                                      @RequestParam(required = false) Integer page) {
        return new ResponseEntity<>(projectPageService.getProjectListResponse(), HttpStatus.OK);
    }

    @Operation(description = "project를 조회")
    @GetMapping()
    public ResponseEntity<ProjectResponse> getProjectResponse(@RequestParam Long projectId) {
        return new ResponseEntity<>(projectPageService.getProjectResponse(projectId), HttpStatus.OK);
    }
}

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
import pro.hexa.backend.main.api.domain.project.dto.ProjectTechStackResponse;
import pro.hexa.backend.main.api.domain.project.service.ProjectPageService;
import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectPageController {

    private final ProjectPageService projectPageService;

    @Operation(description = "project 리스트를 조회")
    @GetMapping("/query")
    public ResponseEntity<ProjectListResponse> getProjectListResponse(@RequestParam(required = false, defaultValue = "") String searchText,
                                                                      @RequestParam(required = false) List<String> status,
                                                                      @RequestParam(required = false) String sort,
                                                                      @RequestParam(required = false) List<String> includeTechStack,
                                                                      @RequestParam(required = false) List<String> excludeTechStack,
                                                                      @RequestParam(required = false) Integer year,
                                                                      @RequestParam(required = false) Integer pageNum,
                                                                      @RequestParam(required = false) Integer page) {
        return new ResponseEntity<>(projectPageService.getProjectListResponse(searchText, status, sort,
                includeTechStack, excludeTechStack, year, pageNum, page), HttpStatus.OK);
    }

    @Operation(description = "project를 조회")
    @GetMapping()
    public ResponseEntity<ProjectResponse> getProjectResponse(@RequestParam Long projectId) {
        return new ResponseEntity<>(projectPageService.getProjectResponse(projectId), HttpStatus.OK);
    }

    @Operation(description = "techStackList를 가져오는 API ")
    @GetMapping("/techStackList")
    public ResponseEntity<ProjectTechStackResponse> getProjectTechStackResponse() {
        return new ResponseEntity<>(projectPageService.getProjectTechStackResponse(), HttpStatus.OK);
    }
}

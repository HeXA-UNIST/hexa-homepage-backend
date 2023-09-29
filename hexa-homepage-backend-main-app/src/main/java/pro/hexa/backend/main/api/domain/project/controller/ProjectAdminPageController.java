package pro.hexa.backend.main.api.domain.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.hexa.backend.main.api.domain.project.dto.AdminCreateProjectRequestDto;
import pro.hexa.backend.main.api.domain.project.dto.AdminModifyProjectRequestDto;
import pro.hexa.backend.main.api.domain.project.dto.AdminProjectDetailResponse;
import pro.hexa.backend.main.api.domain.project.dto.AdminProjectListResponse;
import pro.hexa.backend.main.api.domain.project.service.ProjectPageService;

@RestController
@RequestMapping("/admin/project")
@RequiredArgsConstructor
public class ProjectAdminPageController {

    ProjectPageService projectPageService;


    @Operation(description = "프로젝트 리스트 조회")
    @GetMapping("/list")
    public ResponseEntity<AdminProjectListResponse> getAdminProjectList(
        @RequestParam(required = false) Integer pageNum,
        @RequestParam(required = false) @Valid @Min(value = 1) Integer perPage
    ) {

        return ResponseEntity.ok(projectPageService.getAdminProjectList(pageNum, perPage));
    }

    @Operation(description = "프로젝트 수정 창에서 보여줄 정보 조회")
    @GetMapping("/detail")
    public ResponseEntity<AdminProjectDetailResponse> getAdminProjectDetail(
        @RequestParam() Long projectId
    ) {

        return ResponseEntity.ok(projectPageService.getAdminProjectDetail(projectId));
    }

    @Operation(description = "프로젝트 생성 요청")
    @PostMapping("/create")
    public ResponseEntity<Void> adminCreateProject(@RequestBody AdminCreateProjectRequestDto adminCreateProjectRequestDto) {
        projectPageService.adminCreateProject(adminCreateProjectRequestDto);
        return ResponseEntity.ok(null);
    }

    @Operation(description = "프로젝트 수정 요청")
    @PostMapping("/modify")
    public ResponseEntity<Void> adminModifyProject(@RequestBody AdminModifyProjectRequestDto adminModifyProjectRequestDto) {
        projectPageService.adminModifyProject(adminModifyProjectRequestDto);
        return ResponseEntity.ok(null);
    }

    @Operation(description = "프로젝트 삭제")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> adminDeleteProject(
        @RequestParam() Long projectId
    ) {
        projectPageService.adminDeleteProject(projectId);
        return ResponseEntity.ok(null);
    }


}

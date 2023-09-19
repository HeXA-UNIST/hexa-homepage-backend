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

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ProjectAdminPageController {


    @Operation(description = "프로젝트 리스트 조회")
    @GetMapping("/projectList")
    public ResponseEntity<AdminProjectListResponse> getAdminProjectList(
        @RequestParam(required = false) Integer pageNum,
        @RequestParam(required = false) @Valid @Min(value = 1) Integer perPage
    ) {

    }

    @Operation(description = "프로젝트 수정 창에서 보여줄 정보 조회")
    @GetMapping("/projectDetail")
    public ResponseEntity<AdminProjectDetailResponse> getAdminProjectDetail(
        @RequestParam() Long projectId
    ) {

    }

    @Operation(description = "프로젝트 생성 요청")
    @PostMapping("/createProject")
    public ResponseEntity<Void> adminCreateProject(@RequestBody AdminCreateProjectRequestDto adminCreateProjectRequestDto) {

    }

    @Operation(description = "프로젝트 수정 요청")
    @PostMapping("/modifyProject")
    public ResponseEntity<Void> adminModifyProject(@RequestBody AdminModifyProjectRequestDto adminModifyProjectRequestDto) {

    }

    @Operation(description = "프로젝트 삭제")
    @DeleteMapping("/deleteProject")
    public ResponseEntity<Void> adminDeleteProject(
        @RequestParam() Long projectId
    ) {
    }


}

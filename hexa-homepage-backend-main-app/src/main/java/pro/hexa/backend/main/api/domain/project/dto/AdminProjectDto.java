package pro.hexa.backend.main.api.domain.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminProjectDto {

    @Schema(description = "프로젝트 id")
    private Long projectId;

    @Schema(description = "이미지 파일 id")
    private Long thumbnail;

    @Schema(description = "프로젝트 제목")
    private String title;

    @Schema(description = "상태")
    private String state;
}

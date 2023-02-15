package pro.hexa.backend.main.api.domain.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectTechStackResponse {

    @Schema(description = "테크스텍 목록")
    private List<String> techStackList;
}

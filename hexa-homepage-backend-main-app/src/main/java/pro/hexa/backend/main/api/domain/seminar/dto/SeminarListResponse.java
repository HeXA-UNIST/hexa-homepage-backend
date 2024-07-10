package pro.hexa.backend.main.api.domain.seminar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SeminarListResponse {


    private Integer totalPage;

    @Schema(description = "세미나 목록")
    private List<SeminarDto> seminars;
}
package pro.hexa.backend.main.api.domain.main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MainPageResponse {

    @Schema(description = "뉴스 목록")
    private List<MainPageNewsDto> newsList;

    @Schema(description = "서비스 목록")
    private List<MainPageServiceDto> serviceList;
}

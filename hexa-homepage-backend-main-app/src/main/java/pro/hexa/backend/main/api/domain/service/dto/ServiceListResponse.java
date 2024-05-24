package pro.hexa.backend.main.api.domain.service.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ServiceListResponse {
    private List<ServiceDto> list;
}

package pro.hexa.backend.main.api.domain.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ServiceDetailResponse {

    private String title;
    private String content;
    private String description;
    private Long thumbnail;
    private String siteLink;
    private String githubLink;
}

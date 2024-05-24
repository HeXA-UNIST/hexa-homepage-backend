package pro.hexa.backend.main.api.domain.service.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import pro.hexa.backend.domain.service.domain.Service;
import pro.hexa.backend.domain.service.repository.ServiceRepository;
import pro.hexa.backend.main.api.domain.service.dto.ServiceDetailResponse;
import pro.hexa.backend.main.api.domain.service.dto.ServiceDto;
import pro.hexa.backend.main.api.domain.service.dto.ServiceListResponse;

@Slf4j
@org.springframework.stereotype.Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ServicePageService {

    private final ServiceRepository serviceRepository;

    public ServiceListResponse getServiceList() {
        List<Service> serviceList = serviceRepository.getAll();
        List<ServiceDto> serviceDtoList = serviceList.stream()
            .map(service -> new ServiceDto(
                    service.getId(),
                    service.getThumbnail().getId(),
                    service.getTitle(),
                    service.getDescription()
                )
            ).collect(Collectors.toList());

        return ServiceListResponse.builder()
            .list(serviceDtoList)
            .build();
    }

    public ServiceDetailResponse getServiceDetail(Long serviceId) {
        Service service = serviceRepository.findById(serviceId).orElseThrow();

        return new ServiceDetailResponse(
            service.getTitle(),
            service.getContent(),
            service.getDescription(),
            service.getThumbnail().getId(),
            service.getSiteLink(),
            service.getGithubLink()
        );
    }
}

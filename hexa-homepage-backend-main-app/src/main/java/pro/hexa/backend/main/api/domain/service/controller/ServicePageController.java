package pro.hexa.backend.main.api.domain.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.hexa.backend.main.api.domain.service.dto.ServiceDetailResponse;
import pro.hexa.backend.main.api.domain.service.dto.ServiceListResponse;
import pro.hexa.backend.main.api.domain.service.service.ServicePageService;

@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
public class ServicePageController {

    private final ServicePageService servicePageService;

    @GetMapping("/list")
    public ResponseEntity<ServiceListResponse> getServiceList(){
        return ResponseEntity.ok(servicePageService.getServiceList());
    }

    @GetMapping("/detail")
    public ResponseEntity<ServiceDetailResponse> getServiceDetail(@RequestParam Long serviceId){
        return ResponseEntity.ok(servicePageService.getServiceDetail(serviceId));
    }
}

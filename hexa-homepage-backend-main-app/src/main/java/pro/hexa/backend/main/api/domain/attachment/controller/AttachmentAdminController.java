package pro.hexa.backend.main.api.domain.attachment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pro.hexa.backend.main.api.domain.attachment.dto.UploadAttachmentResponseDto;
import pro.hexa.backend.main.api.domain.attachment.service.AttachmentAdminService;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AttachmentAdminController {

    private final AttachmentAdminService attachmentAdminService;

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadAttachmentResponseDto> uploadAttachment(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(attachmentAdminService.uploadAttachment(file));
    }

}

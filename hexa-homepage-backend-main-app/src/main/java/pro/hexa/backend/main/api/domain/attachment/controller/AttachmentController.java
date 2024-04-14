package pro.hexa.backend.main.api.domain.attachment.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.hexa.backend.domain.attachment.domain.Attachment;
import pro.hexa.backend.main.api.domain.attachment.service.AttachmentService;

@RestController
@RequestMapping("/attachment")
@RequiredArgsConstructor
public class AttachmentController {
    private final AttachmentService attachmentService;
    @GetMapping("")
    public ResponseEntity<InputStreamResource> getAttachment(@RequestParam Long attachmentId) throws FileNotFoundException {
        Attachment attachment = attachmentService.getAttachment(attachmentId);

        MediaType mediaType = attachmentService.getMediaTypeOfAttachment(attachment);

        FileInputStream inputStream = attachmentService.getFileInputStreamFromAttachment(attachment);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + attachment.getName());
        headers.setContentType(mediaType);

        return ResponseEntity.ok()
            .headers(headers)
            .body(new InputStreamResource(inputStream));
    }

}

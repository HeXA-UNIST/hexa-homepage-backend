package pro.hexa.backend.main.api.domain.attachment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UploadAttachmentResponseDto {

    private Long fileId;

    private String fileName;

    private Long fileSize;
}

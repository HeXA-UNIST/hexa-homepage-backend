package pro.hexa.backend.main.api.domain.attachment.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.hexa.backend.domain.attachment.domain.Attachment;
import pro.hexa.backend.domain.attachment.repository.AttachmentRepository;
import pro.hexa.backend.main.api.common.exception.BadRequestException;
import pro.hexa.backend.main.api.common.exception.BadRequestType;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AttachmentService {
    private final AttachmentRepository attachmentRepository;

    public Attachment getAttachment(Long attachmentId){
        return attachmentRepository.findById(attachmentId)
            .orElseThrow(() -> new BadRequestException(BadRequestType.ATTACHMENT_NOT_EXIST));
    }

    public MediaType getMediaTypeOfAttachment(Attachment attachment) {
        String fileName = attachment.getName();

        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
        if(fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg")) {
            mediaType = MediaType.IMAGE_JPEG;
        } else if (fileName.toLowerCase().endsWith(".png")) {
            mediaType = MediaType.IMAGE_PNG;
        } else if (fileName.toLowerCase().endsWith(".gif")) {
            mediaType = MediaType.IMAGE_GIF;
        }

        return mediaType;
    }

    public FileInputStream getFileInputStreamFromAttachment(Attachment attachment) throws FileNotFoundException {
        String fileName = attachment.getName();
        String location = attachment.getLocation();
        File file = new File(location, fileName);
        if (!file.exists()){
            throw new BadRequestException(BadRequestType.ATTACHMENT_NOT_EXIST);
        }

        return new FileInputStream(file);
    }
}

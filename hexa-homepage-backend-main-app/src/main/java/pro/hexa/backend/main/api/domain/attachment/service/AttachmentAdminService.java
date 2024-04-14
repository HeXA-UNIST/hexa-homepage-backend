package pro.hexa.backend.main.api.domain.attachment.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pro.hexa.backend.domain.attachment.domain.Attachment;
import pro.hexa.backend.domain.attachment.repository.AttachmentRepository;
import pro.hexa.backend.main.api.common.exception.BadRequestException;
import pro.hexa.backend.main.api.common.exception.BadRequestType;
import pro.hexa.backend.main.api.common.exception.StorageException;
import pro.hexa.backend.main.api.common.exception.StorageExceptionType;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AttachmentAdminService {

    private final AttachmentRepository attachmentRepository;
    @Value(value = "${file.uploadPath}")
    private String uploadPath;

    @Transactional
    public void uploadAttachment(MultipartFile file) {
        if(file.isEmpty()){
            throw new BadRequestException(BadRequestType.FILE_UPLOAD_FAIL);
        }
        Attachment attachment = new Attachment();
        attachmentRepository.saveAndFlush(attachment);

        String fileName = file.getOriginalFilename();
        Path destinationPath = Path.of(uploadPath, attachment.getId().toString()).normalize().toAbsolutePath();
        long size = file.getSize();

        Path destinationFile = destinationPath.resolve(Path.of(fileName));
        if (!destinationFile.getParent().equals(destinationPath.toAbsolutePath())) {
            // This is a security check
            throw new StorageException(StorageExceptionType.ILLIGAL_PATH);
        }
        try (InputStream inputStream = file.getInputStream()) {
            Files.createDirectories(destinationPath);
            Files.copy(inputStream, destinationFile,
                StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        attachment.setName(fileName);
        attachment.setLocation(destinationPath.toString());
        attachment.setSize(size);
    }
}

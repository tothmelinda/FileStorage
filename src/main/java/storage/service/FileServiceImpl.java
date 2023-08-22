package storage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import storage.entity.File;
import storage.exceptions.FileStorageException;
import storage.repository.FileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Value("${file.upload.path}")
    private String uploadPath;

    @Override
    public File storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = extractFileExtension(fileName);
        String fileType = determineFileType(fileExtension);
        Path uploadDir = Paths.get(uploadPath).toAbsolutePath().normalize();
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        Path targetLocation = uploadDir.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        File fileEntity = new File();
        fileEntity.setName(fileName);
        fileEntity.setPathToFile(targetLocation.toString());
        fileEntity.setFileExtension(fileExtension);
        fileEntity.setType(fileType);

        return fileRepository.save(fileEntity);
    }

    @Override
    public List<File> getAllFiles( String type, String page) {
        return fileRepository.findAll();
    }

    @Override
    public File getFileById(Long fileId) {
        return fileRepository.findById(fileId).orElse(null);
    }

    @Override
    public String getFilePathById(Long fileId){
       File selectedFile = fileRepository.findById(fileId).orElse(null);
        if (selectedFile != null) {
            return selectedFile.getPathToFile();
        }
        throw new FileStorageException("The file does not exists");
    }

    @Override
    public List<File> getFilesByType(String type) {
        return fileRepository.findByType(type);
    }

    @Override
    public void deleteFile(Long fileId) throws IOException {
        File fileEntity = fileRepository.findById(fileId).orElse(null);
        if (fileEntity != null) {
            Path file = Paths.get(fileEntity.getPathToFile());
            Files.deleteIfExists(file);
            fileRepository.delete(fileEntity);
        }
    }

    private String extractFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }

    private String determineFileType(String fileExtension) {
        //TODO implement different pages for audio, archive etc..
        return switch (fileExtension.toLowerCase()) {
            // Movie Extensions
            case "mp4", "avi", "mkv", "mov", "wmv", "flv" -> "movie";

            // Picture Extensions
            case "jpg", "jpeg", "png", "gif", "bmp", "svg" -> "picture";

            // Audio Extensions
            case "mp3", "wav", "ogg", "flac", "aac" -> "movie";

            // Document Extensions
            case "pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "txt" -> "file";

            // Archive Extensions
            case "zip", "rar", "tar", "gz", "7z" -> "file";

            // Default: Other Files
            default -> "file";
        };
    }

}

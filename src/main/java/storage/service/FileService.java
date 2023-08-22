package storage.service;

import org.springframework.web.multipart.MultipartFile;
import storage.entity.File;

import java.io.IOException;
import java.util.List;

public interface FileService {

    File storeFile(MultipartFile file) throws IOException;

    List<File> getAllFiles(String type, String page);

    List<File> getFilesByType(String fileExtension);

    String getFilePathById(Long fileId);

    File getFileById(Long fileId);

    void deleteFile(Long fileId) throws IOException;

}

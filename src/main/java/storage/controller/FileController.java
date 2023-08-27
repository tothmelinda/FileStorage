package storage.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import storage.entity.File;
import storage.service.FileService;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public ResponseEntity<File> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            File savedFile = fileService.storeFile(file);
            return ResponseEntity.ok(savedFile);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<File>> getAllFiles(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String page
    ) {
        List<File> files;

        if (type != null && !type.isEmpty()) {
            files = fileService.getFilesByType(type);
        } else {
            files = fileService.getAllFiles(type, page);
        }

        return ResponseEntity.ok(files);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) throws IOException {
        File fileEntity = fileService.getFileById(fileId);
        if (fileEntity != null) {
            Path file = Paths.get(fileEntity.getPathToFile());
            Resource resource = new FileSystemResource(file.toFile());

            if (resource.exists() && resource.isReadable()) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentDisposition(ContentDisposition.inline().filename(fileEntity.getName()).build());
                headers.setContentType(MediaType.IMAGE_JPEG); // Adjust the content type accordingly
                headers.setCacheControl("max-age=3600"); // Cache for 1 hour (adjust as needed)

                return ResponseEntity.ok()
                        .headers(headers)
                        .contentType(MediaType.IMAGE_JPEG) // Adjust the content type accordingly
                        .body(resource);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/view/{fileId}")
    public ResponseEntity<Resource> viewFile(@PathVariable Long fileId) throws IOException {
        String filePath = fileService.getFilePathById(fileId);
        if (filePath != null) {
            Path file = Paths.get(filePath);
            Resource resource = new FileSystemResource(file.toFile());

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .headers(headers)
                        .body(resource);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{fileId}/pathToFile")
    public ResponseEntity<String> getFilePathById(@PathVariable Long fileId) {
        String filePath = fileService.getFilePathById(fileId);
        if (filePath != null) {
            return ResponseEntity.ok(filePath);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<?> deleteFile(@PathVariable Long fileId) {
        try {
            fileService.deleteFile(fileId);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

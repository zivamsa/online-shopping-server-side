package OnlineShopping.services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    @Value("${upload.directory}")
    private String uploadDirectory;

    public boolean storeFile(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        return storeFile(file, filename);
    }

    public boolean storeFile(MultipartFile file, String filename) {
        Path filePath = Paths.get(uploadDirectory).resolve(filename);

        try {
            Files.createDirectories(filePath.getParent());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
}

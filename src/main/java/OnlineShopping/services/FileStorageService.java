package OnlineShopping.services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    @Value("${upload.directory}")
    private String uploadDirectory;

    public boolean uploadProduct(MultipartFile image, Long productId) {
        if (!isImage(image)) {
            throw new Error("Product preview must be an image!");
        }
        String extension = getFileExtension(image);
        if (extension == null) {
            return false;
        }

        String filename = String.format("%s.%s", productId.toString(), extension);

        return storeFile(image, String.format("product/%s", filename));
    }

    private boolean storeFile(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        return storeFile(file, filename);
    }

    private boolean storeFile(MultipartFile file, String filename) {
        Path filePath = Paths.get(uploadDirectory).resolve(filename);

        try {
            Files.createDirectories(filePath.getParent());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    private boolean isImage(MultipartFile file) {
        try {
            InputStream stream = file.getInputStream();
            return ImageIO.read(stream) != null;
        } catch (IOException ex) {
            return false;
        }
    }

    private String getFileExtension(MultipartFile file) {
        var fileParts = file.getOriginalFilename().split("\\.");
        if (fileParts.length == 0) return null;
        return fileParts[fileParts.length - 1];
    }
}

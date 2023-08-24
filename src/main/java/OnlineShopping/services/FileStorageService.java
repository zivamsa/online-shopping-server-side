package OnlineShopping.services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

@Service
public class FileStorageService {

    @Value("${upload.directory}")
    private String uploadDirectory;

    public String uploadProduct(MultipartFile image, Long productId) {
        if (!isImage(image)) {
            throw new Error("Product preview must be an image!");
        }
        String extension = getFileExtension(image);
        if (extension == null) {
            throw new Error("Product image with no file extension");
        }

        removePreviousProductImage(productId);
        String filename = String.format("%s.%s", productId.toString(), extension);

        String uploadedPath = storeFile(image, String.format("product/%s", filename));
        if (uploadedPath == null) {
            throw new Error("Error while saving image");
        }


        String parsedPath = uploadedPath
                .replace('\\', '/')
                .replaceFirst(uploadDirectory, "image"); // exposed resource;
        String host = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return String.format("%s/%s", host, parsedPath);
    }

    private String storeFile(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        return storeFile(file, filename);
    }

    private String storeFile(MultipartFile file, String filename) {
        Path filePath = Paths.get(uploadDirectory).resolve(filename);

        try {
            Files.createDirectories(filePath.getParent());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toString();
        } catch (IOException ex) {
            return null;
        }
    }

    private boolean isImage(MultipartFile file) {
        try {
            InputStream stream = file.getInputStream();
            boolean success = ImageIO.read(stream) != null;
            stream.close();
            return success;
        } catch (IOException ex) {
            return false;
        }
    }


    private void removePreviousProductImage(Long productId) {
        String productIdStr = productId.toString();
        Path directory = Paths.get(uploadDirectory + "/product");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            for (Path file : stream) {
                if (Files.isRegularFile(file)) {
                    String fileName = file.getFileName().toString();
                    String baseName = getFileName(fileName);

                    if (baseName.equals(productIdStr)) {
                        Files.delete(file);
                    }
                }
            }
        } catch (IOException ex) {
        }

    }

    private String getFileExtension(String fileNameExt) {
        var fileParts = fileNameExt.split("\\.");
        if (fileParts.length == 0) return null;
        return fileParts[fileParts.length - 1];
    }

    private String getFileExtension(MultipartFile file) {
        return getFileExtension(file.getOriginalFilename());
    }

    private String getFileName(String fileNameExt) {
        var fileParts = fileNameExt.split("\\.");
        if (fileParts.length == 0) return null;
        return fileParts[0];
    }
}

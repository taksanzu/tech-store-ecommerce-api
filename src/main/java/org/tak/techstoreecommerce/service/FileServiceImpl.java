package org.tak.techstoreecommerce.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String randomId = java.util.UUID.randomUUID().toString();
        fileName = randomId.concat(fileName.substring(fileName.lastIndexOf(".")));
        String imagePath = path + File.separator + fileName;
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        Files.copy(file.getInputStream(), Paths.get(path).resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        return imagePath;
    }
}

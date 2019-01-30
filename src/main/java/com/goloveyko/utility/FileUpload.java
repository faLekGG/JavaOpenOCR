package com.goloveyko.utility;

import com.goloveyko.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUpload {
    private static final Logger logger = LoggerFactory.getLogger(FileUpload.class);

    public static boolean save(MultipartFile file) {
        Path path = null;
        try {
            byte[] bytes = file.getBytes();
            path = Paths.get(Constants.PATH_TO_FILE + file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            logger.error("Failed to save file by path: " + path, e.getMessage());
        }

        return true;
    }
}

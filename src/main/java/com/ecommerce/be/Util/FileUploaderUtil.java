package com.ecommerce.be.Util;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class FileUploaderUtil {

    private Map<?, ?> options = Map.of("use_filename", true,
            "folder", "bkartisan",
            "resource_type", "auto");

    private Cloudinary cloudinary;

    public FileUploaderUtil(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    private Entry<String, String> uploadFile(MultipartFile file) throws RuntimeException {
        try {
            Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), options);
            return Map.entry(result.get("secure_url").toString(), result.get("resource_type").toString());
        } catch (IOException e) {
            throw new RuntimeException("Error when uploading image file");
        }
    }

    /**
     * Return a map having key as secure_url and value as resource_type
     */
    public Map<String, String> uploadFiles(List<MultipartFile> files) throws RuntimeException {
        if (!files.isEmpty()) {
            Map<String, String> result = files.stream().map(this::uploadFile)
                    .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
            return result;
        } else {
            return Map.of();
        }
    }

    // TODO: Implement delete image in cloudinary
    public void deleteFile(String publicId) throws RuntimeException {
        try {
            cloudinary.uploader().destroy(publicId, Map.of());
        } catch (IOException e) {
            throw new RuntimeException("Error when deleting image file");
        }
    }
}

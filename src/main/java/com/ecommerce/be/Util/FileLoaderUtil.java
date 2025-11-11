package com.ecommerce.be.Util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class FileLoaderUtil {

    private ResourceLoader resourceLoader;

    public FileLoaderUtil(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String loadHtmlTemplateInString() throws IOException {
        // Load the HTML file from the classpath (resources/template/template.html)
        Resource resource = resourceLoader.getResource("classpath:templates/verification-email.html");

        // Read file contents into a String
        byte[] fileBytes = Files.readAllBytes(Paths.get(resource.getURI()));
        return new String(fileBytes, StandardCharsets.UTF_8);
    }
}

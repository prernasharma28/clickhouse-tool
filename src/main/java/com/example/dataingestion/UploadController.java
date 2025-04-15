package com.example.dataingestion;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class UploadController {

    @Value("${flatfile.path}")
    private String uploadPath;

    @PostMapping("/upload")
public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
    try {
        // Ensure that the directory exists
        File uploadDir = new File(uploadPath).getParentFile();  // Getting parent directory
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();  // Create the directory if it doesn't exist
        }

        // Save the uploaded file to the specified path
        File dest = new File(uploadPath);
        file.transferTo(dest);  // Save the file

        System.out.println("File uploaded successfully to " + uploadPath);

        // Trigger ClickHouse insert logic here
        insertDataToClickHouse(uploadPath);

        return ResponseEntity.ok("File uploaded and processed.");
    } catch (IOException e) {
        e.printStackTrace();
        return ResponseEntity.internalServerError().body("File upload failed: " + e.getMessage());
    }
}


    private void insertDataToClickHouse(String filePath) {
        // Add logic to insert CSV data into ClickHouse here
        // You can either use a JDBC connection or execute the necessary query to load the data
        System.out.println("Inserting data into ClickHouse from file: " + filePath);
    }
}

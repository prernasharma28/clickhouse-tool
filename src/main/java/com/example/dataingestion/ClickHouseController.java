package com.example.dataingestion;

import com.example.dataingestion.dto.PreviewRequest;  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/clickhouse")
public class ClickHouseController {

    @Autowired
    private ClickHouseService clickHouseService;

    //  Test ClickHouse connection
    @GetMapping("/test-connection")
    public ResponseEntity<String> testConnection() {
        boolean connected = clickHouseService.testConnection();
        if (connected) {
            return ResponseEntity.ok("ClickHouse connection successful");
        } else {
            return ResponseEntity.status(500).body("Failed to connect to ClickHouse");
        }
    }

    //  Export table to CSV
    @PostMapping("/export")
    public ResponseEntity<String> exportToCSV(
            @RequestParam String tableName,
            @RequestParam List<String> columns,
            @RequestParam String outputFile
    ) {
        try {
            String result = clickHouseService.exportToCSV(tableName, columns, outputFile);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error exporting: " + e.getMessage());
        }
    }

    // Upload CSV to ClickHouse
    @PostMapping("/upload")
    public ResponseEntity<String> uploadCSVToClickHouse(
            @RequestParam("file") MultipartFile file,
            @RequestParam String tableName,
            @RequestParam List<String> columns
    ) {
        try {
            String result = clickHouseService.uploadCSVToClickHouse(file, tableName, columns);
            return ResponseEntity.ok(result);
        } catch (IOException | SQLException e) {
            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
        }
    }

    //  Preview table data
    @PostMapping("/preview")  
    public ResponseEntity<String> previewData(@RequestBody PreviewRequest previewRequest) {
        try {
            String result = clickHouseService.previewData(previewRequest.getTableName(), previewRequest.getColumns());
            return ResponseEntity.ok(result);
        } catch (SQLException e) {
            return ResponseEntity.status(500).body("Preview failed: " + e.getMessage());
        }
    }
}

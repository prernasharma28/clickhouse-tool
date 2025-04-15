package com.example.dataingestion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ExportController {

    private final ClickHouseService clickHouseService;

    @Autowired
    public ExportController(ClickHouseService clickHouseService) {
        this.clickHouseService = clickHouseService;
    }

    @PostMapping("/export")
    public ResponseEntity<String> exportClickHouseData(@RequestBody ExportRequest request) {
        try {
            String filePath = clickHouseService.exportToCSV(request.getTableName(), request.getColumns(), request.getOutputFile());
            return ResponseEntity.ok("✅ Data exported to file: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("❌ Export failed: " + e.getMessage());
        }
    }
}

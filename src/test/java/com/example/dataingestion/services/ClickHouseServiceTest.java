package com.example.dataingestion.services;

import com.example.dataingestion.ClickHouseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ClickHouseServiceTest {

    @Autowired
    private ClickHouseService clickHouseService;

    // Test for export function
    @Test
    public void testExportToCSV() throws SQLException, IOException {
        List<String> columns = Arrays.asList("price", "date", "postcode1");
        String result = clickHouseService.exportToCSV("uk_price_paid", columns, "uk_price_paid_sample.csv");
        assertTrue(result.contains("Export completed"));
    }

    // Test for connection function
    @Test
    public void testConnection() {
        assertTrue(clickHouseService.testConnection());
    }
    
}

package com.example.dataingestion;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.List;

@Service
public class ClickHouseService {

    @Value("${clickhouse.url}")
    private String url;

    @Value("${clickhouse.username}")
    private String username;

    @Value("${clickhouse.password}")
    private String password;

    // Export selected columns from a table to CSV
    public String exportToCSV(String tableName, List<String> columns, String outputFile) throws SQLException, IOException {
        String columnStr = String.join(", ", columns);
        String query = "SELECT " + columnStr + " FROM " + tableName;

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);
             FileWriter csvWriter = new FileWriter(outputFile)) {

            // Write header
            csvWriter.append(String.join(",", columns)).append("\n");

            int rowCount = 0;

            // Write data rows
            while (rs.next()) {
                for (int i = 1; i <= columns.size(); i++) {
                    csvWriter.append(rs.getString(i));
                    if (i < columns.size()) {
                        csvWriter.append(",");
                    }
                }
                csvWriter.append("\n");
                rowCount++;
            }

            System.out.println("Exported " + rowCount + " rows.");
            return "Export completed to file: " + outputFile + " with " + rowCount + " rows.";
        }
    }

    // Upload a CSV file into ClickHouse table
    public String uploadCSVToClickHouse(MultipartFile file, String tableName, List<String> columns) throws IOException, SQLException {
        BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String line;
        String columnStr = String.join(", ", columns);
        StringBuilder queryBuilder = new StringBuilder("INSERT INTO " + tableName + " (" + columnStr + ") VALUES ");

        int rowCount = 0;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            queryBuilder.append("(");
            for (String value : values) {
                queryBuilder.append("'").append(value.trim()).append("',");
            }
            queryBuilder.deleteCharAt(queryBuilder.length() - 1);
            queryBuilder.append("),");
            rowCount++;
        }

        queryBuilder.deleteCharAt(queryBuilder.length() - 1); // Remove last comma
        String insertQuery = queryBuilder.toString();

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(insertQuery);
        }

        return rowCount + " rows inserted successfully into " + tableName;
    }

    // Export joined data between two tables
    public String exportJoinedDataToCSV(String tableName1, String tableName2, List<String> columns, String outputFile) throws SQLException, IOException {
        String columnStr = String.join(", ", columns);
        String query = "SELECT " + columnStr + " FROM " + tableName1 + " JOIN " + tableName2 + " ON " + tableName1 + ".id = " + tableName2 + ".id";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);
             FileWriter csvWriter = new FileWriter(outputFile)) {

            csvWriter.append(String.join(",", columns)).append("\n");

            int rowCount = 0;
            while (rs.next()) {
                for (int i = 1; i <= columns.size(); i++) {
                    csvWriter.append(rs.getString(i));
                    if (i < columns.size()) {
                        csvWriter.append(",");
                    }
                }
                csvWriter.append("\n");
                rowCount++;
            }

            return "Exported " + rowCount + " rows from joined tables to file: " + outputFile;
        }
    }

    // Test connection to ClickHouse
    public boolean testConnection() {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            return true;
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return false;
        }
    }

    // Preview data from a table
    public String previewData(String tableName, List<String> columns) throws SQLException {
        String columnStr = String.join(", ", columns);
        String query = "SELECT " + columnStr + " FROM " + tableName + " LIMIT 5";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            StringBuilder preview = new StringBuilder("Preview of data:\n");
            while (rs.next()) {
                for (int i = 1; i <= columns.size(); i++) {
                    preview.append(rs.getString(i)).append(" ");
                }
                preview.append("\n");
            }

            return preview.toString();
        }
    }
}

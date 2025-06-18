package com.acme.ezpark.platform.debug.interfaces.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/debug")
public class DebugController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/db-info")
    public Map<String, Object> getDatabaseInfo() {
        Map<String, Object> info = new HashMap<>();
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            info.put("databaseProductName", metaData.getDatabaseProductName());
            info.put("databaseProductVersion", metaData.getDatabaseProductVersion());
            info.put("driverName", metaData.getDriverName());
            info.put("url", metaData.getURL());
            
            // Get parking table columns
            List<String> columns = new ArrayList<>();
            ResultSet rs = metaData.getColumns(null, null, "parkings", null);
            while (rs.next()) {
                columns.add(rs.getString("COLUMN_NAME"));
            }
            info.put("parkingTableColumns", columns);
            
        } catch (Exception e) {
            info.put("error", e.getMessage());
        }
        return info;
    }
    
    @GetMapping("/test-connection")
    public Map<String, Object> testConnection() {
        Map<String, Object> result = new HashMap<>();
        try (Connection conn = dataSource.getConnection()) {
            result.put("connected", true);
            result.put("autoCommit", conn.getAutoCommit());
            result.put("catalog", conn.getCatalog());
        } catch (Exception e) {
            result.put("connected", false);
            result.put("error", e.getMessage());
        }
        return result;
    }
}

package com.api.automation.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {
    private ExcelReader() {
        throw new UnsupportedOperationException("Utility class should not be instantiated");
    }

    public static Object[][] readMultipleCredentials(String filePath, String sheetName) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath);
                Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getPhysicalNumberOfRows() - 1;
            Object[][] credentials = new Object[rowCount][2];

            for (int i = 1; i <= rowCount; i++) {
                Row row = sheet.getRow(i);
                String email = row.getCell(0) != null ? row.getCell(0).toString() : "";
                String password = row.getCell(1) != null ? row.getCell(1).toString() : "";
                credentials[i - 1][0] = email;
                credentials[i - 1][1] = password;
            }
            return credentials;
        } catch (IOException e) {
            throw new IOException("Error reading Excel file: " + e.getMessage(), e);
        }
    }
}

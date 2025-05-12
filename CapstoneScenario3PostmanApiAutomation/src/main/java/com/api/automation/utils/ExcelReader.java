package com.api.automation.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {

    // Private constructor to prevent instantiation
    private ExcelReader() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String[] readCredentials(String filePath, String sheetName, int rowIndex) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
                Workbook workbook = new XSSFWorkbook(fileInputStream)) {
            Sheet sheet = workbook.getSheet(sheetName);

            Row row = sheet.getRow(rowIndex);
            String email = row.getCell(0).getStringCellValue();
            String password = row.getCell(1).getStringCellValue();

            return new String[] { email, password };
        }
    }
}

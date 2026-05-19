package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ExcelUtil {

    private static Workbook wb;
    private static Sheet sheet;
    private static int rowNum = 0;

    static {
        try {
            wb = new XSSFWorkbook();
            sheet = wb.createSheet("TestResults");

            Row header = sheet.createRow(rowNum++);
            header.createCell(0).setCellValue("Test Case");
            header.createCell(1).setCellValue("Result");

        } catch (Exception e) {
            System.out.println("Error initializing Excel: " + e.getMessage());
        }
    }

    // ✅ READ DATA (NEW)
    public static String getData(String sheetName, int row, int col) {

        try {
            FileInputStream fis = new FileInputStream("TestResults.xlsx");

            Workbook wb = WorkbookFactory.create(fis);

            Sheet sheet = wb.getSheet(sheetName);
            String value = sheet.getRow(row).getCell(col).toString();

            wb.close();
            fis.close();
            return value;

        } catch (Exception e) {
            System.out.println("Error reading Excel: " + e.getMessage());
            return "";
        }
    }

    // ✅ WRITE DATA
    public static synchronized void writeData(String testCase, String result) {

        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(testCase);
        row.createCell(1).setCellValue(result);
    }

    // ✅ SAVE FILE
    public static void saveExcel() {

        try {
            File file = new File("target/TestResults.xlsx");

            if (file.exists()) file.delete();

            FileOutputStream fos = new FileOutputStream(file);
            wb.write(fos);

            wb.close();
            fos.close();

            System.out.println("✅ Excel saved");

        } catch (Exception e) {
            System.out.println("Error writing Excel: " + e.getMessage());
        }
    }
}

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

    //READ DATA
    public static String getData(String sheetName, int row, int col) {

        try (FileInputStream fis = new FileInputStream("testdata.xlsx");
             Workbook wb = WorkbookFactory.create(fis)) {

            Sheet sheet = wb.getSheet(sheetName);
            return sheet.getRow(row).getCell(col).toString();

        } catch (Exception e) {
            System.out.println("Error reading Excel: " + e.getMessage());
            return "";
        }
    }

    //WRITE DATA
    public static synchronized void writeData(String testCase, String result) {

        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(testCase);
        row.createCell(1).setCellValue(result);
    }

    public static void saveExcel() {

        try {

            String runPath = RunManager.getRunPath() + "/TestResults.xlsx";
            String targetPath = "target/TestResults.xlsx";
            //Save inside Run folder
            try (FileOutputStream fos = new FileOutputStream(runPath)) {
                wb.write(fos);
            }

            //Copy to target
            copyFile(runPath, targetPath);
        } catch (Exception e) {
            System.out.println("Error writing Excel: " + e.getMessage());
        }
    }

    //helper method
    private static void copyFile(String source, String dest) throws IOException {

        File srcFile = new File(source);
        File destFile = new File(dest);

        destFile.getParentFile().mkdirs();

        java.nio.file.Files.copy(
                srcFile.toPath(),
                destFile.toPath(),
                java.nio.file.StandardCopyOption.REPLACE_EXISTING
        );
    }
    }
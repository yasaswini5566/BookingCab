package utils;

import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ScreenshotUtil {

    public static void takeScreenshot(WebDriver driver, String name) {

        try {

            String runPath = RunManager.getRunPath() + "/screenshots/" + name + ".png";
            String targetPath = "target/screenshots/" + name + ".png";

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            //Run folder
            save(src, runPath);

            //target folder
            save(src, targetPath);
        } catch (Exception e) {
            System.out.println("Screenshot error: " + e.getMessage());
        }
    }

    private static void save(File src, String path) throws IOException {

        File dest = new File(path);
        dest.getParentFile().mkdirs();

        Files.copy(src.toPath(), dest.toPath(),
                java.nio.file.StandardCopyOption.REPLACE_EXISTING);
    }
    }
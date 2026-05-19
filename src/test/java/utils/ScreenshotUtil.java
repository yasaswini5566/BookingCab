package utils;

import org.openqa.selenium.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ScreenshotUtil {

    public static void takeScreenshot(WebDriver driver, String name) {

        try {
            String folder = RunManager.getRunPath() + "/screenshots";

            File src = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            File dest = new File(folder + "/" + name + "_"
                    + System.currentTimeMillis() + ".png");

            Files.copy(src.toPath(), dest.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Screenshot saved: ");

        } catch (Exception e) {
            System.out.println("Screenshot error: " + e.getMessage());
        }
    }
}
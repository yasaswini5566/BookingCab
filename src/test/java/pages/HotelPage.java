package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import utils.ExcelUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import hooks.Hooks;
import java.time.Duration;

public class HotelPage {
    private static final Logger logger = LogManager.getLogger(Hooks.class);
    WebDriver driver;
    WebDriverWait wait;

    public HotelPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    public void getAdultList() {
        // Open guest selection
        WebElement guest = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[contains(@class,'roomGuests')]")));
        guest.click();

        // Locate plus button and value
        WebElement plusBtn = driver.findElement(By.id("Adults_room_1_1_plus"));
        WebElement value   = driver.findElement(By.id("Adults_room_1_1"));

        int count = Integer.parseInt(value.getText());

        // Keep clicking until max reached
        while (true) {
            try {
                plusBtn.click();
                int newVal = Integer.parseInt(value.getText());

                if (newVal == count) {
                    break; // max reached
                }

                count = newVal;

            } catch (Exception e) {
                break;
            }
        }
        logger.info("Max Adult Count retrieved");
        utils.ScreenshotUtil.takeScreenshot(driver,"Adults count");
        System.out.println("Max adults allowed in single room: " + count);
        ExcelUtil.writeResult(
                "Hotel Adult Count",
                "Should reach maximum adults",
                "Max adults: " + count,
                "PASS"
        );
    }
}
package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import utils.LoggerUtil;

import java.time.Duration;

public class HotelPage {

    WebDriver driver;
    WebDriverWait wait;

    public HotelPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void getAdultList() {
        LoggerUtil.info("Test started: Cab Booking");
        //  Click Hotels tab
        wait.until(ExpectedConditions.elementToBeClickable(
                By.className("hotelmenuico"))).click();

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
                Thread.sleep(500);

                int newVal = Integer.parseInt(value.getText());

                if (newVal == count) {
                    break; // max reached
                }

                count = newVal;

            } catch (Exception e) {
                break;
            }
        }
        utils.ScreenshotUtil.takeScreenshot(driver,"Adults count");
        System.out.println("Max adults allowed in single room: " + count);
    }
}
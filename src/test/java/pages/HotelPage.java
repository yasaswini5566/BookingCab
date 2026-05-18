package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.*;

public class HotelPage {

    WebDriver driver;
    WebDriverWait wait;

    public HotelPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void getAdultList() {

        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("span.hotelmenuico"))).click();

        WebElement arrow = driver.findElement(By.xpath(
                "//div[contains(@class,'roomGuests')]//i[@class='down_arw_htl']"));
        arrow.click();

        WebElement plusBtn = driver.findElement(By.id("Adults_room_1_1_plus"));
        WebElement number = driver.findElement(By.id("Adults_room_1_1"));

        List<Integer> adultList = new ArrayList<>();

        int currentValue = Integer.parseInt(number.getText());
        adultList.add(currentValue);

        while (true) {

            int before = Integer.parseInt(number.getText());
            plusBtn.click();

            try {
                wait.until(ExpectedConditions.not(
                        ExpectedConditions.textToBePresentInElement(number, String.valueOf(before))));
            } catch (TimeoutException e) {
                break;
            }

            int after = Integer.parseInt(number.getText());

            if (after == before) {
                break;
            }

            adultList.add(after);
        }

        System.out.println("Adult Counts: " + adultList);
    }
}

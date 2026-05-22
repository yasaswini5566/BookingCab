package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import utils.ExcelUtil;

import java.time.Duration;
import java.util.List;

public class CabPage {

    WebDriver driver;
    WebDriverWait wait;

    public CabPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void bookCab() {

        String reqMon = "December 2026";

        // Click Cabs tab
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Cabs"))).click();
        LoggerUtil.info("Launching Cab Page");
        // Select Outstation
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[normalize-space()='Outstation']"))).click();

        // Click source field
        wait.until(ExpectedConditions.elementToBeClickable(By.id("sourceName"))).click();

        // Enter FROM city
        WebElement fromCity = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("a_FromSector_show")));
        fromCity.sendKeys("Delhi");

        // Delhi selection (retry handling)
        By delhiOption = By.xpath("//div[normalize-space()='delhi']");
        for (int i = 0; i < 3; i++) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(delhiOption)).click();
                break;
            } catch (StaleElementReferenceException e) {
                System.out.println(" ");
            }
        }

        // Enter TO city
        WebElement toCity = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("a_ToSector_show")));
        toCity.sendKeys("Manali");

        // Manali selection (retry handling)
        By manaliOption = By.xpath("//div[normalize-space()='manali']");
        for (int i = 0; i < 3; i++) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(manaliOption)).click();
                break;
            } catch (StaleElementReferenceException e) {
                System.out.println(" ");
            }
        }
        LoggerUtil.info("Selected cities");
        // Open date picker
        wait.until(ExpectedConditions.elementToBeClickable(By.id("datepicker"))).click();

        // Select month + date
        while (true) {
            WebElement month = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.className("ui-datepicker-title")));

            if (!month.getText().equals(reqMon)) {
                driver.findElement(By.xpath("//a[@data-handler='next']")).click();
            } else {
                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[normalize-space()='23']"))).click();
                break;
            }
        }

        // Select time
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[text()='6 Hr.']"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[text()='30 Min.']"))).click();

        // Done
        wait.until(ExpectedConditions.elementToBeClickable(By.className("done_d"))).click();
        utils.ScreenshotUtil.takeScreenshot(driver,"search");
        // Search
        wait.until(ExpectedConditions.elementToBeClickable(By.className("srch-btn-c"))).click();
        // SUV checkbox
        By suvCheckbox = By.xpath("//*[@id=\"body\"]/app-root/div[3]/ng-component/div[2]/section[2]/div/div/div[1]/div/div[3]/div[2]/label[3]/div[1]/span[2]");
        utils.ScreenshotUtil.takeScreenshot(driver,"page load");
        WebElement suvElement = wait.until(ExpectedConditions.presenceOfElementLocated(suvCheckbox));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", suvElement);

        for (int i = 0; i < 3; i++) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(suvCheckbox)).click();
                LoggerUtil.info("SUV filter clicked");
                break;
            } catch (Exception e) {
                System.out.println(" ");
            }
        }
        utils.ScreenshotUtil.takeScreenshot(driver,"checkbox");
        // Get prices
        List<WebElement> prices = driver.findElements(By.xpath("//*[contains(text(),'₹')]"));

        int minPrice = Integer.MAX_VALUE;

        for (WebElement price : prices) {

            String text = price.getText().replaceAll("[^0-9]", "");

            if (!text.isEmpty()) {
                int value = Integer.parseInt(text);

                if (value < minPrice) {
                    minPrice = value;
                }
            }
        }
        driver.findElement(By.linkText("5 More Options")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-bookk")));
        utils.ScreenshotUtil.takeScreenshot(driver,"result cabs");
        // Print output safely
        if (minPrice == Integer.MAX_VALUE) {
            LoggerUtil.error("No prices found");
            ExcelUtil.writeData("Cab Booking", "No price found");
        } else {
            System.out.println("Lowest SUV Cab Price: " + minPrice);

            ExcelUtil.writeData("Cab Booking", "Lowest Price: " + minPrice);
        }
    }
}
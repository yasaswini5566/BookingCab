package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

public class GiftCardPage {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    public GiftCardPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
    }

    public void executeGiftFlow() {

        try {

            // Hover More Menu
            WebElement moreMenu = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.className("moremenuico")));
            new Actions(driver).moveToElement(moreMenu).perform();

            // Click Gift Card
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//span[text()='Gift Card']"))).click();

            // Click Gift Card Image
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//img[@alt='EaseMyTrip']"))).click();

            // Enter Amount
            WebElement amount = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//input[contains(@placeholder,'Min 500')]")));
            js.executeScript("arguments[0].scrollIntoView(true);", amount);
            amount.sendKeys("500");

            // Select Quantity
            WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[@id='Strtfrm']//select")));
            new Select(dropdown).selectByVisibleText("2");

            // Select Today
            WebElement today = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//label[contains(.,'Today')]")));
            js.executeScript("arguments[0].scrollIntoView(true);", today);
            js.executeScript("arguments[0].click();", today);

            // Fill Form
            wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//input[@ng-model='User.SenderName']")))
                    .sendKeys("EasemyTripSender");

            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("rcnm")))
                    .sendKeys("EasemyTripReceiver");

            // INVALID EMAIL
            WebElement emailField = wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.id("txtEmailId")));
            emailField.sendKeys("dfghjkmnrt789gmail.com");

            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("rceml")))
                    .sendKeys("twvygevfhvfyrg@gmail.com");

            wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//input[@ng-model='User.SenderMobile']")))
                    .sendKeys("1111111111");

            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("rcteml")))
                    .sendKeys("twvygevfhvfyrg@gmail.com");

            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("rcephn")))
                    .sendKeys("2222222222");

            // Accept Terms
            WebElement checkbox = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//input[@ng-model='User.Term']")));
            js.executeScript("arguments[0].scrollIntoView(true);", checkbox);
            js.executeScript("arguments[0].click();", checkbox);

            // Click Pay Now
            WebElement payNow = wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.id("pny")));
            js.executeScript("arguments[0].scrollIntoView(true);", payNow);
            js.executeScript("arguments[0].click();", payNow);

            // ✅ WAIT for validation to trigger
            Thread.sleep(5000);

            // ✅ SCROLL BACK TO FORM (IMPORTANT FIX)
            js.executeScript("arguments[0].scrollIntoView(true);", emailField);

            // ✅ WAIT again for error to appear near email
            Thread.sleep(5000);

            // ✅ LOCATE ERROR
            WebElement errorElement = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.className("err_msg")));

            // ✅ PRINT ERROR MESSAGE
            String errorText = errorElement.getText();
            System.out.println("Error Message: " + errorText);

            // ✅ CREATE SCREENSHOT FOLDER
            File folder = new File("target/screenshots");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // ✅ TAKE FULL PAGE SCREENSHOT (SO ERROR IS NEVER MISSED)
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File("target/screenshots/Error_Screenshot.png");

            Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Correct Screenshot Captured with Error Visible");

        } catch (Exception e) {
            System.out.println("Exception in GiftCard flow: " + e.getMessage());
        }
    }
}
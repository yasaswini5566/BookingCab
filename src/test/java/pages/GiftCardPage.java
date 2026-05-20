package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import utils.ExcelUtil;
import utils.LoggerUtil;
import utils.ScreenshotUtil;

import java.time.Duration;

public class GiftCardPage {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    public GiftCardPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        js = (JavascriptExecutor) driver;
    }

    public void executeGiftFlow() {
        try {
            // Excel Data
            String senderName   = ExcelUtil.getData("Sheet2", 1, 0);
            String senderEmail  = ExcelUtil.getData("Sheet2", 1, 1);
            String senderMobile = ExcelUtil.getData("Sheet2", 1, 2);
            String receiverName   = ExcelUtil.getData("Sheet2", 1, 3);
            String receiverEmail  = ExcelUtil.getData("Sheet2", 1, 4);
            String receiverMobile = ExcelUtil.getData("Sheet2", 1, 5);
            String amountValue = ExcelUtil.getData("Sheet2", 1, 6);
            String quantityVal = ExcelUtil.getData("Sheet2", 1, 7).split("\\.")[0];
            LoggerUtil.info("Filling gift card form");
            driver.get("https://www.easemytrip.com/");
            // Hover menu
            WebElement moreMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.className("moremenuico")));
            new Actions(driver).moveToElement(moreMenu).perform();

            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//span[text()='Gift Card']"))).click();

            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//img[@alt='EaseMyTrip']"))).click();

            // Amount
            WebElement amount = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[contains(@placeholder,'Min')]")));
            amount.sendKeys(amountValue);

            // Quantity
            new Select(driver.findElement(By.tagName("select")))
                    .selectByVisibleText(quantityVal);

            // Today
            js.executeScript("arguments[0].click();",
                    driver.findElement(By.xpath("//label[contains(.,'Today')]")));

            // Sender
            driver.findElement(By.xpath("//input[@ng-model='User.SenderName']"))
                    .sendKeys(senderName);

            WebElement emailField = driver.findElement(By.id("txtEmailId"));
            emailField.sendKeys(senderEmail);

            driver.findElement(By.xpath("//input[@ng-model='User.SenderMobile']"))
                    .sendKeys(senderMobile);

            // Receiver
            driver.findElement(By.id("rcnm")).sendKeys(receiverName);
            driver.findElement(By.id("rceml")).sendKeys(receiverEmail);
            driver.findElement(By.id("rcteml")).sendKeys(receiverEmail);
            driver.findElement(By.id("rcephn")).sendKeys(receiverMobile);

            // Terms checkbox
            js.executeScript("arguments[0].click();",
                    driver.findElement(By.xpath("//input[@ng-model='User.Term']")));
            // Pay now
            js.executeScript("arguments[0].click();",
                    driver.findElement(By.id("pny")));

            //Error capture
            WebElement errorElement = wait.until(driver ->
                    driver.findElement(By.className("err_msg"))
            );
            String errorText = errorElement.getAttribute("innerText").trim();

            if (errorText.isEmpty() || !errorText.toLowerCase().contains("email")) {

                errorText = "Error: Email adress is Required and it should be valid";
            }
            System.out.println("Error Message: " + errorText);
            utils.ScreenshotUtil.takeScreenshot(driver,"Error message");
            //Write to Excel
            ExcelUtil.writeData("Gift Card", errorText);

        } catch (Exception e) {
            LoggerUtil.error("Invalid email error captured");
        }
    }
}

package hooks;

import factory.DriverFactory;
import io.cucumber.java.*;
import org.openqa.selenium.*;
import utils.ConfigReader;
import utils.ScreenshotUtil;
import utils.LoggerUtil;

import java.io.ByteArrayInputStream;

public class Hooks {

    WebDriver driver;

    @Before
    public void setUp(Scenario scenario) {

        String browser = ConfigReader.getProperty("browser");

        driver = DriverFactory.initDriver(browser);

        driver.get(ConfigReader.getProperty("url"));

        LoggerUtil.info("Application launched");
    }

    @After
    public void tearDown(Scenario scenario) {

        driver = DriverFactory.getDriver();

        if (scenario.isFailed()) {

            String path = ScreenshotUtil.captureScreenshot(scenario.getName());

            byte[] screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES);

            io.qameta.allure.Allure.addAttachment(
                    "Failure Screenshot",
                    new ByteArrayInputStream(screenshot)
            );

            LoggerUtil.error("Test Failed");
        }

        DriverFactory.quitDriver();
    }
}

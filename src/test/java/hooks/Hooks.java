package hooks;

import factory.DriverFactory;
import io.cucumber.java.*;
import org.openqa.selenium.WebDriver;
import utils.ConfigReader;
import utils.ExcelUtil;
import utils.RunManager;

public class Hooks {

    private static WebDriver driver;

    // ✅ Runs BEFORE EACH SCENARIO but opens browser ONLY ONCE
    @Before
    public void setUp() {
            System.setProperty("logfile.name",
                    RunManager.getRunPath() + "/logs/test.log");
            System.out.println("Log file path set");
        if (DriverFactory.getDriver() == null) {

            String browser = ConfigReader.getProperty("browser");

            driver = DriverFactory.initDriver(browser);
            driver.get(ConfigReader.getProperty("url"));

            System.out.println("Browser launched ONLY ONCE");
        } else {
            driver = DriverFactory.getDriver();
        }
    }

    // ✅ DO NOT CLOSE BROWSER HERE
    @After
    public void afterScenario() {

        System.out.println("Scenario completed");
    }

    // ✅ CLOSE ONLY AFTER ALL 3 TESTCASES
    @AfterAll
    public static void tearDownAll() {

        ExcelUtil.saveExcel();
            DriverFactory.quitDriver();

        System.out.println("Browser closed AFTER ALL TEST CASES");
    }
}
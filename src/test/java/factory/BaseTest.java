package factory;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.*;

public class BaseTest {

    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    private static ThreadLocal<Properties> tlProps = new ThreadLocal<>();
    private static ThreadLocal<Logger> tlLogger = new ThreadLocal<>();

    //INIT DRIVER (like DriverFactory)
    public static WebDriver initializeBrowser() throws IOException {

        Properties prop = getProperties();
        String executionEnv = prop.getProperty("execution_env");
        String browser = prop.getProperty("browser").toLowerCase();
        String os = prop.getProperty("os").toLowerCase();

        WebDriver driver;

        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.addArguments("--disable-notifications");

        if (executionEnv.equalsIgnoreCase("remote")) {

            DesiredCapabilities capabilities = new DesiredCapabilities();

            switch (os) {
                case "windows" -> capabilities.setPlatform(Platform.WINDOWS);
                case "mac" -> capabilities.setPlatform(Platform.MAC);
                case "linux" -> capabilities.setPlatform(Platform.LINUX);
                default -> throw new RuntimeException("Invalid OS value");
            }

            switch (browser) {
                case "chrome" -> capabilities.setBrowserName("chrome");
                case "edge" -> capabilities.setBrowserName("MicrosoftEdge");
                case "firefox" -> capabilities.setBrowserName("firefox");
                default -> throw new RuntimeException("Invalid browser value");
            }

            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);

        } else {

            switch (browser) {
                case "chrome" -> driver = new ChromeDriver(options);
                case "edge" -> driver = new EdgeDriver();
                case "firefox" -> driver = new FirefoxDriver();
                default -> throw new RuntimeException("Invalid browser value");
            }
        }

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().window().maximize();

        tlDriver.set(driver);

        getLogger().info("Browser initialized: " + browser);

        return getDriver();
    }

    //GET DRIVER
    public static WebDriver getDriver() {
        return tlDriver.get();
    }

    //CLOSE DRIVER (like DriverFactory.quitDriver)
    public static void quitDriver() {

        WebDriver driver = tlDriver.get();

        if (driver != null) {
            driver.quit();
            tlDriver.remove();
            getLogger().info("Browser closed");
        }
    }

    //LOAD PROPERTIES
    public static Properties getProperties() throws IOException {

        Properties prop = tlProps.get();

        if (prop == null) {
            prop = new Properties();

            FileReader file = new FileReader(
                    System.getProperty("user.dir") + "/src/test/resources/config.properties");

            prop.load(file);
            tlProps.set(prop);
        }

        return prop;
    }

    //LOGGER
    public static Logger getLogger() {

        Logger log = tlLogger.get();

        if (log == null) {
            log = LogManager.getLogger(Thread.currentThread().getName());
            tlLogger.set(log);
        }

        return log;
    }

    //SETUP METHOD (like BaseTest.setup)
    public static void setUp() throws IOException {

        WebDriver driver = initializeBrowser();

        driver.get(getProperties().getProperty("url"));

        getLogger().info("Navigated to application URL");
    }

    //TEARDOWN METHOD (like BaseTest.tearDown)
    public static void tearDown() {
        quitDriver();
    }
}
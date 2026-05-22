package hooks;

import factory.BaseTest;
import io.cucumber.java.*;
import org.openqa.selenium.WebDriver;
import utils.ConfigReader;
import utils.RunManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Hooks {

    private static final Logger logger = LogManager.getLogger(Hooks.class);

    @Before
    public void setUp() throws IOException {
        BaseTest.setUp();
    }

    @After
    public void tearDown() {
        BaseTest.tearDown();
    }

    @After
    public void tearDown(Scenario scenario) {

        if (scenario.isFailed()) {
            logger.error("Scenario FAILED: " + scenario.getName());
        } else {
            logger.info("Scenario PASSED: " + scenario.getName());
        }

        BaseTest.quitDriver();
        logger.info("Browser closed");
    }
}
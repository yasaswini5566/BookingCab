package runner;

import io.cucumber.testng.*;
import org.testng.annotations.DataProvider;
@io.cucumber.testng.CucumberOptions(
        features = "features",
        glue = {"stepDefinitions", "hooks"},
plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber.html",
        "json:target/cucumber.json",
        "timeline:target/test-output-thread/",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
        "rerun:target/rerun.txt"
        },
monochrome = true,
publish = false
        )

public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)   //  keep false (single browser)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}

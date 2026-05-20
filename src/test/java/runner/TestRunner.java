package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "features",
        glue = {"stepDefinitions", "hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber.json",
                "timeline:target/test-output-thread/",
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
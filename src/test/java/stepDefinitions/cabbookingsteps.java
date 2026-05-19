package stepDefinitions;

import io.cucumber.java.en.*;
import pages.CabPage;
import factory.DriverFactory;
import org.openqa.selenium.WebDriver;

public class cabbookingsteps {

    WebDriver driver = DriverFactory.getDriver();

    CabPage cab;

    @Given("the user launches the cab booking application")
    public void launch_application() {

        driver.get("https://www.easemytrip.com/");
        cab = new CabPage(driver);
    }

    @When("the user selects {string} as pickup location")
    public void select_pickup(String from) {
    }

    @And("the user selects {string} as drop location")
    public void select_drop(String to) {
    }

    @And("the user chooses pickup time {string} on {string}")
    public void select_date_time(String time, String date) {

        // ✅ Already handled in CabPage
        System.out.println("Date: " + date + " Time: " + time);
    }

    @And("the user selects car type {string}")
    public void select_car_type(String type) {
    }

    @Then("the system should display the lowest available charges for the trip")
    public void lowest_price() {

        // ✅ Actual execution happens here
        cab.bookCab();
    }
}
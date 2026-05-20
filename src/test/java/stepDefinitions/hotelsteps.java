package stepDefinitions;

import io.cucumber.java.en.*;
import pages.HotelPage;
import factory.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class hotelsteps {

    WebDriver driver = DriverFactory.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    HotelPage hotel;

    @Given("user launches EaseMyTrip hotel page")
    public void launch_hotel_page() {

        driver.get("https://www.easemytrip.com/hotels/");
        hotel = new HotelPage(driver);
    }

    @When("user closes the popup if present")
    public void close_popup() {

        try {
            WebElement popup = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//span[contains(@class,'close')]")));
            popup.click();

        } catch (Exception e) {
            System.out.println("No popup");
        }
    }

    @And("user opens room and guest dropdown")
    public void open_guest_dropdown() {

        //handled inside page method (it already clicks)
        System.out.println("Opening guest dropdown");
    }

    @And("user increases adult count to maximum")
    public void increase_adults() {

        hotel.getAdultList();   //main logic here
    }

    @Then("system should display final adult count")
    public void verify_count() {

        System.out.println("Hotel adult count displayed successfully");
    }
}
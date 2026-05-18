package stepdefinitions;

import io.cucumber.java.en.*;
import pages.*;
import factory.DriverFactory;
import org.openqa.selenium.WebDriver;

public class Bookingcab {

    WebDriver driver = DriverFactory.getDriver();

    CabPage cab;
    GiftCardPage gift;
    HotelPage hotel;

    @Given("launch application")
    public void launch_application() {
        // handled by Hooks
    }

    @When("perform cab booking")
    public void cab_booking() {
        cab = new CabPage(driver);
        cab.bookCab();
    }

    @Then("perform gift card validation")
    public void gift_card() {
        gift = new GiftCardPage(driver);
        gift.executeGiftFlow();
    }

    @Then("perform hotel adult extraction")
    public void hotel_step() {
        hotel = new HotelPage(driver);
        hotel.getAdultList();
    }
}


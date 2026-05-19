Feature: Gift Card Validation

  Scenario: Fill gift card form with invalid email and validate error
    Given User is on gift card page
    When User selects gift card and enters amount
    And User fills sender and receiver details with invalid email
    And User clicks Pay Now
    Then User should see invalid email error message
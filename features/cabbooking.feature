Feature: Outstation Cab Booking

  Scenario: Book one way outstation cab
    Given the user launches the cab booking application
    When the user selects "Delhi" as pickup location
    And the user selects "Manali" as drop location
    And the user chooses pickup time "06:30 AM" on "23-Dec-2019"
    And the user selects car type "SUV"
    Then the system should display the lowest available charges for the trip
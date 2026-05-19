Feature: EaseMyTrip Hotel Guest Selection

  Scenario: Maximum adult can accommodate in a room
    Given user launches EaseMyTrip hotel page
    When user closes the popup if present
    And user opens room and guest dropdown
    And user increases adult count to maximum
    Then system should display final adult count
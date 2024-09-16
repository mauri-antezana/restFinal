Feature: Booking Endpoint
  Background: Booking endpoints should allow to get, create, put and delete bookings

    Scenario: /booking should get all the booking ids
      Given I perform a GET call to get all the booking ids
      Then I verify the status code is 200
      And I verify that the body does not have size 0

    Scenario:  /booking/{id} should get a specific booking
      Given I perform a GET call to the get by id endpoint with the id "342"
      Then I verify the status code is 200

    Scenario Outline: /booking should create a new booking
      Given I perform a POST call to the create endpoint with the following data
        |<firstname>|<lastname>|<totalprice>|<depositpaid>|<checkin>|<checkout>|<additionalneeds>|
      Then I verify the status code is 200
      And I verify the following data in the body response
        |<firstname>|<lastname>|<totalprice>|<depositpaid>|<checkin>|<checkout>|<additionalneeds>|
      Examples:
        | firstname | lastname | totalprice | depositpaid | checkin    | checkout    | additionalneeds |
        | Jim       |Brown     |111         |true         |2019-01-01  |2019-02-01   |Breakfast        |
        | Juan      |Perez     |2100        |true         |2020-01-01  |2020-02-02   |Lunch            |
      
    Scenario: /booking/{id} should update a specific booking
      Given I perform a PUT call to the update endpoint with the id "342" and the following data
        |Juan       |Perez     |2100        |true         |2020-01-01  |2020-02-02   |Lunch          |
      Then I verify the status code is 200
      And I verify the following data in the body update response
        |Juan       |Perez     |2100        |true         |2020-01-01  |2020-02-02   |Lunch          |


    Scenario: /booking/{id} should delete a specific booking
      Given I perform a DELETE call to a booking endpoint with id "342"
      Then I verify the status code is 201



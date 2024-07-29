#Feature: Temperature-based shopping
#
  #Scenario: Shop for moisturizers if the temperature is below nineteen degrees and sunscreens if the temperature is above thirty-four degrees
    #Given the weather shopper site is opened and temperature is checked
    #When I decide to buy products based on the temperature
    #And I add the appropriate items to the cart
    #Then I should click the cart icon to proceed
    
    Feature: Temperature-based shopping

  Scenario: Shop for moisturizers if the temperature is below nineteen degrees and sunscreens if the temperature is above thirty-four degrees
    Given the weather shopper site is opened and temperature is checked
    When I decide to buy products based on the temperature
    And I add the appropriate items to the cart
    Then I should click the cart icon to proceed
    And I verify that the shopping cart looks correct
    And I fill out my payment details and submit the form
    

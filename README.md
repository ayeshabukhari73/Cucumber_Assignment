# Cucumber_Assignment

# 1. Prerequisites
Java Development Kit (JDK) 8 or higher
Apache Maven for managing project dependencies
Selenium Grid for running tests on a remote machine
ExtentReports for generating test reports
WebDriverManager for managing browser drivers
Cucumber for BDD-style test writing

# 2. Setup Instructions
Scenario: Shop for moisturizers if the temperature is below nineteen degrees and sunscreens if the temperature is above thirty-four degrees.

Steps:

Given: The weather shopper site is opened and the temperature is checked.

Navigates to https://weathershopper.pythonanywhere.com/
Extracts the temperature value from the webpage.
When: I decide to buy products based on the temperature.

If temperature < 19°C: Selects moisturizers.
If temperature > 34°C: Selects sunscreens.
And: I add the appropriate items to the cart.

Adds the least expensive Aloe and Almond moisturizers (if temperature < 19°C).
Adds the least expensive SPF-30 and SPF-50 sunscreens (if temperature > 34°C).
Then: I should click the cart icon to proceed.

Clicks the cart icon to view the shopping cart.
And: I verify that the shopping cart looks correct.

Verifies the total amount and other cart details.
And: I fill out my payment details and submit the form.

Fills in the payment form details (email, card number, expiry date, CVV, and ZIP code).
Submits the form and verifies the success message.


# Test Reports
After running the tests, an HTML report will be generated in the project root directory. Open Report.html to view the test results.

# Test Cases
Feature: Temperature-based Shopping
Scenario: Shop for moisturizers if the temperature is below nineteen degrees and sunscreens if the temperature is above thirty-four degrees.

# Steps:

Given: The weather shopper site is opened and the temperature is checked.
When: I decide to buy products based on the temperature.
And: I add the appropriate items to the cart.
Then: I should click the cart icon to proceed.
And: I verify that the shopping cart looks correct.
And: I fill out my payment details and submit the form.


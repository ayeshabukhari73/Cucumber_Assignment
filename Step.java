package com.tau.steps;
import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.net.URL;
import junit.framework.Assert;

public class Step {
    private WebDriver driver;
    private int temp;
    private static ExtentReports extent;
    private static ExtentTest test;
    @Before
    public void setUp()throws MalformedURLException  {
    	 DesiredCapabilities capabilities = new DesiredCapabilities();
         capabilities.setBrowserName("firefox");
         
         driver = new RemoteWebDriver(new URL("http://172.16.2.83:4444/wd/hub"), capabilities);
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("Report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        test = extent.createTest("Weather Shopper Test", "Test to verify shopping on Weather Shopper site based on temperature");
    
     
    }

    @Given("the weather shopper site is opened and temperature is checked")
    public void the_weather_shopper_site_is_opened_and_temperature_is_checked() {
        driver.get("https://weathershopper.pythonanywhere.com/");
        WebElement temperatureElement = driver.findElement(By.id("temperature"));
        String value = temperatureElement.getText().replaceAll("[^\\d]", "").trim();
        temp = Integer.parseInt(value);
        System.out.println("The temperature is: " + temp + "°C");
        test.pass("Opened Weather Shopper site and checked temperature: " + temp + "°C");
    }

    @When("I decide to buy products based on the temperature")
    public void i_decide_to_buy_products_based_on_the_temperature() {
        if (temp < 19) {
            driver.findElement(By.cssSelector("body > div > div.row.justify-content-center.top-space-20 > div:nth-child(1) > a > button")).click();
            test.pass("Clicked to buy moisturizers");
        } else if (temp > 34) {
            driver.findElement(By.cssSelector("body > div > div.row.justify-content-center.top-space-20 > div.text-center.col-4.offset-4 > a > button")).click();
            test.pass("Clicked to buy sunscreens");
        }
    }

    @And("I add the appropriate items to the cart")
    public void i_add_the_appropriate_items_to_the_cart() {
        if (temp < 19) {
            // Add least expensive Aloe moisturizer
            addLeastExpensiveProduct("Aloe");
            // Add least expensive Almond moisturizer
            addLeastExpensiveProduct("Almond");
            test.pass("Added least expensive Aloe and Almond moisturizers to the cart");
        } else if (temp > 34) {
            // Add least expensive SPF-30 sunscreen
            addLeastExpensiveProduct("SPF-30");
            // Add least expensive SPF-50 sunscreen
            addLeastExpensiveProduct("SPF-50");
            test.pass("Added least expensive SPF-30 and SPF-50 sunscreens to the cart");
        }
    }

    @Then("I should click the cart icon to proceed")
    public void i_should_click_the_cart_icon_to_proceed() {
        driver.findElement(By.id("cart")).click();
        test.pass("Clicked the cart icon to proceed");
    }

    @And("I verify that the shopping cart looks correct")
    public void i_verify_that_the_shopping_cart_looks_correct() {
        // Add your verification logic here
        WebElement totalElement = driver.findElement(By.xpath("//p[contains(text(), 'Total:')]"));
        String totalText = totalElement.getText();
        System.out.println("Total Amount: " + totalText);
        test.pass("Verified the shopping cart. Total Amount: " + totalText);
        // Additional checks for cart items can be added here
    }

    @And("I fill out my payment details and submit the form")
    public void i_fill_out_my_payment_details_and_submit_the_form() throws InterruptedException {
    	WebElement item1PriceElement = driver.findElement(By.xpath("//tr[1]/td[2]"));
        WebElement item2PriceElement = driver.findElement(By.xpath("//tr[2]/td[2]"));
        WebElement totalPriceElement = driver.findElement(By.cssSelector("#total"));

        // Get the text content of the price elements and convert to integers
        int item1Price = Integer.parseInt(item1PriceElement.getText().trim());
        int item2Price = Integer.parseInt(item2PriceElement.getText().trim());
        int totalPrice = Integer.parseInt(totalPriceElement.getText().replaceAll("[^0-9]", "").trim());

        // Assert that the sum of individual prices equals the total price
        assertEquals("Total price should be the sum of individual prices", item1Price + item2Price, totalPrice);
        Thread.sleep(2000);
        System.out.println("ASSERTION CHECKED");
        driver.findElement(By.cssSelector("body > div.container.top-space-50")).click();
        driver.findElement(By.cssSelector("body > div.container.top-space-50 > div.row.top-space-30 > form > button > span")).click();
        driver.switchTo().frame(0); // Switch to the payment iframe
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("#email")).sendKeys("ayesha.bukhari@emumba.com");
        Thread.sleep(2000);
        driver.findElement(By.id("card_number")).sendKeys("4242424242424242");
        Thread.sleep(2000);
        driver.findElement(By.id("cc-exp")).sendKeys("12/26");
        Thread.sleep(2000);
        driver.findElement(By.id("cc-csc")).sendKeys("123");
       //driver.findElement(By.id("billing-zip")).sendKeys("46000");
       Thread.sleep(2000);
        driver.findElement(By.cssSelector("#submitButton > span > span")).click();
        System.out.println("click being done");
        WebElement thankYou = driver.findElement(By.cssSelector("body > div > div:nth-child(1) > h2"));
        Assert.assertEquals(thankYou.getText(), "PAYMENT SUCCESS");
        test.pass("Filled out payment details and submitted the form");
        
       
    }

    private void addLeastExpensiveProduct(String productType) {
        List<WebElement> products = driver.findElements(By.xpath("//p[contains(text(), '" + productType + "')]/following-sibling::p[contains(text(), 'Price')]/following-sibling::button"));
        WebElement cheapestProduct = products.stream()
                .min((p1, p2) -> {
                    int price1 = Integer.parseInt(p1.findElement(By.xpath("preceding-sibling::p[contains(text(), 'Price')]")).getText().replaceAll("[^\\d]", "").trim());
                    int price2 = Integer.parseInt(p2.findElement(By.xpath("preceding-sibling::p[contains(text(), 'Price')]")).getText().replaceAll("[^\\d]", "").trim());
                    return Integer.compare(price1, price2);
                })
                .orElseThrow(() -> new RuntimeException("No products found for type: " + productType));
        cheapestProduct.click();
    }
    @After
    public void quitting() {
    	driver.quit();
    	extent.flush();
    }
}

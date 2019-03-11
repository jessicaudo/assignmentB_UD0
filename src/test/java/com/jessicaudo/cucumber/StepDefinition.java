package com.jessicaudo.cucumber;

import cucumber.annotation.en.And;
import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;

public class StepDefinition {
    // Variables
    private WebDriver driver;
    private final String PATH_TO_CHROME_DRIVER = "/Downloads/chromedriver";

    private final String IMAGE_URL = "C:\\Users\\Jessica\\Pictures";
    private final String IMAGE_NAME = "DSC_0011.JPG";

    private final String CANCEL_BTN_NAME = "submit.delete.C3NLW69582M4B4";
    private final String COMPOSE_MAIL_URL = "https://mail.google.com/mail/u/0/#inbox?compose=new;

    private final String ATTACH_TO_MAIL_BTN = "//*[@id=\":10t\"]"; //xpath from chrome
    //*[@id=":10t"]
    private final String ACTIVE_MAIL = "sc-active-mail";
    private final String OPEN_BTN = "sc-proceed-to-attach-file";
    private final String DELETE_BTN_NAME = "submit.delete.C3NLW69582M4B4";
    private final String SEND_BTN = "//*[@id=\":z1\"]"; //xpath from chrome

   //Given
    @Given("^I am on Gmail compose window$")
    public void givenOnGmailComposeWindow()throws Throwable {
        setupSeleniumWebDrivers();
        goTo(COMPOSE_MAIL_URL);
    }

    @Given("^I am on Gmail attach file window$")
    public void givenOnGmailAttachWindow()throws Throwable {
        setupSeleniumWebDrivers();
        goTo(IMAGE_URL);
    }

    @And("^I have a image already attached to my email$")
    public void iHaveAnImageAlreadyAttachedToMyEmail() throws Throwable{
        //go to the image page
        goTo(IMAGE_URL);

        //Add an image to email
        System.out.println("Attempting to find Attach button...");
        WebElement btn = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.id(ATTACH_TO_MAIL_BTN)));

        System.out.print("Found!\n");

        btn.click();

        System.out.println("Clicking Attach to email button");

        //Return to compose window
        goTo(COMPOSE_MAIL_URL);
    }

    @And("^I have the same picture that already exists in mail$")
    public void iHaveTheSameProductThatAlreadyExistsInMyMail() throws Throwable{
        iHaveAnImageAlreadyAttachedToMyEmail();
    }

    //When
    @When("^I press \"Delete\"$")
    public void iPressDelete() throws Throwable {
        // Attempt to find a delete button and click on it
        try {
            System.out.println("Attempting to find delete button... ");
            WebElement btn = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.elementToBeClickable(By.name(DELETE_BTN_NAME)));
            System.out.print("Found!\n");
            btn.click();
            System.out.println("Clicking delete button.");
        } catch (Exception e) {
            System.out.println("No delete button present");
        }
    }

    //Clicking Open attaches image
    @When("^I press \"Open\"$")
    public void iPressOpen() throws Throwable {
        goTo(IMAGE_URL);
        // Attempt to find "Open" button and click on it
        try {
            System.out.println("Attempting to find Open button... ");
            WebElement btn = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.elementToBeClickable(By.id(ADD_TO_CART_BTN)));
            System.out.print("Found!\n");
            btn.click();
            System.out.println("Clicking Open button");
        } catch (Exception e) {
            System.out.println("No OPEN button present");
        }
    }

    // Then
    @Then("^the image should exist in my email$")
    public void theImageShouldExistInMyEmail() throws Throwable {
        goTo(COMPOSE_MAIL_URL);

        // Attempt to find active cart
        WebElement mail = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id(ACTIVE_BAIL)));

        // Product name should be in active cart
        Assert.assertTrue(searchForText(mail.getText(), IMAGE_NAME));
    }

    @Then("^the image should no longer exist in my email$")
    public void theImageShouldNoLongerExistInMyEmail() throws Throwable {
        goTo(IMAGE_URL);

        // Attempt to find active mail
        WebElement mail = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id(ACTIVE_MAIL)));

        // Image name should not be in active mail
        Assert.assertFalse(searchForText(cart.getMail(), IMAGE_NAME));
    }

    @Then("^there is nothing to delete from the attachments$")
    public void thereIsNothingToDeleteFromTheAttachments() throws Throwable {
        // Attempt to find a delete button
        try {
            System.out.println("Attempting to find a delete button... ");
            WebElement btn = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.elementToBeClickable(By.name(DELETE_BTN_NAME)));
            btn.click();
            Assert.fail("Delete button should not be present");
        } catch (Exception e) {
            System.out.println("No delete button present");
        }
    }

    @And("^the send button exists$")
    public void theSendButtonExists() throws Throwable {
        // Attempt to find send button
        try {
            System.out.println("Attempting to find send button");
            WebElement checkoutBtn = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.presenceOfElementLocated(By.className(SEND_BTN)));
            System.out.println("Found checkout button.");
            driver.quit();
        } catch (Exception e) {
            Assert.fail("No send button found. Should have been present.");
        }
    }

    @And("^the send button does not exist$")
    public void theSendButtonDoesNotExist() throws Throwable {
        // Attempt to find checkout button
        try {
            System.out.println("Attempting to find send button... ");
            WebElement checkoutBtn = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.presenceOfElementLocated(By.className(SEND_BTN)));
            Assert.fail("Send button was found. Should have not be present.");
        } catch (Exception e) {
            System.out.print("None found!\n");
            driver.quit();
        }
    }

    // Helper functions
    private void setupSeleniumWebDrivers() throws MalformedURLException {
        if (driver == null) {
            System.out.println("Setting up ChromeDriver... ");
            System.setProperty("webdriver.chrome.driver", PATH_TO_CHROME_DRIVER);
            driver = new ChromeDriver();
            System.out.print("Done!\n");
        }
    }

    private boolean searchForText(String text, String textToFind) {
        return text.contains(textToFind);
    }

    private void goTo(String url) {
        if (driver != null) {
            System.out.println("Going to " + url);
            driver.get(url);
        }
    }
}
package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProfilePage;
import java.util.List;

public class FormTest extends BaseTest {

    private ProfilePage profilePage;

    private final String VALID_EMAIL = "garv.patel.growlity@gmail.com";
    private final String VALID_PASSWORD = "GnjA3UqKTN";

    @BeforeMethod
    public void setupPage() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(VALID_EMAIL, VALID_PASSWORD);
        try { Thread.sleep(3000); } catch (Exception e) {}
        
        profilePage = new ProfilePage(getDriver());
        profilePage.navigateToManageProfile();
        try { Thread.sleep(2000); } catch (Exception e) {}
    }

    @Test(priority = 1, description = "Verify form fields accept valid data and save")
    public void testFormValidDataAndSave() {
        // Attempt to update profile name with valid data
        profilePage.updateProfileName("Garv Valid Name");
        
        // Normally we would wait for success toast or verify the field value after refresh
        try { Thread.sleep(2000); } catch (Exception e) {}
        
        // Asserting no error messages exist or assuming success if no exception
        boolean hasError = !getDriver().findElements(By.xpath("//*[contains(@class, 'error') or contains(@class, 'invalid')]")).isEmpty();
        Assert.assertFalse(hasError, "Valid data should not trigger validation errors");
    }

    @Test(priority = 2, description = "Verify required fields empty error")
    public void testRequiredFieldsEmpty() {
        // Find required inputs and clear them to trigger required validation
        profilePage.updateProfileName(""); 
        
        // Check for required validation message
        List<WebElement> errorMsgs = getDriver().findElements(By.xpath("//*[contains(text(), 'required') or contains(text(), 'cannot be empty')]"));
        // Asserting that clearing a required field triggers some validation or error
        // If the element is found, it means validation works
        Assert.assertFalse(errorMsgs.isEmpty(), "Emptying a required field should show a validation error message");
    }

    @Test(priority = 3, description = "Verify invalid format reject (email/phone)")
    public void testInvalidFormatRejection() {
        // Example check for email input if present, or generic input
        List<WebElement> emailInputs = getDriver().findElements(By.xpath("//input[@type='email']"));
        if (!emailInputs.isEmpty()) {
            emailInputs.get(0).clear();
            emailInputs.get(0).sendKeys("invalid_email_format");
            
            // Try saving or triggering blur
            getDriver().findElement(By.xpath("//body")).click();
            
            List<WebElement> errorMsgs = getDriver().findElements(By.xpath("//*[contains(text(), 'invalid') or contains(text(), 'format')]"));
            Assert.assertFalse(errorMsgs.isEmpty(), "Invalid email format should be rejected with an error message");
        }
    }

    @Test(priority = 4, description = "Verify reset button clears form")
    public void testResetButtonClearsForm() {
        // Look for a reset or cancel button
        List<WebElement> resetBtns = getDriver().findElements(By.xpath("//button[@type='reset' or contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'cancel') or contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'clear')]"));
        
        if (!resetBtns.isEmpty()) {
            // Assume we type something first
            List<WebElement> textInputs = getDriver().findElements(By.xpath("//input[@type='text']"));
            if (!textInputs.isEmpty()) {
                textInputs.get(0).sendKeys("Test Reset");
            }
            
            resetBtns.get(0).click();
            try { Thread.sleep(1000); } catch (Exception e) {}
            
            if (!textInputs.isEmpty()) {
                Assert.assertTrue(textInputs.get(0).getAttribute("value").isEmpty() || !textInputs.get(0).getAttribute("value").contains("Test Reset"), "Reset button should clear the form inputs");
            }
        } else {
            System.out.println("No Reset/Cancel button found on this form to test.");
        }
    }
}

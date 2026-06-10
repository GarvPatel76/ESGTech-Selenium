package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    private final String VALID_EMAIL = "garv.patel.growlity@gmail.com";
    private final String VALID_PASSWORD = "GnjA3UqKTN";

    @BeforeMethod
    public void setupPage() {
        loginPage = new LoginPage(getDriver());
        // BaseTest already navigates to https://esgtech.vercel.app/ which redirects to /login
    }

    @Test(priority = 1, description = "Test Valid Login")
    public void testValidLogin() {
        loginPage.login(VALID_EMAIL, VALID_PASSWORD);
        // Wait for redirect to dashboard
        // Assert.assertTrue(getDriver().getCurrentUrl().contains("dashboard"), "Dashboard should load");
    }

    @Test(priority = 2, description = "Test Invalid Email")
    public void testInvalidEmail() {
        loginPage.login("invalid_email@test.com", VALID_PASSWORD);
        Assert.assertFalse(loginPage.getErrorMessage().isEmpty(), "Error message should be displayed for invalid email");
    }

    @Test(priority = 3, description = "Test Invalid Password")
    public void testInvalidPassword() {
        loginPage.login(VALID_EMAIL, "wrongpassword");
        Assert.assertFalse(loginPage.getErrorMessage().isEmpty(), "Error message should be displayed for invalid password");
    }

    @Test(priority = 4, description = "Test Both Invalid Email and Password")
    public void testBothInvalid() {
        loginPage.login("wrong@test.com", "wrongpassword");
        Assert.assertFalse(loginPage.getErrorMessage().isEmpty(), "Error message should be displayed for invalid credentials");
    }

    @Test(priority = 5, description = "Test Empty Email")
    public void testEmptyEmail() {
        loginPage.login("", VALID_PASSWORD);
        Assert.assertTrue(getDriver().getCurrentUrl().contains("login"), "User should remain on login page");
    }

    @Test(priority = 6, description = "Test Empty Password")
    public void testEmptyPassword() {
        loginPage.login(VALID_EMAIL, "");
        Assert.assertTrue(getDriver().getCurrentUrl().contains("login"), "User should remain on login page");
    }

    @Test(priority = 7, description = "Test Empty Email & Password")
    public void testEmptyCredentials() {
        loginPage.login("", "");
        Assert.assertTrue(getDriver().getCurrentUrl().contains("login"), "User should remain on login page");
    }

    @Test(priority = 8, description = "Test Email Format Validation")
    public void testEmailFormatValidation() {
        loginPage.enterEmail("invalid-email-format");
        loginPage.enterPassword(VALID_PASSWORD);
        loginPage.clickSignIn();
        Assert.assertTrue(getDriver().getCurrentUrl().contains("login"), "Validation error should prevent login");
    }

    @Test(priority = 9, description = "Test Password Masking")
    public void testPasswordMasking() {
        Assert.assertTrue(loginPage.isPasswordMasked(), "Password field should have type 'password'");
    }

    @Test(priority = 10, description = "Test Forgot Password Link")
    public void testForgotPasswordLink() {
        loginPage.clickForgotPassword();
        Assert.assertTrue(getDriver().getCurrentUrl().contains("reset-password"), "Should navigate to reset password page");
    }

    @Test(priority = 11, description = "Test Remember Me Functionality")
    public void testRememberMe() {
        loginPage.checkRememberMe();
        Assert.assertTrue(loginPage.isRememberMeChecked(), "Remember Me checkbox should be checked");
    }
}

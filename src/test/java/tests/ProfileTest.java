package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProfilePage;

public class ProfileTest extends BaseTest {

    private ProfilePage profilePage;
    private LoginPage loginPage;

    private final String VALID_EMAIL = "garv.patel.growlity@gmail.com";
    private final String VALID_PASSWORD = "GnjA3UqKTN";

    @BeforeMethod
    public void setupPage() {
        loginPage = new LoginPage(getDriver());
        profilePage = new ProfilePage(getDriver());
        loginPage.login(VALID_EMAIL, VALID_PASSWORD);
    }

    @Test(priority = 1, description = "Test Navigate to Profile")
    public void testNavigateToProfile() {
        profilePage.navigateToManageProfile();
        Assert.assertTrue(getDriver().getPageSource().contains("Profile") || getDriver().getCurrentUrl().contains("profile"), "Should navigate to Profile page");
    }

    @Test(priority = 2, description = "Test Edit Profile")
    public void testEditProfile() {
        profilePage.navigateToManageProfile();
        profilePage.updateProfileName("Garv Patel (Test)");
    }

    @Test(priority = 3, description = "Test Edit Organization")
    public void testEditOrganization() {
        profilePage.navigateToManageProfile();
        profilePage.updateOrganization("Growlity - overall-testing1");
    }
}

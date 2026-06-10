package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;

public class LogoutTest extends BaseTest {

    private DashboardPage dashboardPage;
    private LoginPage loginPage;

    private final String VALID_EMAIL = "garv.patel.growlity@gmail.com";
    private final String VALID_PASSWORD = "GnjA3UqKTN";

    @BeforeMethod
    public void setupPage() {
        loginPage = new LoginPage(getDriver());
        dashboardPage = new DashboardPage(getDriver());
        loginPage.login(VALID_EMAIL, VALID_PASSWORD);
    }

    @Test(priority = 1, description = "Test Successful Logout")
    public void testSuccessfulLogout() {
        dashboardPage.clickLogout();
        Assert.assertTrue(getDriver().getCurrentUrl().contains("login"), "User should be redirected to login page after logout");
    }

    @Test(priority = 2, description = "Test Browser Back Button After Logout")
    public void testBrowserBackButtonAfterLogout() {
        dashboardPage.clickLogout();
        // Attempt to go back
        getDriver().navigate().back();
        // Since session is terminated, it should still redirect to login or not allow access
        Assert.assertFalse(dashboardPage.isDashboardLoaded(), "Dashboard should not load after logging out and clicking back");
    }
}

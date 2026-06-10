package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;

public class DashboardTest extends BaseTest {

    private DashboardPage dashboardPage;
    private LoginPage loginPage;

    // TODO: Replace with valid test credentials
    private final String VALID_EMAIL = "test@example.com";
    private final String VALID_PASSWORD = "Password123!";

    @BeforeMethod
    public void setupPage() {
        loginPage = new LoginPage(getDriver());
        dashboardPage = new DashboardPage(getDriver());
        // Login before running dashboard tests
        loginPage.login(VALID_EMAIL, VALID_PASSWORD);
        
        // Handle scenario where login fails due to dummy credentials
        // If we are still on the login page after login attempt, we skip the test or let it fail naturally on assertion.
    }

    @Test(priority = 1, description = "Test Dashboard Loads Successfully")
    public void testDashboardLoads() {
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard header should be visible");
    }

    @Test(priority = 2, description = "Test Dashboard Widgets Visible")
    public void testDashboardWidgetsVisible() {
        Assert.assertTrue(dashboardPage.areWidgetsVisible(), "Dashboard widgets should be visible");
    }

    @Test(priority = 3, description = "Test Dashboard Navigation Links (Sidebar)")
    public void testSidebarVisible() {
        Assert.assertTrue(dashboardPage.isSidebarVisible(), "Sidebar navigation should be visible on dashboard");
    }

    // Add more dashboard tests as required by Phase 1 (Data load, refresh, cards clickable, etc.)
}

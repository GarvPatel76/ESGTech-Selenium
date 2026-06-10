package tests;

import base.BaseTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class SecurityUITest extends BaseTest {

    private final String VALID_EMAIL = "garv.patel.growlity@gmail.com";
    private final String VALID_PASSWORD = "GnjA3UqKTN";

    @Test(priority = 1, description = "Test Direct URL Access Without Login")
    public void testDirectUrlAccessWithoutLogin() {
        getDriver().get("https://esgtech.vercel.app/dashboard");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(getDriver().getCurrentUrl().contains("login"), "Protected URLs should redirect to login if session is invalid");
    }

    @Test(priority = 2, description = "Test Access Protected Page After Logout")
    public void testAccessAfterLogout() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(VALID_EMAIL, VALID_PASSWORD);
        try { Thread.sleep(2000); } catch (Exception e) {}
        
        pages.DashboardPage dashboardPage = new pages.DashboardPage(getDriver());
        dashboardPage.clickLogout();
        
        getDriver().get("https://esgtech.vercel.app/dashboard");
        try { Thread.sleep(2000); } catch (Exception e) {}
        Assert.assertTrue(getDriver().getCurrentUrl().contains("login"), "Should not access dashboard after logout");
    }

    @Test(priority = 3, description = "Test Session Maintained After Login")
    public void testSessionMaintenance() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(VALID_EMAIL, VALID_PASSWORD);
        try { Thread.sleep(2000); } catch (Exception e) {}
        
        getDriver().navigate().refresh();
        Assert.assertTrue(getDriver().getCurrentUrl().contains("dashboard"), "Session should be maintained after refresh");
    }

    @Test(priority = 4, description = "Test Multiple Tabs Login Behavior")
    public void testMultipleTabsLogin() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(VALID_EMAIL, VALID_PASSWORD);
        try { Thread.sleep(2000); } catch (Exception e) {}
        
        String originalWindow = getDriver().getWindowHandle();
        getDriver().switchTo().newWindow(WindowType.TAB);
        getDriver().get("https://esgtech.vercel.app/dashboard");
        try { Thread.sleep(2000); } catch (Exception e) {}
        
        Assert.assertTrue(getDriver().getCurrentUrl().contains("dashboard"), "Session should be shared across tabs");
        
        getDriver().close();
        getDriver().switchTo().window(originalWindow);
    }
    
    @Test(priority = 5, description = "Test Session Expiry (Simulated)")
    public void testSessionExpiry() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(VALID_EMAIL, VALID_PASSWORD);
        try { Thread.sleep(2000); } catch (Exception e) {}
        
        // Simulate session expiry by clearing cookies/local storage
        getDriver().manage().deleteAllCookies();
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.localStorage.clear();");
        js.executeScript("window.sessionStorage.clear();");
        
        getDriver().navigate().refresh();
        try { Thread.sleep(2000); } catch (Exception e) {}
        Assert.assertTrue(getDriver().getCurrentUrl().contains("login"), "Should auto logout on session expiry");
    }
}

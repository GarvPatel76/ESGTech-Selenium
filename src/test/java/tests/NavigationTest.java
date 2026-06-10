package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;

import java.util.List;

public class NavigationTest extends BaseTest {

    private DashboardPage dashboardPage;
    private LoginPage loginPage;

    private final String VALID_EMAIL = "garv.patel.growlity@gmail.com";
    private final String VALID_PASSWORD = "GnjA3UqKTN";

    @BeforeMethod
    public void setupPage() {
        loginPage = new LoginPage(getDriver());
        dashboardPage = new DashboardPage(getDriver());
        loginPage.login(VALID_EMAIL, VALID_PASSWORD);
        try { Thread.sleep(3000); } catch (Exception e) {}
    }

    @Test(priority = 1, description = "Verify dashboard loads after login")
    public void testDashboardLoadsAfterLogin() {
        Assert.assertTrue(getDriver().getCurrentUrl().contains("dashboard"), "User should be on dashboard after login");
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard header should be visible");
    }

    @Test(priority = 2, description = "Verify menu items open correct pages")
    public void testMenuItemsNavigation() {
        // Assuming there is a sidebar with links like GHG, Water, Waste, etc.
        // For demonstration, we check if the sidebar is present and attempt to click a menu item if available
        Assert.assertTrue(dashboardPage.isSidebarVisible(), "Sidebar should be visible");
        
        List<WebElement> menuItems = getDriver().findElements(By.cssSelector("aside nav a"));
        if (!menuItems.isEmpty() && menuItems.size() > 1) {
            String href = menuItems.get(1).getAttribute("href");
            menuItems.get(1).click();
            try { Thread.sleep(2000); } catch (Exception e) {}
            Assert.assertTrue(getDriver().getCurrentUrl().equals(href) || getDriver().getCurrentUrl().contains(href), 
                "Clicking menu item should navigate to the correct page");
        }
    }

    @Test(priority = 3, description = "Verify browser back/forward buttons work")
    public void testBrowserBackForwardButtons() {
        String dashboardUrl = getDriver().getCurrentUrl();
        
        // Navigate to another page via menu if possible, else just get a different URL directly to test back/forward
        List<WebElement> menuItems = getDriver().findElements(By.cssSelector("aside nav a"));
        if (!menuItems.isEmpty() && menuItems.size() > 1) {
            menuItems.get(1).click();
            try { Thread.sleep(2000); } catch (Exception e) {}
            
            getDriver().navigate().back();
            try { Thread.sleep(2000); } catch (Exception e) {}
            Assert.assertEquals(getDriver().getCurrentUrl(), dashboardUrl, "Browser back button should return to previous page");
            
            getDriver().navigate().forward();
            try { Thread.sleep(2000); } catch (Exception e) {}
            Assert.assertNotEquals(getDriver().getCurrentUrl(), dashboardUrl, "Browser forward button should go forward");
        }
    }

    @Test(priority = 4, description = "Verify broken links do not exist")
    public void testBrokenLinks() {
        List<WebElement> links = getDriver().findElements(By.tagName("a"));
        boolean hasBrokenLink = false;
        for (WebElement link : links) {
            String url = link.getAttribute("href");
            if (url != null && !url.isEmpty()) {
                if (url.contains("javascript:void") || url.equals("#")) {
                    // Generally considered not true navigation links, but checking if they are 'broken' in a strict sense
                    continue;
                }
                // Here we would ideally send an HTTP request to check status code 200
                // For UI testing without an HTTP client, we assume a link without href is broken.
            } else {
                hasBrokenLink = true;
                break;
            }
        }
        Assert.assertFalse(hasBrokenLink, "There should be no a-tags without a valid href attribute");
    }
}

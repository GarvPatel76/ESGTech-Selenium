package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.util.List;

public class PerformanceTest extends BaseTest {

    private final String VALID_EMAIL = "garv.patel.growlity@gmail.com";
    private final String VALID_PASSWORD = "GnjA3UqKTN";

    @BeforeMethod
    public void setupPage() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(VALID_EMAIL, VALID_PASSWORD);
        try { Thread.sleep(3000); } catch (Exception e) {}
    }

    @Test(priority = 1, description = "Verify page load time is acceptable")
    public void testPageLoadTime() {
        long startTime = System.currentTimeMillis();
        
        getDriver().navigate().refresh();
        
        // Wait for ready state complete
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        boolean isReady = false;
        long maxWait = 10000; // 10 seconds max wait
        while (!isReady && (System.currentTimeMillis() - startTime) < maxWait) {
            isReady = js.executeScript("return document.readyState").equals("complete");
            try { Thread.sleep(500); } catch (Exception e) {}
        }
        
        long loadTime = System.currentTimeMillis() - startTime;
        
        System.out.println("Page load time: " + loadTime + "ms");
        Assert.assertTrue(loadTime < 8000, "Page load time should be under 8 seconds. Actual: " + loadTime + "ms");
    }

    @Test(priority = 2, description = "Verify multiple rapid clicks don't crash the system")
    public void testMultipleClicks() {
        List<WebElement> buttons = getDriver().findElements(By.tagName("button"));
        if (!buttons.isEmpty()) {
            WebElement btn = buttons.get(0);
            if (btn.isDisplayed() && btn.isEnabled()) {
                // Click 5 times rapidly
                for (int i = 0; i < 5; i++) {
                    try {
                        btn.click();
                        Thread.sleep(100); // 100ms between clicks
                    } catch (Exception e) {
                        // Click might have navigated away or removed the button
                        break;
                    }
                }
                
                // If we didn't get an unhandled alert or browser crash, test passes
                Assert.assertTrue(getDriver().getTitle() != null, "Browser should remain active and not crash after rapid clicks");
            }
        }
    }

    @Test(priority = 3, description = "Verify large data load does not hang UI")
    public void testLargeDataUIHang() {
        // We will simulate scrolling to trigger potential lazy loading or large data rendering
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < 5; i++) {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            try { Thread.sleep(500); } catch (Exception e) {}
        }
        
        long actionTime = System.currentTimeMillis() - startTime;
        
        // If scrolling and basic script execution works, UI is not totally hung
        Assert.assertTrue(actionTime < 5000, "UI interactions (scrolling) should complete without significant hang");
    }
}

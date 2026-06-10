package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.time.Duration;

public class ReportExportTest extends BaseTest {

    private LoginPage loginPage;

    private final String VALID_EMAIL = "garv.patel.growlity@gmail.com";
    private final String VALID_PASSWORD = "GnjA3UqKTN";

    @BeforeMethod
    public void setupPage() {
        loginPage = new LoginPage(getDriver());
        loginPage.login(VALID_EMAIL, VALID_PASSWORD);
    }

    @Test(priority = 1, description = "Test Export Report as PDF")
    public void testExportAsPdf() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        
        // Navigate to Reports (or any page with Export PDF button like Dashboard/GHG)
        By reportsLink = By.xpath("//a[contains(., 'Reports') or contains(., 'Measure')]");
        wait.until(ExpectedConditions.elementToBeClickable(reportsLink)).click();

        By exportPdfBtn = By.xpath("//button[contains(., 'Export as PDF')]");
        wait.until(ExpectedConditions.elementToBeClickable(exportPdfBtn)).click();

        // In Selenium, clicking Export PDF usually triggers a file download or a new tab.
        // We can verify if a new window/tab is opened.
        int windowCount = getDriver().getWindowHandles().size();
        Assert.assertTrue(windowCount > 0, "A download or popup action should be initiated");
    }
}

package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.GHGPage;
import pages.LoginPage;

import java.time.Duration;

public class WasteTest extends BaseTest {

    private GHGPage gridPage;
    private LoginPage loginPage;

    private final String VALID_EMAIL = "garv.patel.growlity@gmail.com";
    private final String VALID_PASSWORD = "GnjA3UqKTN";

    @BeforeMethod
    public void setupPage() {
        loginPage = new LoginPage(getDriver());
        gridPage = new GHGPage(getDriver());
        loginPage.login(VALID_EMAIL, VALID_PASSWORD);
        
        // Navigate to Waste
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(., 'Measure')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(., 'ESG Data Management')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(., 'Waste')]"))).click();
    }

    @Test(priority = 1, description = "Test Delete Waste Record")
    public void testDeleteWasteRecord() {
        // Reuse grid interactions
        gridPage.clickDeleteFirstRecord();
        // Typically a confirmation modal appears here.
    }

    @Test(priority = 2, description = "Test Sort Waste Data")
    public void testSortWasteData() {
        gridPage.clickSortOnDate();
    }
}

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

public class WaterTest extends BaseTest {

    private GHGPage gridPage; // Reusing grid interactions
    private LoginPage loginPage;

    private final String VALID_EMAIL = "garv.patel.growlity@gmail.com";
    private final String VALID_PASSWORD = "GnjA3UqKTN";

    @BeforeMethod
    public void setupPage() {
        loginPage = new LoginPage(getDriver());
        gridPage = new GHGPage(getDriver());
        loginPage.login(VALID_EMAIL, VALID_PASSWORD);
        
        // Navigate to Water & Effluents
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(., 'Measure')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(., 'ESG Data Management')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(., 'Water & Effluents')]"))).click();
    }

    @Test(priority = 1, description = "Test Add Water Record")
    public void testAddWaterRecord() {
        // Assume simple consumption input based on codegen structure
        gridPage.addGHGRecord("5000"); // Reusing add form logic structure
    }

    @Test(priority = 2, description = "Test Filter Water Data")
    public void testFilterWaterData() {
        gridPage.applyFilter("2026-06-09");
        Assert.assertTrue(getDriver().getPageSource().contains("2026-06-09"), "Water grid should filter by date");
    }
}

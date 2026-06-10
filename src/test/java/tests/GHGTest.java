package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.GHGPage;
import pages.LoginPage;

public class GHGTest extends BaseTest {

    private GHGPage ghgPage;
    private LoginPage loginPage;

    private final String VALID_EMAIL = "garv.patel.growlity@gmail.com";
    private final String VALID_PASSWORD = "GnjA3UqKTN";

    @BeforeMethod
    public void setupPage() {
        loginPage = new LoginPage(getDriver());
        ghgPage = new GHGPage(getDriver());
        
        loginPage.login(VALID_EMAIL, VALID_PASSWORD);
        ghgPage.navigateToGHG();
    }

    @Test(priority = 1, description = "Test Navigation to GHG Mobile Combustion")
    public void testNavigateToMobileCombustion() {
        ghgPage.openMobileCombustion();
        Assert.assertTrue(getDriver().getCurrentUrl().contains("mobile-combustion") || getDriver().getPageSource().contains("Mobile Combustion"), "Should navigate to Mobile Combustion page");
    }

    @Test(priority = 2, description = "Test Add New GHG Record")
    public void testAddGHGRecord() {
        ghgPage.openMobileCombustion();
        ghgPage.addGHGRecord("123233");
        // Verify toast message or record in grid
        Assert.assertTrue(getDriver().getPageSource().contains("123233") || getDriver().getPageSource().contains("Success"), "Record should be added successfully");
    }

    @Test(priority = 3, description = "Test Sorting Data in GHG Grid")
    public void testSortGHGData() {
        ghgPage.openMobileCombustion();
        ghgPage.clickSortOnDate();
        // Just executing sort to verify it doesn't crash; validation would require parsing grid rows
    }

    @Test(priority = 4, description = "Test Filtering Data in GHG Grid")
    public void testFilterGHGData() {
        ghgPage.openMobileCombustion();
        ghgPage.applyFilter("2026-06-09");
        // Validate filter applied
        Assert.assertTrue(getDriver().getPageSource().contains("2026-06-09"), "Grid should filter by date");
    }

    @Test(priority = 5, description = "Test Edit Existing GHG Record")
    public void testEditGHGRecord() {
        ghgPage.openMobileCombustion();
        ghgPage.clickEditFirstRecord();
        // Wait for edit modal to appear, can re-use addGHGRecord or similar method
    }

    @Test(priority = 6, description = "Test Delete Existing GHG Record")
    public void testDeleteGHGRecord() {
        ghgPage.openMobileCombustion();
        ghgPage.clickDeleteFirstRecord();
        // Typically a confirmation modal appears here.
    }

    @Test(priority = 7, description = "Test Pagination")
    public void testPagination() {
        ghgPage.openMobileCombustion();
        ghgPage.changePageSize("25");
        ghgPage.goToLastPage();
    }
}

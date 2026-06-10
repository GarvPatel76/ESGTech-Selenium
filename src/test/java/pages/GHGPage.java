package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GHGPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Navigation Locators
    private By measureNav = By.xpath("//a[contains(., 'Measure')]");
    private By esgDataManagementBtn = By.xpath("//button[contains(., 'ESG Data Management')]");
    private By ghgEmissionBtn = By.xpath("//button[normalize-space()='GHG Emission']");
    private By mobileCombustionLink = By.xpath("//a[contains(., 'Mobile Combustion')]");

    // Form Locators (Add Record)
    private By selectTypeBtn = By.xpath("//button[contains(., 'Select Type')]");
    private By optionButane = By.xpath("//li[@role='option' and contains(., 'Butane')]");
    private By unitOfMeasurementDropdown = By.xpath("//div[contains(., 'Unit of Measurement') and contains(@class, 'select')] | //*[text()='Select Unit of Measurement']");
    private By optionTonnes = By.xpath("//li[@role='option' and contains(., 'tonnes')]");
    private By consumptionInput = By.xpath("//input[@type='number' or contains(@name, 'Consumption')]");
    private By submitBtn = By.xpath("//button[contains(., 'Submit')]");

    // AG Grid Locators (Filter, Sort, Edit, Delete)
    private By headerCells = By.cssSelector(".ag-header-cell-label");
    private By dateHeader = By.xpath("//div[@role='columnheader' and .//span[text()='Date']]");
    private By filterIcon = By.cssSelector(".ag-icon-filter, .ag-header-icon");
    private By sortDescIcon = By.cssSelector(".ag-icon-desc");
    private By filterValueInput = By.xpath("//input[@aria-label='Filter Value']");
    private By editBtn = By.xpath("//button[contains(., 'Edit')]");
    private By deleteBtn = By.xpath("//button[contains(., 'Delete')]");

    public GHGPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void navigateToGHG() {
        wait.until(ExpectedConditions.elementToBeClickable(measureNav)).click();
        wait.until(ExpectedConditions.elementToBeClickable(esgDataManagementBtn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(ghgEmissionBtn)).click();
    }
    
    public void openMobileCombustion() {
        wait.until(ExpectedConditions.elementToBeClickable(mobileCombustionLink)).click();
    }

    public void addGHGRecord(String consumption) {
        wait.until(ExpectedConditions.elementToBeClickable(selectTypeBtn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(optionButane)).click();

        try {
            wait.until(ExpectedConditions.elementToBeClickable(unitOfMeasurementDropdown)).click();
        } catch (Exception e) {
            // Dropdown locator might be slightly different based on codegen
            driver.findElement(By.xpath("//*[contains(text(), 'Unit of Measurement')]")).click();
        }
        wait.until(ExpectedConditions.elementToBeClickable(optionTonnes)).click();

        WebElement consInput = wait.until(ExpectedConditions.visibilityOfElementLocated(consumptionInput));
        consInput.clear();
        consInput.sendKeys(consumption);

        wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();
    }

    public void clickSortOnDate() {
        wait.until(ExpectedConditions.elementToBeClickable(dateHeader)).click();
    }

    public void applyFilter(String value) {
        wait.until(ExpectedConditions.elementToBeClickable(filterIcon)).click();
        WebElement filterInput = wait.until(ExpectedConditions.visibilityOfElementLocated(filterValueInput));
        filterInput.clear();
        filterInput.sendKeys(value);
    }

    public void clickEditFirstRecord() {
        // Clicks the first edit button found in the grid
        wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
    }

    public void clickDeleteFirstRecord() {
        // Clicks the first delete button found in the grid
        wait.until(ExpectedConditions.elementToBeClickable(deleteBtn)).click();
    }
}

package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WastePage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By plasticInput = By.xpath("//input[contains(@aria-label, 'Plastic') or contains(@name, 'Plastic')] | //*[contains(text(), 'Plastic')]/following-sibling::input");
    private By eWasteInput = By.xpath("//input[contains(@aria-label, 'E-Waste') or contains(@name, 'E-Waste')] | //*[contains(text(), 'E-Waste')]/following-sibling::input");
    private By biomedicalInput = By.xpath("//input[contains(@aria-label, 'Bio-Medical') or contains(@name, 'Bio-Medical')] | //*[contains(text(), 'Bio-Medical')]/following-sibling::input");
    private By constructionInput = By.xpath("//input[contains(@aria-label, 'Construction') or contains(@name, 'Construction')] | //*[contains(text(), 'Construction')]/following-sibling::input");
    private By batteryInput = By.xpath("//input[contains(@aria-label, 'Battery') or contains(@name, 'Battery')] | //*[contains(text(), 'Battery')]/following-sibling::input");
    private By radioactiveInput = By.xpath("//input[contains(@aria-label, 'Radioactive') or contains(@name, 'Radioactive')] | //*[contains(text(), 'Radioactive')]/following-sibling::input");
    private By hazardousInput = By.xpath("//input[contains(@aria-label, 'Other Hazardous') or contains(@name, 'Other Hazardous')] | //*[contains(text(), 'Other Hazardous')]/following-sibling::input");
    private By nonHazardousInput = By.xpath("//input[contains(@aria-label, 'Non-Hazardous') or contains(@name, 'Non-Hazardous')] | //*[contains(text(), 'Non-Hazardous')]/following-sibling::input");
    private By submitBtn = By.xpath("//button[contains(., 'Submit')]");

    public WastePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void fillWasteGenerated(String plastic, String eWaste, String bio, String construction, String battery, String radioactive, String hazardous, String nonHazardous) {
        try {
            WebElement plasticElem = wait.until(ExpectedConditions.visibilityOfElementLocated(plasticInput));
            plasticElem.clear();
            plasticElem.sendKeys(plastic);

            driver.findElement(eWasteInput).sendKeys(eWaste);
            driver.findElement(biomedicalInput).sendKeys(bio);
            driver.findElement(constructionInput).sendKeys(construction);
            driver.findElement(batteryInput).sendKeys(battery);
            driver.findElement(radioactiveInput).sendKeys(radioactive);
            driver.findElement(hazardousInput).sendKeys(hazardous);
            driver.findElement(nonHazardousInput).sendKeys(nonHazardous);

            driver.findElement(submitBtn).click();
        } catch (Exception e) {
            System.out.println("Waste fields issue: " + e.getMessage());
        }
    }
}

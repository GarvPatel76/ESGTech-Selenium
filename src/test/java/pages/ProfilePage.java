package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators from Codegen
    private By userMenuBtn = By.xpath("//button[contains(., 'GP Garv Patel') or contains(., 'Garv')]");
    private By manageProfileBtn = By.xpath("//*[contains(text(), 'Manage Profile') or contains(text(), 'Profile')]");
    private By organizationBtn = By.xpath("//button[contains(., 'Organization') or contains(text(), 'Organization')]");
    
    // Profile Edit Locators (Assumed standard fields)
    private By nameInput = By.xpath("//input[contains(@name, 'name') or contains(@id, 'name')]");
    private By saveBtn = By.xpath("//button[contains(., 'Save') or contains(., 'Update')]");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateToManageProfile() {
        wait.until(ExpectedConditions.elementToBeClickable(userMenuBtn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(manageProfileBtn)).click();
    }

    public void updateProfileName(String newName) {
        try {
            WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput));
            nameField.clear();
            nameField.sendKeys(newName);
            wait.until(ExpectedConditions.elementToBeClickable(saveBtn)).click();
        } catch (Exception e) {
            System.out.println("Name input or save button not found on profile page.");
        }
    }

    public void updateOrganization(String companyName) {
        wait.until(ExpectedConditions.elementToBeClickable(organizationBtn)).click();
        WebElement companyInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@name, 'Company Name') or @aria-label='Company Name *'] | //*[contains(text(), 'Company Name')]/following-sibling::input")));
        
        try {
            companyInput.clear();
            companyInput.sendKeys(companyName);
            wait.until(ExpectedConditions.elementToBeClickable(saveBtn)).click();
        } catch (Exception e) {
            System.out.println("Organization fields issue");
        }
    }
}

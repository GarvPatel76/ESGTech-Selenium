package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators based on Playwright codegen
    private By dashboardHeader = By.xpath("//*[contains(text(), 'Dashboard') or contains(@aria-label, 'Dashboard')]");
    private By userProfileIcon = By.xpath("//button[contains(., 'Garv') or contains(., 'GVN')]");
    private By sidebarNav = By.xpath("//nav | //aside");
    private By widgetContainer = By.cssSelector(".recharts-layer"); // from codegen chart interaction
    private By logoutButton = By.xpath("//*[@role='menuitem' and contains(., 'Sign Out')]");
    private By reportsLink = By.xpath("//a[contains(., 'Reports')]");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isDashboardLoaded() {
        try {
            // Use userProfileIcon instead of dashboardHeader because the word "Dashboard" is also present on the login page
            return wait.until(ExpectedConditions.visibilityOfElementLocated(userProfileIcon)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean areWidgetsVisible() {
        return !driver.findElements(widgetContainer).isEmpty();
    }

    public boolean isSidebarVisible() {
        return driver.findElement(sidebarNav).isDisplayed();
    }

    public void clickReports() {
        wait.until(ExpectedConditions.elementToBeClickable(reportsLink)).click();
    }

    public void updateDashboardFilters(String option1, String option2) {
        try {
            // Wait for comboboxes to appear (Selenium alternative to nth(2))
            java.util.List<WebElement> comboboxes = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//select | //div[@role='combobox']")));
            if(comboboxes.size() >= 3) {
                // Simplified interaction assuming standard select or click-based combobox
                comboboxes.get(2).click();
                driver.findElement(By.xpath("//*[text()='" + option1 + "']")).click();
                
                comboboxes.get(3).click();
                driver.findElement(By.xpath("//*[text()='" + option2 + "']")).click();
            }
        } catch (Exception e) {
            System.out.println("Dashboard filters not interactable: " + e.getMessage());
        }
    }

    public void clickLogout() {
        try {
            // Ensure we are fully on dashboard before clicking
            wait.until(ExpectedConditions.urlContains("dashboard"));
            Thread.sleep(3000); // Wait for potential overlays to disappear
            
            WebElement profileBtn = wait.until(ExpectedConditions.presenceOfElementLocated(userProfileIcon));
            try {
                wait.until(ExpectedConditions.elementToBeClickable(profileBtn)).click();
            } catch (Exception e) {
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", profileBtn);
            }
            
            Thread.sleep(1500); // wait for dropdown animation
            
            WebElement logoutBtn = wait.until(ExpectedConditions.presenceOfElementLocated(logoutButton));
            try {
                wait.until(ExpectedConditions.elementToBeClickable(logoutBtn)).click();
            } catch (Exception e) {
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", logoutBtn);
            }
            
            wait.until(ExpectedConditions.urlContains("login"));
        } catch (Exception e) {
            System.out.println("Could not click logout: " + e.getMessage());
        }
    }
}

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
    private By userProfileIcon = By.xpath("//*[contains(text(), 'Garv') or contains(., 'Garv') or contains(@class, 'avatar') or contains(@class, 'profile') or .//img]");
    private By sidebarNav = By.xpath("//nav | //aside");
    private By widgetContainer = By.cssSelector(".recharts-layer"); // from codegen chart interaction
    private By logoutButton = By.xpath("//*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'sign out') or contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'logout') or contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'log out')]");
    private By reportsLink = By.xpath("//a[contains(., 'Reports')]");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isDashboardLoaded() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardHeader)).isDisplayed();
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
            Thread.sleep(2000); // Wait for potential overlays to disappear
            WebElement profileBtn = wait.until(ExpectedConditions.elementToBeClickable(userProfileIcon));
            profileBtn.click();
            Thread.sleep(1500); // wait for dropdown animation
            WebElement logoutBtn = wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
            logoutBtn.click();
        } catch (Exception e) {
            System.out.println("Could not click logout: " + e.getMessage());
        }
    }
}

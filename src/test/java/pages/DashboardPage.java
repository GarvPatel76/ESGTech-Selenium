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
    private By dashboardHeader = By.xpath("//a[contains(., 'Dashboard') or contains(text(), 'Dashboard')]");
    private By userProfileIcon = By.xpath("//button[contains(., 'GP Garv Patel') or contains(., 'Garv')]");
    private By sidebarNav = By.xpath("//nav[@aria-label='Main navigation' or .//a[contains(., 'Dashboard')]]");
    private By widgetContainer = By.cssSelector(".recharts-layer"); // from codegen chart interaction
    private By logoutButton = By.xpath("//*[contains(text(), 'Sign Out')]");
    private By measureLink = By.xpath("//a[contains(., 'Measure')]");
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

    public void clickLogout() {
        wait.until(ExpectedConditions.elementToBeClickable(userProfileIcon)).click();
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
    }
}

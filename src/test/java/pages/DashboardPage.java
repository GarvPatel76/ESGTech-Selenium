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

    // Placeholders for dashboard locators - to be updated once DOM is available
    private By dashboardHeader = By.xpath("//h1[contains(text(), 'Dashboard') or contains(text(), 'Overview')]");
    private By userProfileIcon = By.cssSelector(".profile-icon, [aria-label='Profile']");
    private By sidebarNav = By.tagName("nav");
    private By widgetContainer = By.cssSelector(".widget, .card");
    private By logoutButton = By.xpath("//button[contains(text(), 'Logout')]");

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

package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By emailInput = By.id("email");
    private By passwordInput = By.id("password");
    private By signInButton = By.xpath("//button[contains(text(), 'Sign In') or .//div[text()='Sign In']]");
    private By rememberMeCheckbox = By.id("remember");
    private By forgotPasswordLink = By.xpath("//a[@href='/reset-password']");
    
    // We assume there might be a toast or error message container for invalid logins
    private By errorMessage = By.cssSelector(".Toastify__toast-body, .text-red-500, [role='alert']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterEmail(String email) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        element.clear();
        element.sendKeys(email);
    }

    public void enterPassword(String password) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        element.clear();
        element.sendKeys(password);
    }

    public void clickSignIn() {
        wait.until(ExpectedConditions.elementToBeClickable(signInButton)).click();
    }

    public void checkRememberMe() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(rememberMeCheckbox));
        if (!element.isSelected()) {
            element.click();
        }
    }
    
    public boolean isRememberMeChecked() {
        return driver.findElement(rememberMeCheckbox).isSelected();
    }

    public void clickForgotPassword() {
        wait.until(ExpectedConditions.elementToBeClickable(forgotPasswordLink)).click();
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSignIn();
    }

    public String getErrorMessage() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isEmailInputPresent() {
        return !driver.findElements(emailInput).isEmpty();
    }
    
    public boolean isPasswordMasked() {
        return driver.findElement(passwordInput).getAttribute("type").equals("password");
    }
}

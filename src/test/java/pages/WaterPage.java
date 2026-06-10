package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaterPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By surfaceInput = By.xpath("//input[contains(@aria-label, 'Surface') or contains(@name, 'Surface')] | //*[contains(text(), 'Surface')]/following-sibling::input");
    private By groundwaterInput = By.xpath("//input[contains(@aria-label, 'Groundwater') or contains(@name, 'Groundwater')] | //*[contains(text(), 'Groundwater')]/following-sibling::input");
    private By thirdPartyInput = By.xpath("//input[contains(@aria-label, 'Third') or contains(@name, 'Third')] | //*[contains(text(), 'Third')]/following-sibling::input");
    private By submitBtn = By.xpath("//button[contains(., 'Submit')]");

    public WaterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void fillWaterWithdrawal(String surface, String groundwater, String thirdParty) {
        try {
            WebElement surfaceElem = wait.until(ExpectedConditions.visibilityOfElementLocated(surfaceInput));
            surfaceElem.clear();
            surfaceElem.sendKeys(surface);

            WebElement groundwaterElem = driver.findElement(groundwaterInput);
            groundwaterElem.clear();
            groundwaterElem.sendKeys(groundwater);

            WebElement thirdElem = driver.findElement(thirdPartyInput);
            thirdElem.clear();
            thirdElem.sendKeys(thirdParty);

            driver.findElement(submitBtn).click();
        } catch (Exception e) {
            System.out.println("Water input fields could not be filled: " + e.getMessage());
        }
    }
}

package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SecurityUITest extends BaseTest {

    @Test(priority = 1, description = "Test Direct URL Access Without Login")
    public void testDirectUrlAccessWithoutLogin() {
        // Attempt to access dashboard directly without logging in
        getDriver().get("https://esgtech.vercel.app/dashboard");
        
        // Wait and verify if redirected to login
        try {
            Thread.sleep(2000); // Wait for redirect
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        Assert.assertTrue(getDriver().getCurrentUrl().contains("login"), "Protected URLs should redirect to login if session is invalid");
    }
}

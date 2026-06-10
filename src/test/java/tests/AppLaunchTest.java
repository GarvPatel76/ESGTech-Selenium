package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AppLaunchTest extends BaseTest {

    @Test
    public void testAppTitle() {
        String title = getDriver().getTitle();
        // Just verify that we actually loaded something. The title might vary.
        // For ESG Tech, we can check if title is not null or contains a specific keyword once we know it.
        Assert.assertNotNull(title, "Page title should not be null");
        System.out.println("Page Title: " + title);
    }
}

package utils;

import org.testng.annotations.DataProvider;

public class DataProviderUtils {

    @DataProvider(name = "loginData")
    public static Object[][] getLoginData() {
        // In a real framework, this could parse a CSV or Excel file (e.g., using Apache POI).
        // For demonstration, we use a 2D array.
        return new Object[][] {
            {"garv.patel.growlity@gmail.com", "GnjA3UqKTN"},
            {"invalid_user@test.com", "wrongpassword"}
        };
    }
}

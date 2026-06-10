package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport.html";
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(new File(reportPath));
            
            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setDocumentTitle("ESG Tech Automation Report");
            sparkReporter.config().setReportName("Test Execution Results");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Browser", "Multi-Browser");
            extent.setSystemInfo("Tester", "Automation Framework");
        }
        return extent;
    }
}

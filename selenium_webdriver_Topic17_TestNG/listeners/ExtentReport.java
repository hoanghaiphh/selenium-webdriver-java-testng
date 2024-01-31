package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentReport implements ITestListener {
    // r-Click ExtentReport >> Generate >> Override Methods >> Select...

    // for Testcases:
    @Override
    public void onTestStart(ITestResult result) {

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Attach passed message to report");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Take screenshot and Attach failed message to report");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Attach skipped message to report");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {

    }

    // for Class:
    @Override
    public void onStart(ITestContext context) {
        System.out.println("Action before class test");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Action after class test");
    }
}

package utilities;

import base.Driver;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestListener implements ITestListener {

    private static final ExtentReports extent = ExtentManager.getExtentReports();
    private static final ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

    private String getTestName(ITestResult result) {
        return result.getMethod().getMethodName();
    }

    @Override
    public void onTestStart(ITestResult result) {
        String name = getTestName(result);
        ExtentTest test = extent.createTest(name);
        testThread.set(test);

        test.log(Status.INFO, "Test started: " + name);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        testThread.get().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String methodName = getTestName(result);
        ExtentTest test = testThread.get();

        test.log(Status.FAIL, "Test failed: " + result.getThrowable());

        try {

            String screenshotPath = takeScreenshot(methodName);
            test.addScreenCaptureFromPath(screenshotPath);


            attachScreenshotToAllure("Failure Screenshot - " + methodName);
        }
        catch (NoSuchSessionException e) {
            test.log(Status.WARNING, "Screenshot alınamadı: Browser session kapanmış.");
        }
        catch (Exception e) {
            test.log(Status.WARNING, "Screenshot alınırken hata oluştu: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        testThread.get().log(Status.SKIP, "Test skipped: " + result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    private String takeScreenshot(String methodName) {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String folderPath = System.getProperty("user.dir") + "/test-output/screenshots/";

        new File(folderPath).mkdirs();

        String fullPath = folderPath + methodName + "_" + timeStamp + ".png";

        if (Driver.getDriver() == null) {
            throw new NoSuchSessionException("Driver null → Browser kapanmış.");
        }

        File src = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.FILE);
        File dest = new File(fullPath);

        try {
            FileHandler.copy(src, dest);
        } catch (Exception e) {
            throw new RuntimeException("Screenshot alınamadı: " + e.getMessage(), e);
        }

        return fullPath;
    }

    private void attachScreenshotToAllure(String name) {
        if (Driver.getDriver() == null) {
            return;
        }
        byte[] bytes = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment(name, new ByteArrayInputStream(bytes));
    }
}

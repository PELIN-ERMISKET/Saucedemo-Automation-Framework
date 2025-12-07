package base;

import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import pages.LoginPage;
import utilities.ConfigReader;
import utilities.TestListener;
import utilities.RecorderUtil;

import java.util.Locale;

@Listeners({TestListener.class, AllureTestNg.class})
public class BaseTest {

    protected WebDriver driver;
    protected LoginPage loginPage;

    @BeforeMethod
    public void setup() throws Exception {
        Locale.setDefault(Locale.ENGLISH);

        RecorderUtil.startRecording("TestRecording");

        driver = Driver.getDriver();
        driver.get(ConfigReader.getProperty("url"));

        loginPage = new LoginPage(driver);
    }

    @AfterMethod
    public void tearDown() throws Exception {

        RecorderUtil.stopRecording();

        Driver.closeDriver();
    }
}

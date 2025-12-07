package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import utilities.ConfigReader;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class Driver {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {

        if (driver.get() == null) {

            String browser = ConfigReader.getProperty("browser");

            WebDriver createdDriver;

            switch (browser.toLowerCase()) {

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    createdDriver = new FirefoxDriver();
                    break;

                case "chrome":
                default:
                    WebDriverManager.chromedriver().setup();

                    ChromeOptions options = new ChromeOptions();

                    Map<String, Object> prefs = new HashMap<>();
                    prefs.put("credentials_enable_service", false);
                    prefs.put("profile.password_manager_enabled", false);
                    options.setExperimentalOption("prefs", prefs);

                    options.addArguments("--disable-features=PasswordLeakDetection");
                    options.addArguments("--disable-features=PasswordManagerEnabled");
                    options.addArguments("--disable-features=UnifiedPasswordManager");

                    createdDriver = new ChromeDriver(options);
                    break;
            }

            createdDriver.manage().window().maximize();
            createdDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            driver.set(createdDriver);
        }

        return driver.get();
    }

    public static void closeDriver() {
        WebDriver currentDriver = driver.get();
        if (currentDriver != null) {
            currentDriver.quit();
            driver.remove();
        }
    }
}

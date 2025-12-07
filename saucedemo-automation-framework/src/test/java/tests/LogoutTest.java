package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;

import java.time.Duration;

@Epic("Login Module")
@Feature("Logout Functionality")
public class LogoutTest extends BaseTest {

    @Story("User logs out successfully")
    @Description("Kullanıcı standard_user ile login olur, menüden logout olur. "
            + "Logout sonrası login sayfasına dönüldüğü doğrulanır.")
    @Test
    public void verifyUserCanLogoutSuccessfully() {

        String username = "standard_user";
        String password = "secret_sauce";

        driver.get("https://www.saucedemo.com/");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.openMenu();
        productsPage.clickLogout();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlToBe("https://www.saucedemo.com/"));

        String currentUrl = driver.getCurrentUrl();

        Assert.assertFalse(
                currentUrl.contains("inventory.html"),
                "Logout sonrası hâlâ inventory sayfasındasın!"
        );

        Assert.assertTrue(
                loginPage.isLoginButtonDisplayed(),
                "Logout sonrası login butonu görünmüyor!"
        );
    }
}

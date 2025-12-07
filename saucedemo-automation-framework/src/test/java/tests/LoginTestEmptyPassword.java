package tests;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Description;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

@Epic("Login Module")
@Feature("Negative Login")
@Story("Boş şifre ile login denemesi")
public class LoginTestEmptyPassword extends BaseTest {

    @Test
    @Description("Kullanıcı boş şifre ile login olmaya çalıştığında hata mesajı ve error button görünür olmalıdır.")
    public void emptyPasswordLogin() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.openLoginPage();
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("");
        loginPage.clickLoginButton();

        WebElement errorButton = loginPage.getErrorButton();
        Assert.assertTrue(errorButton.isDisplayed(), "Error button görünmüyor!");
    }
}

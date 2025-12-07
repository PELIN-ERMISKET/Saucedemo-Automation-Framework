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
@Story("Boş username ve boş password ile giriş denemesi")
public class LoginTestEmptyUsernameAndPassword extends BaseTest {

    @Test
    @Description("Kullanıcı hem username hem de password alanlarını boş bırakarak login olmaya çalıştığında hata mesajının görünmesi doğrulanır.")
    public void emptyUsernameAndPasswordLogin() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.openLoginPage();
        loginPage.enterUsername("");
        loginPage.enterPassword("");
        loginPage.clickLoginButton();

        WebElement errorButton = loginPage.getErrorButton();
        Assert.assertTrue(errorButton.isDisplayed(), "Error button görünmüyor!");
    }
}

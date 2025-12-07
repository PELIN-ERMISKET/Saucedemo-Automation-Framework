package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

@Epic("Login Module")
@Feature("Negative Login")
@Story("Geçersiz (yanlış) username ile login denemesi")
public class LoginTestInvalidUsername extends BaseTest {

    @Test
    @Description("Kullanıcı yanlış username girip doğru password kullandığında hata mesajının görüntülendiği doğrulanır.")
    public void invalidUsernameLogin() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.openLoginPage();
        loginPage.enterUsername("invalid_user"); // yanlış kullanıcı adı
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        WebElement errorButton = loginPage.getErrorButton();
        Assert.assertTrue(errorButton.isDisplayed(), "Error button görünmüyor!");
    }
}

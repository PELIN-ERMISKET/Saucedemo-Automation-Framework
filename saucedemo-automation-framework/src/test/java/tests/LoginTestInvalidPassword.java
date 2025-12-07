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
@Story("Geçersiz (yanlış) password ile login denemesi")
public class LoginTestInvalidPassword extends BaseTest {

    @Test
    @Description("Kullanıcı doğru username fakat yanlış password girdiğinde hata mesajının görüntülendiği doğrulanır.")
    public void invalidPasswordLogin() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.openLoginPage();
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret"); // yanlış password
        loginPage.clickLoginButton();

        WebElement errorButton = loginPage.getErrorButton();
        Assert.assertTrue(errorButton.isDisplayed(), "Error button görünmüyor!");
    }
}

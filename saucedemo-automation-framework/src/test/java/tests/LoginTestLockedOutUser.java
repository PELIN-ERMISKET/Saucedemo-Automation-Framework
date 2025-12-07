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
@Story("Locked-out user login attempt")
public class LoginTestLockedOutUser extends BaseTest {

    @Test
    @Description("Locked_out_user ile giriş yapıldığında 'user locked out' hata mesajının doğru şekilde gösterildiği doğrulanır.")
    public void lockedOutUserLogin() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.openLoginPage();
        loginPage.enterUsername("locked_out_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        WebElement errorButton = loginPage.getErrorButton();
        Assert.assertTrue(errorButton.isDisplayed(), "Error button görünmüyor!");

        String errorMessage = loginPage.getErrorMessageText();
        Assert.assertEquals(
                errorMessage,
                "Epic sadface: Sorry, this user has been locked out.",
                "Hata mesajı beklenenle uyuşmuyor!"
        );
    }
}

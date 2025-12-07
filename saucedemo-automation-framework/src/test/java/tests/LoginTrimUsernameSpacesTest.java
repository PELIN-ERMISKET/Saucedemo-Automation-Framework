package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Login Module")
@Feature("Input Validation")
@Story("Leading/trailing spaces in username should cause login failure")
public class LoginTrimUsernameSpacesTest extends BaseTest {

    @Test
    @Description("Username alanına başında/sonunda boşluk eklendiğinde trim edilmediğini ve login'in başarısız olduğunu doğrular.")
    public void usernameTrimFail() {

        loginPage.enterUsername(" standard_user ");  // başında ve sonunda bosluk
        loginPage.enterPassword("secret_sauce");     // doğru şifre
        loginPage.clickLoginButton();

        Assert.assertTrue(
                driver.findElement(By.cssSelector("[data-test='error']")).isDisplayed(),
                "Hata mesajı görünmedi!"
        );

        Assert.assertEquals(
                driver.findElement(By.cssSelector("[data-test='error']")).getText().trim(),
                "Epic sadface: Username and password do not match any user in this service"
        );
    }
}

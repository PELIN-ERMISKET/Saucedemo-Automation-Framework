package tests;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Description;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Login Module")
@Feature("Password Validation")
@Story("Case sensitivity check")
public class LoginPasswordCaseSensitivityTest extends BaseTest {

    @Test
    @Description("Parola büyük/küçük harf duyarlılığı yanlış girildiğinde login işleminin başarısız olduğunu doğrular.")
    public void loginShouldFail_WhenPasswordHasWrongCase() {

        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("Secret_Sauce"); // yanlış case
        loginPage.clickLoginButton();

        Assert.assertTrue(
                driver.findElement(By.cssSelector("[data-test='error']")).isDisplayed(),
                "Hata mesajı görünmedi!"
        );

        Assert.assertEquals(
                driver.findElement(By.cssSelector("[data-test='error']")).getText().trim(),
                "Epic sadface: Username and password do not match any user in this service",
                "Hata mesajı beklenen ile uyuşmuyor!"
        );
    }
}

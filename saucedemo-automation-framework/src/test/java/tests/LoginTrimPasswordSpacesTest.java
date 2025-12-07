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
@Story("Password input should not ignore leading/trailing spaces")
public class LoginTrimPasswordSpacesTest extends BaseTest {

    @Test
    @Description("Password alanına başında/sonunda boşluk eklenince trim edilmediğini ve login'in başarısız olduğunu doğrular.")
    public void passwordTrimFail() {

        loginPage.enterUsername("standard_user");
        loginPage.enterPassword(" secret_sauce ");    // başında & sonunda boşluk
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

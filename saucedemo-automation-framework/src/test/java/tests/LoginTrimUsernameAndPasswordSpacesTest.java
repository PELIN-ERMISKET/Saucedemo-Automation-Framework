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
@Story("Leading/trailing spaces in both username and password should cause login failure")
public class LoginTrimUsernameAndPasswordSpacesTest extends BaseTest {

    @Test
    @Description("Hem username hem password alanına başında/sonunda boşluk eklendiğinde trim edilmediğini ve login'in başarısız olduğunu doğrular.")
    public void loginShouldFail_WhenUsernameAndPasswordHaveSpaces() {

        loginPage.enterUsername(" standard_user ");
        loginPage.enterPassword(" secret_sauce ");
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

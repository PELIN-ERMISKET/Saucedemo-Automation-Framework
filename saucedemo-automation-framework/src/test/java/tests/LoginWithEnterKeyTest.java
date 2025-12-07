package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.Waits;

@Epic("Login Module")
@Feature("Keyboard Actions")
@Story("Login with ENTER key")
public class LoginWithEnterKeyTest extends BaseTest {

    @Test
    @Description("Kullanıcı şifre alanında ENTER tuşuna basarak giriş yapabilir. Enter ile login işleminin başarıyla gerçekleştiğini doğrular.")
    public void loginShouldSucceed_WhenPressingEnterKey() {

        loginPage.enterUsername("standard_user");

        driver.findElement(By.id("password"))
                .sendKeys("secret_sauce" + Keys.ENTER);


        Assert.assertTrue(
                driver.getCurrentUrl().contains("inventory.html"),
                "Enter ile login olmadı → URL inventory.html içermiyor!"
        );

        Assert.assertEquals(
                driver.findElement(By.className("title")).getText(),
                "Products",
                "Products sayfası açılmadı!"
        );
    }
}

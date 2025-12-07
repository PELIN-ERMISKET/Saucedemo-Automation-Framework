package tests;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

@Epic("Login Module")
@Feature("Error Handling")
@Story("Error message dismissal behavior")
public class LoginErrorMessageIconTest extends BaseTest {

    @Test
    @Description("Kullanıcı yanlış şifre girdiğinde çıkan hata mesajının ve input error ikonlarının X butonuna basıldığında kaybolduğunu doğrular.")
    public void errorMessageAndStylesShouldDisappear_WhenClickOnErrorIcon() {

        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("wrong_password");
        loginPage.clickLoginButton();

        WebElement errorContainer =
                driver.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertTrue(errorContainer.isDisplayed(),
                "Hata mesajı container'ı görünmedi!");

        String expectedErrorText =
                "Epic sadface: Username and password do not match any user in this service";
        Assert.assertEquals(errorContainer.getText().trim(), expectedErrorText,
                "Hata mesajı metni beklenen ile uyuşmuyor!");

        WebElement usernameInput = driver.findElement(By.id("user-name"));
        WebElement passwordInput = driver.findElement(By.id("password"));

        String usernameClassBefore = usernameInput.getAttribute("class");
        String passwordClassBefore = passwordInput.getAttribute("class");

        Assert.assertTrue(usernameClassBefore.contains("error"),
                "Username input error class'ı almamış!");
        Assert.assertTrue(passwordClassBefore.contains("error"),
                "Password input error class'ı almamış!");

        WebElement errorButton =
                driver.findElement(By.cssSelector("[data-test='error-button']"));
        errorButton.click();

        List<WebElement> errorContainersAfter =
                driver.findElements(By.cssSelector("[data-test='error']"));
        Assert.assertEquals(errorContainersAfter.size(), 0,
                "X ikonuna basınca hata mesajı container'ı DOM'dan kaybolmadı!");

        List<WebElement> errorIconsAfter =
                driver.findElements(By.className("error_icon"));
        Assert.assertEquals(errorIconsAfter.size(), 0,
                "Inputlardaki hata ikonları kaybolmadı!");
    }
}

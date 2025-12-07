package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

@Epic("Checkout Module")
@Feature("Form Validation")
public class CheckoutPostalCodeFormatTest extends BaseTest {

    @Story("User cannot proceed with invalid or too-short postal code")
    @Description("Checkout Step One ekranında '1' gibi geçersiz kısa postal code girildiğinde kullanıcı "
            + "Step Two ekranına geçememeli. Form validation doğrulanır.")
    @Test
    public void postalCodeTooShort_ShouldNotAllowProceeding() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        loginPage.openLoginPage();
        loginPage.login("standard_user", "secret_sauce");
        wait.until(ExpectedConditions.urlContains("inventory"));

        driver.findElement(By.cssSelector("button[data-test='add-to-cart-sauce-labs-backpack']")).click();
        driver.findElement(By.className("shopping_cart_link")).click();
        driver.findElement(By.id("checkout")).click();
        wait.until(ExpectedConditions.urlContains("checkout-step-one"));

        driver.findElement(By.id("first-name")).sendKeys("Test");
        driver.findElement(By.id("last-name")).sendKeys("User");
        driver.findElement(By.id("postal-code")).sendKeys("1"); // INVALID INPUT
        driver.findElement(By.id("continue")).click();

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("checkout-step-two"),
                ExpectedConditions.urlContains("checkout-step-one")
        ));

        boolean advancedToStepTwo = driver.getCurrentUrl().contains("checkout-step-two");

        Assert.assertFalse(
                advancedToStepTwo,
                "FAIL ❌: '1' gibi geçersiz kısa Postal Code ile form ilerledi! (Format validation eksik!)"
        );
    }
}

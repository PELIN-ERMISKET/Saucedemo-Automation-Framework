package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

@Epic("Checkout Module")
@Feature("Form Validation")
public class CheckoutFirstNameNumericInputTest extends BaseTest {

    @Story("User should NOT be able to checkout with numeric first name")
    @Description("First Name alanına sadece sayı girildiğinde siparişin tamamlanmaması beklenir. "
            + "Sistem doğru şekilde validation yapmalıdır.")
    @Test
    public void firstNameNumeric_ShouldNotAllowCompletingOrder() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        loginPage.openLoginPage();
        loginPage.login("standard_user", "secret_sauce");

        wait.until(ExpectedConditions.urlContains("inventory"));

        driver.findElement(By.cssSelector("button[data-test='add-to-cart-sauce-labs-backpack']")).click();
        driver.findElement(By.className("shopping_cart_link")).click();
        driver.findElement(By.id("checkout")).click();

        wait.until(ExpectedConditions.urlContains("checkout-step-one"));

        WebElement firstName = driver.findElement(By.id("first-name"));
        firstName.sendKeys("12345");

        WebElement lastName = driver.findElement(By.id("last-name"));
        lastName.sendKeys("Test");

        WebElement postalCode = driver.findElement(By.id("postal-code"));
        postalCode.sendKeys("34000");

        driver.findElement(By.id("continue")).click();

        wait.until(ExpectedConditions.urlContains("checkout-step-two"));
        driver.findElement(By.id("finish")).click();

        boolean isOrderCompleted =
                driver.findElements(By.cssSelector(".complete-header")).size() > 0;

        Assert.assertFalse(
                isOrderCompleted,
                "FAIL ❌: Numeric First Name ile sipariş TAMAMLANDI! (Bu kabul edilemez)"
        );

        System.out.println("PASS ✔: Numeric First Name ile sipariş tamamlanmaması bekleniyordu.");
    }
}

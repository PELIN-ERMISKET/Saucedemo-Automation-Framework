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
public class CheckoutLastNameSpecialCharsTest extends BaseTest {

    @Story("User should NOT be able to checkout when Last Name contains special characters")
    @Description("Last Name alanına sadece özel karakter girildiğinde siparişin tamamlanmaması beklenir. "
            + "Sistem doğru şekilde validation yapmalıdır.")
    @Test
    public void lastNameSpecialChars_ShouldNotAllowCompletingOrder() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        loginPage.openLoginPage();
        loginPage.login("standard_user", "secret_sauce");
        wait.until(ExpectedConditions.urlContains("inventory"));

        driver.findElement(By.cssSelector("button[data-test='add-to-cart-sauce-labs-backpack']")).click();
        driver.findElement(By.className("shopping_cart_link")).click();
        driver.findElement(By.id("checkout")).click();

        wait.until(ExpectedConditions.urlContains("checkout-step-one"));

        driver.findElement(By.id("first-name")).sendKeys("Test");
        driver.findElement(By.id("last-name")).sendKeys("@@@@@"); // ❌ Geçersiz input
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.id("continue")).click();

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("checkout-step-two"),
                ExpectedConditions.urlContains("checkout-complete")
        ));

        driver.findElement(By.id("finish")).click();

        boolean orderCompleted = driver.getCurrentUrl().contains("checkout-complete");

        Assert.assertFalse(
                orderCompleted,
                "FAIL ❌: Özel karakter içeren Last Name ile sipariş TAMAMLANDI! (Validation eksik)"
        );
    }
}

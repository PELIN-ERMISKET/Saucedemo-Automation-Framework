package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

@Epic("Cart Module")
@Feature("Cart Badge Behavior")
public class CartSingleItemRemoveTest extends BaseTest {

    @Story("Single item remove should clear the badge")
    @Description("Sepete 1 ürün eklenir, badge '1' olur. "
            + "Ürün remove edilince badge'in tamamen DOM'dan kaybolduğu doğrulanır.")
    @Test
    public void cartBadgeShouldDisappear_WhenSingleItemRemoved() {

        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        List<WebElement> initialBadges =
                driver.findElements(By.className("shopping_cart_badge"));

        Assert.assertEquals(
                initialBadges.size(),
                0,
                "Test ön koşulu: başlangıçta cart badge görünmemeliydi!"
        );

        WebElement firstAddToCartBtn = driver.findElements(
                By.cssSelector(".inventory_item button[data-test^='add-to-cart']")
        ).get(0);
        firstAddToCartBtn.click();

        WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
        Assert.assertTrue(cartBadge.isDisplayed(), "Cart badge görünmüyor!");
        Assert.assertEquals(cartBadge.getText().trim(), "1",
                "Badge üzerinde beklenen sayı 1 değil!");

        WebElement firstRemoveBtn = driver.findElements(
                By.cssSelector(".inventory_item button[data-test^='remove']")
        ).get(0);
        firstRemoveBtn.click();

        List<WebElement> badgesAfterRemove =
                driver.findElements(By.className("shopping_cart_badge"));

        Assert.assertEquals(
                badgesAfterRemove.size(),
                0,
                "Remove sonrası cart badge DOM'dan kaybolmadı!"
        );

        WebElement cartIcon = driver.findElement(By.id("shopping_cart_container"));
        Assert.assertTrue(
                cartIcon.isDisplayed(),
                "Remove sonrası cart ikonu görünmüyor (UI bozulmuş olabilir)!"
        );
    }
}

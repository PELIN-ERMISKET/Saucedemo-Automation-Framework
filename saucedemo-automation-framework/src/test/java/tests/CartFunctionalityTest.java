package tests;

import base.BaseTest;
import base.Driver;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductsPage;

@Epic("Cart Module")
@Feature("Cart Basic Operations")
public class CartFunctionalityTest extends BaseTest {

    @Story("Add & Remove items from cart")
    @Description("standard_user ile giriş yapılıp 2 ürün sepete eklenir, "
            + "badge 2 olduğu doğrulanır. Sonra 1 ürün sepetten silinir ve badge 1 olmalıdır.")
    @Test
    public void verifyCartFunctionality() {

        LoginPage loginPage = new LoginPage(Driver.getDriver());
        ProductsPage productsPage = new ProductsPage(Driver.getDriver());
        CartPage cartPage = new CartPage();

        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        productsPage.addProductToCart(0);
        productsPage.addProductToCart(1);

        int badgeCount = productsPage.getCartBadgeCount();
        Assert.assertEquals(badgeCount, 2, "Badge sayısı yanlış!");

        productsPage.clickCartIcon();
        cartPage.removeProduct(0);

        int updatedBadge = productsPage.getCartBadgeCount();
        Assert.assertEquals(updatedBadge, 1, "Remove sonrası badge hatalı!");
    }
}

package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;
import pages.ProductDetailPage;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Description;

@Epic("Products Module")
@Feature("Price Consistency")
@Story("Visual_user için liste fiyatı ile detay fiyatının aynı olduğunu doğrulama")
public class VisualUserPriceConsistencyTest extends BaseTest {

    @Test
    @Description("Visual_user ile login olduktan sonra listede görünen ilk ürünün fiyatı ile ürün detay sayfasındaki fiyatın birebir eşleştiğini doğrular.")
    public void priceShouldBeSameOnListAndDetail_forVisualUser() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.enterUsername("visual_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        ProductsPage productsPage = new ProductsPage(driver);
        String listPrice = productsPage.getFirstProductPriceOnList();

        productsPage.clickFirstProduct();

        ProductDetailPage detailPage = new ProductDetailPage(driver);
        String detailPrice = detailPage.getProductPrice();

        Assert.assertEquals(
                detailPrice,
                listPrice,
                "Products sayfası ile ürün detay sayfasındaki fiyatlar farklı!"
        );
    }
}

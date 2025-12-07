package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;
import pages.ProductDetailPage;

@Epic("Products & UI Validation")
@Feature("Product Detail Consistency")
@Story("Liste ve ürün detay sayfası arasında içerik tutarlılığının doğrulanması")
public class ProductDetailVerificationTest extends BaseTest {

    @Test
    @Description("Liste sayfasındaki ilk ürünün adı, açıklaması ve fiyatının detay sayfasıyla birebir aynı olduğunu ve Back to Products sonrası listeye dönülebildiğini doğrular.")
    public void verifyProductDetailPage() {

        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);

        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        Assert.assertTrue(
                productsPage.isProductsPageDisplayed(),
                "Products sayfası görüntülenemedi!"
        );

        String expectedName        = productsPage.getFirstProductNameFromList();
        String expectedDescription = productsPage.getFirstProductDescriptionFromList();
        String expectedPrice       = productsPage.getFirstProductPriceFromList();

        ProductDetailPage detailPage = productsPage.clickFirstProductName();

        Assert.assertEquals(
                detailPage.getProductName(),
                expectedName,
                "Ürün adı listede görünenle aynı değil!"
        );
        Assert.assertEquals(
                detailPage.getProductDescription(),
                expectedDescription,
                "Ürün açıklaması listede görünenle aynı değil!"
        );
        Assert.assertEquals(
                detailPage.getProductPrice(),
                expectedPrice,
                "Ürün fiyatı listede görünenle aynı değil!"
        );

        detailPage.clickBackToProducts();

        Assert.assertTrue(
                productsPage.isProductsPageDisplayed(),
                "Back to products sonrası kullanıcı Products listesine dönemedi!"
        );
    }
}

package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;

import java.util.List;

@Epic("Products & UI Validation")
@Feature("Problem User Visual Regressions")
@Story("Problem_user için ürün liste görseli ile detay sayfası görsel tutarlılığı")
public class ProblemUserImageTest extends BaseTest {

    @Test
    @Description("problem_user ile giriş yapıldığında ürün liste thumbnail görselleri ile ürün detay sayfası görsellerinin aynı olduğunu doğrular.")
    public void productThumbnailsShouldMatchDetailImagesForProblemUser() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.login("problem_user", "secret_sauce");

        ProductsPage productsPage = new ProductsPage(driver);

        List<WebElement> images = productsPage.getProductImages();
        int productCount = images.size();

        for (int i = 0; i < productCount; i++) {

            String listSrc = images.get(i).getAttribute("src");

            productsPage.openProductByIndex(i);
            String detailSrc = productsPage.getDetailImageSrc();

            Assert.assertEquals(
                    listSrc,
                    detailSrc,
                    "Ürün index " + i + " için thumbnail ve detay görseli uyuşmuyor!"
            );

            productsPage.clickBackToProducts();
            images = productsPage.getProductImages();
        }
    }
}

package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

@Epic("Checkout Module")
@Feature("End-to-End checkout flow")
public class EndToEndCheckoutTestPerformanceGlitchUser extends BaseTest {

    @Story("Performance glitch user should complete full checkout successfully")
    @Description("performance_glitch_user ile login yapılır, ürün sepete eklenir, checkout akışı tamamlanır ve 'Back Home' sonrası Products sayfasına dönülür.")
    @Test
    public void endToEndCheckout_performanceGlitchUser() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.enterUsername("performance_glitch_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertTrue(
                productsPage.isProductsPageDisplayed(),
                "Login başarısız! Products sayfası görüntülenmedi."
        );

        productsPage.addFirstItemToCart();
        productsPage.clickCartIcon();

        CartPage cartPage = new CartPage();
        Assert.assertTrue(
                cartPage.isItemInCart(),
                "Sepete eklenen ürün görüntülenmedi!"
        );

        cartPage.clickCheckoutButton();

        CheckoutInformationPage infoPage = new CheckoutInformationPage(driver);
        infoPage.enterFirstName("Pelin");
        infoPage.enterLastName("Ermisket");
        infoPage.enterPostalCode("34000");
        infoPage.clickContinueButton();

        CheckoutOverviewPage overviewPage = new CheckoutOverviewPage();
        Assert.assertTrue(
                overviewPage.isOverviewPageDisplayed(),
                "Checkout Overview sayfası açılmadı!"
        );

        overviewPage.clickFinishButton();

        CheckoutCompletePage completePage = new CheckoutCompletePage();
        Assert.assertTrue(
                completePage.isSuccessMessageDisplayed(),
                "Sipariş başarıyla tamamlanamadı!"
        );

        completePage.clickBackHomeButton();

        Assert.assertTrue(
                productsPage.isProductsPageDisplayed(),
                "Back Home sonrası Products sayfasına dönülemedi!"
        );
    }
}

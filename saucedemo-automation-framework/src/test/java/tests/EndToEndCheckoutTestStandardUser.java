package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utilities.Waits;

@Epic("Checkout Module")
@Feature("End-to-End checkout flow")
public class EndToEndCheckoutTestStandardUser extends BaseTest {

    @Story("Standard User checkout flow validation")
    @Description("standard_user ile baştan sona ürün ekleme, checkout, finish ve Back Home adımlarının başarılı şekilde çalıştığını doğrular.")
    @Test
    public void endToEndCheckout_standardUser() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.enterUsername("standard_user");
        Waits.pauseSeconds(2);
        loginPage.enterPassword("secret_sauce");
        Waits.pauseSeconds(2);
        loginPage.clickLoginButton();

        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertTrue(
                productsPage.isProductsPageDisplayed(),
                "Login başarısız! Products sayfası görüntülenmedi."
        );

        productsPage.addFirstItemToCart();
        Waits.pauseSeconds(2);
        productsPage.clickCartIcon();
        Waits.pauseSeconds(2);

        CartPage cartPage = new CartPage();
        Assert.assertTrue(
                cartPage.isItemInCart(),
                "Sepete eklenen ürün görünmüyor!"
        );
        cartPage.clickCheckoutButton();
        Waits.pauseSeconds(2);

        CheckoutInformationPage infoPage = new CheckoutInformationPage(driver);
        infoPage.enterFirstName("Pelin");
        Waits.pauseSeconds(2);
        infoPage.enterLastName("Ermisket");
        Waits.pauseSeconds(2);
        infoPage.enterPostalCode("34000");
        Waits.pauseSeconds(2);
        infoPage.clickContinueButton();
        Waits.pauseSeconds(2);

        CheckoutOverviewPage overviewPage = new CheckoutOverviewPage();
        Assert.assertTrue(
                overviewPage.isOverviewPageDisplayed(),
                "Checkout Overview sayfası açılmadı!"
        );
        overviewPage.clickFinishButton();
        Waits.pauseSeconds(2);

        CheckoutCompletePage completePage = new CheckoutCompletePage();
        Assert.assertTrue(
                completePage.isSuccessMessageDisplayed(),
                "Sipariş başarıyla tamamlanamadı!"
        );
        Waits.pauseSeconds(2);

        completePage.clickBackHomeButton();
        Assert.assertTrue(
                productsPage.isProductsPageDisplayed(),
                "Back Home sonrası Products sayfasına dönülemedi!"
        );
        Waits.pauseSeconds(2);
    }
}

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
@Feature("End-to-End checkout flow with known buggy users")
public class EndToEndCheckoutTestProblemUser extends BaseTest {

    @Story("Problem User checkout validation")
    @Description("problem_user ile checkout tamamlanmaya çalışılır; last name alanının yazılamaması bug olarak erken aşamada yakalanır.")
    @Test
    public void endToEndCheckout_problemUser() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.enterUsername("problem_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertTrue(
                productsPage.isProductsPageDisplayed(),
                "Login başarısız! Products sayfası görüntülenmedi."
        );

        productsPage.waitForAllProductImages();

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


        Assert.assertEquals(
                infoPage.getLastNameValue(),
                "Ermisket",
                "BUG: problem_user last name alanına yazılamıyor!"
        );

        infoPage.enterPostalCode("34000");
        infoPage.clickContinueButton();

        CheckoutOverviewPage overviewPage = new CheckoutOverviewPage();
        Assert.assertTrue(
                overviewPage.isOverviewPageDisplayed(),
                "BUG: Last name girilemediği için Overview sayfası açılmadı!"
        );

        overviewPage.clickFinishButton();

        CheckoutCompletePage completePage = new CheckoutCompletePage();
        Assert.assertFalse(
                completePage.isSuccessMessageDisplayed(),
                "BUG: problem_user ile sipariş başarıyla tamamlanmamalıydı!"
        );
    }
}

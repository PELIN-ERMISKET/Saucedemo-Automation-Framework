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
import pages.CartPage;
import pages.CheckoutInformationPage;
import pages.CheckoutOverviewPage;
import pages.CheckoutCompletePage;


@Epic("Checkout Module")
@Feature("End-to-End checkout flow")
public class EndToEndErrorUserTest extends BaseTest {

    @Story("Known bug scenario for error_user")
    @Description("error_user ile checkout akışı çalıştırılır. Last name alanı yazılamıyorsa test doğru noktada fail eder.")
    @Test
    public void endToEndCheckout_shouldPassButManualBugExists() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.enterUsername("error_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addFirstItemToCart();
        productsPage.clickCartIcon();

        CartPage cartPage = new CartPage();
        cartPage.clickCheckoutButton();

        CheckoutInformationPage info = new CheckoutInformationPage(driver);
        info.enterFirstName("Pelin");
        info.enterLastName("Ermisket");

        Assert.assertEquals(
                info.getLastNameValue(),
                "Ermisket",
                "Last Name alanı yazılamadı! Bu bir bug."
        );

        info.enterPostalCode("34000");
        info.clickContinueButton();

        CheckoutOverviewPage overview = new CheckoutOverviewPage();
        overview.clickFinishButton();

        CheckoutCompletePage complete = new CheckoutCompletePage();
        Assert.assertTrue(
                complete.isSuccessMessageDisplayed(),
                "Sipariş tamamlanamadı!"
        );
    }
}

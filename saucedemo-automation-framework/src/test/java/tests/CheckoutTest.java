package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utilities.ConfigReader;

@Epic("Checkout Module")
@Feature("End-to-End Purchase Flow")
public class CheckoutTest extends BaseTest {

    @Story("Standard user completes a full purchase successfully")
    @Description("Standard_user ile login olunur, ürün sepete eklenir, " +
            "checkout adımları doldurulur ve sipariş başarıyla tamamlanır. " +
            "CheckoutComplete sayfasında başarı mesajı görülmelidir.")
    @Test
    public void endToEndCheckoutTest() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addFirstItemToCart();
        productsPage.clickCartIcon();

        CartPage cartPage = new CartPage();
        Assert.assertTrue(cartPage.isItemInCart(), "Sepette ürün yok!");
        cartPage.clickCheckoutButton();

        CheckoutInformationPage infoPage = new CheckoutInformationPage(driver);
        infoPage.fillInformationAndContinue(
                ConfigReader.getProperty("firstName"),
                ConfigReader.getProperty("lastName"),
                ConfigReader.getProperty("postalCode")
        );

        CheckoutOverviewPage overviewPage = new CheckoutOverviewPage();
        overviewPage.clickFinishButton();

        CheckoutCompletePage completePage = new CheckoutCompletePage();
        Assert.assertTrue(
                completePage.isSuccessMessageDisplayed(),
                "Sipariş tamamlanamadı!"
        );
    }
}

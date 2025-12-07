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
import pages.CheckoutInformationPage;
import pages.LoginPage;
import pages.ProductsPage;

@Epic("Checkout Module")
@Feature("Required Fields Validation")
public class CheckoutRequiredFieldsTest extends BaseTest {

    private void goToCheckoutWithOneItem() {
        LoginPage loginPage = new LoginPage(Driver.getDriver());
        ProductsPage productsPage = new ProductsPage(Driver.getDriver());
        CartPage cartPage = new CartPage();

        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        productsPage.addFirstItemToCart();
        productsPage.clickCartIcon();
        cartPage.clickCheckoutButton();
    }

    @Story("First Name field is mandatory")
    @Description("Checkout bilgi formunda First Name boş bırakıldığında "
            + "kullanıcı devam edememeli ve 'Error: First Name is required' mesajı görülmelidir.")
    @Test
    public void firstNameRequired() {
        goToCheckoutWithOneItem();
        CheckoutInformationPage infoPage = new CheckoutInformationPage(Driver.getDriver());

        infoPage.enterFirstName("");
        infoPage.enterLastName("Test");
        infoPage.enterPostalCode("34000");
        infoPage.clickContinueButton();

        String error = infoPage.getErrorMessageText();
        Assert.assertTrue(error.contains("Error: First Name is required"),
                "First Name is required mesajı görünmedi!");
    }

    @Story("Last Name field is mandatory")
    @Description("Checkout bilgi formunda Last Name boş bırakıldığında "
            + "kullanıcı devam edememeli ve 'Error: Last Name is required' mesajı görülmelidir.")
    @Test
    public void lastNameRequired() {
        goToCheckoutWithOneItem();
        CheckoutInformationPage infoPage = new CheckoutInformationPage(Driver.getDriver());

        infoPage.enterFirstName("Pelin");
        infoPage.enterLastName("");
        infoPage.enterPostalCode("34000");
        infoPage.clickContinueButton();

        String error = infoPage.getErrorMessageText();
        Assert.assertTrue(error.contains("Error: Last Name is required"),
                "Last Name is required mesajı görünmedi!");
    }

    @Story("Postal Code field is mandatory")
    @Description("Checkout bilgi formunda Postal Code boş bırakıldığında "
            + "kullanıcı devam edememeli ve 'Error: Postal Code is required' mesajı görülmelidir.")
    @Test
    public void postalCodeRequired() {
        goToCheckoutWithOneItem();
        CheckoutInformationPage infoPage = new CheckoutInformationPage(Driver.getDriver());

        infoPage.enterFirstName("Pelin");
        infoPage.enterLastName("Ermişket");
        infoPage.enterPostalCode("");
        infoPage.clickContinueButton();

        String error = infoPage.getErrorMessageText();
        Assert.assertTrue(error.contains("Error: Postal Code is required"),
                "Postal Code is required mesajı görünmedi!");
    }
}

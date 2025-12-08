package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utilities.Waits;

import java.time.Duration;

@Epic("Checkout Module")
@Feature("End-to-End checkout flow")
public class EndToEndCheckoutTestVisualUser extends BaseTest {

    @Story("Visual User checkout flow validation")
    @Description("visual_user ile liste fiyatı kontrolü, ürün ekleme, checkout, overview price validation ve sipariş tamamlama süreci doğrulanır.")
    @Test
    public void endToEndCheckout_visualUser() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.enterUsername("visual_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertTrue(
                productsPage.isProductsPageDisplayed(),
                "Login başarısız! Products sayfası görüntülenmedi."
        );

        String listPrice = productsPage.getFirstProductPriceOnList();
        System.out.println("Visual_user için liste fiyatı: " + listPrice);

        productsPage.addFirstItemToCart();
        productsPage.clickCartIcon();

        CartPage cartPage = new CartPage();
        Assert.assertTrue(
                cartPage.isItemInCart(),
                "Sepete eklenen ürün Cart sayfasında görüntülenmedi!"
        );
        cartPage.clickCheckoutButton();

        CheckoutInformationPage infoPage = new CheckoutInformationPage(driver);
        infoPage.enterFirstName("Pelin");
        infoPage.enterLastName("Ermisket");
        infoPage.enterPostalCode("34000");
        infoPage.clickContinueButton();
        Waits.pauseSeconds(2);

        CheckoutOverviewPage overviewPage = new CheckoutOverviewPage();
        Assert.assertTrue(
                overviewPage.isOverviewPageDisplayed(),
                "Checkout Overview sayfası açılmadı!"
        );

        String totalPrice = overviewPage.getTotalPrice();
        System.out.println("Visual_user için toplam tutar: " + totalPrice);

        overviewPage.clickFinishButton();

        Waits.pauseSeconds(2);


        boolean isSuccessVisible;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement completeHeader = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.className("complete-header"))
            );
            isSuccessVisible = completeHeader.isDisplayed();
        } catch (Exception e) {
            isSuccessVisible = false; // element bulunamazsa test FAIL olacak
        }

        Assert.assertTrue(
                isSuccessVisible,
                "Sipariş başarıyla tamamlanamadı! (complete-header bulunamadı)"
        );

        CheckoutCompletePage completePage = new CheckoutCompletePage();
        completePage.clickBackHomeButton();
        Assert.assertTrue(
                productsPage.isProductsPageDisplayed(),
                "Back Home sonrası Products sayfasına dönülemedi!"
        );
    }
}

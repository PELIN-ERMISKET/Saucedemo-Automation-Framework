package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

import java.time.Duration;

@Epic("Checkout Module")
@Feature("Negative checkout scenarios")
public class EmptyCartCheckoutTest extends BaseTest {

    @Story("User must NOT be able to complete an order with an empty cart")
    @Description("Kullanıcı standard_user ile login olur, sepete hiç ürün eklemeden checkout akışını tamamlamaya çalışır. " +
            "Overview ekranında Item Total, Tax ve Total değerleri 0 olmalı ve Finish sonrası sipariş tamamlanmamalıdır.")
    @Test
    public void checkoutWithEmptyCart_ShouldNotCompleteOrder() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));


        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.login("standard_user", "secret_sauce");

        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertTrue(
                productsPage.isProductsPageDisplayed(),
                "Products sayfası görüntülenemedi!"
        );


        productsPage.clickCartIcon();

        CartPage cartPage = new CartPage();
        Assert.assertFalse(
                cartPage.isItemInCart(),
                "Sepet boş değil, içinde ürün var!"
        );


        cartPage.clickCheckoutButton();

        CheckoutInformationPage infoPage = new CheckoutInformationPage(driver);
        infoPage.fillInformationAndContinue("Pelin", "Test", "34000");

        CheckoutOverviewPage overviewPage = new CheckoutOverviewPage();
        Assert.assertTrue(
                overviewPage.isOverviewPageDisplayed(),
                "Checkout Overview sayfası görüntülenemedi!"
        );

        WebElement itemTotalLabel = driver.findElement(By.className("summary_subtotal_label"));
        WebElement taxLabel       = driver.findElement(By.className("summary_tax_label"));
        WebElement totalLabel     = driver.findElement(By.className("summary_total_label"));

        String itemText  = itemTotalLabel.getText();
        String taxText   = taxLabel.getText();
        String totalText = totalLabel.getText();

        Assert.assertTrue(
                itemText.contains("$0"),
                "Item Total 0 değil! Görünen: " + itemText
        );
        Assert.assertTrue(
                taxText.contains("$0.00"),
                "Tax 0.00 değil! Görünen: " + taxText
        );
        Assert.assertTrue(
                totalText.contains("$0.00") || totalText.endsWith("$0"),
                "Total 0 değil! Görünen: " + totalText
        );

        String urlBeforeFinish = driver.getCurrentUrl();
        overviewPage.clickFinishButton();

        boolean completed = false;
        try {
            wait.until(ExpectedConditions.urlContains("checkout-complete"));
            completed = true;
        } catch (TimeoutException e) {
            completed = false;
        }

        Assert.assertFalse(
                completed,
                "Boş sepetle sipariş tamamlandı, bu bir hatadır!"
        );

        Assert.assertEquals(
                driver.findElements(By.className("complete-header")).size(),
                0,
                "Thank you mesajı görüntülendi, boş sepetle sipariş tamamlanmamalı!"
        );
    }
}

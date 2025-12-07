package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;
import utilities.ConfigReader;
import utilities.Waits;

import java.util.List;

@Epic("Cart & Session Isolation")
@Feature("Multi-user cart isolation between different accounts")
public class CrossUserCartIsolationTest extends BaseTest {

    @Story("Cart data must NOT leak between different users")
    @Description("standard_user sepetine ürün ekler, ardından yeni sekmelerde visual_user ve error_user ile login olunur. " +
            "Her kullanıcının kendi sepeti izole olmalı, badge ve cart_item sayıları birbirinden etkilenmemelidir. " +
            "Son adımda standard_user tekrar login olur ve diğer kullanıcıların sepet verisini görmemelidir.")
    @Test
    public void cartsShouldBeIsolatedBetweenUsers() {

        String url = ConfigReader.getProperty("url");

        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        Waits.pauseSeconds(3);
        loginPage.clickLoginButton();
        Waits.pauseSeconds(3);

        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                "Products sayfası açılmadı, login başarısız olabilir!");
        Waits.pauseSeconds(3);

        productsPage.addProductToCart(0);
        productsPage.addProductToCart(1);

        int stdBadge = productsPage.getCartBadgeCount();
        Assert.assertEquals(stdBadge, 2,
                "standard_user için badge 2 olmalıydı!");
        Waits.pauseSeconds(3);

        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(url);

        loginPage = new LoginPage(driver);
        loginPage.enterUsername("visual_user");
        Waits.pauseSeconds(2);
        loginPage.enterPassword("secret_sauce");
        Waits.pauseSeconds(2);
        loginPage.clickLoginButton();
        Waits.pauseSeconds(3);

        productsPage = new ProductsPage(driver);
        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                "visual_user login sonrası Products sayfası açılmadı!");
        Waits.pauseSeconds(3);

        int visualBadge = productsPage.getCartBadgeCount();
        Assert.assertEquals(visualBadge, 0,
                "visual_user başka kullanıcının sepetindeki ürünleri görüyor!");
        Waits.pauseSeconds(3);

        productsPage.clickCartIcon();
        int visualCartItemCount = getCartItemCount();
        Assert.assertEquals(visualCartItemCount, 0,
                "visual_user sepet sayfasında ürün görüyor, leak olabilir!");
        Waits.pauseSeconds(3);

        productsPage.openMenu();
        productsPage.clickLogout();

        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(url);

        loginPage = new LoginPage(driver);
        loginPage.enterUsername("error_user");
        Waits.pauseSeconds(3);
        loginPage.enterPassword("secret_sauce");
        Waits.pauseSeconds(3);
        loginPage.clickLoginButton();

        productsPage = new ProductsPage(driver);
        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                "error_user login sonrası Products sayfası açılmadı!");

        productsPage.addProductToCart(0);
        productsPage.addProductToCart(1);
        productsPage.addProductToCart(2);

        int errorBadge = productsPage.getCartBadgeCount();
        Assert.assertEquals(errorBadge, 3,
                "error_user için badge 3 olmalıydı!");
        Waits.pauseSeconds(3);

        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(url);

        loginPage = new LoginPage(driver);
        loginPage.enterUsername("standard_user");
        Waits.pauseSeconds(3);
        loginPage.enterPassword("secret_sauce");
        Waits.pauseSeconds(3);
        loginPage.clickLoginButton();

        productsPage = new ProductsPage(driver);
        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                "standard_user tekrar login sonrası Products sayfası açılmadı!");
        Waits.pauseSeconds(3);

        int stdBadgeAfter = productsPage.getCartBadgeCount();
        Assert.assertEquals(stdBadgeAfter, 0,
                "standard_user, diğer kullanıcıların sepet verisini görüyor!");
        Waits.pauseSeconds(3);

        productsPage.clickCartIcon();
        int stdCartItemCountAfter = getCartItemCount();
        Assert.assertEquals(stdCartItemCountAfter, 0,
                "standard_user sepetinde logout / kullanıcı değişimi sonrası ürün kalmış!");
        Waits.pauseSeconds(3);
    }

    private int getCartItemCount() {
        List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
        return cartItems.size();
    }
}

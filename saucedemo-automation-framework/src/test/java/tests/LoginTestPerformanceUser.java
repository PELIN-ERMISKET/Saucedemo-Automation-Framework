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

@Epic("Performance Tests")
@Feature("Login Performance")
@Story("Performance glitch user page load validation")
public class LoginTestPerformanceUser extends BaseTest {

    @Test
    @Description("performance_glitch_user ile login sonrası ürün sayfasının açılma süresi ölçülür. 1000 ms üzeri yüklenme performans hatası olarak değerlendirilir.")
    public void performanceUserLogin() {

        LoginPage loginPage = new LoginPage(driver);

        long startTime = System.currentTimeMillis();

        loginPage.openLoginPage();
        loginPage.enterUsername("performance_glitch_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.waitForAllProductImages();

        long endTime = System.currentTimeMillis();
        long loadTime = endTime - startTime;

        System.out.println("Gerçek yüklenme süresi: " + loadTime + " ms");

        Assert.assertTrue(
                loadTime <= 1000,
                "HATA: Performans problemi! Sayfa 1 saniyeden geç açıldı -> " + loadTime + " ms"
        );
    }
}

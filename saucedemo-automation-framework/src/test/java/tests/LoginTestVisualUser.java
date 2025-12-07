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

@Epic("Login Module")
@Feature("Visual User Login")
@Story("Visual_user kullanıcı tipiyle başarılı giriş doğrulaması")
public class LoginTestVisualUser extends BaseTest {

    @Test
    @Description("visual_user kullanıcısının başarıyla login olup Products Page'in görüntülendiğini doğrular.")
    public void visualUserShouldLoginSuccessfully() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.enterUsername("visual_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        ProductsPage productsPage = new ProductsPage(driver);

        Assert.assertTrue(
                productsPage.isProductsPageDisplayed(),
                "Login başarısız! Products Page görüntülenmedi."
        );
    }
}

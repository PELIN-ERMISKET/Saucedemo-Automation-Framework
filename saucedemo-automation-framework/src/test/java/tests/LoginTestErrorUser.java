package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Description;

@Epic("Login Module")
@Feature("Negative Login Scenarios")
public class LoginTestErrorUser extends BaseTest {

    @Story("Error user login attempt")
    @Description("Hatalı kullanıcı olan 'error_user' ile giriş yapılır, login sonrası Products Page görüntülenmelidir.")
    @Test
    public void LoginErrorUser() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.enterUsername("error_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        ProductsPage productsPage = new ProductsPage(driver);

        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                "Login başarısız! Products Page görüntülenmedi.");
    }
}

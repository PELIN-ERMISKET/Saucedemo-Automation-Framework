package tests;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;

@Epic("Login Module")
@Feature("Positive Login")
@Story("Standart kullanıcı başarılı giriş senaryosu")
public class LoginTest extends BaseTest {

    @Test
    @Description("Standard_user ile başarılı login sonrası ürün sayfasının görüntülendiğini doğrular.")
    public void loginTest() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        ProductsPage productsPage = new ProductsPage(driver);

        Assert.assertTrue(
                productsPage.getProductTitle().isDisplayed(),
                "Login başarısız → ürün sayfası açılmadı!"
        );
    }
}

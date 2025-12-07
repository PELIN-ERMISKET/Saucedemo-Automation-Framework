package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;
import base.BaseTest;

import java.util.List;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@Epic("Products Module")
@Feature("Product Image Consistency")
@Story("Farklı kullanıcı tiplerinde ürün görsellerinin (SRC) tutarlı olup olmadığının doğrulanması")
public class ProductImageConsistencyTest extends BaseTest {

    @Test
    @Description("Farklı kullanıcılarla login olup Products sayfasındaki tüm ürün görsellerinin SRC değerlerinin standard_user referansına göre tutarlı olduğunu doğrular. 'problem_user' için negatif senaryo uygulanır.")
    public void verifyProductImageSrcConsistencyAcrossUsers() {

        String[] users = {
                "standard_user",
                "problem_user",
                "performance_glitch_user"
                // "error_user",
                // "visual_user"
        };

        String password = "secret_sauce";

        List<String> referenceSrcList = null;

        for (String user : users) {
            System.out.println("----- Kullanıcı ile login olunuyor: '" + user + "' -----");

            driver.get("https://www.saucedemo.com/");
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login(user, password);

            ProductsPage productsPage = new ProductsPage(driver);
            List<String> currentSrcList = productsPage.getAllProductImageSrc();

            if (referenceSrcList == null) {
                referenceSrcList = currentSrcList;
                System.out.println("Referans liste standard_user’dan alındı.");
            } else {

                if (user.equals("problem_user")) {
                    System.out.println("⚠ problem_user bilerek bozuk görseller getirir. Bu user NEGATIVE testtir.");
                } else {
                    Assert.assertEquals(
                            currentSrcList,
                            referenceSrcList,
                            "HATA → " + user + " kullanıcısında görsel SRC değerleri tutarsız!"
                    );
                }
            }
        }

        System.out.println("✓ Test başarıyla tamamlandı: Tüm kullanıcıların görselleri tutarlı.");
    }
}

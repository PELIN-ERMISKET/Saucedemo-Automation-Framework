package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;

import java.util.LinkedHashMap;
import java.util.Map;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@Epic("Products Module")
@Feature("Product Price Consistency")
@Story("Farklı kullanıcı tiplerinde her bir ürünün fiyatının tutarlı olup olmadığının kontrolü")
public class ProductPriceConsistencyTest extends BaseTest {

    @Test
    @Description("standard_user için ürün bazlı fiyatları beklenen test datasıyla doğrular ve diğer kullanıcı tiplerinde (problem_user, performance_glitch_user, visual_user) her bir ürünün fiyatının standard_user referansına göre tutarlı olduğunu kontrol eder.")
    public void verifyProductPriceConsistencyPerProductAcrossUsers() {

        String[] users = {
                "standard_user",
                "problem_user",
                "performance_glitch_user",
                "visual_user"
        };

        String password = "secret_sauce";

        Map<String, String> expectedStandardUserPrices = new LinkedHashMap<>();
        expectedStandardUserPrices.put("Sauce Labs Backpack", "$29.99");
        expectedStandardUserPrices.put("Sauce Labs Bike Light", "$9.99");
        expectedStandardUserPrices.put("Sauce Labs Bolt T-Shirt", "$15.99");
        expectedStandardUserPrices.put("Sauce Labs Fleece Jacket", "$49.99");
        expectedStandardUserPrices.put("Sauce Labs Onesie", "$7.99");
        expectedStandardUserPrices.put("Test.allTheThings() T-Shirt (Red)", "$15.99");

        Map<String, String> referencePriceMap = null;

        for (String user : users) {

            System.out.println("----- Kullanıcı ile login olunuyor: '" + user + "' -----");


            driver.get("https://www.saucedemo.com/");
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login(user, password);


            ProductsPage productsPage = new ProductsPage(driver);
            Map<String, String> currentPriceMap = productsPage.getProductNamePriceMap();

            if (referencePriceMap == null) {

                referencePriceMap = currentPriceMap;
                System.out.println("Referans fiyat map'i standard_user'dan alındı.");


                Assert.assertEquals(
                        referencePriceMap,
                        expectedStandardUserPrices,
                        "standard_user için ürün bazlı fiyatlar beklenen test datasıyla eşleşmiyor!"
                );

            } else {

                Assert.assertEquals(
                        currentPriceMap,
                        referencePriceMap,
                        "HATA → " + user + " kullanıcısında ürün bazlı fiyatlar standard_user ile TUTARSIZ!"
                );
            }
        }

        System.out.println("✓ Test başarıyla tamamlandı: Tüm kullanıcı tiplerinde her bir ürünün fiyatı aynı.");
    }
}

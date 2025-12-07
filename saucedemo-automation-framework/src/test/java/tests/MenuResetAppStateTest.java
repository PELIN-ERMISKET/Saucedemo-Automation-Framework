package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ProductsPage;

@Epic("Menu Module")
@Feature("Reset App State")
@Story("Reset App State clears cart and UI state")
public class MenuResetAppStateTest extends BaseTest {

    @Test
    @Description("Menüdeki 'Reset App State' özelliğinin sepetteki ürünleri temizlediğini ve cart badge'in sıfırlandığını doğrular.")
    public void resetAppStateShouldClearCartBadge() {

        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        ProductsPage productsPage = new ProductsPage(driver);

        productsPage.addProductToCart(0);
        productsPage.addProductToCart(1);

        int badgeBefore = productsPage.getCartBadgeCount();
        Assert.assertTrue(
                badgeBefore > 0,
                "Reset öncesi badge boş, ürün eklenmemiş!"
        );

        productsPage.openMenu();
        productsPage.clickResetAppState();

        driver.navigate().refresh();

        int badgeAfter = productsPage.getCartBadgeCount();
        Assert.assertEquals(
                badgeAfter,
                0,
                "Reset sonrası sepet badge hala dolu!"
        );
    }
}

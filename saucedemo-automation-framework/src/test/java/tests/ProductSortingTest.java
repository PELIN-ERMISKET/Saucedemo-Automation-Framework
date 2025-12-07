package tests;

import base.BaseTest;
import base.Driver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Description;

@Epic("Products Module")
@Feature("Sorting Feature")
@Story("Ürünlerin farklı sıralama seçeneklerine göre doğru şekilde listelenmesini doğrulama")
public class ProductSortingTest extends BaseTest {

    @Test
    @Description("A→Z, Z→A, fiyat düşük→yüksek ve fiyat yüksek→düşük sıralamalarının doğru çalıştığını doğrular.")
    public void verifyProductSorting() {

        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);

        loginPage.openLoginPage();
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                "Products sayfası görüntülenemedi!");

        productsPage.sortByNameAToZ();
        List<String> namesAToZ = productsPage.getProductNamesOnPage();
        List<String> expectedAToZ = new ArrayList<>(namesAToZ);
        Collections.sort(expectedAToZ);
        Assert.assertEquals(namesAToZ, expectedAToZ,
                "Ürünler Name (A to Z) sıralamasında değil!");

        productsPage.sortByNameZToA();
        List<String> namesZToA = productsPage.getProductNamesOnPage();
        List<String> expectedZToA = new ArrayList<>(namesZToA);
        Collections.sort(expectedZToA, Collections.reverseOrder());
        Assert.assertEquals(namesZToA, expectedZToA,
                "Ürünler Name (Z to A) sıralamasında değil!");


        productsPage.sortByPriceLowToHigh();
        List<Double> pricesLowHigh = productsPage.getProductPricesOnPage();
        List<Double> expectedLowHigh = new ArrayList<>(pricesLowHigh);
        Collections.sort(expectedLowHigh);
        Assert.assertEquals(pricesLowHigh, expectedLowHigh,
                "Ürünler Price (low to high) sıralamasında değil!");


        productsPage.sortByPriceHighToLow();
        List<Double> pricesHighLow = productsPage.getProductPricesOnPage();
        List<Double> expectedHighLow = new ArrayList<>(pricesHighLow);
        Collections.sort(expectedHighLow, Collections.reverseOrder());
        Assert.assertEquals(pricesHighLow, expectedHighLow,
                "Ürünler Price (high to low) sıralamasında değil!");
    }
}

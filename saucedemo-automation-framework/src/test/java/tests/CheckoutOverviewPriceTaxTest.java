package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

@Epic("Checkout Module")
@Feature("Price & Tax Calculation")
public class CheckoutOverviewPriceTaxTest extends BaseTest {

    @Story("User sees correct item total, tax and final total on checkout overview page")
    @Description("3 ürün sepete eklenir, checkout overview sayfasında item total, tax ve total değerlerinin "
            + "backend formülüne (sum + %8 tax) göre doğru hesaplandığı doğrulanır.")
    @Test
    public void pricesAndTax_ShouldBeCalculatedCorrectly() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        loginPage.openLoginPage();
        loginPage.login("standard_user", "secret_sauce");
        wait.until(ExpectedConditions.urlContains("inventory"));

        driver.findElement(By.cssSelector("button[data-test='add-to-cart-sauce-labs-backpack']")).click();
        driver.findElement(By.cssSelector("button[data-test='add-to-cart-sauce-labs-bike-light']")).click();
        driver.findElement(By.cssSelector("button[data-test='add-to-cart-sauce-labs-bolt-t-shirt']")).click();

        driver.findElement(By.className("shopping_cart_link")).click();
        wait.until(ExpectedConditions.urlContains("cart"));
        driver.findElement(By.id("checkout")).click();
        wait.until(ExpectedConditions.urlContains("checkout-step-one"));

        driver.findElement(By.id("first-name")).sendKeys("Test");
        driver.findElement(By.id("last-name")).sendKeys("User");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.id("continue")).click();

        wait.until(ExpectedConditions.urlContains("checkout-step-two"));

        double priceBackpack   = 29.99;
        double priceBikeLight  = 9.99;
        double priceBoltTshirt = 15.99;

        double expectedItemTotal = priceBackpack + priceBikeLight + priceBoltTshirt;
        double expectedTax       = roundTo2Decimals(expectedItemTotal * 0.08); // %8 vergi
        double expectedTotal     = roundTo2Decimals(expectedItemTotal + expectedTax);

        WebElement itemTotalLabel = driver.findElement(By.className("summary_subtotal_label"));
        WebElement taxLabel       = driver.findElement(By.className("summary_tax_label"));
        WebElement totalLabel     = driver.findElement(By.className("summary_total_label"));

        double actualItemTotal = extractAmount(itemTotalLabel.getText()); // "Item total: $55.97"
        double actualTax       = extractAmount(taxLabel.getText());       // "Tax: $4.48"
        double actualTotal     = extractAmount(totalLabel.getText());     // "Total: $60.45"

        Assert.assertEquals(actualItemTotal, expectedItemTotal, 0.01,
                "item_total = price1 + price2 + price3 eşit değil!");

        Assert.assertEquals(actualTax, expectedTax, 0.01,
                "tax = item_total * 0.08 hesaplanmamış!");

        Assert.assertEquals(actualTotal, expectedTotal, 0.01,
                "total = item_total + tax eşit değil!");
    }

    private double extractAmount(String text) {
        String onlyNumber = text.replaceAll("[^0-9.]", "");
        return Double.parseDouble(onlyNumber);
    }

    private double roundTo2Decimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}

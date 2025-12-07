package pages;

import base.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {

    private WebDriver driver = Driver.getDriver();

    private By cartBadge      = By.className("shopping_cart_badge");
    private By checkoutButton = By.id("checkout");
    private By cartItems      = By.className("cart_item");
    private By removeButtons  = By.cssSelector("button[id^='remove']");

    public CartPage() {}

    public int getCartBadgeCount() {
        String text = driver.findElement(cartBadge).getText();
        return Integer.parseInt(text);
    }

    public boolean isItemInCart() {
        return driver.findElements(cartItems).size() > 0;
    }

    public void removeProduct(int index) {
        driver.findElements(removeButtons).get(index).click();
    }

    public void clickCheckoutButton() {
        driver.findElement(checkoutButton).click();
    }
}

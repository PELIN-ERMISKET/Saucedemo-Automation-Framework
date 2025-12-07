package pages;

import base.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutCompletePage {

    private WebDriver driver = Driver.getDriver();

    private By successMessage = By.className("complete-header");
    private By backHomeButton = By.id("back-to-products");

    public boolean isSuccessMessageDisplayed() {
        return driver.findElement(successMessage).isDisplayed();
    }

    public void clickBackHomeButton() {
        driver.findElement(backHomeButton).click();
    }
}

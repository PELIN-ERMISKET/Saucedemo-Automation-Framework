package pages;

import base.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

public class CheckoutOverviewPage {

    private WebDriver driver = Driver.getDriver();

    private By totalPrice = By.className("summary_total_label");
    private By finishButton = By.id("finish");
    private By overviewContainer = By.id("checkout_summary_container");

    public String getTotalPrice(){
        return driver.findElement(totalPrice).getText();
    }

    public void clickFinish(){
        driver.findElement(finishButton).click();
    }

    public void clickFinishButton() {
        driver.findElement(finishButton).click();
    }

    public boolean isOverviewPageDisplayed() {
        return driver.findElement(overviewContainer).isDisplayed();
    }

}

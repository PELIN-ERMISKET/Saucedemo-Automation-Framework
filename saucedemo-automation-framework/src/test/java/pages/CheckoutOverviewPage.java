package pages;

import base.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutOverviewPage {

    private WebDriver driver = Driver.getDriver();

    private By totalPrice = By.className("summary_total_label");
    private By finishButton = By.id("finish");
    private By overviewContainer = By.id("checkout_summary_container");

    public String getTotalPrice() {
        return driver.findElement(totalPrice).getText();
    }

    public void clickFinish() {
        driver.findElement(finishButton).click();
    }

    public void clickFinishButton() {
        clickFinish();
    }

    public boolean isOverviewPageDisplayed() {
        try {

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(overviewContainer));

            return driver.findElement(overviewContainer).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {

            return false;
        }
    }
}

package pages;

import base.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {

    private WebDriver driver = Driver.getDriver();

    private By usernameInput = By.id("user-name");
    private By passwordInput = By.id("password");
    private By loginButton = By.id("login-button");
    private By errorButton = By.cssSelector("[data-test='error-button']");
    private By errorMessage = By.cssSelector("[data-test='error']");


    public boolean isLoginButtonDisplayed() {
        return driver.findElement(loginButton).isDisplayed();
    }


    public String getErrorMessageText() {
        return driver.findElement(errorMessage).getText();
    }

    public void enterUsername(String username) {
        driver.findElement(usernameInput).sendKeys(username);
    }

    public void enterPassword (String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }
    public void openLoginPage() {
        driver.get("https://www.saucedemo.com/");
    }
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }
    public WebElement getErrorButton() {
        return driver.findElement(errorButton);
    }



}

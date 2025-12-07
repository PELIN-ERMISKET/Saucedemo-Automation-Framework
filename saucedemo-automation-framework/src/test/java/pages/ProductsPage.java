package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

public class    ProductsPage {

    private WebDriver driver;

    private By titleText               = By.className("title");
    private By firstItemAddToCartButton =
            By.xpath("(//button[contains(@id,'add-to-cart')])[1]");
    private By cartIcon = By.cssSelector("a.shopping_cart_link");   // Sepet ikonu

    private By productTitle            = By.className("title");
    private By productImages           = By.className("inventory_item_img");
    private By productNames            = By.className("inventory_item_name");
    private By productDescriptions     = By.cssSelector(".inventory_item_desc");
    private By detailImage             = By.className("inventory_details_img");
    private By backToProductsButton    = By.id("back-to-products");
    private By firstProductPrice       = By.cssSelector(".inventory_item_price");
    private By firstProductName        = By.className("inventory_item_name");
    private By menuButton              = By.id("react-burger-menu-btn");
    private By logoutLink              = By.id("logout_sidebar_link");
    private By addToCartButtons        = By.cssSelector(".btn_inventory");     // Add to cart butonları
    private By cartBadge               = By.className("shopping_cart_badge");  // Sepet badge
    private By cartRemoveButtons       = By.cssSelector(".cart_button");
    private By sortDropdown            = By.cssSelector("select[data-test='product_sort_container']");
    private By productPrices           = By.cssSelector(".inventory_item_price");
    private By resetAppStateLink       = By.id("reset_sidebar_link");
    private By inventoryItems          = By.className("inventory_item");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
    }

    public List<String> getAllProductPrices() {
        List<WebElement> priceElements = driver.findElements(productPrices);

        List<String> prices = new ArrayList<>();
        for (WebElement price : priceElements) {
            prices.add(price.getText().trim());     // "$29.99" gibi
        }
        return prices;
    }

    public Map<String, String> getProductNamePriceMap() {
        List<WebElement> items = driver.findElements(inventoryItems);

        Map<String, String> namePriceMap = new LinkedHashMap<>();

        for (WebElement item : items) {
            String name  = item.findElement(productNames).getText().trim();
            String price = item.findElement(productPrices).getText().trim();
            namePriceMap.put(name, price);
        }

        return namePriceMap;
    }

    public String getAddToCartButtonText(int index) {
        return driver.findElements(addToCartButtons).get(index).getText().trim();
    }

    public void clickResetAppState() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement resetLink = wait.until(
                ExpectedConditions.elementToBeClickable(resetAppStateLink)
        );
        resetLink.click();
    }

    private WebElement getSortSelect() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Önce Products sayfasının geldiğinden emin ol
        wait.until(ExpectedConditions.urlContains("inventory.html"));

        // Sonra sort dropdown'un DOM'a gelmesini ve görünmesini bekle
        By sortSelect = By.className("product_sort_container");
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(sortSelect)
        );
    }

    public void sortByNameAToZ() {
        Select select = new Select(getSortSelect());
        select.selectByValue("az");
    }

    public void sortByNameZToA() {
        Select select = new Select(getSortSelect());
        select.selectByValue("za");
    }

    public void sortByPriceLowToHigh() {
        Select select = new Select(getSortSelect());
        select.selectByValue("lohi");
    }

    public void sortByPriceHighToLow() {
        Select select = new Select(getSortSelect());
        select.selectByValue("hilo");
    }

    public List<String> getProductNamesOnPage() {
        List<WebElement> elements = driver.findElements(productNames);
        List<String> names = new ArrayList<>();
        for (WebElement el : elements) {
            names.add(el.getText().trim());
        }
        return names;
    }

    public List<Double> getProductPricesOnPage() {
        List<WebElement> elements = driver.findElements(productPrices);
        List<Double> prices = new ArrayList<>();
        for (WebElement el : elements) {
            String text = el.getText().replace("$", "").trim();
            prices.add(Double.parseDouble(text));
        }
        return prices;
    }

    public void addProductToCart(int index) {
        List<WebElement> buttons = driver.findElements(addToCartButtons);
        buttons.get(index).click();
    }

    public int getCartBadgeCount() {
        try {
            String text = driver.findElement(cartBadge).getText();
            return Integer.parseInt(text);
        } catch (Exception e) {
            return 0;
        }
    }

    public void removeProductFromCartInCartPage(int index) {
        List<WebElement> removeBtns = driver.findElements(cartRemoveButtons);
        removeBtns.get(index).click();
    }

    public void openMenu() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(menuButton));
        menu.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(logoutLink));
    }

    public void clickLogout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement logout = wait.until(ExpectedConditions.elementToBeClickable(logoutLink));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", logout);
    }

    public List<WebElement> getProductImages() {
        return driver.findElements(productImages);
    }

    public void addFirstItemToCart() {
        driver.findElement(firstItemAddToCartButton).click();
    }

    public void clickCartIcon() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement icon = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector(".shopping_cart_link"))
        );

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", icon);
    }

    public WebElement getProductTitle() {
        return driver.findElement(productTitle);
    }

    public void openProductByIndex(int index) {
        List<WebElement> names = driver.findElements(productNames);
        names.get(index).click();
    }

    public String getDetailImageSrc() {
        return driver.findElement(detailImage).getAttribute("src");
    }

    public void clickBackToProducts() {
        driver.findElement(backToProductsButton).click();
    }

    public void waitForAllProductImages() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                By.className("inventory_item_img"), 5
        ));
    }

    public boolean isProductsPageDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(titleText));

            String currentUrl = driver.getCurrentUrl();
            String title      = driver.findElement(titleText).getText();

            System.out.println("Login sonrası URL: " + currentUrl + " | Title: " + title);

            boolean urlOk   = currentUrl.contains("inventory");
            boolean titleOk = "Products".equalsIgnoreCase(title.trim());

            return urlOk && titleOk;
        } catch (Exception e) {
            System.out.println("Products sayfası yüklenemedi. Mevcut URL: " + driver.getCurrentUrl());
            return false;
        }
    }

    public boolean isOnProductsPage() {
        return driver.getCurrentUrl().contains("inventory.html");
    }

    public List<String> getAllProductImageSrc() {
        List<WebElement> images = driver.findElements(By.cssSelector("img.inventory_item_img"));
        List<String> srcList = new ArrayList<>();

        for (WebElement img : images) {
            srcList.add(img.getAttribute("src"));
        }
        return srcList;
    }

    public String getFirstProductPriceOnList() {
        return driver.findElements(firstProductPrice).get(0).getText();
    }

    public void clickFirstProduct() {
        driver.findElements(firstProductName).get(0).click();
    }

    public String getFirstProductNameFromList() {
        return driver.findElements(productNames).get(0).getText();
    }

    public String getFirstProductDescriptionFromList() {
        return driver.findElements(productDescriptions).get(0).getText();
    }

    public String getFirstProductPriceFromList() {
        return driver.findElements(productPrices).get(0).getText();
    }

    public ProductDetailPage clickFirstProductName() {
        driver.findElements(productNames).get(0).click();
        return new ProductDetailPage(driver);
    }
}

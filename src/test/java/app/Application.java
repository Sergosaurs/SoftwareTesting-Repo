package app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.CartPage;
import pages.GeneralPage;
import pages.ProductPage;

import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;

public class Application {

    WebDriver driver;
    CartPage cartPage;
    GeneralPage generalPage;
    ProductPage productPage;
    WebDriverWait wait;

    public Application() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
        generalPage = new GeneralPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
    }

    public void openMainPage() {
        generalPage.openGeneralPage();
    }

    public void openMostPopularPage() {
        generalPage.mostPopularPage();
    }

    public void addProductToCart() {
        productPage.requiredFieldValidation();
        productPage.addToCart.click();
        productPage.quantity = driver.findElement(By.cssSelector("span.quantity"));
        wait.until(ExpectedConditions.textToBePresentInElement(productPage.quantity,
                String.valueOf(productPage.productCounter() + 1)));
    }

    public void openCartPage() {
        cartPage.openCart();
        wait.until(presenceOfElementLocated(By.cssSelector(".dataTable tr")));
    }

    public void clearCart() {
        int numberOfElements = cartPage.cartItems.size();
        while (numberOfElements > 0) {
            cartPage.productTable = driver.findElement(By.cssSelector("table.dataTable"));
            cartPage.removeFromCart.click();
            wait.until(stalenessOf((cartPage.productTable)));
            numberOfElements--;
        }
        cartPage.assertCartIsEmpty();
    }

    public void quit() {
        driver.quit();
    }
}
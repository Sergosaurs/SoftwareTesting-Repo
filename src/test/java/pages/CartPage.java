package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends Page {

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".dataTable")
    public WebElement productTable;

    @FindBy(name = "remove_cart_item")
    public WebElement removeFromCart;

    @FindBy(css = "table.dataTable td.item")
    public List<WebElement> cartItems;

    @FindBy(css = "#page td.content")
    public WebElement pageContent;

    @FindBy(css = "#cart .link")
    public WebElement cartCheckout;

    /*
     * открыть корзину
     */
    public void openCart() {
        cartCheckout.click();
    }

}
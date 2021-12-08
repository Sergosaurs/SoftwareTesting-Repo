package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class ProductPage extends Page {

    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "td.options")
    public List<WebElement> options;

    @FindBy(css = "span.quantity")
    public WebElement quantity;

    @FindBy(name = "add_cart_product")
    public WebElement addToCart;

    @FindBy(name = "options[Size]")
    public WebElement sizeField;

    /*
     * Счетчик количества товара в корзине
     */
    public int productCounter() {
        return Integer.parseInt(quantity.getAttribute("textContent"));
    }

    /*
     * Выбор значения в списке "Size" если он присутствует
     */
    public void requiredFieldValidation() {
        if (isElementPresent(options)) {
            new Select(sizeField).selectByVisibleText(getProductRandomSize());
        }
    }

    /*
     * Проверка наличия элемента без задержки
     */
    public boolean isElementPresent(List<WebElement> elementList) {
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            return elementList.size() > 0;
        } finally {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
    }

    /*
     * Сучайное значения из списка "Size"
     */
    public String getProductRandomSize() {
        List<String> givenList = new ArrayList<>();
        givenList.add("Small");
        givenList.add("Medium +$2.50");
        givenList.add("Large +$5");
        return givenList.get(new Random().nextInt(givenList.size()));
    }
}
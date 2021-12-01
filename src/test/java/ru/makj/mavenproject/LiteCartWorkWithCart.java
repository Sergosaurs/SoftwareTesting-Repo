package ru.makj.mavenproject;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class LiteCartWorkWithCart extends AppTest {

    @Test
    public void emptyShoppingCartTest() {
        driver.get("http://localhost/litecart/en/");

        int counter = 0;
        WebElement productTable;

        while (counter != 3) {
            click(By.cssSelector("#box-most-popular li:nth-child(1)"));

            if (isElementPresent(By.cssSelector("td.options"))) {
                selectInDropDownList(By.cssSelector("[name='options[Size]']"), getRandomSize());
            }

            click(By.cssSelector("button[name='add_cart_product']"));
            counter++;
            WebElement cartPresent = driver.findElement(By.cssSelector("span.quantity"));
            wait.until(textToBePresentInElement(cartPresent, String.valueOf(counter)));
            driver.navigate().back();
        }
        driver.findElement(By.cssSelector("#cart .link")).click();
        wait.until(presenceOfElementLocated(By.cssSelector(".dataTable tr")));

        int cartItems = driver.findElements(By.cssSelector("table.dataTable td.item")).size();

        while (cartItems > 0) {
            productTable = driver.findElement(By.cssSelector("table.dataTable"));
            click(By.cssSelector("button[name=remove_cart_item]"));
            wait.until(stalenessOf((productTable)));
            cartItems--;
        }
        Assert.assertTrue("The text indicating that the cart is empty was not found",
                driver.findElement(By.cssSelector("#page td.content")).getText().contains("There are no items in your cart."));
    }


    /*
    Проверка наличия элемента без задержки
     */
    boolean isElementPresent(By locator) {
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            return driver.findElements(locator).size() > 0;
        } finally {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
    }

    /*
    Случайного значения из списка "Size"
     */
    public String getRandomSize() {
        List<String> givenList = new ArrayList<>();
        givenList.add("Small");
        givenList.add("Medium +$2.50");
        givenList.add("Large +$5");
        return givenList.get(new Random().nextInt(givenList.size()));
    }
}

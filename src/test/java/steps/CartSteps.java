package steps;

import app.Application;
import io.cucumber.java8.En;

public class CartSteps implements En {

    Application app = new Application();

    public CartSteps() {
        When("go to the main page of the store", () -> {
            app.openMainPage();
        });
        And("add first item to the cart {} times", (Integer number) -> {
            app.addProductToCart(number);
        });
        And("go to the cart page", () -> {
            app.openCartPage();
        });
        And("delete all the items from the shopping cart", () -> {
            app.clearCart();
        });
        Then("shopping cart must be empty", () -> {
            app.cartIsEmpty();
        });
        After(() -> {
            app.quit();
        });
    }
}
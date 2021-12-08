package tests;

import org.junit.Test;

public class ClearCart extends TestBase {

    /*
     * Сценарий для добавления и удаления товаров из корзины
     */
    @Test
    public void fillAndClearCartTest() {
        for (int i = 0; i < 3; i++) {

            app.openMainPage(); //окрыть главную страницу
            app.openMostPopularPage(); //открыть страницу с популярными товарами
            app.addProductToCart(); //добавить товар в корзину
        }

        app.openCartPage(); //открыть корзину
        app.clearCart(); //очистить корзину
    }
}
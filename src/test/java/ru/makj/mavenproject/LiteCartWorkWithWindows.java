package ru.makj.mavenproject;

import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Set;


public class LiteCartWorkWithWindows extends AppTest {

    @Test
    public void linksOpenInANewWindowTest() {

        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        loginAsAdmin();

        click(By.cssSelector("#content .button"));
        String originalWindow = driver.getWindowHandle();
        Set<String> oldWindowsSet = driver.getWindowHandles();

        List<WebElement> linksList = driver.findElements(By.cssSelector(".fa-external-link"));

        for (WebElement element : linksList) {
            element.click();

            //new window id
            String newWindow = wait.until(anyWindowOtherThan(oldWindowsSet));
            driver.switchTo().window(newWindow);

            /*ожидание загрузки страницы, т.к. в моем случае в FireFox переключение происходит очень быстро не ожидая полную загрузку страницы,
            в некоторых случаях не удается получить даже title окон и PageLoad стратегия не отрабатывает, но если указать явно -
            ожидать статус "complete" через js, то все отрабатывает отлично, но в лекции говорилось, что использование document.readyState не всегда хорошо.
            Так же насколько я знаю Thread.sleep использовать не желательно... решил ожидать появление элемента в новом окне,
            так как страниц много и они разные выбрал селектор который содержит тайтл, для моей задачи его думаю достаточно.
            FireFox версии 94.0.2.
            В Chrome подобных проблем нет, он ожидает загрузку страницы до конца, без дополнительных условий..
            */
//            wait.until((ExpectedCondition<Boolean>) driver ->
//                    ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));

            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("head title")));
            System.out.println("Switched to new window with title: " + driver.getTitle());
            driver.close();

            driver.switchTo().window(originalWindow);
            System.out.println("Switched to old window with title: " + driver.getTitle() + "\n");

        }
    }
}
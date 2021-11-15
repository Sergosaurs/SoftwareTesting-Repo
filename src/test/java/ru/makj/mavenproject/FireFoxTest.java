package ru.makj.mavenproject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class FireFoxTest {
    /**
     * Примера по запуску теста на FireFox версии 45ESR,
     * без использования исполнительного вспомогательного файла "gecko-driver"
     * !Важно - Для более поздних версий Selenium 2.x обязательно использовать драйвер gecko,
     * для успешного выполнения данного сценария версия Selenium была понижена до 2.53.1
     */
    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 15);
    }

    @Test
    public void startFireFoxTest() {
        driver.get("http://www.google.com");
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("Hello!");
        element.submit();
        System.out.println("Page title is: " + driver.getTitle());
        wait.until((ExpectedCondition<Boolean>) d -> d.getTitle().toLowerCase().startsWith("hello!"));
        System.out.println("Page title is: " + driver.getTitle());
    }

    @After
    public void stopDriver() {
        driver.quit();
    }
}




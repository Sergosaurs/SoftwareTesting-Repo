package ru.makj.mavenproject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class LiteCartStickerChecker {

    WebDriver driver;
    WebElement duck;
    int stickersNumber;
    String duckName;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://localhost/litecart/en/");
    }

    @Test
    public void runTest() {
        List<WebElement> duckList = driver.findElements(By.cssSelector("li[class*=product]"));
        for (WebElement webElement : duckList) {
            duck = webElement;
            stickersNumber = duck.findElements(By.cssSelector("li[class*=product] .sticker")).size();
            duckName = duck.findElement(By.cssSelector("li[class*=product] .name")).getText();
            if (stickersNumber != 1)
                throw new AssertionError("Error! Might be a Duck have more/less than 1 sticker!");
            else
                System.out.println(duckName + " have only one sticker");
        }
    }

    @After
    public void stop() {
        driver.quit();
    }

}
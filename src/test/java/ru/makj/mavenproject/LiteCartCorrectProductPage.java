package ru.makj.mavenproject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class LiteCartCorrectProductPage {
    private WebDriver driver;

    @Before
    public void setUp() {
//        driver = new ChromeDriver();
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @After
    public void stop() {
        driver.quit();
    }


    //    а) на главной странице и на странице товара совпадает текст названия товара
    @Test
    public void productNameMatching() {
        driver.get("http://localhost/litecart/en/");
        String nameOnMainPage = driver.findElement(By.cssSelector("#box-campaigns .product .name")).getAttribute("textContent");
        driver.findElement(By.cssSelector("#box-campaigns .link")).click();
        String nameOnProdPage = driver.findElement(By.cssSelector("#box-product .title")).getAttribute("textContent");
        assertEquals("The names don't match!", nameOnMainPage, nameOnProdPage);
    }

    //    б) на главной странице и на странице товара совпадают цены (обычная и акционная)
    @Test
    public void productPricesMatching() {
        driver.get("http://localhost/litecart/en/");

        String priceOnMainPage = driver.findElement(By.cssSelector("#box-campaigns .price-wrapper .regular-price")).getAttribute("textContent");
        String campPriceOnMainPage = driver.findElement(By.cssSelector("#box-campaigns .price-wrapper .campaign-price")).getAttribute("textContent");

        driver.findElement(By.cssSelector("#box-campaigns .link")).click();

        String priceOnProdPage = driver.findElement(By.cssSelector("#box-product .price-wrapper .regular-price")).getAttribute("textContent");
        String campPriceOnProdPage = driver.findElement(By.cssSelector("#box-product .price-wrapper .campaign-price")).getAttribute("textContent");

        assertEquals("The prices don't match!", priceOnMainPage, priceOnProdPage);
        assertEquals("The prices don't match!", campPriceOnMainPage, campPriceOnProdPage);
    }

    //    в) обычная цена зачёркнутая и серая (можно считать, что "серый" цвет это такой,
    //    у которого в RGBa представлении одинаковые значения для каналов R, G и B)
    @Test
    public void lineThroughGrayTextCheck() {
        driver.get("http://localhost/litecart/en/");

        WebElement priceOnMainPage = driver.findElement(By.cssSelector("#box-campaigns .price-wrapper .regular-price"));
        String priceOnMainPageDecor = driver.findElement(By.cssSelector("#box-campaigns .price-wrapper .regular-price")).getCssValue("text-decoration");

        Assert.assertTrue(priceOnMainPageDecor.contains("line-through"));
        assertIsGrayColor(getColor(priceOnMainPage));

        driver.findElement(By.cssSelector("#box-campaigns .link")).click();

        WebElement priceOnProdPage = driver.findElement(By.cssSelector("#box-product .price-wrapper .regular-price"));
        String campPriceOnProdPageDecor = driver.findElement(By.cssSelector("#box-product .price-wrapper .regular-price")).getCssValue("text-decoration");

        Assert.assertTrue(campPriceOnProdPageDecor.contains("line-through"));
        assertIsGrayColor(getColor(priceOnProdPage));
    }


    //    г) акционная жирная и красная (можно считать, что "красный" цвет это такой, у которого в RGBa представлении каналы G и B имеют нулевые значения)
//(цвета надо проверить на каждой странице независимо, при этом цвета на разных страницах могут не совпадать)
    @Test
    public void boldRedTextCheck() {
        driver.get("http://localhost/litecart/en/");

        String campPriceOnMainPage = driver.findElement(By.cssSelector("#box-campaigns .price-wrapper .campaign-price")).getCssValue("font-weight");
        WebElement campPriceOnMainPageColor = driver.findElement(By.cssSelector("#box-campaigns .price-wrapper .campaign-price"));

        assertIsRedColor(getColor(campPriceOnMainPageColor));
        assertTrue("The font is not bold!", campPriceOnMainPage.equals("bold") || Integer.parseInt(campPriceOnMainPage) >= 700);

        driver.findElement(By.cssSelector("#box-campaigns .link")).click();

        String campPriceOnProdPage = driver.findElement(By.cssSelector("#box-product .price-wrapper .campaign-price")).getCssValue("font-weight");
        WebElement campPriceOnProdPageColor = driver.findElement(By.cssSelector("#box-product .price-wrapper .campaign-price"));

        assertIsRedColor(getColor(campPriceOnProdPageColor));
        assertTrue("The font is not bold!", campPriceOnProdPage.equals("bold") || Integer.parseInt(campPriceOnProdPage) >= 700);
    }


    //    д) акционная цена крупнее, чем обычная (это тоже надо проверить на каждой странице независимо)
    @Test
    public void compaignPriceGreaterCheck() {
        driver.get("http://localhost/litecart/en/");

        double mainPagePriceFontSize = getTextFontSize(driver.findElement(By.cssSelector("#box-campaigns .price-wrapper .regular-price")));
        double mainPageCampPriceFontSize = getTextFontSize(driver.findElement(By.cssSelector("#box-campaigns .price-wrapper .campaign-price")));

        assertTrue("The font is no larger!", mainPagePriceFontSize < mainPageCampPriceFontSize);

        driver.findElement(By.cssSelector("#box-campaigns .link")).click();

        double prodPagePriceFontSize = getTextFontSize(driver.findElement(By.cssSelector("#box-product .price-wrapper .regular-price")));
        double prodPageCampPriceFontSize = getTextFontSize(driver.findElement(By.cssSelector("#box-product .price-wrapper .campaign-price")));

        assertTrue("The font is no larger!", prodPagePriceFontSize < prodPageCampPriceFontSize);
    }


    /**
     * Получение значений RGB у элемента
     *
     * @param webElement элемент для извлечения RGB
     * @return список значений RGB
     */
    public List<String> getColor(WebElement webElement) {
        String colorExtractor = webElement.getCssValue("color");
        return Arrays.asList(colorExtractor
                .substring(colorExtractor.indexOf("(") + 1, colorExtractor.lastIndexOf(")")).split(", "));
    }

    /**
     * Получение размера текста в численном виде
     *
     * @param webElement элемент для извлечения числа
     * @return число типа double
     */
    public double getTextFontSize(WebElement webElement) {
        return Double.parseDouble(webElement.getCssValue("font-size").replace("px", ""));
    }

    /**
     * Проверка, что элемент серого цвета
     * (можно считать, что "серый" цвет это такой,
     * у которого в RGBa представлении одинаковые значения для каналов R, G и B)
     *
     * @param colorList лист со значениями RGB
     */
    public void assertIsGrayColor(List<String> colorList) {
        Assert.assertTrue("Colors don't match!", colorList.get(0).equals(colorList.get(1)) & colorList.get(0).equals(colorList.get(2)));
    }

    /**
     * Проверка, что элемент красного цвета
     * (можно считать, что "красный" цвет это такой,
     * у которого в RGBa представлении каналы G и B имеют нулевые значения)
     *
     * @param colorList лист со значениями RGB
     */
    public void assertIsRedColor(List<String> colorList) {
        Assert.assertTrue("Colors don't match!", colorList.get(1).equals("0") & colorList.get(2).equals("0"));
    }

}


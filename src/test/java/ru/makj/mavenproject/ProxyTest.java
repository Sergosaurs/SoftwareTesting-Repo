package ru.makj.mavenproject;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class ProxyTest {
    public WebDriver driver;
    public BrowserMobProxy proxy;

    @Before
    public void setup() {

        // старт прокси
        proxy = new BrowserMobProxyServer();
        proxy.setTrustAllServers(true);
        proxy.start(9091);

        // получить объект Selenium
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--ignore-certificate-errors");
        chromeOptions.setProxy(seleniumProxy);

        // создание драйвера
        driver = new ChromeDriver(chromeOptions);

    }

    @Test
    public void testSearch() {

        // включить более детальный захват HAR
        proxy.newHar("www.google.com");
        driver.get("https://www.google.com/");

        WebElement search = driver.findElement(By.name("q"));
        search.sendKeys("Selenium WebDriver", Keys.ENTER);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // получить данные HAR
        Har har = proxy.getHar();
        System.out.println("HAR: " + har.getLog().getVersion());
        for (int i = 0; i < har.getLog().getEntries().size(); i++) {
            String link = har.getLog().getEntries().get(i).getRequest().getUrl();
            int code = har.getLog().getEntries().get(i).getResponse().getStatus();
            System.out.println(code + " LINK: " + link);
        }

        List<WebElement> elements = driver.findElements(By.className("g"));
        Assert.assertNotEquals(0, elements.size());
    }

    @After
    public void tearDown() {
        driver.quit();
        proxy.stop();
    }
}

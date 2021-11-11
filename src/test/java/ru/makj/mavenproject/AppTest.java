package ru.makj.mavenproject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;

public class AppTest {
    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 15);
    }

    @Test
    public void runTest() {
        driver.get("https://www.yahoo.com");
        driver.findElement(By.id("ybar-sbq")).sendKeys("Selenium");
        driver.findElement(By.id("ybar-search")).click();
        wait.until(ExpectedConditions.titleContains("Selenium - Yahoo Search Results"));
    }

    @After
    public void stop() {
        driver.close();
    }

}

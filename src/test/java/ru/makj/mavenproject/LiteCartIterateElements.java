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

public class LiteCartIterateElements {

    WebDriver driver;
    WebElement mainList;
    WebElement innerList;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void runTest() {
        driver.get("http://localhost/litecart/admin");
        loginForm();

        List<WebElement> externalElements = driver.findElements(By.cssSelector("ul#box-apps-menu li#app-"));
        for (int i = 0; i < externalElements.size(); i++) {
            externalElements = driver.findElements(By.cssSelector("ul#box-apps-menu li#app-"));
            mainList = externalElements.get(i);
            System.out.println("Click on menu tab: " + mainList.getText());
            mainList.click();
            titleChecker();

            List<WebElement> internalElements = driver.findElements(By.cssSelector("ul[class*=docs]  li"));
            for (int j = 0; j < internalElements.size(); j++) {
                internalElements = driver.findElements(By.cssSelector("ul[class*=docs] li"));
                innerList = internalElements.get(j);
                System.out.println("Click on element: " + innerList.getText());
                innerList.click();
                titleChecker();
            }
        }
    }

    @After
    public void stop() {
        driver.quit();
    }

    public void loginForm() {
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    public void titleChecker() {
        WebElement headText = driver.findElement(By.cssSelector("td#content>h1"));
        System.out.println("Title with name: '" + headText.getText() + "' is displayed." + "\n");
    }


}


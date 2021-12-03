package ru.makj.mavenproject;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.util.Hashtable;
import java.util.Set;
import java.net.URL;


public class CloudTestingPlatform {

    public static final String USERNAME = "sergemacoveu_1DmhR5";
    public static final String AUTOMATE_KEY = "XJYy3A7rgywkRZh3eSfu";
    public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";
    WebDriver driver;

    @Test
    public void testRunner90() {
        runMobile();
        runEdge();
        runFireFox();
    }

    public void executeTest(Hashtable<String, String> capsHashtable) {
        String key;
        DesiredCapabilities caps = new DesiredCapabilities();
        Set<String> keys = capsHashtable.keySet();
        for (String s : keys) {
            key = s;
            caps.setCapability(key, capsHashtable.get(key));
        }
        try {
            driver = new RemoteWebDriver(new URL(URL), caps);
            driver.get("https://lambdatest.github.io/sample-todo-app/");
            driver.findElement(By.name("li1")).click();
            driver.findElement(By.name("li2")).click();
            driver.findElement(By.id("sampletodotext")).sendKeys("Yey, Let's add it to list", Keys.ENTER);
            new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div > div > div > ul > li:nth-child(6) > span")));
            String enteredText = driver.findElement(By.cssSelector("body > div > div > div > ul > li:nth-child(6) > span")).getText();
            if (enteredText.equals("Yey, Let's add it to list")) {
                System.out.println(enteredText + " - Cloud Testing Is Complete");
            } else {
                Assert.fail("Test is not complete!");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver.quit();
    }


    /*
    Capabilities builder
     */
    public void runFireFox() {
        Hashtable<String, String> capsHashtable = new Hashtable<>();
        capsHashtable.put("browser", "firefox");
        capsHashtable.put("browser_version", "latest");
        capsHashtable.put("os", "Windows");
        capsHashtable.put("os_version", "10");
        capsHashtable.put("build", "browserstack-build-1");
        capsHashtable.put("name", "Thread 2");
        executeTest(capsHashtable);
    }

    public void runEdge() {
        Hashtable<String, String> capsHashtable = new Hashtable<>();
        capsHashtable.put("browser", "edge");
        capsHashtable.put("browser_version", "96.0");
        capsHashtable.put("os", "Windows");
        capsHashtable.put("os_version", "10");
        capsHashtable.put("build", "browserstack-build-1");
        capsHashtable.put("name", "Thread 3");
        executeTest(capsHashtable);
    }

    public void runMobile() {
        Hashtable<String, String> capsHashtable = new Hashtable<>();
        capsHashtable.put("device", "Google Pixel 5");
        capsHashtable.put("os_version", "11.0");
        capsHashtable.put("browserName", "android");
        capsHashtable.put("realMobile", "true");
        capsHashtable.put("build", "browserstack-build-1");
        capsHashtable.put("name", "Thread 1");
        executeTest(capsHashtable);
    }
}

package ru.makj.mavenproject;

import com.github.javafaker.Faker;
import com.google.common.io.Files;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class AppTest {
//    EventFiringWebDriver driver;
    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logPrefs);


        driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
    }

    @After
    public void stop() {
        driver.quit();
    }

    public void click(By locator) {
        driver.findElement(locator).click();
    }

    public void logOut() {
        wait.until(elementToBeClickable(By.linkText("Logout")));
        click(By.linkText("Logout"));
    }

    public void logIn(String password, String email) {
        click(By.cssSelector("input[name=email]"));
        sendText(By.cssSelector("input[name=email]"), email);
        sendText(By.cssSelector("input[name=password]"), password);
    }

    public void loginAsAdmin() {
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    /**
     * Выбор значения в выпадающем списке
     *
     * @param locator локатор
     * @param value   необходимое значение
     */
    public void selectInDropDownList(By locator, String value) {
        WebElement webElement = driver.findElement(locator);
        new Select(webElement).selectByVisibleText(value);
    }

    /**
     * отправка текста в указанный элемент
     *
     * @param locator локатор
     * @param text    текст
     */
    public void sendText(By locator, String text) {
        String fieldText = driver.findElement(locator).getAttribute("value");
        if (!text.equals(fieldText)) {
            driver.findElement(locator).clear();
            driver.findElement(locator).sendKeys(text);
        }
    }

    /**
     * отправка текста в указанный элемент
     *
     * @param locator локатор
     * @param text    текст
     * @param key     клавиша
     */
    public void sendTextWithKey(By locator, String text, Keys key) {
        String fieldText = driver.findElement(locator).getAttribute("value");
        if (!text.equals(fieldText)) {
            driver.findElement(locator).clear();
            driver.findElement(locator).sendKeys(text, key);
        }
    }

    /**
     * Генерация данных для регистрации нового пользователя
     *
     * @return user
     */
    public HashMap<String, String> userGenerator() {
        Faker faker = new Faker();
        HashMap<String, String> user = new HashMap<>();
        user.put("taxId", String.valueOf(faker.number().randomNumber()));
        user.put("firstName", faker.name().firstName());
        user.put("productName", faker.name().username());
        user.put("lastName", faker.name().lastName());
        user.put("streetAddress", faker.address().streetAddress());
        user.put("postcode", String.valueOf(faker.number().randomNumber()));
        user.put("city", faker.address().city());
        user.put("email", faker.internet().emailAddress());
        user.put("password", faker.internet().password());
        user.put("phoneNumber", String.valueOf(faker.number().randomNumber()));
        user.put("randomNumber", String.valueOf(faker.number().randomDigit()));
        return user;
    }

    /**
     * Генерация данных для регистрации нового пользователя
     *
     * @param digits количество цифр
     * @return user
     */
    public HashMap<String, String> userGenerator(int digits) {
        Faker faker = new Faker();
        HashMap<String, String> user = new HashMap<>();
        user.put("taxId", faker.number().digits(digits));
        user.put("firstName", faker.name().firstName());
        user.put("productName", faker.name().username());
        user.put("lastName", faker.name().lastName());
        user.put("streetAddress", faker.address().streetAddress());
        user.put("postcode", faker.number().digits(digits));
        user.put("city", faker.address().city());
        user.put("email", faker.internet().emailAddress());
        user.put("password", faker.internet().password());
        user.put("phoneNumber", faker.number().digits(digits));
        user.put("randomNumber", String.valueOf(faker.number().digits(digits)));
        return user;
    }

    /**
     * Проверка наличия элемента без задержки
     */
    boolean isElementPresent(By locator) {
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            return driver.findElements(locator).size() > 0;
        } finally {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
    }

    /**
     * Ожидание появления нового окна
     */
    public ExpectedCondition<String> anyWindowOtherThan(Set<String> oldWindows) {
        return driver -> {
            Set<String> handles = driver.getWindowHandles();
            handles.removeAll(oldWindows);
            return handles.size() > 0 ? handles.iterator().next() : null;
        };
    }

    /*
    Listener событий для логирования
     */
    public static class MyListener extends AbstractWebDriverEventListener {

        @Override
        public void afterFindBy(By by, WebElement element, WebDriver driver) {
            System.out.println("'" + by + "'" + " found");
        }

        @Override
        public void onException(Throwable throwable, WebDriver driver) {
            System.out.println(throwable.getMessage());
            String path = null;
            try {
                WebDriver augmentedDriver = new Augmenter().augment(driver);
                File source = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
                path = "./target/screenshots/" + "screenshot-" + System.currentTimeMillis() + ".png";
                Files.copy(source, new File(path));
            } catch (IOException e) {
                System.out.println("Failed to capture screenshot: " + e.getMessage());
            }
            System.out.println("*************Screenshot saved in " + path + "**********************");
        }
    }
}

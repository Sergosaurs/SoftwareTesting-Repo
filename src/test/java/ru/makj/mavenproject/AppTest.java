package ru.makj.mavenproject;

import com.github.javafaker.Faker;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class AppTest {
    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
//        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
    }

    @After
    public void stop() {
        driver.close();
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
     * @param key    клавиша
     *
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
}

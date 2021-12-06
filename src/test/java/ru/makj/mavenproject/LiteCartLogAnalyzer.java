package ru.makj.mavenproject;


import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public class LiteCartLogAnalyzer extends AppTest {

    public LogEntries logEntries;

    @Test
    public void productLogsChecking() {
        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=2");
        loginAsAdmin();
        List<WebElement> productList = driver.findElements(By.cssSelector(".dataTable tr.row"));
        for (int i = 3; i < productList.size(); i++) {
            productList = driver.findElements(By.cssSelector(".dataTable tr.row"));
            productList.get(i).findElement(By.tagName("a")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#content h1")));

            logEntries = driver.manage().logs().get(LogType.BROWSER);
            for (LogEntry logEntry : logEntries) {
                System.out.println(logEntry.getLevel() + " [" + new Timestamp(System.currentTimeMillis()) + "] : [" + logEntry.getMessage() + "]");
                warningLogChecker(logEntry, Level.WARNING);
            }
            driver.navigate().back();
        }
        driver.quit();
    }

    public void warningLogChecker(LogEntry logEntry, Level level) {
        if (Objects.equals(logEntry.getLevel(), level)) {
            throw new AssertionError(String.format(
                    "Received error message in browser log console right after opening the page, message: %s",
                    logEntry));
        }
    }
}

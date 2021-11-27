package ru.makj.mavenproject;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LiteCartCreateNewProduct extends AppTest {

    String productName = "Cyan Duck";

    @Test
    public void createProductTest() {
        driver.get("http://localhost/litecart/admin/");
        loginAsAdmin();

        click(By.cssSelector("[href*=catalog]"));
        click(By.linkText("Add New Product"));

        //General tab
        sendText(By.cssSelector("input[name='name[en]']"), productName);
        sendText(By.cssSelector("input[name=code]"), "rd007");
        click(By.cssSelector("[data-name=Subcategory]"));
        click(By.cssSelector(".input-wrapper input[value='1-3']"));
        sendText(By.name("quantity"), "99");

        sendText(By.cssSelector("[type = file]"), new File("src/mint-duck.jpg").getAbsolutePath());

        //data generator (generation of date, today and plus N years)
        String validFrom = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        LocalDate date = LocalDate.parse(validFrom, DateTimeFormatter.ofPattern("dd.MM.yyyy")).plusYears(2);
        String validTo = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        sendText(By.cssSelector("input[name=date_valid_from]"), validFrom);
        sendText(By.cssSelector("input[name=date_valid_to]"), validTo);

        //Information tab
        click(By.cssSelector("a[href='#tab-information']"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("select[name=manufacturer_id]")));
        selectInDropDownList(By.cssSelector("select[name=manufacturer_id]"), "ACME Corp.");

        sendText(By.cssSelector("input[name=keywords]"), "Rubber Duck");
        sendText(By.cssSelector("input[name='short_description[en]']"), "A rubber duck or rubber ducky is a toy shaped like a stylized duck, " +
                "generally yellow with a flat base. It may be made of rubber or rubber-like material such as vinyl plastic.");

        sendText(By.cssSelector("input[name='head_title[en]']"), productName);
        sendText(By.cssSelector(".trumbowyg-editor"), "Rubber ducks were invented in the late 1800s when it became possible to more easily shape rubber," +
                "and are believed to improve developmental skills in children during water play. ");

        sendText(By.cssSelector("input[name='meta_description[en]']"), "A new colored duck");

        //Prices tab
        click(By.cssSelector("a[href='#tab-prices']"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name=purchase_price]")));
        sendText(By.cssSelector("input[name=purchase_price]"), "35");
        selectInDropDownList(By.cssSelector("select[name=purchase_price_currency_code]"), "Euros");
        sendText(By.name("prices[USD]"), "78");
        sendText(By.name("prices[EUR]"), "35");
        click(By.cssSelector(".button-set [name=save]"));

        if (driver.findElement(By.linkText(productName)).isDisplayed()) {
            click(By.cssSelector("tr.row.semi-transparent input[type=checkbox]"));
            click(By.cssSelector("button[name=enable]"));
            System.out.println("Product " + productName + " has been successfully added!");
        } else Assert.fail("Product not found on page!!!");
    }
}

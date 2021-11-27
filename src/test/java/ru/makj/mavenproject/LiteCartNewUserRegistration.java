package ru.makj.mavenproject;

import org.junit.Test;
import org.openqa.selenium.By;

public class LiteCartNewUserRegistration extends AppTest {

    String password;
    String email;

    @Test
    public void userRegistrationTest() {
        driver.get("http://localhost/litecart/en/");

        click(By.cssSelector("#box-account-login td a"));

        sendText(By.cssSelector("input[name=tax_id]"), userGenerator(8).get("taxId"));
        sendText(By.cssSelector("input[name=firstname]"), userGenerator().get("firstName"));
        sendText(By.cssSelector("input[name=lastname]"), userGenerator().get("lastName"));
        sendText(By.cssSelector("input[name=address1]"), userGenerator().get("streetAddress"));
        sendText(By.cssSelector("input[name=postcode]"), userGenerator(5).get("postcode"));
        sendText(By.cssSelector("input[name=city]"), userGenerator().get("city"));

        selectInDropDownList(By.cssSelector("select[name=country_code]"), "United States");

        password = userGenerator().get("password");
        email = userGenerator().get("email");

        sendText(By.cssSelector("input[name=email]"), email);
        sendText(By.cssSelector("input[name=phone]"), driver.findElement(By.cssSelector("input[name=phone]"))
                .getAttribute("placeholder") + userGenerator(9).get("phoneNumber"));
        sendText(By.cssSelector("input[name=password]"), password);
        sendText(By.cssSelector("input[name=confirmed_password]"), password);

        click(By.cssSelector("button[name='create_account']"));
        logOut();

//        повторный вход в только что созданную учётную запись
        logIn(password, email);
        click(By.cssSelector("button[name='login']"));
        logOut();
    }
}

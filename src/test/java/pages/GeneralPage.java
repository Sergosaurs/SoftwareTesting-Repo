package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GeneralPage extends Page {

    public GeneralPage(WebDriver driver) {
            super(driver);
            PageFactory.initElements(driver, this);
    }

    @FindBy(css = "#box-most-popular li:nth-child(1)")
    public WebElement mppPage;

    public void openGeneralPage() {
        driver.get("http://localhost/litecart/en/");
    }

    public void mostPopularPage() {
        mppPage.click();
    }
}
package go.mail.ru;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
/**
 * Created by step on 20.03.14.
 */
public class SearchFormElement {
    private WebDriver driver;

    public SearchFormElement(WebDriver driver) {
        this.driver = driver;
    }

    public SearchFormElement enterText(String query) {
        driver.findElement(By.id("q")).sendKeys(query);
        return this;
    }

    public ResultPage clickGoButton() {
        driver.findElement(By.cssSelector(".js-is-not-scrollable")).submit();
        return new ResultPage(driver);
    }

    public ResultPage search(String query) {
        this.enterText(query);
        this.clickGoButton();
        return new ResultPage(driver);
    }

    public ResultPage getOpenedPage() {
        return new ResultPage(driver);
    }

    public SearchFormElement cleanButton() {
        driver.findElement(By.id("go-form__qclear")).click();
        return this;
    }

    public String getSuggests() {
        return driver.findElement(By.className("go-suggests__items")).getText();
    }
}

package go.mail.ru;
import org.openqa.selenium.WebDriver;
/**
 * Created by step on 20.03.14.
 */
public class MainPage {
    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public SearchFormElement getSearchFormElement() {
        return new SearchFormElement(driver);
    }
}

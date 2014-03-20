package go.mail.ru;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by step on 20.03.14.
 */
public class ResultPage {
    private WebDriver driver;

    public ResultPage(WebDriver driver) {
        this.driver = driver;
    }

    public SearchFormElement getSearchFormElement() {
        return new SearchFormElement(driver);
    }

    public String getResults() {
        return driver.findElement(By.cssSelector(".smack-converter")).getText();
    }

    public ResultPage inputValueForConvert(String value){
        driver.findElement(By.id("ival")).clear();
        driver.findElement(By.id("ival")).sendKeys(value);
        return this;
    }

    public String getConvertResult() {
        return driver.findElement(By.id("oval")).getText();
    }

    public ResultPage changeMeasure() {
        driver.findElement(By.cssSelector("#measureSelector .smack-converter__title-span")).click();
        driver.findElement(By.cssSelector("#measureSelector .m_current~li span")).click();
        return this;
    }

    public ResultPage changeMeasure2() {
        driver.findElement(By.cssSelector("#measureSelector .smack-converter__title-span")).click();
        driver.findElement(By.cssSelector("#measureSelector li[data-measure=speed] span")).click();
        return this;
    }

    public ResultPage changeMeasureFirst() {
        //JavascriptExecutor js = (JavascriptExecutor)driver;
        //js.executeScript("document.querySelector('.smack-converter_classN').click();");
        driver.findElement(By.cssSelector("#icode")).click();
        driver.findElement(By.cssSelector("span[data-code='2']")).click();
        return this;
    }

    public String getMeasureFirst() {
        return driver.findElement(By.cssSelector("#icode")).getText();
    }

    public ResultPage changeMeasureSecond() {
        driver.findElement(By.cssSelector("#ocode")).click();
        driver.findElement(By.cssSelector("#change_conv~.smack-converter__select span[data-code='8']")).click();
        return this;
    }

    public String getMeasureSecond() {
        return driver.findElement(By.cssSelector("#ocode")).getText();
    }

    public ResultPage makeExchange() {
        driver.findElement(By.cssSelector("#change_conv")).click();
        return this;
    }

    public String getExchangeLeft() {
        return driver.findElement(By.cssSelector("#icode")).getText();
    }

    public String getExchangeRight() {
        return driver.findElement(By.cssSelector("#ocode")).getText();
    }

    public boolean getMinusResult() {
        try{
            driver.findElement(By.cssSelector("#ival[style=\"color: red;\"]")).isDisplayed();
        }
        catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public boolean getTextAnalyse() {
        String str = driver.findElement(By.id("icode")).getText();
        String num = driver.findElement(By.id("ival")).getAttribute("value");
        Pattern forString = Pattern.compile("^.+а$");
        Pattern forNumbers = Pattern.compile("^.*[^1]+2|3|4$");
        Matcher testForNum = forNumbers.matcher(num);
        Matcher isThereA = forString.matcher(str);
        if (num.indexOf(',')!=-1 & isThereA.matches()) {
                return true;
        } else {
            if (testForNum.matches() & isThereA.matches()) {
                return true;
            }
        }
        return false;
    }
}

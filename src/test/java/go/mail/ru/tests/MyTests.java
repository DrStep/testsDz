package go.mail.ru.tests; /**
 * Created by step on 20.03.14.
 */
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import go.mail.ru.MainPage;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.net.MalformedURLException;
import java.net.URL;

public class MyTests {
    private WebDriver driver;

    @BeforeMethod
    @Parameters({"browser", "hub", "url"})
    public void setUp(String browser, String hub, String url) throws MalformedURLException {
        if (browser.toLowerCase().equals("chrome"))
            this.driver = new RemoteWebDriver(new URL(hub), DesiredCapabilities.chrome());
        else if (browser.toLowerCase().equals("firefox"))
            this.driver = new RemoteWebDriver(new URL(hub), DesiredCapabilities.firefox());
        else
            throw new NotImplementedException();
        this.driver.manage().window().maximize();
        this.driver.get(url);
    }

    @Test
    public void test1_addConverterTest() {      //тест для проверки на существование конвертера при поиске
        String results = new MainPage(this.driver).getSearchFormElement().search("кг в фунты").getResults();
        Assert.assertTrue(results.contains("Конвертер величин:"));
    }

    @Test
    public void test2_ForKgConvert() {          //тест для проверки корректности результата конвертации
        String results = new MainPage(this.driver).getSearchFormElement().search("кг в фунты").inputValueForConvert("10").getConvertResult();
        Assert.assertEquals(results, "22,04623");
    }

    @Test
    public void test3_ChangeMeasure() {         //тест для проверки корректности изменения типа конвертации (вес/длина/..)
        String results = new MainPage(this.driver).getSearchFormElement().search("кг в фунты").inputValueForConvert("10").changeMeasure().getConvertResult();
        Assert.assertEquals(results,"0,00977");
    }

    @Test
    public void test3_OneMoreVariant() {        //ещё один вариант того же теста (лучше реализация)
        String results = new MainPage(this.driver).getSearchFormElement().search("кг в фунты").inputValueForConvert("10").changeMeasure2().getConvertResult();
        Assert.assertEquals(results,"6,21504");
    }

    @Test
    public void test4_firstMeasureChanged() {   //тест для проверки корректности изменения меры для конвертации кг/граммы/.. (и слева, и справа)
        String resultFirst = new MainPage(this.driver).getSearchFormElement().search("кг в фунты").inputValueForConvert("10").changeMeasureFirst().getMeasureFirst();
        Assert.assertEquals(resultFirst,"грамм");
        String resultSecond = new MainPage(this.driver).getSearchFormElement().cleanButton().search("кг в фунты").inputValueForConvert("10").changeMeasureSecond().getMeasureSecond();
        Assert.assertEquals(resultSecond,"центнера");
    }

    @Test
    public void test5_exchange() {      //тест на корректность "поменять местами"
        String resultL = new MainPage(this.driver).getSearchFormElement().search("кг в фунты").inputValueForConvert("10").makeExchange().getExchangeLeft();
        String resultR = new MainPage(this.driver).getSearchFormElement().cleanButton().search("кг в фунты").inputValueForConvert("10").makeExchange().getExchangeRight();
        Assert.assertEquals(resultL,"фунтов");
        Assert.assertEquals(resultR,"килограмма");
    }


    @Test
    public void test6_Minus() {     //тест на корректность обработки отрицательного числа
        boolean results = new MainPage(this.driver).getSearchFormElement().search("кг в фунты").inputValueForConvert("-100").getMinusResult();
        Assert.assertEquals(results,true);
        boolean normal = new MainPage(this.driver).getSearchFormElement().cleanButton().search("кг в фунты").inputValueForConvert("100").getMinusResult();
        Assert.assertEquals(normal,false);
    }

    @Test
    public void test7_None() {      //тест на проверку результат конвертации при пустом окне ввода исходных данных
        String result = new MainPage(this.driver).getSearchFormElement().search("кг в фунты").inputValueForConvert("").changeMeasure().getConvertResult();
        Assert.assertEquals(result,"0");
    }

    @Test
    public void test8_textAnalyse() {   //тест на проверку, что при числах с плавающей запятой или кончающихся на 1,2,3,4 (кроме 11,12,13,14) имеем киллограммА, граммА (А на конце)
        boolean hasA1 = new MainPage(this.driver).getSearchFormElement().search("кг в фунты").inputValueForConvert("10,001").getTextAnalyse();
        Assert.assertEquals(hasA1,true);
        boolean hasA2 = new MainPage(this.driver).getSearchFormElement().cleanButton().search("кг в фунты").inputValueForConvert("22").getTextAnalyse();
        Assert.assertEquals(hasA2,true);
        boolean noA = new MainPage(this.driver).getSearchFormElement().cleanButton().search("кг в фунты").inputValueForConvert("10").getTextAnalyse();
        Assert.assertEquals(noA,false);
    }

    @AfterMethod
    public void tearDown() {
        this.driver.close();
    }
}

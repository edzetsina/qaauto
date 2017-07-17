package test;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import page.LoginPage;
import page.MainPage;

import java.util.List;

public class MainPageTests {

    public WebDriver webDriver;
    MainPage mainPage;
    public String username = "denvert1@shotspotter.net";
    /**
     * Password for password field
     */
    public String password = "Test123!";


    /**
     * Kills WebDriver instance
     */
    @AfterClass
    public void afterClass() {
        webDriver.quit();
    }

    @Parameters({"BrowserType"})

    /**
     * Opens FireFox browser and navigate to web page
     */
    @BeforeMethod
    public void beforeMethod(@Optional("firefox") String BrowserType) throws InterruptedException {

        if (BrowserType.toLowerCase().equals("chrome")) {
            ChromeDriverManager.getInstance().setup();
            webDriver = new ChromeDriver();
        }

        if (BrowserType.toLowerCase().equals("firefox")) {
            FirefoxDriverManager.getInstance().setup();
            webDriver = new FirefoxDriver();
        }

        webDriver.navigate().to("https://alerts.shotspotter.biz");
        LoginPage loginPage = new LoginPage(webDriver);
        mainPage = loginPage.login(username, password);
    }

    @Test
    public void testValidateIncidentCardFields() {
        mainPage.openIncidentsList();
        String expectedCity = "Denver";
        List<String> listCities = mainPage.getIncidentCardsCities();
        List<String> listStreets = mainPage.getIncidentCardsStreets();
        List<String> listTimeStamps = mainPage.getIncidentCardsTimeStamps();

        for (String elementCity: listCities) {
            Assert.assertEquals(elementCity, expectedCity, "City is not Denver");
        }
        for (String elementStreet: listStreets) {
            Assert.assertNotEquals(elementStreet, "", "Street address is empty");
        }
        for (String elementTimeStamps: listTimeStamps) {
            Assert.assertNotEquals(elementTimeStamps, "", "Street address is empty");
        }
    }

    /**
     * Positive login test with correct credentials
     */
    @Test
    public void testSwitchIncidentsPeriod() {

        int[] timeFrameOptions = {24,3,7};
        for(int timeFrameOption : timeFrameOptions){
            mainPage.switchTimeFramePeriod(timeFrameOption);
            int resultsCount = mainPage.getResultsCount();
            int incidentCardsCount = mainPage.getIncidentCardsCount();

            System.out.println("Preriod: "+timeFrameOption);
            System.out.println("resultsCount: "+resultsCount);
            System.out.println("incidentCardsCount: "+incidentCardsCount);
            Assert.assertEquals(resultsCount, incidentCardsCount, "Results count doesn't match Incident Cards count");
        }
    }

    @DataProvider
    public static Object[][] timeFrameOptions(){
        return new Object[][]{ {24},{3},{7}};
    }

    @Test(dataProvider = "timeFrameOptions")
        public void testSwitchIncidentsPeriodByDataProvider(int timeFrameOption) {
        mainPage.switchTimeFramePeriod(timeFrameOption);
        int resultsCount = mainPage.getResultsCount();
        int incidentCardsCount = mainPage.getIncidentCardsCount();

        System.out.println("Period: "+timeFrameOption);
        System.out.println("resultsCount: "+resultsCount);
        System.out.println("incidentCardsCount: "+incidentCardsCount);
        Assert.assertEquals(resultsCount, incidentCardsCount, "Results count doesn't match Incident Cards count");
    }

    @Test
    public void testTermOfService() {
        mainPage.about();
        mainPage.openTermOfServiceWindow();
        Assert.assertEquals(webDriver.getCurrentUrl(), "https://alerts.shotspotter.biz/main/9720-001037", "Main page URL is wrong");
    }

}

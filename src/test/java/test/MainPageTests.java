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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.StringContains.containsString;

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

    /**
     * Validation test, checked that field are not empty
     */
    @Test
    public void testValidateIncidentCardFields() {
        String expectedCity = "Denver";
        mainPage.switchTimeFramePeriod(7);
        mainPage.openIncidentsList();

        List<String> listCities = mainPage.getIncidentCardsDetails("Cities");
        List<String> listStreets = mainPage.getIncidentCardsDetails("Streets");
        List<String> listTimeStamps = mainPage.getIncidentCardsDetails("TimeStamps");
        System.out.println(listTimeStamps);
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
     * Test to check addresses, cities and time list are unique and not epmty
     */
    @Test
    public void testIncidentCardsDetailsData() {
        Assert.assertFalse(mainPage.isAddressesListContainsEmptyStrings(), "Addresses list contains empty strings" );
        Assert.assertTrue(mainPage.isAllCitiesEqualsTo("Denver"), "Not all cities are Denver" );
        Assert.assertTrue(mainPage.isTimeListContainsUniqueElements(), "Elements of time list are not unique" );
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

    /**
     * Test for switching time frame period with DataProvider
     * @param timeFrameOption 24 hours, 3 days, 7 days
     */
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

    /**
     * Test for term of service page
     */
    @Test
    public void testTermOfService() {
        mainPage.about();
        mainPage.openTermOfServiceWindow();
        Assert.assertEquals(webDriver.getCurrentUrl(), "https://alerts.shotspotter.biz/main/9720-007738", "Main page URL is wrong");
    }


    public String generateStringFromResource(String path) throws IOException {

        return new String(Files.readAllBytes(Paths.get(path)));

    }

    @Test

        public void testNewAlertNotification() throws IOException {
        String expectedStreet = "Broadway";

            String jsonBody = generateStringFromResource("C:\\Users\\edzet\\projects\\qaauto\\src\\test\\resources\\notificationRequestBody.json");

            given().
                    contentType("application/json").
                    body(jsonBody).
                    when().
                    post("https://alerts.shotspotter.biz/api/incidents/v2").
                    then().
                    statusCode(200).
                    body(containsString("incident message is published to queue successfully"));
            mainPage.switchTimeFramePeriod(3);
            mainPage.openIncidentsList();
            List<String> listStreets = mainPage.getIncidentCardsDetails("Streets");
            Assert.assertTrue(listStreets.contains(expectedStreet), "Card with street name "+expectedStreet+" not found in the list");

        }

    }


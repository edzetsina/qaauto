package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.LoginPage;
import page.MainPage;

public class MainPageTests {

    public WebDriver webDriver;

    public String username = "denvert1@shotspotter.net";
    /**
     * Password for password field
     */
    public String password = "Test123!";

    /**
     * Opens FireFox browser and navigate to web page
     */
    @BeforeMethod
    public void beforeMethod() throws InterruptedException {
        webDriver = new FirefoxDriver();
        webDriver.navigate().to("https://alerts.shotspotter.biz");
    }
    /**
     * Kills WebDriver instance
     */
    @AfterMethod
    public void afterMethod() {
        webDriver.quit();
    }

    /**
     * Positive login test with correct credentials
     */
    @Test
    public void testSwitchIncidentsPeriod() throws InterruptedException {
        LoginPage loginPage = new LoginPage(webDriver);
        MainPage mainPage = loginPage.login(username, password);

        mainPage.switchTimeFramePeriod(7);     //home work
       int resultsCount = mainPage.getResultsCount();
       int incidentCardsCount = mainPage.getIncidentCardsCount();   //home work

       Assert.assertEquals(resultsCount, incidentCardsCount, "Results count doesn't match Incident Cards count");



    }
}

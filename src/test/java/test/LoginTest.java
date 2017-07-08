package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import page.LoginPage;
import page.MainPage;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


/**
 * Login Test
 */
public class LoginTest {


    public WebDriver webDriver;
    /**
     * Username for login field
     */
    public String username = "sst.tau@gmail.com";
    /**
     * Password for password field
     */
    public String password = "P@ssword123";

    @Parameters({"BrowserType"})

    /**
     * Opens FireFox browser and navigate to web page
     */
    @BeforeMethod
    public void beforeMethod(@Optional("firefox") String BrowserType) throws InterruptedException {
        if (BrowserType.toLowerCase().equals("chrome")) {
            System.setProperty("webDriver.chrome.driver", "C:\\Windows\\System32\\chromedriver");
            webDriver = new ChromeDriver();
        }

        if (BrowserType.toLowerCase().equals("firefox")) {
            webDriver = new FirefoxDriver();
        }

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
    public void testLoginPositive() throws InterruptedException {
        LoginPage loginPage = new LoginPage(webDriver);
        Assert.assertEquals(loginPage.getPageTitle(), "Shotspotter - Login", "Main page title is wrong");
        Assert.assertEquals(loginPage.getPageURL(), "https://alerts.shotspotter.biz/", "Wrong URL on Login Page");
        MainPage mainPage = loginPage.login(username, password);
        Assert.assertEquals(mainPage.getPageTitle(), "Shotspotter", "Main page title is wrong");
        Assert.assertTrue(mainPage.isPageLoaded(), "Setting icon is not displayed");
    }

    @DataProvider
    public static Object[][] negativeTestEnvironment() {
        return new Object[][]{
                {"", "", "The provided credentials are not correct."},
                {" ", " ", "The provided credentials are not correct."},
                {"st.tau@gmail.com", "", "The provided credentials are not correct."},
                {"sst.taugmail.com", "P@ssword123", "The provided credentials are not correct."},
                {"sst.tau@ gmail.com", "P@ssword123", "The provided credentials are not correct."},
                {" sst.tau@gmail.com", "P@sSword123", "The provided credentials are not correct."},
                {"192.168.1.1@gmail.com", "P@ssword123", "The provided credentials are not correct."},
                {"@#$%^#/!><@gmail.com", "P@ssword123", "The provided credentials are not correct."}
        };
    }

    /**
     * Negative login test with wrong credentials
     */
    @Test(dataProvider = "negativeTestEnvironment")
    public void testLoginNegative(String username, String password, String expectedErrorMsg) throws InterruptedException {
        LoginPage loginPage = new LoginPage(webDriver);
        Assert.assertEquals(loginPage.getPageTitle(), "Shotspotter - Login", "Main page title is wrong");
        Assert.assertEquals(loginPage.getPageURL(), "https://alerts.shotspotter.biz/", "Wrong URL on Login Page");
        loginPage = loginPage.login(username, password);
        Assert.assertTrue(loginPage.isPageLoaded(), "Login page is not loaded");
        Assert.assertTrue(loginPage.isInvalidCredentialMsg(), "Error message was not displayed on login page");
        Assert.assertEquals(loginPage.getErrorMsgText(), expectedErrorMsg, "Error msg has wrong test");
    }

    /**
     * Logout test
     */
    @Test
    public void testLogOut() {
        LoginPage loginPage = new LoginPage(webDriver);
        Assert.assertEquals(loginPage.getPageTitle(), "Shotspotter - Login", "Main page title is wrong");
        Assert.assertEquals(loginPage.getPageURL(), "https://alerts.shotspotter.biz/", "Wrong URL on Login Page");
        MainPage mainPage = loginPage.login(username, password);
        Assert.assertEquals(mainPage.getPageTitle(), "Shotspotter", "Main page title is wrong");
        Assert.assertTrue(mainPage.isPageLoaded(), "Setting icon is not displayed");
        loginPage = mainPage.logOut();
        Assert.assertEquals(loginPage.getPageTitle(), "Shotspotter - Login", "Main page title is wrong");
        Assert.assertEquals(loginPage.getPageURL(), "https://alerts.shotspotter.biz/", "Wrong URL on Login Page");
    }
}

package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.reporters.jq.Main;
import page.LoginPage;
import page.MainPage;


public class LoginTest {

    public WebDriver webDriver;
    public String username="sst.tau@gmail.com";
    public String password="P@ssword123";

    @BeforeMethod
    public void beforeMethod() throws InterruptedException {
        webDriver = new FirefoxDriver();
        webDriver.navigate().to( "https://alerts.shotspotter.biz");
    }


    @AfterMethod
    public void afterMethod() {
        webDriver.quit();
    }

    @Test
    public void testLoginPositive() throws InterruptedException {
     LoginPage loginPage = new LoginPage(webDriver);
     Assert.assertEquals(loginPage.getPageTitle(), "Shotspotter - Login", "Main page title is wrong");
     Assert.assertEquals(loginPage.getPageURL(), "https://alerts.shotspotter.biz/", "Wrong URL on Login Page");
     MainPage mainPage = loginPage.login(username, password);
     Assert.assertEquals(mainPage.getPageTitle(), "Shotspotter", "Main page title is wrong");
     Assert.assertTrue(mainPage.isPageLoaded(), "Setting icon is not displayed");
     }


    @Test
    public void testLoginNegative() throws InterruptedException {
        String expectedErrorMsg = "The provided credentials are not correct.";
        LoginPage loginPage = new LoginPage(webDriver);
        Assert.assertEquals(loginPage.getPageTitle(), "Shotspotter - Login", "Main page title is wrong");
        Assert.assertEquals(loginPage.getPageURL(), "https://alerts.shotspotter.biz/", "Wrong URL on Login Page");
        loginPage = loginPage.login(username, "Test");
        Assert.assertTrue(loginPage.isPageLoaded(), "Login page is not loaded");
        Assert.assertTrue(loginPage.isInvalidCredentialMsg(), "Error message was not displayed on login page");
        Assert.assertEquals(loginPage.getErrorMsgText(), expectedErrorMsg, "Error msg has wrong test");

    }

    @Test
    public  void testLogOut(){
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

package test;

import com.gargoylesoftware.htmlunit.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.reporters.jq.Main;
import page.LoginPage;
import page.MainPage;

import static java.lang.Thread.sleep;

public class LoginTest {

    public WebDriver webDriver;

    @BeforeMethod
    public void beforeMethod() throws InterruptedException {
        webDriver = new FirefoxDriver();
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
     MainPage mainPage = loginPage.loginAs("denvert1@shotspotter.net", "Test123!");

     Assert.assertEquals(webDriver.getTitle(), "Shotspotter", "Main page title is wrong");
     Assert.assertTrue(mainPage.isPageLoaded(), "Setting icon is not displayed");
     Assert.assertTrue(webDriver.getCurrentUrl().contains("https://alerts.shotspotter.biz/main"), "Wrong URL on Main page"); // url change last 4-5 symbols, we need to sort ith function "contain"

     }


    @Test
    public void testLoginNegative() throws InterruptedException {
        String expectedErrorMsg = "The provided credentials are not correct.";

        LoginPage loginPage = new LoginPage(webDriver);
        Assert.assertEquals(loginPage.getPageTitle(), "Shotspotter - Login", "Main page title is wrong");
        Assert.assertEquals(loginPage.getPageURL(), "https://alerts.shotspotter.biz/", "Wrong URL on Login Page");
        loginPage = loginPage.loginAsReturnToLogin("denvert1@shotspotter.net", "Test");

        Assert.assertTrue(loginPage.isPageLoaded(), "Login page is not loaded");
        Assert.assertTrue(loginPage.isInvalidCredentialMsg(), "Error message was not displayed on login page");
        Assert.assertEquals(loginPage.getErrorMsgText(), expectedErrorMsg, "Error msg has wrong test");

    }

}

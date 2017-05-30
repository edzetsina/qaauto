package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;
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

     Assert.assertEquals(webDriver.getTitle(), "Shotspotter - Login", "Main page title is wrong");



     MainPage mainPage = loginPage.loginAs("denvert1@shotspotter.net", "Test123!");
     mainPage.isPageLoaded();


     Assert.assertEquals(webDriver.getTitle(), "Shotspotter", "Main page title is wrong");
     Assert.assertTrue(mainPage.isPageLoaded(), "Setting icon is not displayed");
     Assert.assertTrue(webDriver.getCurrentUrl().contains("https://alerts.shotspotter.biz/main"), "Wrong URL on Main page"); // url change last 4-5 symbols, we need to sort ith function "contain"

     }
}

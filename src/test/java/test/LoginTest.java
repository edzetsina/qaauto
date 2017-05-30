package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import page.LoginPage;

import static java.lang.Thread.sleep;

public class LoginTest {
    public static WebDriver webDriver;

    @BeforeMethod
    public void beforeMethod() throws InterruptedException {
        webDriver = new FirefoxDriver();
        LoginPage.webDriver = webDriver;
        webDriver.navigate().to( "https://alerts.shotspotter.biz");
        sleep(5000);
        LoginPage.init();
    }


    @AfterMethod
    public void afterMethod() {
        webDriver.quit();

    }

    @Test
    public void testLoginPositive() throws InterruptedException {


     Assert.assertEquals(webDriver.getTitle(), "Shotspotter - Login", "Main page title is wrong");
        LoginPage.emailField.sendKeys("denvert1@shotspotter.net");
        LoginPage.passwordField.sendKeys("Test123!");
        LoginPage.goButton.click();
     sleep(5000);

     Assert.assertEquals(webDriver.getTitle(), "Shotspotter", "Main page title is wrong");
     WebElement settingIcon = webDriver.findElement(By.className("settings"));
     Assert.assertTrue(settingIcon.isDisplayed(), "Setting icon is not displayed");
     Assert.assertTrue(webDriver.getCurrentUrl().contains("https://alerts.shotspotter.biz/main"), "Wrong URL on Main page"); // url change last 4-5 symbols, we need to sort ith function "contain"





    /* Assert.assertTrue(webDriver.findElement(By.xpath("//*[@class='google-maps-container']")).isDisplayed());
     Assert.assertTrue(webDriver.findElement(By.xpath("//*[@class='settings']")).isDisplayed());
     Assert.assertTrue(webDriver.findElement(By.xpath("//*[@class='logo']")).isDisplayed());*/

     // webDriver.quit();
     }
}

package page;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static java.lang.Thread.sleep;


public class LoginPage {
    private WebDriver webDriver;


    private WebElement emailField;
    private WebElement passwordField;
    private WebElement goButton;

    private void InitLoginPageWebElements() {
        emailField = webDriver.findElement(By.xpath("//input[@type='email']"));
        passwordField = webDriver.findElement(By.xpath("//input[@type='password']"));
        goButton = webDriver.findElement(By.xpath("//*[@class='button' and text()='GO']"));
    }

    public LoginPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        webDriver.navigate().to( "https://alerts.shotspotter.biz");
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        InitLoginPageWebElements();
    }

    public MainPage loginAs(String userEmail, String userPassword) {
        emailField.sendKeys(userEmail);
        passwordField.sendKeys(userPassword);
        goButton.click();
        try {
               sleep(5000);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
        return new MainPage(webDriver);
    }







}

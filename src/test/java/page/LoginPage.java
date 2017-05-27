package page;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class LoginPage {
    public static WebDriver webDriver;


    public static WebElement emailField = webDriver.findElement(By.xpath("//input[@type='email']"));
    public static WebElement passwordField = webDriver.findElement(By.xpath("//input[@type='password']"));
    public static WebElement goButton = webDriver.findElement(By.xpath("//*[@class='button' and text()='GO']"));

}

package page;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class LoginPage {
    public static WebDriver webDriver;

    public static WebElement emailField;
    public static WebElement passwordField;
    public static WebElement goButton;

    public static void init () {
          emailField = webDriver.findElement(By.xpath("//input[@type='email']"));
          passwordField = webDriver.findElement(By.xpath("//input[@type='password']"));
          goButton = webDriver.findElement(By.xpath("//*[@class='button' and text()='GO']"));

    }


}

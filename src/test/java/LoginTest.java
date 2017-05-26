import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import static java.lang.Thread.sleep;

public class LoginTest {
    public static void main(String[] args) throws InterruptedException {
     WebDriver webDriver = new FirefoxDriver();
     webDriver.navigate().to( "https://alerts.shotspotter.biz");
     sleep(5000);
     webDriver.findElement(By.xpath("//input[@type='email']")).sendKeys("denvert1@shotspotter.net");
     webDriver.findElement(By.xpath("//input[@type='password']")).sendKeys("Test123!");
     webDriver.findElement(By.xpath("//*[@class='button' and text()='GO']")).click();
     sleep(5000);

     Assert.assertTrue(webDriver.findElement(By.xpath("//*[@class='google-maps-container']")).isDisplayed());
     Assert.assertTrue(webDriver.findElement(By.xpath("//*[@class='settings']")).isDisplayed());
     Assert.assertTrue(webDriver.findElement(By.xpath("//*[@class='logo']")).isDisplayed());

     webDriver.quit();
     }
}

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import static java.lang.Thread.sleep;

public class NegativeLoginTest {
    public static void main(String[] args) throws InterruptedException {
        WebDriver webDriver = new FirefoxDriver();
        webDriver.navigate().to( "https://alerts.shotspotter.biz");
        sleep(5000);
        webDriver.findElement(By.xpath("//*[@class='button' and text()='GO']")).click();
        sleep(5000);
        Assert.assertTrue(webDriver.findElement(By.xpath("//*[@class='invalid-credentials']")).isDisplayed());
        webDriver.quit();
    }
}

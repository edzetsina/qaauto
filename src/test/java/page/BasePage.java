package page;


import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class BasePage {
    public WebDriver webDriver;


    public BasePage(WebDriver webDriver) {
        this.webDriver=webDriver;
    }

    public String getPageURL() {
        return webDriver.getCurrentUrl();
    }

    public String getPageTitle() {
        return webDriver.getTitle();
    }

    public boolean isElementDisplayed(WebElement element, int timeout){
        try {
            waitUntilElementDisplayed(element).isDisplayed();
        }
        catch (TimeoutException e){
            return false;
        }
        return true;

    }

    public WebElement waitUntilElementDisplayed(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(webDriver, timeout);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
    public WebElement  waitUntilElementDisplayed(WebElement element) {
        return waitUntilElementDisplayed(element, 10);
    }
    public WebElement waitUntilElementLoad (WebElement element, int timeout){
        WebElement webElement = (new WebDriverWait(webDriver, timeout)).until(ExpectedConditions.elementToBeClickable(element));
        return waitUntilElementDisplayed(webElement, 10);

    }


}


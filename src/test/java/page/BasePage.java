package page;


import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * Base Page
 */
public class BasePage {
    /**
     * WebDriver instance
     */
    public WebDriver webDriver;


    /**
     * BasePage constructor
     *
     * @param webDriver instance
     */
    public BasePage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    /**
     * Common method to get current Page URL
     *
     * @return String with current Page URL
     */
    public String getPageURL() {
        return webDriver.getCurrentUrl();
    }

    /**
     * Common method to get current Page title
     *
     * @return String with current Page title
     */
    public String getPageTitle() {
        return webDriver.getTitle();
    }

    /**
     * Waits until element is displayed using specific max timeout
     *
     * @param element WebElement to wait for
     * @param timeout max timeout is seconds
     * @return True if element is displayed, false - if not
     */
    public boolean isElementDisplayed(WebElement element, int timeout) {
        try {
            waitUntilElementDisplayed(element).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
        return true;

    }


    /**
     * Waits until element is displayed using specific max timeout
     *
     * @param element WebElement to wait for
     * @param timeout max timeout is seconds
     * @return WebElement after expected condition
     */
    public WebElement waitUntilElementDisplayed(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(webDriver, timeout);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waits until element is displayed using specific timeout in 10 seconds
     *
     * @param element WebElement to wait for
     * @return WebElement after expected condition
     */
    public WebElement waitUntilElementDisplayed(WebElement element) {
        return waitUntilElementDisplayed(element, 10);
    }

    /**
     * Waits until element is clickable using specific max timeout
     *
     * @param element WebElement to wait for
     * @param timeout max timeout in seconds
     * @return WebElement after expected condition
     */
    public WebElement waitUntilElementClickable(WebElement element, int timeout) {
        WebElement webElement = (new WebDriverWait(webDriver, timeout)).until(ExpectedConditions.elementToBeClickable(element));
        return waitUntilElementDisplayed(webElement, 10);
    }


}


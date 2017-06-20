package page;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static java.lang.Thread.sleep;

/**
 * Main Page
 */
public class MainPage extends BasePage {

    /**
     * Identifying the Setting icon element
     */
    @FindBy(className = "settings")
    private WebElement settingIcon;
    /**
     * Identifying the Setting drop down menu element
     */
    @FindBy(xpath = "//div[@class='settings isOpen']")
    private WebElement settingsMenu;
    /**
     * Identifying the Logout button element in the Setting drop down menu
     */
    @FindBy(xpath = "//li[text()='Logout']")
    private WebElement logOutMenuItem;


    /**
     * MainPage constructor
     *
     * @param webDriver instance BasPage
     */
    public MainPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
        waitUntilElementDisplayed(settingIcon, 20);
    }

    /**
     * Common method to logout from MainPage
     *
     * @return LoginPage, if logout was complete successfully
     */
    public LoginPage logOut() {
        settingIcon.click();
        waitUntilElementDisplayed(settingsMenu);
        waitUntilElementClickable(logOutMenuItem, 5).click();
        return PageFactory.initElements(webDriver, LoginPage.class);
    }

    /**
     * Checks if settingIcon element displayed
     *
     * @return True if element is displayed, False if not
     */
    public boolean isPageLoaded() {
        return settingIcon.isDisplayed();
    }
}



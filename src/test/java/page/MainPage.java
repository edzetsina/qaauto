package page;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
     * Identifying time switch element
     */
    @FindBy(xpath = "//filter-menu//div[@class='selected-option']")
    private WebElement incidentsTimeSwitch;
    /**
     * Identifying count of results
     */
    @FindBy(xpath = "//*[@class='result-count']")
    private WebElement resultsCount;
    /**
     * Identifying list button element
     */
    @FindBy(xpath = "//div//*[text()='List']")
    private WebElement listButton;
    /**
     * Identifying list of card
     */
    @FindBy(xpath = "//incident-list//incident-card")
    private List<WebElement> incidentsCardList;
    /**
     * Identifying About button in the dropdown menu
     */
    @FindBy(xpath = "//li[text()='About']")
    private WebElement aboutButton;
    /**
     * Identifying Term of service link in the About information
     */
    @FindBy(xpath = "//div[@class='modal-body']//a[text()='terms of service']")
    private WebElement termsOfServiceLink;
    /**
     * Identifying close button at the About window
     */
    @FindBy(xpath = "//button[text()='Close']")
    private WebElement closeAboutWindowButton;
    /**
     * Identifying list of time in the incident cards
     */
    @FindBy(xpath = "//incident-list//incident-card//div[contains(@class, 'incident')]//div[@class='cell day']//div[@class='content']")
    private List<WebElement> timeList;
    /**
     * Identifying list of cities in the incident cards
     */
    @FindBy(xpath = "//incident-list//incident-card//div[contains(@class, 'incident')]//div[@class='city S']")
    private List<WebElement> citiesList;
    /**
     * Identifying list of addresses in the incident cards
     */
    @FindBy(xpath = "//incident-list//incident-card//div[contains(@class, 'incident')]//div[@class='address']")
    private List<WebElement> addressesList;


    /**
     * Method which change time period
     *
     * @param period of time element in cards list
     * @return time period
     */
    private WebElement getTimeFramePeriodOption(int period) {
        return webDriver.findElement(By.xpath(String.format("//filter-menu//div[@class='available-options']//*[@class='time-increment' and text()='%d']", period)));
    }

    /**
     * Method which count results
     *
     * @return
     */
    public int getResultsCount() {
        return Integer.parseInt(resultsCount.getText().replace(" RESULTS", ""));
    }


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
     * Method which open About information
     *
     * @return MainPage
     */
    public MainPage about() {
        settingIcon.click();
        waitUntilElementDisplayed(settingsMenu);
        waitUntilElementClickable(aboutButton, 5).click();
        return this;
    }


    /**
     * Checks if settingIcon element displayed
     *
     * @return True if element is displayed, False if not
     */
    public boolean isPageLoaded() {
        return settingIcon.isDisplayed();
    }


    /**
     * Switching period of time
     *
     * @param period 24, 3, 7
     */
    public void switchTimeFramePeriod(int period) {
        incidentsTimeSwitch.click();
        getTimeFramePeriodOption(period).click();
        waitResultCountUpdated(5);
    }

    /**
     * Method which wait until incident cards count update
     *
     * @param maxTimeoutSec timeout in seconds
     */
    public void waitResultCountUpdated(int maxTimeoutSec) {
        int initialResultCount = getResultsCount();
        for (int i = 0; i < maxTimeoutSec; i++) {
            try {
                Thread.sleep(1000);
                int currentResultCount = getResultsCount();
                if (initialResultCount != currentResultCount) {
                    break;
                }
            } catch (InterruptedException ie) {
            }
        }
    }

    /**
     * Method which count cards
     *
     * @return incident cards size
     */
    public int getIncidentCardsCount() {
        listButton.click();
        return incidentsCardList.size();
    }


    /**
     * Method wait until list of cards updated
     */
    public void openIncidentsList() {
        listButton.click();
        waitUntilElementClickable(incidentsCardList.get(1), 5);
    }

    /**
     * Gets List of desired Incident cards details elements
     *
     * @param details String "cities", "streets", "timestamps"
     * @return istDetails List of WebElements
     */
    public List<String> getIncidentCardsDetails(String details) {
        List<String> listDetails = new ArrayList<>();
        String elementXpath = getCardsDetailsElementXpath(details);
        for (WebElement cardsElement: incidentsCardList) {
            String cardsElementText = cardsElement.findElement(By.xpath(elementXpath)).getText();
            listDetails.add(cardsElementText);
        }
        return listDetails;
    }

    /**
     *  Gets Xpath String to find Incident cards details List of WebElements
     *
     * @param details String "cities", "streets", "timestamps"
     * @return String with xPath of Incident cards Details elements cities, streets, timestamps
     */
    public String getCardsDetailsElementXpath(String details) {
        switch (details.toLowerCase()) {
            case "cities":
                return ".//div[@class='city S']";
            case "streets":
                return ".//div[@class='address']";
            case "timestamps":
                return ".//div[@class='cell day']//div[@class='content']";
            default:
                return "";
        }
    }

    /**
     * Method which switching to new tab TermOfService, close and switching to mainpage window
     * @return MainPage
     */
    public MainPage openTermOfServiceWindow() {
        termsOfServiceLink.click();

        String parentWindow = webDriver.getWindowHandle();
        Set<String> handles = webDriver.getWindowHandles();
        for (String windowHandle : handles) {
            if (!windowHandle.equals(parentWindow)) {
                webDriver.switchTo().window(windowHandle);
                AboutPage aboutPage = new AboutPage(webDriver);
                aboutPage.isAboutPageLoaded();
                Assert.assertEquals(getPageURL(), "http://www.shotspotter.com/apps/tos", "Wrong URL on About Page");
                webDriver.close();
                webDriver.switchTo().window(parentWindow);
            }
        }
        waitUntilElementDisplayed(closeAboutWindowButton).click();
        return this;
    }

    /**
     * Checks if Time list contains unique elements
     *
     * @return true if Time list contains unique elements
     */
    public boolean isTimeListContainsUniqueElements() {

        List<String> timeTextList = new ArrayList<>();

        for (WebElement timeElement : timeList) {
            timeTextList.add(timeElement.getText());
        }

        Set<String> timeTextUnique = new HashSet<String>(timeTextList);

        if (timeTextUnique.size() == timeTextList.size()) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Checks if all Cities in Cities list are equal to string
     *
     * @param cityText String city to check
     * @return true if all cities are cityText, false if not
     */
    public boolean isAllCitiesEqualsTo(String cityText) {
        for (WebElement city : citiesList) {
            if (!city.getText().equalsIgnoreCase(cityText)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if Addresses list contains empty Strings
     *
     * @return true if contains, false if not
     */
    public boolean isAddressesListContainsEmptyStrings() {
        for (WebElement address : addressesList) {
            if (address.getText().equalsIgnoreCase("")) {
                return true;
            }
        }
        return false;
    }


}



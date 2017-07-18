package page;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @FindBy(xpath = "//filter-menu//div[@class='selected-option']")
    private WebElement incidentsTimeSwitch;
    @FindBy(xpath = "//*[@class='result-count']")
    private WebElement resultsCount;
    @FindBy(xpath = "//div//*[text()='List']")
    private WebElement listButton;
    @FindBy(xpath = "//incident-list//incident-card")
    private List<WebElement> incidentsCardList;
    @FindBy(xpath = "//li[text()='About']")
    private WebElement aboutButton;
    @FindBy(xpath = "//div[@class='modal-body']//a[text()='terms of service']")
    private WebElement termsOfServiceLink;
    @FindBy(xpath = "//button[text()='Close']")
    private WebElement closeAboutWindowButton;
    @FindBy(xpath = "//incident-list//incident-card//div[contains(@class, 'incident')]//div[@class='cell day']//div[@class='content']")
    private List<WebElement> timeList;
    @FindBy(xpath = "//incident-list//incident-card//div[contains(@class, 'incident')]//div[@class='city S']")
    private List<WebElement> citiesList;
    @FindBy(xpath = "//incident-list//incident-card//div[contains(@class, 'incident')]//div[@class='address']")
    private List<WebElement> addressesList;


    private WebElement getTimeFramePeriodOption(int period) {
        return webDriver.findElement(By.xpath(String.format("//filter-menu//div[@class='available-options']//*[@class='time-increment' and text()='%d']", period)));
    }

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


    public void switchTimeFramePeriod(int period) {
        incidentsTimeSwitch.click();
        getTimeFramePeriodOption(period).click();
        waitResultCountUpdated(5);
    }

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

    public int getIncidentCardsCount() {
        listButton.click();
        return incidentsCardList.size();
    }


    public void openIncidentsList() {
        listButton.click();
        waitUntilElementClickable(incidentsCardList.get(1), 5);
    }

    public List<String> getIncidentCardsDetails(String details) {
        List<String> listDetails = new ArrayList<>();
        String elementXpath = getCardsDetailsElementXpath(details);
        for (WebElement cardsElement: incidentsCardList) {
            String cardsElementText = cardsElement.findElement(By.xpath(elementXpath)).getText();
            listDetails.add(cardsElementText);
        }
        return listDetails;
    }

    public String getCardsDetailsElementXpath(String details) {
        switch (details.toLowerCase()) {
            case "cities":
                return "//div[@class='city S']";
            case "streets":
                return "//div[@class='address']";
            case "timestamps":
                return "//div[@class='cell day']//div[@class='content']";
            default:
                return "";
        }
    }

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

    public boolean isAllCitiesEqualsTo(String cityText) {
        for (WebElement city : citiesList) {
            if (!city.getText().equalsIgnoreCase(cityText)) {
                return false;
            }
        }
        return true;
    }

    public boolean isAddressesListContainsEmptyStrings() {
        for (WebElement address : addressesList) {
            if (address.getText().equalsIgnoreCase("")) {
                return true;
            }
        }
        return false;
    }
}



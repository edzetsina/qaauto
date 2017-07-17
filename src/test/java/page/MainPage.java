package page;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.ArrayList;
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

    public List<String> getIncidentCardsCities() {
        List<String> listCities = new ArrayList<String>();
        for (WebElement incidentCard : incidentsCardList) {
            String cityText = incidentCard.findElement(By.xpath("//div[@class='city S']")).getText();
            listCities.add(cityText);
        }
        return listCities;
    }

    public List<String> getIncidentCardsStreets() {
        List<String> listStreets = new ArrayList<String>();
        for (WebElement incidentCard : incidentsCardList) {
            String cityText = incidentCard.findElement(By.xpath("//div[@class='address']")).getText();
            listStreets.add(cityText);
        }
        return listStreets;
    }

    public List<String> getIncidentCardsTimeStamps() {
        List<String> listTimeStamps = new ArrayList<String>();
        for (WebElement incidentCard : incidentsCardList) {
            String cityText = incidentCard.findElement(By.xpath("//div[@class='cell day']//div[@class='content']")).getText();
            listTimeStamps.add(cityText);
        }
        return listTimeStamps;
    }

    public MainPage openTermOfServiceWindow() {
        termsOfServiceLink.click();

        String parentWindow = webDriver.getWindowHandle();
        Set<String> handles = webDriver.getWindowHandles();
        for (String windowHandle : handles) {
            if (!windowHandle.equals(parentWindow)) {
                webDriver.switchTo().window(windowHandle);

                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Assert.assertEquals(getPageURL(), "http://www.shotspotter.com/apps/tos", "Wrong URL on About Page");

                webDriver.close();
                webDriver.switchTo().window(parentWindow);
            }

        }
        waitUntilElementDisplayed(closeAboutWindowButton).click();
        return this;
    }
}



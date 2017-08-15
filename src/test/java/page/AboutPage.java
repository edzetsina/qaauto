package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by edzet on 15.07.2017.
 */
public class AboutPage extends BasePage {

    /**
     * Identifying term of service text
     */
    @FindBy(xpath = "//*[@class='entry']")
    private WebElement termOfServiceText;

    /**
     * About page constructor
     *
     * @param webDriver WebDriver instance
     */
    public AboutPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    /**
     * Method which checked is the AboutPage loaded
     * @return About page
     */
    public AboutPage isAboutPageLoaded() {
        waitUntilElementDisplayed(termOfServiceText);

        return this;
    }


}

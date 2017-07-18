package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by edzet on 15.07.2017.
 */
public class AboutPage extends BasePage {

    @FindBy(xpath = "//*[@class='entry']")
    private WebElement termOfServiceText;

    public AboutPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public AboutPage isAboutPageLoaded() {
        waitUntilElementDisplayed(termOfServiceText);

        return this;
    }


}

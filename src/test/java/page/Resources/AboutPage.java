package page.Resources;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import page.BasePage;

/**
 * Created by edzet on 15.07.2017.
 */
public class AboutPage extends BasePage {




    public AboutPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }




}

package page;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static java.lang.Thread.sleep;

public class MainPage extends BasePage {

   @FindBy(className = "settings")
   private WebElement settingIcon;
   @FindBy(xpath = "//settings-drop-down//li[text()='Logout']")
   private WebElement logOutButton;

       public MainPage(WebDriver webDriver) {
           super(webDriver);
           PageFactory.initElements(webDriver, this);
           waitUntilElementDisplayed(settingIcon, 20);
    }
    public LoginPage logOut() {
        settingIcon.click();

        waitUntilElementLoad(logOutButton, 5).click();
        return PageFactory.initElements(webDriver, LoginPage.class);
    }

    public boolean isPageLoaded() {
           return settingIcon.isDisplayed();
    }
}



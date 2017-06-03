package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends BasePage {

   @FindBy(className = "settings")
   private WebElement settingIcon;

       public MainPage(WebDriver webDriver) {
           super(webDriver);
           PageFactory.initElements(webDriver, this);
    }

    public boolean isPageLoaded() {

           return settingIcon.isDisplayed();
    }


}



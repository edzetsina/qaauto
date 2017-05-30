package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage {
    private WebDriver webDriver;
    private WebElement settingIcon;

    private void InitMainPageWebElements() {
        settingIcon = webDriver.findElement(By.className("settings"));
    }

    public MainPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        InitMainPageWebElements();

    }


    public boolean isPageLoaded() {
        return settingIcon.isDisplayed();
    }
}
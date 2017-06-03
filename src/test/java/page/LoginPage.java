package page;


import com.sun.org.glassfish.gmbal.DescriptorFields;
import org.apache.xalan.xsltc.dom.SimpleResultTreeImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static java.lang.Thread.sleep;


public class LoginPage extends BasePage {

    @FindBy(xpath = "//input[@type='email']")
    private WebElement emailField;
    @FindBy(xpath = "//input[@type='password']")
    private WebElement passwordField;
    @FindBy (xpath = "//*[@class='button' and text()='GO']")
    private WebElement goButton;
    @FindBy(className = "invalid-credentials")
    private WebElement invalidCredentialsErrorMsg;


    public LoginPage(WebDriver webDriver) {
        super(webDriver);
        webDriver.navigate().to( "https://alerts.shotspotter.biz");
        waitUntilElementDisplayed(goButton, 5);
        PageFactory.initElements(webDriver, this);

    }

    public MainPage loginAs(String userEmail, String userPassword) {
        emailField.sendKeys(userEmail);
        passwordField.sendKeys(userPassword);
        goButton.click();
        try {
               sleep(5000);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
        return new MainPage(webDriver);

    }

    public LoginPage loginAsReturnToLogin(String userEmail, String userPassword) {
        emailField.sendKeys(userEmail);
        passwordField.sendKeys(userPassword);
        goButton.click();
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }
    public boolean isInvalidCredentialMsg() {
        return invalidCredentialsErrorMsg.isDisplayed();
    }
    public String getErrorMsgText() {
        return  invalidCredentialsErrorMsg.getText();
    }

    public boolean isPageLoaded() {
        return emailField.isDisplayed();
    }

}

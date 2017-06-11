package page;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;



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
        PageFactory.initElements(webDriver, this);
        waitUntilElementDisplayed(goButton, 20);
    }

    public <T> T login(String userEmail, String userPassword) {
        emailField.sendKeys(userEmail);
        passwordField.sendKeys(userPassword);
        goButton.click();

        if (isElementDisplayed(goButton, 3)) {
            return (T) this;
        }
        else {

            return (T) PageFactory.initElements(webDriver, MainPage.class);
        }
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

package page;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


/**
 * Login Page
 */
public class LoginPage extends BasePage {

    /**
     * Identifying the Email field element
     */
    @FindBy(xpath = "//input[@type='email']")
    private WebElement emailField;
    /**
     * Identifying the Password field element
     */
    @FindBy(xpath = "//input[@type='password']")
    private WebElement passwordField;
    /**
     * Identifying the 'GO' button element
     */
    @FindBy(xpath = "//*[@class='button' and text()='GO']")
    private WebElement goButton;
    /**
     * Identifying the Error message element if login is incorrect
     */
    @FindBy(className = "invalid-credentials")
    private WebElement invalidCredentialsErrorMsg;


    /**
     * LoginPage constructor, which verify that the LoginPage loaded
     *
     * @param webDriver WebDriver instance
     */
    public LoginPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
        waitUntilElementDisplayed(goButton, 20);
    }

    /**
     * Common method to login web page
     *
     * @param userEmail    String user e-mail credentials
     * @param userPassword String user password credentials
     * @param <T> define which of variants of login passed
     * @return PageObject MainPage if login successful, if not - LoginPage
     */
    public <T> T login(String userEmail, String userPassword) {
        emailField.sendKeys(userEmail);
        passwordField.sendKeys(userPassword);
        goButton.click();

        if (isElementDisplayed(goButton, 3)) {
            return (T) this;
        } else {

            return (T) PageFactory.initElements(webDriver, MainPage.class);
        }
    }

    /**
     * Checks if invalidCredentialsErrorMsg element displayed
     *
     * @return True if element is displayed, False if not
     */
    public boolean isInvalidCredentialMsg() {
        return invalidCredentialsErrorMsg.isDisplayed();
    }

    /**
     * Checks text of invalidCredentialsErrorMsg element
     *
     * @return String text of invalidCredentialsErrorMsg element
     */
    public String getErrorMsgText() {
        return invalidCredentialsErrorMsg.getText();
    }

    /**
     * Checks if emailField element displayed
     *
     * @return True if element is displayed, False if not
     */
    public boolean isPageLoaded() {
        return emailField.isDisplayed();
    }

}

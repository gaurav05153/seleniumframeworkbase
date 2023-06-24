package pagerepository;

import base.BaseClass;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BaseClass {

    public LoginPage(){
        PageFactory.initElements(driver, this);
    }

    public String errorMessageWhenNoPassword = "Password must be present";

    @FindBy(id="username")
    public WebElement userNameTextBox;

    @FindBy(id="password")
    public WebElement passwordTextBox;

    @FindBy(css="#log-in")
    public WebElement loginButton;

    @FindBy(xpath="//div[starts-with(@id, 'random_id')]")
    public WebElement passwordAlert;

    public boolean IsUserOnLoginPage(){
        waitForElementVisible(driver, loginButton);
        return loginButton.isDisplayed();
    }

    public void loginToApplication(String userName, String password) throws Exception {
        sendKeys(userNameTextBox, userName);
        sendKeys(passwordTextBox, password);
        clickOnElement(loginButton);
    }
}

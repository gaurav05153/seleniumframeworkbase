package pagerepository;

import base.BaseClass;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage extends BaseClass {

    public HomePage(){
        PageFactory.initElements(driver, this);
    }

    public static String homePageUrl = "https://sakshingp.github.io/assignment/home.html";

    @FindBy(xpath="//span[text()='Add Account']")
    public WebElement addAccountButton;

    @FindBy(id="logged-user-name")
    public WebElement loggedUserName;

    @FindBy(id="log-in")
    public WebElement loginButton;

    @FindBy(id="amount")
    public WebElement amountHeader;

    @FindBy(xpath="//table[@id='transactionsTable']/tbody//td[contains(@class, 'text-right')]/span")
    public List<WebElement> amountsInTable;

    public boolean IsUserOnHomePage(){
        waitForElementVisible(driver, addAccountButton);
        return addAccountButton.isDisplayed();

    }
}

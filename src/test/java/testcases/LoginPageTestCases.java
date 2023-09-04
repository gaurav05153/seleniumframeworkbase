package testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import base.BaseClass;
import pagerepository.LoginPage;

public class LoginPageTestCases extends BaseClass {

    LoginPage loginPage;

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws Exception {
        createDriver();
        loginPage= new LoginPage();
    }

    @Test(description = "This TC will verify the user is on Login Page of the application", priority = 1, enabled = true)
    public void verifyTheLoginPageIsOpened() {
        boolean flag = loginPage.IsUserOnLoginPage();
        Assert.assertTrue(flag, "User is not on Login Page");
        System.out.println(driver.getTitle());
        Assert.assertEquals(driver.getTitle(), "Demo App", "User is not on Login Page");
        Assert.assertEquals(driver.getCurrentUrl(), prop.getProperty("url"), "User is not on Login Page");
    }

    @Test(description = "This TC will verify error message if user has not entered the password and try to login", priority = 2, enabled = true)
    public void verifyErrorMessageIfPasswordNotProvided() throws Exception {
        boolean flag = loginPage.IsUserOnLoginPage();
        Assert.assertTrue(flag, "User is not on Login Page");

        loginPage.loginToApplication("admin","");

        waitForElementVisible(driver, loginPage.passwordAlert);

        String actualMessage = loginPage.passwordAlert.getText();
        Assert.assertEquals(actualMessage, loginPage.errorMessageWhenNoPassword, "Expected Error Message is not appeared");
    }

    @Test(description = "This TC will verify Remember me check box is by default unchecked and then tick the checkbox", priority = 3, enabled = true)
    public void verifyCheckBoxForRememberMe() throws InterruptedException {
        boolean flag = loginPage.IsUserOnLoginPage();
        Assert.assertTrue(flag, "User is not on Login Page");

        waitForElementVisibleAndClickable(driver, loginPage.rememberMeCheckBox);
        Boolean check = loginPage.rememberMeCheckBox.isSelected();

        Assert.assertFalse(check, "Remember Me Check box is selected by default");

        clickOnElement(loginPage.rememberMeCheckBox);

        Boolean checkAfter = loginPage.rememberMeCheckBox.isSelected();

        Assert.assertTrue(checkAfter, "Remember Me Check box is not getting selected");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        driver.quit();
    }
}


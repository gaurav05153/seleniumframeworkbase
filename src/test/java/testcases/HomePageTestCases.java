package testcases;

import base.BaseClass;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pagerepository.HomePage;
import pagerepository.LoginPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomePageTestCases extends BaseClass {

    LoginPage loginPage;
    HomePage homePage;

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws Exception {
        createDriver();
        loginPage = new LoginPage();
        loginPage.loginToApplication(prop.getProperty("userName"), prop.getProperty("password"));
        homePage = new HomePage();
    }

    @Test(description = "This TC will verify that user is on Home Page or not", enabled = true)
    public void verifyUserIsOnHomePage() {
        boolean flag = homePage.IsUserOnHomePage();
        Assert.assertTrue(flag, "User is not on Home Page");
        Assert.assertEquals(driver.getCurrentUrl(), homePage.homePageUrl, "Expected Home Page URL is not appearing");
    }

    @Test(description = "This TC will verify the amount sort order of recent transaction table", enabled = true)
    public void verifyAmountSortOfRecentTransactionTableOnHomePage() throws Exception {

        boolean flag = homePage.IsUserOnHomePage();
        Assert.assertTrue(flag, "User is not on Home Page");


        clickOnElement(homePage.amountHeader);  //Will click on Amount header of transaction table


        List<WebElement> amounts = homePage.amountsInTable;

        List<String> actualAmountInString = new ArrayList<>();

        List<Double> actualAmountListInDecimal = new ArrayList<>();

        for (WebElement am : amounts) {
            String st = am.getText();

            String st1 = st.replace(" USD", "");  //- 320.00

            st1 = st1.replace(" ", "");  //-320.00 -244.00 +17.99 +340.00 +952.23 +1,250.00
            st1 = st1.replace(",", "");  //-320.00 -244.00 +17.99 +340.00 +952.23 +1250.00
            actualAmountInString.add(st1);
            actualAmountListInDecimal.add(Double.parseDouble(st1));
        }

        System.out.println("\nAmounts present in transaction table are: \n");

        for (Double amountInDecimal : actualAmountListInDecimal)
            System.out.print(amountInDecimal + " ");

        List<Double> clonedList = new ArrayList<>(actualAmountListInDecimal);

        System.out.println("\nAmounts present in Cloned List before Sorting are: \n");
        for (Double st1 : clonedList) {
            System.out.print(st1 + " ");
        }

        Collections.sort(clonedList);

        System.out.println("\nAmounts present in Cloned List After Sorting are: \n");
        for (Double st : clonedList)
            System.out.print(st + " ");

        Assert.assertEquals(actualAmountListInDecimal, clonedList, "Amounts in the table is not sorted");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }
}




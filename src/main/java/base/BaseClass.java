package base;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BaseClass {


    public static WebDriver driver;
    public static Properties prop;
    public static WebDriverWait wait;
    private static final String propertyFilePath="config.properties";
    private JavascriptExecutor jsExecutor;

    private static final Logger logger = LoggerFactory.getLogger(BaseClass.class);


    public BaseClass() {
        try {
            prop = new Properties();
            FileInputStream fis = new FileInputStream(propertyFilePath);
            prop.load(fis);

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            logger.error("File not found Exception");
        } catch (IOException ex) {
            ex.printStackTrace();
            logger.error("IO Exception");
        }
    }

    public static void createDriver() {
        switch (String.valueOf(prop.get("browser"))) {
            case "FIREFOX":
                driver = new FirefoxDriver();
                logger.info("Firefox driver created");
                break;
            case "CHROME":
                ChromeOptions options = new ChromeOptions();
                options.setAcceptInsecureCerts(true);
                options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
                options.addArguments("start-maximized");
                options.addArguments("--disabled-popup-blocking");
                options.addArguments("--remote-allow-origins=*");
                options.addArguments("--incognito");
                if (prop.getProperty("headless").equalsIgnoreCase("YES")) {
                    options.addArguments("--headless");
                    options.addArguments("--disable-gpu");
                }
                driver = new ChromeDriver(options);
                logger.info("Chrome driver created");
                break;
            case "EDGE":
                driver = new EdgeDriver();
                logger.info("Edge driver created");
                break;
            default:
                System.out.println("No matching browser found");
                logger.info("No matching driver found. Closing the script run.");
                System.exit(0);
                break;
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(prop.getProperty("implicitWait"))));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Long.parseLong(prop.getProperty("pageLoadTimeout"))));
        driver.manage().window().maximize();
        logger.info("Browser window maximize");
        driver.get(prop.getProperty("url"));
    }

    public void clickOnElement(WebElement ele) throws Exception
    {
        waitForElementVisibleAndClickable(driver,ele);
        ele.click();
    }

    //Entering text in text box
    public void sendKeys(WebElement ele, String testData) throws InterruptedException
    {
        waitForVisibilityOfElement(driver,ele);
        ele.clear();
        ele.sendKeys(testData);
    }

    //Waiting until element is visible and clickable
    public static void waitForElementVisibleAndClickable(WebDriver driver, WebElement element)
    {
        wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(element));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    //Waiting until element is visible
    public static void waitForElementVisible(WebDriver driver, WebElement element){
        wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    //Waiting until element is visibility
    public WebElement waitForVisibilityOfElement(WebDriver driver, WebElement ele)
    {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class,StaleElementReferenceException.class);
        wait.until(ExpectedConditions.visibilityOf(ele));
        return ele;
    }


    }

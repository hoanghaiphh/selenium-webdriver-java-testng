package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class Topic_13X_HTML5_ValidationMsg_On_MultiBrowsers {
    WebDriver driver;
    WebDriverWait explicitWait;
    JavascriptExecutor jsExecutor;

    public void sleepInMilliSeconds(long timeInMilliSecond) {
        try {
            Thread.sleep(timeInMilliSecond);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public WebElement waitPresenceOfElement(By locator) {
        return explicitWait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void navigateByJsExecutor(String pageUrl) {
        jsExecutor.executeScript("window.location = " + "'" + pageUrl + "'");
        explicitWait.until(ExpectedConditions.urlToBe(pageUrl));
        explicitWait.until(driver -> jsExecutor.executeScript("return document.readyState").equals("complete"));
    }

    public void highlightElement(WebElement webElement) {
        String originalStyle = webElement.getAttribute("style");
        jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", webElement, "border: 2px solid red; border-style: dashed;");
        sleepInMilliSeconds(500);
        jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", webElement, originalStyle);
    }

    public void clickByJsExecutor(By locator) {
        WebElement webElement = waitPresenceOfElement(locator);
        highlightElement(webElement);
        jsExecutor.executeScript("arguments[0].click()", webElement);
    }

    public void sendKeysByJsExecutor(By locator, String keysToSend) {
        WebElement webElement = waitPresenceOfElement(locator);
        highlightElement(webElement);
        jsExecutor.executeScript("arguments[0].setAttribute('value', '')", webElement);
        jsExecutor.executeScript("arguments[0].setAttribute('value', '" + keysToSend + "')", webElement);
    }

    public void assertValidationMsgByJsExecutor(By locator, String expectedMsg) {
        WebElement webElement = waitPresenceOfElement(locator);
        highlightElement(webElement);
        String actualMsg = (String) jsExecutor.executeScript("return arguments[0].validationMessage", webElement);
        Assert.assertEquals(actualMsg, expectedMsg);
    }

    // HTML5 Validation Message is different on each Browser.
    @Test
    public void TC_01_Chrome() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        jsExecutor = (JavascriptExecutor) driver;

        navigateByJsExecutor("https://warranty.rode.com/login");

        clickByJsExecutor(By.cssSelector("button[type='submit']"));
        assertValidationMsgByJsExecutor(By.cssSelector("input#email"), "Please fill out this field.");

        sendKeysByJsExecutor(By.cssSelector("input#email"), "automation");
        clickByJsExecutor(By.cssSelector("button[type='submit']"));
        assertValidationMsgByJsExecutor(By.cssSelector("input#email"), "Please include an '@' in the email address. 'automation' is missing an '@'.");

        sendKeysByJsExecutor(By.cssSelector("input#email"), "automation@#!");
        clickByJsExecutor(By.cssSelector("button[type='submit']"));
        assertValidationMsgByJsExecutor(By.cssSelector("input#email"), "A part following '@' should not contain the symbol '#'.");

        sendKeysByJsExecutor(By.cssSelector("input#email"), "automation@gmail.com");
        clickByJsExecutor(By.cssSelector("button[type='submit']"));
        assertValidationMsgByJsExecutor(By.cssSelector("input#password"), "Please fill out this field.");

        driver.quit();
    }

    @Test
    public void TC_02_Edge() {
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        jsExecutor = (JavascriptExecutor) driver;

        navigateByJsExecutor("https://warranty.rode.com/login");

        clickByJsExecutor(By.cssSelector("button[type='submit']"));
        assertValidationMsgByJsExecutor(By.cssSelector("input#email"), "Please fill out this field.");

        sendKeysByJsExecutor(By.cssSelector("input#email"), "automation");
        clickByJsExecutor(By.cssSelector("button[type='submit']"));
        assertValidationMsgByJsExecutor(By.cssSelector("input#email"), "Please include an '@' in the email address. 'automation' is missing an '@'.");

        sendKeysByJsExecutor(By.cssSelector("input#email"), "automation@#!");
        clickByJsExecutor(By.cssSelector("button[type='submit']"));
        assertValidationMsgByJsExecutor(By.cssSelector("input#email"), "A part following '@' should not contain the symbol '#'.");

        sendKeysByJsExecutor(By.cssSelector("input#email"), "automation@gmail.com");
        clickByJsExecutor(By.cssSelector("button[type='submit']"));
        assertValidationMsgByJsExecutor(By.cssSelector("input#password"), "Please fill out this field.");

        driver.quit();
    }

    @Test
    public void TC_03_Firefox() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        jsExecutor = (JavascriptExecutor) driver;

        navigateByJsExecutor("https://warranty.rode.com/login");

        clickByJsExecutor(By.cssSelector("button[type='submit']"));
        assertValidationMsgByJsExecutor(By.cssSelector("input#email"), "Please fill out this field.");

        sendKeysByJsExecutor(By.cssSelector("input#email"), "automation");
        clickByJsExecutor(By.cssSelector("button[type='submit']"));
        assertValidationMsgByJsExecutor(By.cssSelector("input#email"), "Please enter an email address.");

        sendKeysByJsExecutor(By.cssSelector("input#email"), "automation@#!");
        clickByJsExecutor(By.cssSelector("button[type='submit']"));
        assertValidationMsgByJsExecutor(By.cssSelector("input#email"), "Please enter an email address.");

        sendKeysByJsExecutor(By.cssSelector("input#email"), "automation@gmail.com");
        clickByJsExecutor(By.cssSelector("button[type='submit']"));
        assertValidationMsgByJsExecutor(By.cssSelector("input#password"), "Please fill out this field.");

        driver.quit();
    }

    @Test
    public void TC_04_Safari() {
        if (System.getProperty("os.name").contains("Mac")) {
            driver = new SafariDriver();
            driver.manage().window().maximize();
            explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
            jsExecutor = (JavascriptExecutor) driver;

            navigateByJsExecutor("https://warranty.rode.com/login");

            clickByJsExecutor(By.cssSelector("button[type='submit']"));
            assertValidationMsgByJsExecutor(By.cssSelector("input#email"), "Fill out this field");

            sendKeysByJsExecutor(By.cssSelector("input#email"), "automation");
            clickByJsExecutor(By.cssSelector("button[type='submit']"));
            assertValidationMsgByJsExecutor(By.cssSelector("input#email"), "Enter an email address");

            sendKeysByJsExecutor(By.cssSelector("input#email"), "automation@#!");
            clickByJsExecutor(By.cssSelector("button[type='submit']"));
            assertValidationMsgByJsExecutor(By.cssSelector("input#email"), "Enter an email address");

            sendKeysByJsExecutor(By.cssSelector("input#email"), "automation@gmail.com");
            clickByJsExecutor(By.cssSelector("button[type='submit']"));
            assertValidationMsgByJsExecutor(By.cssSelector("input#password"), "Fill out this field");

            driver.quit();
        } else {
            System.out.println("Current OS: " + System.getProperty("os.name"));
            System.out.println("This computer does not have Safari Browser.");
        }
    }
}

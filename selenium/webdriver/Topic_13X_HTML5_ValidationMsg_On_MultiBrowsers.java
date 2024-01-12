package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
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

    public void openBrowserAndNavigateToUrl(WebDriver webDriver, String pageUrl) {
        driver = webDriver;
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.location = " + "'" + pageUrl + "'");
        explicitWait.until(ExpectedConditions.urlToBe(pageUrl));
        explicitWait.until(driver -> jsExecutor.executeScript("return document.readyState").equals("complete"));
    }

    public void clickByJs(By locator) {
        jsExecutor.executeScript("arguments[0].click()", driver.findElement(locator));
    }

    public void sendKeysByJs(By locator, String keysToSend) {
        jsExecutor.executeScript("arguments[0].setAttribute('value', '')", driver.findElement(locator));
        jsExecutor.executeScript("arguments[0].setAttribute('value', '" + keysToSend + "')", driver.findElement(locator));
    }

    public void assertValidationMsg(By locator, String expectedMsg) {
        String actualMsg = (String) jsExecutor.executeScript("return arguments[0].validationMessage", driver.findElement(locator));
        Assert.assertEquals(actualMsg, expectedMsg);
    }

    // HTML5 Validation Message is different on each Browser.
    @Test
    public void TC_01_Chrome() {
        openBrowserAndNavigateToUrl(new ChromeDriver(), "https://warranty.rode.com/login");

        clickByJs(By.cssSelector("button[type='submit']"));
        assertValidationMsg(By.cssSelector("input#email"), "Please fill out this field.");

        sendKeysByJs(By.cssSelector("input#email"), "automation");
        clickByJs(By.cssSelector("button[type='submit']"));
        assertValidationMsg(By.cssSelector("input#email"), "Please include an '@' in the email address. 'automation' is missing an '@'.");

        sendKeysByJs(By.cssSelector("input#email"), "automation@#!");
        clickByJs(By.cssSelector("button[type='submit']"));
        assertValidationMsg(By.cssSelector("input#email"), "A part following '@' should not contain the symbol '#'.");

        sendKeysByJs(By.cssSelector("input#email"), "automation@gmail.com");
        clickByJs(By.cssSelector("button[type='submit']"));
        assertValidationMsg(By.cssSelector("input#password"), "Please fill out this field.");

        driver.quit();
    }

    @Test
    public void TC_02_Edge() {
        openBrowserAndNavigateToUrl(new EdgeDriver(), "https://warranty.rode.com/login");

        clickByJs(By.cssSelector("button[type='submit']"));
        assertValidationMsg(By.cssSelector("input#email"), "Please fill out this field.");

        sendKeysByJs(By.cssSelector("input#email"), "automation");
        clickByJs(By.cssSelector("button[type='submit']"));
        assertValidationMsg(By.cssSelector("input#email"), "Please include an '@' in the email address. 'automation' is missing an '@'.");

        sendKeysByJs(By.cssSelector("input#email"), "automation@#!");
        clickByJs(By.cssSelector("button[type='submit']"));
        assertValidationMsg(By.cssSelector("input#email"), "A part following '@' should not contain the symbol '#'.");

        sendKeysByJs(By.cssSelector("input#email"), "automation@gmail.com");
        clickByJs(By.cssSelector("button[type='submit']"));
        assertValidationMsg(By.cssSelector("input#password"), "Please fill out this field.");

        driver.quit();
    }

    @Test
    public void TC_03_Firefox() {
        openBrowserAndNavigateToUrl(new FirefoxDriver(), "https://warranty.rode.com/login");

        clickByJs(By.cssSelector("button[type='submit']"));
        assertValidationMsg(By.cssSelector("input#email"), "Please fill out this field.");

        sendKeysByJs(By.cssSelector("input#email"), "automation");
        clickByJs(By.cssSelector("button[type='submit']"));
        assertValidationMsg(By.cssSelector("input#email"), "Please enter an email address.");

        sendKeysByJs(By.cssSelector("input#email"), "automation@#!");
        clickByJs(By.cssSelector("button[type='submit']"));
        assertValidationMsg(By.cssSelector("input#email"), "Please enter an email address.");

        sendKeysByJs(By.cssSelector("input#email"), "automation@gmail.com");
        clickByJs(By.cssSelector("button[type='submit']"));
        assertValidationMsg(By.cssSelector("input#password"), "Please fill out this field.");

        driver.quit();
    }

    @Test
    public void TC_04_Safari() {
        if (System.getProperty("os.name").contains("Mac")) {
            openBrowserAndNavigateToUrl(new SafariDriver(), "https://warranty.rode.com/login");

            clickByJs(By.cssSelector("button[type='submit']"));
            assertValidationMsg(By.cssSelector("input#email"), "Fill out this field");

            sendKeysByJs(By.cssSelector("input#email"), "automation");
            clickByJs(By.cssSelector("button[type='submit']"));
            assertValidationMsg(By.cssSelector("input#email"), "Enter an email address");

            sendKeysByJs(By.cssSelector("input#email"), "automation@#!");
            clickByJs(By.cssSelector("button[type='submit']"));
            assertValidationMsg(By.cssSelector("input#email"), "Enter an email address");

            sendKeysByJs(By.cssSelector("input#email"), "automation@gmail.com");
            clickByJs(By.cssSelector("button[type='submit']"));
            assertValidationMsg(By.cssSelector("input#password"), "Fill out this field");

            driver.quit();
        } else {
            System.out.println("Current OS: " + System.getProperty("os.name"));
            System.out.println("This computer does not have Safari Browser.");
        }
    }
}

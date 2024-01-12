package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class Topic_15_16X_ExplicitWait_Conditions {
    WebDriver driver = new FirefoxDriver();
    WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));

    // @Test
    public void TC_01_Conditions() {

        // visibilityOfElementLocated: findElement inside wait
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("")));
        // visibilityOf: findElement finish --> wait
        explicitWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(""))));

        // invisibility: 2 cases:
        // 1 - Element still in DOM but not visible: OK
        // 2 - Element no longer in DOM: override implicitWait for faster running
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("")));

        // not/ end/ or...
        explicitWait.until(ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(By.cssSelector(""))));

        // wait for status of checkbox/ radiobutton/ default dropdown
        explicitWait.until(ExpectedConditions.elementToBeSelected(By.cssSelector("")));
        explicitWait.until(ExpectedConditions.elementSelectionStateToBe(By.cssSelector(""), true));
        explicitWait.until(ExpectedConditions.elementSelectionStateToBe(By.cssSelector(""), false));

        // frame/ iframe switch
        explicitWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(""));

        // javascript
        explicitWait.until(ExpectedConditions.jsReturnsValue(""));
        explicitWait.until(ExpectedConditions.javaScriptThrowsNoExceptions(""));

        // element redrawn --> avoid StaleElementReferenceException
        explicitWait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(""))));

    }

}

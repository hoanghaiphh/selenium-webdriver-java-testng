package webdriver;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v118.network.Network;
import org.openqa.selenium.devtools.v118.network.model.Headers;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Topic_09_p3_Alert {
    WebDriver driver;
    WebDriverWait explicitWait;

    public WebElement findVisibleElement(By locator) {
        return explicitWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void clickOnElement(By locator) {
        explicitWait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public void openBrowser(WebDriver webDriver) {
        driver = webDriver;
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Test
    public void TC_07_08_09_Alert() {
        openBrowser(new FirefoxDriver());
        driver.get("https://automationfc.github.io/basic-form/index.html");

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false); window.scrollBy(0,500);", driver.findElement(By.xpath("//button[text()='Click for JS Alert']")));

        clickOnElement(By.xpath("//button[text()='Click for JS Alert']"));
        Alert alert = explicitWait.until(ExpectedConditions.alertIsPresent());
        Assert.assertEquals(alert.getText(), "I am a JS Alert");
        alert.accept();
        Assert.assertEquals(findVisibleElement(By.cssSelector("p#result")).getText(), "You clicked an alert successfully");

        clickOnElement(By.xpath("//button[text()='Click for JS Confirm']"));
        alert = explicitWait.until(ExpectedConditions.alertIsPresent());
        Assert.assertEquals(alert.getText(), "I am a JS Confirm");
        alert.accept();
        Assert.assertEquals(findVisibleElement(By.cssSelector("p#result")).getText(), "You clicked: Ok");

        clickOnElement(By.xpath("//button[text()='Click for JS Confirm']"));
        alert = explicitWait.until(ExpectedConditions.alertIsPresent());
        alert.dismiss();
        Assert.assertEquals(findVisibleElement(By.cssSelector("p#result")).getText(), "You clicked: Cancel");

        clickOnElement(By.xpath("//button[text()='Click for JS Prompt']"));
        alert = explicitWait.until(ExpectedConditions.alertIsPresent());
        Assert.assertEquals(alert.getText(), "I am a JS prompt");
        String alertSendKeys = "Automation Testing";
        alert.sendKeys(alertSendKeys);
        alert.accept();
        Assert.assertEquals(findVisibleElement(By.cssSelector("p#result")).getText(), "You entered: " + alertSendKeys);

        clickOnElement(By.xpath("//button[text()='Click for JS Prompt']"));
        alert = explicitWait.until(ExpectedConditions.alertIsPresent());
        alert.dismiss();
        Assert.assertEquals(findVisibleElement(By.cssSelector("p#result")).getText(), "You entered: null");

        driver.quit();
    }

    @Test
    public void TC_11a_Authentication_Alert_Bypass_Link() {
        openBrowser(new FirefoxDriver());

        // access directly by link
        driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");

        Assert.assertTrue(findVisibleElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());

        driver.quit();
    }

    @Test
    public void TC_11b_Authentication_Alert_Bypass_Link() {
        openBrowser(new ChromeDriver());

        // move from another page
        driver.get("http://the-internet.herokuapp.com");

        String originUrl = driver.findElement(By.xpath("//a[text()='Basic Auth']")).getAttribute("href");
        String userName = "admin", password = "admin";
        String[] originUrlArray = originUrl.split("//");
        String authenUrl = originUrlArray[0] + "//" + userName + ":" + password + "@" + originUrlArray[1];

        driver.get(authenUrl);

        Assert.assertTrue(findVisibleElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());

        driver.quit();
    }

    public String getAuthenUrl (String originUrl, String userName, String password) {
        String[] originUrlSplit = originUrl.split("//");
        return originUrlSplit[0] + "//" + userName + ":" + password + "@" + originUrlSplit[1];
    }
    @Test
    public void TC_11c_Authentication_Alert_Bypass_Link() {
        openBrowser(new ChromeDriver());
        driver.get("http://the-internet.herokuapp.com");

        String authenUrl = getAuthenUrl(driver.findElement(By.xpath("//a[text()='Basic Auth']")).getAttribute("href"), "admin", "admin");

        driver.get(authenUrl);

        Assert.assertTrue(findVisibleElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());

        driver.quit();
    }

    @Test
    public void TC_12a_Authentication_Alert_AutoIT() throws IOException, InterruptedException {
        if (System.getProperty("os.name").contains("Windows")) {
            openBrowser(new FirefoxDriver());

            String username = "admin", password = "admin";
            String firefoxAuthen = System.getProperty("user.dir") + "\\autoIT\\authen_firefox.exe";
            String chromeAuthen = System.getProperty("user.dir") + "\\autoIT\\authen_chrome.exe";
            if (driver.toString().contains("firefox")) {
                Runtime.getRuntime().exec(new String[]{firefoxAuthen, username, password});
            } else if (driver.toString().contains("chrome")) {
                Runtime.getRuntime().exec(new String[]{chromeAuthen, username, password});
            }

            driver.get("http://the-internet.herokuapp.com/basic_auth");
            Thread.sleep(5000); // not necessary if run on Chrome

            Assert.assertTrue(findVisibleElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());

            driver.quit();
        }
    }
    @Test
    public void TC_12b_Authentication_Alert_AutoIT() throws IOException {
        if (System.getProperty("os.name").contains("Windows")) {
            openBrowser(new ChromeDriver());

            String username = "admin", password = "admin";
            String firefoxAuthen = System.getProperty("user.dir") + "\\autoIT\\authen_firefox.exe";
            String chromeAuthen = System.getProperty("user.dir") + "\\autoIT\\authen_chrome.exe";
            if (driver.toString().contains("firefox")) {
                Runtime.getRuntime().exec(new String[]{firefoxAuthen, username, password});
            } else if (driver.toString().contains("chrome")) {
                Runtime.getRuntime().exec(new String[]{chromeAuthen, username, password});
            }

            driver.get("http://the-internet.herokuapp.com/basic_auth");

            Assert.assertTrue(findVisibleElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());

            driver.quit();
        }
    }

    @Test
    public void TC_13a_Authentication_Alert_CDP() {
        // Only use for browser base on Chromium
        // Selenium version need to have devtools version compatible with Chromium version
        // Base64 lib: common-codec
        openBrowser(new ChromeDriver());

        // Get DevTool object
        DevTools devTools = ((HasDevTools) driver).getDevTools();

        // Start new session
        devTools.createSession();

        // Enable the Network domain of devtools
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        // Encode username/ password
        Map<String, Object> headers = new HashMap<String, Object>();
        String basicAuthen = "Basic " + new String(new Base64().encode(String.format("%s:%s", "admin", "admin").getBytes()));
        headers.put("Authorization", basicAuthen);

        // Set to Header
        devTools.send(Network.setExtraHTTPHeaders(new Headers(headers)));

        driver.get("https://the-internet.herokuapp.com/basic_auth");

        Assert.assertTrue(findVisibleElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());

        driver.quit();
    }
    @Test
    public void TC_13b_Authentication_Alert_CDP() {
        openBrowser(new EdgeDriver());

        DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        Map<String, Object> headers = new HashMap<String, Object>();
        String basicAuthen = "Basic " + new String(new Base64().encode(String.format("%s:%s", "admin", "admin").getBytes()));
        headers.put("Authorization", basicAuthen);
        devTools.send(Network.setExtraHTTPHeaders(new Headers(headers)));

        driver.get("https://the-internet.herokuapp.com/basic_auth");

        Assert.assertTrue(findVisibleElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());

        driver.quit();
    }
}
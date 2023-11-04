package exercise;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

public class Exercise_09_Alert {
    WebDriver driver;

    public void sleepInSeconds (long timeInSecond) {
        try {
            Thread.sleep(timeInSecond*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void TC_07_08_09_Alert() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();

        driver.get("https://automationfc.github.io/basic-form/index.html");
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement result = driver.findElement(By.cssSelector("p#result"));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//button[text()='Click for JS Alert']")));
        driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
        Assert.assertEquals(explicitWait.until(ExpectedConditions.alertIsPresent()).getText(), "I am a JS Alert");
        explicitWait.until(ExpectedConditions.alertIsPresent()).accept();
        sleepInSeconds(1);
        Assert.assertEquals(result.getText(), "You clicked an alert successfully");

        driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
        Assert.assertEquals(explicitWait.until(ExpectedConditions.alertIsPresent()).getText(), "I am a JS Confirm");
        explicitWait.until(ExpectedConditions.alertIsPresent()).accept();
        sleepInSeconds(1);
        Assert.assertEquals(result.getText(), "You clicked: Ok");
        driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
        explicitWait.until(ExpectedConditions.alertIsPresent()).dismiss();
        sleepInSeconds(1);
        Assert.assertEquals(result.getText(), "You clicked: Cancel");

        driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
        Assert.assertEquals(explicitWait.until(ExpectedConditions.alertIsPresent()).getText(), "I am a JS prompt");
        String alertSendKeys = "Automation Testing";
        explicitWait.until(ExpectedConditions.alertIsPresent()).sendKeys(alertSendKeys);
        explicitWait.until(ExpectedConditions.alertIsPresent()).accept();
        sleepInSeconds(1);
        Assert.assertEquals(result.getText(), "You entered: " + alertSendKeys);
        driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
        explicitWait.until(ExpectedConditions.alertIsPresent()).dismiss();
        sleepInSeconds(1);
        Assert.assertEquals(result.getText(), "You entered: null");

        driver.quit();
    }

    @Test
    public void TC_11_Authentication_Alert_Bypass_Link() {
        // access directly by link
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();

        driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
        sleepInSeconds(1);
        Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());

        driver.quit();

        // move from another page
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();

        driver.get("http://the-internet.herokuapp.com");
        String originUrl = driver.findElement(By.xpath("//a[text()='Basic Auth']")).getAttribute("href");
        String userName = "admin", password = "admin";
        String[] originUrlArray = originUrl.split("//");
        String authenUrl = originUrlArray[0] + "//" + userName + ":" + password + "@" + originUrlArray[1];
        driver.get(authenUrl);
        sleepInSeconds(1);
        Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());

        driver.quit();
    }

    public String getAuthenUrl (String originUrl, String userName, String password) {
        String[] originUrlSplit = originUrl.split("//");
        return originUrlSplit[0] + "//" + userName + ":" + password + "@" + originUrlSplit[1];
    }
    @Test
    public void TC_11x_Authentication_Alert_Bypass_Link() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
        driver.get("http://the-internet.herokuapp.com");

        driver.get(getAuthenUrl(driver.findElement(By.xpath("//a[text()='Basic Auth']")).getAttribute("href"), "admin", "admin"));
        sleepInSeconds(1);
        Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());

        driver.quit();
    }

    @Test
    public void TC_12_Authentication_Alert_AutoIT() throws IOException {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();

        if (System.getProperty("os.name").contains("Windows")) {
            String username = "admin", password = "admin";
            String firefoxAuthen = System.getProperty("user.dir") + "\\autoIT\\authen_firefox.exe";
            String chromeAuthen = System.getProperty("user.dir") + "\\autoIT\\authen_chrome.exe";
            if (driver.toString().contains("firefox")) {
                Runtime.getRuntime().exec(new String[]{firefoxAuthen, username, password});
            } else if (driver.toString().contains("chrome")) {
                Runtime.getRuntime().exec(new String[]{chromeAuthen, username, password});
            }
            driver.get("http://the-internet.herokuapp.com/basic_auth");
            sleepInSeconds(7);
            Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());
        }
        driver.quit();

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();

        if (System.getProperty("os.name").contains("Windows")) {
            String username = "admin", password = "admin";
            String firefoxAuthen = System.getProperty("user.dir") + "\\autoIT\\authen_firefox.exe";
            String chromeAuthen = System.getProperty("user.dir") + "\\autoIT\\authen_chrome.exe";
            if (driver.toString().contains("firefox")) {
                Runtime.getRuntime().exec(new String[]{firefoxAuthen, username, password});
            } else if (driver.toString().contains("chrome")) {
                Runtime.getRuntime().exec(new String[]{chromeAuthen, username, password});
            }
            driver.get("http://the-internet.herokuapp.com/basic_auth");
            sleepInSeconds(7);
            Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());
        }
        driver.quit();
    }

    @Test
    public void TC_13a_Authentication_Alert_CDP() {
        // Only use for browser base on Chromium
        // Selenium version need to have devtools version compatible with Chromium version
        // Base64 lib: common-codec
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();

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
        sleepInSeconds(5);
        Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());

        driver.quit();
    }
    @Test
    public void TC_13b_Authentication_Alert_CDP() {
        driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();

        DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        Map<String, Object> headers = new HashMap<String, Object>();
        String basicAuthen = "Basic " + new String(new Base64().encode(String.format("%s:%s", "admin", "admin").getBytes()));
        headers.put("Authorization", basicAuthen);
        devTools.send(Network.setExtraHTTPHeaders(new Headers(headers)));

        driver.get("https://the-internet.herokuapp.com/basic_auth");
        sleepInSeconds(5);
        Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());

        driver.quit();
    }
}
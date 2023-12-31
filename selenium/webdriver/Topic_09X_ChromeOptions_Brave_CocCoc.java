package webdriver;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v118.network.Network;
import org.openqa.selenium.devtools.v118.network.model.Headers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Topic_09X_ChromeOptions_Brave_CocCoc {
    WebDriver driver;

    @Test
    public void TC_01_CocCoc() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        if (System.getProperty("os.name").contains("Windows")) {
            options.setBinary("C:\\Program Files\\CocCoc\\Browser\\Application\\browser.exe");
        } else if (System.getProperty("os.name").contains("Mac")) {
            options.setBinary("/Applications/CocCoc.app/Contents/MacOS/CocCoc");
        }
        driver = new ChromeDriver(options);
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
        Thread.sleep(1000);
        Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());

        driver.quit();
    }

    @Test
    public void TC_02_Brave() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        if (System.getProperty("os.name").contains("Windows")) {
            options.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");
        } else if (System.getProperty("os.name").contains("Linux")) {
            options.setBinary("/opt/brave.com/brave/brave");
        } else if (System.getProperty("os.name").contains("Mac")) {
            options.setBinary("/Applications/Brave Browser.app/Contents/MacOS/Brave Browser");
        }
        driver = new ChromeDriver(options);
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
        Thread.sleep(1000);
        Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());

        driver.quit();
    }
}

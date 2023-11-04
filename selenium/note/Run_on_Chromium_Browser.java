package note;

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

public class Run_on_Chromium_Browser {
    WebDriver driver;

    @Test
    public void TC_01_CocCoc() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Program Files\\CocCoc\\Browser\\Application\\browser.exe");
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
        options.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");
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

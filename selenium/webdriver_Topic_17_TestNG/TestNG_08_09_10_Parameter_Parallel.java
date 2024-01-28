package webdriver_Topic_17_TestNG;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class TestNG_08_09_10_Parameter_Parallel {
    WebDriver driver;

    public void getBrowserDriver(String browserName) {
        switch (browserName) {
            case "firefox" -> driver = new FirefoxDriver();
            case "chrome" -> driver = new ChromeDriver();
            case "edge" -> driver = new EdgeDriver();
            default -> throw new RuntimeException("Browser name is not valid.");
        }
    }

    public String getEnvironmentUrl(String environmentName) {
        String urlValue;
        switch (environmentName) {
            case "dev" -> urlValue = "http://dev.techpanda.org";
            case "test" -> urlValue = "http://test.techpanda.org";
            case "staging" -> urlValue = "http://staging.techpanda.org";
            case "live/production" -> urlValue = "http://live.techpanda.org";
            case "sandbox" -> urlValue = "http://sandbox.techpanda.org";
            default -> throw new RuntimeException("Environment name is not valid.");
        }
        return urlValue;
    }

    @Parameters ({"browser", "version"})
    @BeforeClass
    public void beforeClass(String browserName, String browserVersion) {
        getBrowserDriver(browserName);
        System.out.println(browserName + " with version: " + browserVersion);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @Parameters ("environment")
    @Test
    public void TC_01_LoginToSystem(@Optional("live/production") String environmentName)  {
        driver.get(getEnvironmentUrl(environmentName) + "/index.php/customer/account/login/");

        driver.findElement(By.xpath("//*[@id='email']")).sendKeys("selenium_11_01@gmail.com");
        driver.findElement(By.xpath("//*[@id='pass']")).sendKeys("111111");
        driver.findElement(By.xpath("//*[@id='send2']")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='col-1']//p")).getText().contains("selenium_11_01@gmail.com"));
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

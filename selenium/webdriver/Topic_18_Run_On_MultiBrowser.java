package webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Topic_18_Run_On_MultiBrowser {
    WebDriver driver;

    @Test
    public void TC_01_Run_On_Firefox() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("https://www.facebook.com/");
        driver.quit();
    }

    @Test
    public void TC_02_Run_On_Chrome() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("https://www.facebook.com/");
        driver.quit();
    }

    @Test
    public void TC_03_Run_On_Edge() {
        driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("https://www.facebook.com/");
        driver.quit();
    }

    @Test
    public void TC_04_Run_On_Safari() {
        String osName = System.getProperty("os.name");
        if (osName.contains("Mac")) {
            driver = new SafariDriver();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            driver.get("https://www.facebook.com/");
            driver.quit();
        }
    }

}


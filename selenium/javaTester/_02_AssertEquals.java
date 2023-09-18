package javaTester;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class _02_AssertEquals {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
        }

        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_() {
        driver.get("https://demo.nopcommerce.com/login?returnUrl=%2Fregister");

        driver.findElement(By.xpath("//div[@class='page-title']//h1"));
        System.out.print(driver.findElement(By.xpath("//div[@class='page-title']//h1")).getText());

        // Get text va gan cho 1 bien string
        String successMessageText = driver.findElement(By.xpath("//div[@class='page-title']//h1")).getText();
        // Doi chieu
        Assert.assertEquals(successMessageText,"Welcome, Please Sign In!");

    }

    @Test
    public void TC_02_() {

    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

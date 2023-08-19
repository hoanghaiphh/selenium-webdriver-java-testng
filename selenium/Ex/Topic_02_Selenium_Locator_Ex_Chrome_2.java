package Ex;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Topic_02_Selenium_Locator_Ex_Chrome_2 {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
        } else {
            System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
        }

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://live.techpanda.org/index.php/customer/account/create/");
    }

    // Selenium version: 1.x/ 2.x/ 3.x/ 4.x
    // 8 loại Locator
    // Selenium Locator = HTML Attribute
    // Id/ Class/ Name = Trùng vs 3 attribute của HTML
    // LinkText/ Partial LinkText: HTML Link (thẻ a)
    // Tagname: HTML Tagname
    // Css/ XPath

    // Selenium version 4.x - GUI (Graphic User Interface)
    // Class - Relative Locator
    // above/ bellow/ near/ leftOf/ rightOf

    // UI Testing
    // FUI: Functional UI

    // GUI: Graphic UI - Visualize Testing
    // Font/ Size/ Color/ Position/ Location/ Resolution/ Responsive/...

    @Test
    public void TC_01_sendKeys() {
        driver.findElement(By.id("firstname")).sendKeys("Hai");
        driver.findElement(By.cssSelector("input[name='middlename']")).sendKeys("Hoang");
        driver.findElement(By.xpath("//input[@id='lastname']")).sendKeys("Phan");
        driver.findElement(By.xpath("//input[@class='input-text validate-email required-entry']")).sendKeys("hoanghai.phh@gmail.com");
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys("Abc@1234");
        driver.findElement(By.xpath("//input[@name='confirmation']")).sendKeys("Abc@1234");
    }

    @Test
    public void TC_02_click() {
        driver.findElement(By.xpath("//div[@class='buttons-set']//button[@class='button']//span")).click();
    }

    @AfterClass
    public void
    afterClass() {
        // driver.quit();
    }
}
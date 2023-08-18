package Ex;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Topic_02_Selenium_Locator_Ex_Chrome {
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
    public void TC_01_ID() {
        driver.findElement(By.id("firstname"));
        System.out.println(driver.findElement(By.id("firstname")));
    }

    @Test
    public void TC_02_Class() {
        driver.findElement(By.className("page-title"));
    }

    @Test
    public void TC_03_Name() {
        driver.findElement(By.name("middlename"));
    }

    @Test
    public void TC_04_Tagname() {
        driver.findElements(By.tagName("input"));
    }

    @Test
    public void TC_05_Link_Text() {
        // do chinh xac cao
        driver.findElement(By.linkText("TV"));
    }
    @Test
    public void TC_06_Partial_Link_Text() {
        // do chinh xac ko cao
        driver.findElement(By.partialLinkText("AND RETURNS"));
    }

    @Test
    public void TC_07_CSS() {
        // CSS vs ID
        driver.findElement(By.cssSelector("input[id='firstname']"));
        driver.findElement(By.cssSelector("input#firstname"));
        driver.findElement(By.cssSelector("#firstname"));

        // CSS vs Class
        driver.findElement(By.cssSelector("div[class='page-title']"));
        driver.findElement(By.cssSelector("div.page-title"));
        driver.findElement(By.cssSelector(".page-title"));

        // CSS vs Name
        driver.findElement(By.cssSelector("input[name='middlename']"));

        // CSS vs tagName
        driver.findElements(By.cssSelector("input"));

        // CSS vs LinkText
        driver.findElement(By.cssSelector("a[href='http://live.techpanda.org/index.php/tv.html']"));

        // CSS vs Partial Link Text
        driver.findElement(By.cssSelector("a[href*='tv']"));
    }
    @Test
    public void TC_08_XPATH() {
        // CSS vs ID
        driver.findElement(By.xpath("//input[@id='firstname']"));

        // CSS vs Class
        driver.findElement(By.xpath("//div[@class='page-title']"));

        // CSS vs Name
        driver.findElement(By.xpath("//input[@name='middlename']"));

        // CSS vs tagName
        driver.findElements(By.xpath("//input"));

        // CSS vs LinkText
        driver.findElement(By.xpath("//a[@href='http://live.techpanda.org/index.php/tv.html']"));
        driver.findElement(By.xpath("//a[text()='TV']"));

        // CSS vs Partial Link Text
        driver.findElement(By.xpath("//a[contains(@href,'/tv')]"));
        driver.findElement(By.xpath("//a[contains(text(),'TV')]"));
    }
    @AfterClass
    public void
    afterClass() {
        driver.quit();
    }
}
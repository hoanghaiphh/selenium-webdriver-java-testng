package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class Topic_11_Selenium_Locator {
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
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.get("https://demo.nopcommerce.com/register");
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
        driver.findElement(By.id("FirstName")).sendKeys("Keane");
    }
    @Test
    public void TC_02_Class() {
        driver.findElement(By.className("header-logo"));
    }
    @Test
    public void TC_03_Name() {
        driver.findElement(By.name("DateOfBirthDay"));
    }
    @Test
    public void TC_04_Tagname() {
        driver.findElements(By.tagName("input"));
    }
    @Test
    public void TC_05_Link_Text() {
        // do chinh xac cao
        driver.findElement(By.linkText("Shipping & returns"));
    }
    @Test
    public void TC_06_Partial_Link_Text() {
        // do chinh xac ko cao
        driver.findElement(By.partialLinkText("vendor account"));
    }

    @Test
    public void TC_07_CSS() {
        // CSS vs ID
        driver.findElement(By.cssSelector("input[id='FirstName']"));
        driver.findElement(By.cssSelector("input#FirstName"));
        driver.findElement(By.cssSelector("#FirstName"));

        // CSS vs Class
        driver.findElement(By.cssSelector("div[class='header-logo']"));
        driver.findElement(By.cssSelector("div.header-logo"));
        driver.findElement(By.cssSelector(".header-logo"));

        // CSS vs Name
        driver.findElement(By.cssSelector("input[name='FirstName']"));

        // CSS vs tagName
        driver.findElements(By.cssSelector("input"));

        // CSS vs LinkText
        driver.findElement(By.cssSelector("a[href='/customer/addresses']"));

        // CSS vs Partial Link Text
        driver.findElement(By.cssSelector("a[href*='addresses']"));
        // driver.findElement(By.cssSelector("a[href^='addresses']"));
        // driver.findElement(By.cssSelector("a[href$='addresses']"));
    }

    @Test
    public void TC_08_XPATH() {
        // CSS vs ID
        driver.findElement(By.xpath("//input[@id='LastName']"));

        // CSS vs Class
        driver.findElement(By.xpath("//div[@class='currency-selector']"));

        // CSS vs Name
        driver.findElement(By.xpath("//input[@name='Email']"));

        // CSS vs tagName
        driver.findElements(By.xpath("//span"));

        // CSS vs LinkText
        driver.findElement(By.xpath("//a[@href='/jewelry']"));
        driver.findElement(By.xpath("//a[text()='Jewelry ']"));

        // CSS vs Partial Link Text
        driver.findElement(By.xpath("//a[contains(@href,'viewedproducts')]"));
        driver.findElement(By.xpath("//a[contains(text(),'Recently vie')]"));
    }

    @Test
    public void TC_09_Relative_Locator() {
        driver.get("https://demo.nopcommerce.com/login?returnUrl=%2Fregister");

        // Login button
        By loginButtonBy = By.cssSelector("button.login-button");
        WebElement loginButtonElement = driver.findElement(By.cssSelector("button.login-button"));

        // Remember me checkbox
        By rememberMeBy = By.cssSelector("input#RememberMe");
        WebElement rememberMeElement = driver.findElement(By.cssSelector("input#RememberMe"));

        // Forgot password link
        WebElement forgotPasswordElement = driver.findElement(By.cssSelector("span.forgot-password"));

        // Password textbox
        By passwordTextboxBy = By.cssSelector("input#Password");

        // GUI (location/ position)
        WebElement rememberMeTextElement = driver
                .findElement(RelativeLocator.with(By.tagName("label"))
                        .above(loginButtonBy)
                        .toRightOf(rememberMeElement)
                        .toLeftOf(forgotPasswordElement)
                        .below(passwordTextboxBy)
                        .near(forgotPasswordElement));
        System.out.println(rememberMeTextElement.getText());

        List<WebElement> alllinks = driver.findElements(RelativeLocator.with(By.tagName("a")));
        System.out.println(alllinks.size());

        By rememberMeTextBy = RelativeLocator.with(By.tagName("label"))
                .above(loginButtonElement)
                .toRightOf(rememberMeBy);
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
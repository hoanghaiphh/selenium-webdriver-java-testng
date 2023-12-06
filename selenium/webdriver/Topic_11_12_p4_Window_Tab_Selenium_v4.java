package webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Set;

public class Topic_11_12_p4_Window_Tab_Selenium_v4 {
    WebDriver driver;
    WebDriverWait explicitWait;

    public void switchToNewTab(WebElement webElement) {
        String tabUrl = webElement.getAttribute("href");
        driver.switchTo().newWindow(WindowType.TAB).get(tabUrl);
        explicitWait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }

    public void closeAllAndSwitch(String parentWindowHandle) {
        Set<String> allWindowHandles = driver.getWindowHandles();
        for (String windowHandle : allWindowHandles) {
            if (!windowHandle.equals(parentWindowHandle)) {
                driver.switchTo().window(windowHandle);
                driver.close();
            }
        }
        driver.switchTo().window(parentWindowHandle);
    }

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Test
    public void TC_13_Window_Tab() {
        driver.get("https://automationfc.github.io/basic-form/index.html");
        String parentWindowHandle = driver.getWindowHandle();

        // Google
        switchToNewTab(driver.findElement(By.xpath("//a[text()='GOOGLE']")));

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.google.com.vn/");
        Assert.assertEquals(driver.getTitle(), "Google");

        // Facebook
        driver.switchTo().window(parentWindowHandle);

        switchToNewTab(driver.findElement(By.xpath("//a[text()='FACEBOOK']")));

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.facebook.com/");
        Assert.assertEquals(driver.getTitle(), "Facebook – log in or sign up");

        // Tiki
        driver.switchTo().window(parentWindowHandle);

        switchToNewTab(driver.findElement(By.xpath("//a[text()='TIKI']")));

        Assert.assertEquals(driver.getCurrentUrl(), "https://tiki.vn/");
        Assert.assertEquals(driver.getTitle(), "Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");

        // close all except parent window/tab --> switch back to parent window/tab
        closeAllAndSwitch(parentWindowHandle);

        Assert.assertEquals(driver.getCurrentUrl(), "https://automationfc.github.io/basic-form/index.html");
        Assert.assertEquals(driver.getTitle(), "Selenium WebDriver");
    }

    @Test
    public void TC_14_Window_Tab() {
        driver.get("https://skills.kynaenglish.vn/");
        String parentWindowHandle = driver.getWindowHandle();

        // Facebook
        switchToNewTab(driver.findElement(By.xpath("//div[@class='hotline']//img[@alt='facebook']/parent::a")));

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.facebook.com/kyna.vn");
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("form#login_popup_cta_form")));
        Assert.assertEquals(driver.getTitle(), "Kyna.vn | Ho Chi Minh City | Facebook");

        // Youtube
        driver.switchTo().window(parentWindowHandle);

        switchToNewTab(driver.findElement(By.xpath("//div[@class='hotline']//img[@alt='youtube']/parent::a")));

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.youtube.com/user/kynavn");
        Assert.assertEquals(driver.getTitle(), "Kyna.vn - YouTube");

        // close all except parent window/tab --> switch back to parent window/tab
        closeAllAndSwitch(parentWindowHandle);

        Assert.assertEquals(driver.getCurrentUrl(), "https://skills.kynaenglish.vn/");
        Assert.assertEquals(driver.getTitle(), "Kyna.vn - Học online cùng chuyên gia");
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

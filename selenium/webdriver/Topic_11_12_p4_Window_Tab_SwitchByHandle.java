package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class Topic_11_12_p4_Window_Tab_SwitchByHandle {
    WebDriver driver;

    public void sleepInSeconds (long timeInSecond) {
        try {
            Thread.sleep(timeInSecond*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToNewWindow(WebElement webElement) {
        Set<String> beforeHandles = driver.getWindowHandles();
        webElement.click();
        sleepInSeconds(1);
        Set<String> afterHandles = driver.getWindowHandles();
        for (String windowHandle : afterHandles) {
            if (!beforeHandles.contains(windowHandle)) {
                driver.switchTo().window(windowHandle);
            }
        }
    }

    public void closeAllWindowsAndSwitch(String parentWindowHandle) {
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
    }

    @Test
    public void TC_13_Window_Tab() {
        driver.get("https://automationfc.github.io/basic-form/index.html");
        String parentWindowHandle = driver.getWindowHandle();

        // Google
        switchToNewWindow(driver.findElement(By.xpath("//a[text()='GOOGLE']")));
        sleepInSeconds(3);

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.google.com.vn/");
        Assert.assertEquals(driver.getTitle(), "Google");

        // Facebook
        driver.switchTo().window(parentWindowHandle);

        switchToNewWindow(driver.findElement(By.xpath("//a[text()='FACEBOOK']")));
        sleepInSeconds(3);

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.facebook.com/");
        Assert.assertEquals(driver.getTitle(), "Facebook – log in or sign up");

        // Tiki
        driver.switchTo().window(parentWindowHandle);

        switchToNewWindow(driver.findElement(By.xpath("//a[text()='TIKI']")));
        sleepInSeconds(3);

        Assert.assertEquals(driver.getCurrentUrl(), "https://tiki.vn/");
        Assert.assertEquals(driver.getTitle(), "Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");

        // close all except parent window/tab --> switch back to parent window/tab
        closeAllWindowsAndSwitch(parentWindowHandle);
        sleepInSeconds(1);

        Assert.assertEquals(driver.getCurrentUrl(), "https://automationfc.github.io/basic-form/index.html");
        Assert.assertEquals(driver.getTitle(), "Selenium WebDriver");
    }

    @Test
    public void TC_14_Window_Tab() {
        driver.get("https://skills.kynaenglish.vn/");
        String parentWindowHandle = driver.getWindowHandle();

        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,document.body.scrollHeight)");
        sleepInSeconds(1);

        // Facebook
        switchToNewWindow(driver.findElement(By.cssSelector("div.hotline img[alt='facebook']")));
        sleepInSeconds(3);

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.facebook.com/kyna.vn");
        Assert.assertEquals(driver.getTitle(), "Kyna.vn | Ho Chi Minh City | Facebook");

        // Youtube
        driver.switchTo().window(parentWindowHandle);

        switchToNewWindow(driver.findElement(By.cssSelector("div.hotline img[alt='youtube']")));
        sleepInSeconds(3);

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.youtube.com/user/kynavn");
        Assert.assertEquals(driver.getTitle(), "Kyna.vn - YouTube");

        // close all except parent window/tab --> switch back to parent window/tab
        closeAllWindowsAndSwitch(parentWindowHandle);
        sleepInSeconds(1);

        Assert.assertEquals(driver.getCurrentUrl(), "https://skills.kynaenglish.vn/");
        Assert.assertEquals(driver.getTitle(), "Kyna.vn - Học online cùng chuyên gia");
    }

    @Test
    public void TC_15_Window_Tab() {
        driver.get("http://live.techpanda.org/");
        String parentWindowHandle = driver.getWindowHandle();

        driver.findElement(By.xpath("//a[text()='Mobile']")).click();

        driver.findElement(By.xpath("//a[text()='IPhone']/parent::h2/parent::div//a[@class='link-compare']")).click();
        sleepInSeconds(1);

        Assert.assertEquals(driver.findElement(By.cssSelector("li.success-msg span")).getText(), "The product IPhone has been added to comparison list.");
        Assert.assertTrue(driver.findElement(By.xpath("//ol[@id='compare-items']//a[text()='IPhone']")).isDisplayed());

        driver.findElement(By.xpath("//a[text()='Sony Xperia']/parent::h2/parent::div//a[@class='link-compare']")).click();
        sleepInSeconds(1);

        Assert.assertEquals(driver.findElement(By.cssSelector("li.success-msg span")).getText(), "The product Sony Xperia has been added to comparison list.");
        Assert.assertTrue(driver.findElement(By.xpath("//ol[@id='compare-items']//a[text()='Sony Xperia']")).isDisplayed());

        switchToNewWindow(driver.findElement(By.cssSelector("button[title='Compare']")));
        sleepInSeconds(3);

        Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/catalog/product_compare/index/");
        Assert.assertEquals(driver.getTitle(), "Products Comparison List - Magento Commerce");
        Assert.assertEquals(driver.findElement(By.cssSelector("h1")).getText(), "COMPARE PRODUCTS");

        closeAllWindowsAndSwitch(parentWindowHandle);

        Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/mobile.html");
        Assert.assertEquals(driver.getTitle(), "Mobile");

        driver.findElement(By.xpath("//a[text()='Clear All']")).click();
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.alertIsPresent()).accept();
        sleepInSeconds(1);

        Assert.assertEquals(driver.findElement(By.cssSelector("li.success-msg span")).getText(), "The comparison list was cleared.");
    }

    @Test
    public void TC_16_Window_Tab() { // there is a not-in-DOM popup displayed randomly
        driver.get("https://dictionary.cambridge.org/vi/");
        String parentWindowHandle = driver.getWindowHandle();

        List<WebElement> popup = driver.findElements(By.xpath("//div[contains(@class, 'surveyContainer')]"));
        if (!popup.isEmpty() && popup.get(0).isDisplayed()) {
            driver.findElement(By.xpath("//button[contains(@class, 'closeModalBtn')]")).click();
        }

        switchToNewWindow(driver.findElement(By.cssSelector("span.cdo-login-button span")));

        driver.findElement(By.cssSelector("input[value='Log in']")).click();
        sleepInSeconds(3);

        Assert.assertEquals(driver.findElement(By.cssSelector("div.gigya-composite-control-textbox>span.gigya-error-msg-active")).getText(), "This field is required");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.gigya-composite-control-password>span.gigya-error-msg-active")).getText(), "This field is required");

        closeAllWindowsAndSwitch(parentWindowHandle);

        /*popup = driver.findElements(By.xpath("//div[contains(@class, 'surveyContainer')]"));
        if (!popup.isEmpty() && popup.get(0).isDisplayed()) {
            driver.findElement(By.xpath("//button[contains(@class, 'closeModalBtn')]")).click();
        }*/

        driver.findElement(By.cssSelector("input#searchword")).sendKeys("automation");
        driver.findElement(By.cssSelector("button.cdo-search-button")).click();
        sleepInSeconds(3);

        Assert.assertEquals(driver.findElement(By.cssSelector("article#page-content h1")).getText(), "Ý nghĩa của automation trong tiếng Anh");
    }

    @Test
    public void TC_17_Window_Tab() { // close authentication popup before switch
        driver.get("https://courses.dce.harvard.edu/");
        String parentWindowHandle = driver.getWindowHandle();

        driver.findElement(By.cssSelector("div.banner__auth")).click();

        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", driver.findElement(By.cssSelector("button.sam-wait__close")));
        sleepInSeconds(1);

        Set<String> allWindowHandles = driver.getWindowHandles();
        for (String windowHandle : allWindowHandles) {
            if (!windowHandle.equals(parentWindowHandle)) {
                driver.switchTo().window(windowHandle);
                driver.manage().window().maximize();
            }
        }

        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", driver.findElement(By.xpath("//div[text()='Log in with HarvardKey']")));

        driver.findElement(By.cssSelector("button.mdc-button--raised")).click();
        sleepInSeconds(3);

        Assert.assertEquals(driver.findElement(By.cssSelector("div.login-status-message")).getText(), "Password is a required field. Username is a required field.");

        closeAllWindowsAndSwitch(parentWindowHandle);

        driver.findElement(By.cssSelector("input#crit-keyword")).sendKeys("law");
        driver.findElement(By.cssSelector("button#search-button")).click();
        sleepInSeconds(3);

        Assert.assertEquals(driver.findElement(By.cssSelector("div.panel--kind-results h2")).getText(), "Search Results");
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

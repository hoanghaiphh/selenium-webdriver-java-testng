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
    WebDriverWait explicitWait;

    public void sleepInSeconds (long timeInSecond) {
        try {
            Thread.sleep(timeInSecond*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public WebElement waitVisibilityOfElement(By locator) {
        return explicitWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void clickAndSwitch(WebElement webElement) {
        Set<String> beforeHandles = driver.getWindowHandles();
        webElement.click();
        explicitWait.until(ExpectedConditions.numberOfWindowsToBe(beforeHandles.size() + 1));
        Set<String> afterHandles = driver.getWindowHandles();
        for (String windowHandle : afterHandles) {
            if (!beforeHandles.contains(windowHandle)) {
                driver.switchTo().window(windowHandle);
            }
        }
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
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Test
    public void TC_13_Window_Tab() {
        driver.get("https://automationfc.github.io/basic-form/index.html");
        String parentWindowHandle = driver.getWindowHandle();

        // Google
        clickAndSwitch(waitVisibilityOfElement(By.xpath("//a[text()='GOOGLE']")));

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.google.com.vn/");
        Assert.assertEquals(driver.getTitle(), "Google");

        // Facebook
        driver.switchTo().window(parentWindowHandle);

        clickAndSwitch(waitVisibilityOfElement(By.xpath("//a[text()='FACEBOOK']")));

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.facebook.com/");
        Assert.assertEquals(driver.getTitle(), "Facebook – log in or sign up");

        // Tiki
        driver.switchTo().window(parentWindowHandle);

        clickAndSwitch(waitVisibilityOfElement(By.xpath("//a[text()='TIKI']")));

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

        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,document.body.scrollHeight)");

        // Facebook
        clickAndSwitch(waitVisibilityOfElement(By.cssSelector("div.hotline img[alt='facebook']")));

        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("form#login_popup_cta_form")));
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.facebook.com/kyna.vn");
        Assert.assertEquals(driver.getTitle(), "Kyna.vn | Ho Chi Minh City | Facebook");

        // Youtube
        driver.switchTo().window(parentWindowHandle);

        clickAndSwitch(waitVisibilityOfElement(By.cssSelector("div.hotline img[alt='youtube']")));

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.youtube.com/user/kynavn");
        Assert.assertEquals(driver.getTitle(), "Kyna.vn - YouTube");

        // close all except parent window/tab --> switch back to parent window/tab
        closeAllAndSwitch(parentWindowHandle);

        Assert.assertEquals(driver.getCurrentUrl(), "https://skills.kynaenglish.vn/");
        Assert.assertEquals(driver.getTitle(), "Kyna.vn - Học online cùng chuyên gia");
    }

    @Test
    public void TC_15_Window_Tab() {
        driver.get("http://live.techpanda.org/");
        String parentWindowHandle = driver.getWindowHandle();

        waitVisibilityOfElement(By.xpath("//a[text()='Mobile']")).click();

        waitVisibilityOfElement(By.xpath("//a[text()='IPhone']/parent::h2/parent::div//a[@class='link-compare']")).click();

        Assert.assertEquals(waitVisibilityOfElement(By.cssSelector("li.success-msg span")).getText(), "The product IPhone has been added to comparison list.");
        Assert.assertTrue(waitVisibilityOfElement(By.xpath("//ol[@id='compare-items']//a[text()='IPhone']")).isDisplayed());

        waitVisibilityOfElement(By.xpath("//a[text()='Sony Xperia']/parent::h2/parent::div//a[@class='link-compare']")).click();

        Assert.assertEquals(waitVisibilityOfElement(By.cssSelector("li.success-msg span")).getText(), "The product Sony Xperia has been added to comparison list.");
        Assert.assertTrue(waitVisibilityOfElement(By.xpath("//ol[@id='compare-items']//a[text()='Sony Xperia']")).isDisplayed());

        clickAndSwitch(waitVisibilityOfElement(By.cssSelector("button[title='Compare']")));

        Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/catalog/product_compare/index/");
        Assert.assertEquals(driver.getTitle(), "Products Comparison List - Magento Commerce");
        Assert.assertEquals(waitVisibilityOfElement(By.cssSelector("h1")).getText(), "COMPARE PRODUCTS");

        closeAllAndSwitch(parentWindowHandle);

        Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/mobile.html");
        Assert.assertEquals(driver.getTitle(), "Mobile");

        waitVisibilityOfElement(By.xpath("//a[text()='Clear All']")).click();

        explicitWait.until(ExpectedConditions.alertIsPresent()).accept();

        explicitWait.until(ExpectedConditions.stalenessOf(driver.findElement(By.cssSelector("li.success-msg span"))));
        Assert.assertEquals(driver.findElement(By.cssSelector("li.success-msg span")).getText(), "The comparison list was cleared.");
    }

    @Test
    public void TC_16_Window_Tab() { // there is a not-in-DOM popup displayed randomly
        // driver.get("https://dictionary.cambridge.org/vi/");
        ((JavascriptExecutor) driver).executeScript("window.location = 'https://dictionary.cambridge.org/vi/'");
        sleepInSeconds(10);
        String parentWindowHandle = driver.getWindowHandle();

        List<WebElement> popup = driver.findElements(By.xpath("//div[contains(@class, 'surveyContainer')]"));
        if (!popup.isEmpty() && popup.get(0).isDisplayed()) {
            driver.findElement(By.xpath("//button[contains(@class, 'closeModalBtn')]")).click();
        }

        clickAndSwitch(waitVisibilityOfElement(By.cssSelector("span.cdo-login-button span")));

        waitVisibilityOfElement(By.cssSelector("input[value='Log in']")).click();

        Assert.assertEquals(waitVisibilityOfElement(By.cssSelector("div.gigya-composite-control-textbox>span.gigya-error-msg-active")).getText(), "This field is required");
        Assert.assertEquals(waitVisibilityOfElement(By.cssSelector("div.gigya-composite-control-password>span.gigya-error-msg-active")).getText(), "This field is required");

        closeAllAndSwitch(parentWindowHandle);

        waitVisibilityOfElement(By.cssSelector("input#searchword")).sendKeys("automation");
        waitVisibilityOfElement(By.cssSelector("button.cdo-search-button")).click();

        Assert.assertEquals(waitVisibilityOfElement(By.cssSelector("article#page-content h1")).getText(), "Ý nghĩa của automation trong tiếng Anh");
    }

    @Test
    public void TC_17_Window_Tab() { // close authentication popup before switch
        driver.get("https://courses.dce.harvard.edu/");
        String parentWindowHandle = driver.getWindowHandle();

        waitVisibilityOfElement(By.cssSelector("div.banner__auth")).click();

        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", waitVisibilityOfElement(By.cssSelector("button.sam-wait__close")));

        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#sam-wait")));
        Set<String> allWindowHandles = driver.getWindowHandles();
        for (String windowHandle : allWindowHandles) {
            if (!windowHandle.equals(parentWindowHandle)) {
                driver.switchTo().window(windowHandle);
                driver.manage().window().maximize();
            }
        }

        waitVisibilityOfElement(By.xpath("//div[text()='Log in with HarvardKey']")).click();

        waitVisibilityOfElement(By.cssSelector("button.mdc-button--raised")).click();

        Assert.assertEquals(waitVisibilityOfElement(By.cssSelector("div.login-status-message")).getText(), "Password is a required field. Username is a required field.");

        closeAllAndSwitch(parentWindowHandle);

        waitVisibilityOfElement(By.cssSelector("input#crit-keyword")).sendKeys("law");
        waitVisibilityOfElement(By.cssSelector("button#search-button")).click();

        explicitWait.until(ExpectedConditions.stalenessOf(driver.findElement(By.cssSelector("div.panel--kind-results h2"))));
        Assert.assertEquals(driver.findElement(By.cssSelector("div.panel--kind-results h2")).getText(), "Search Results");
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

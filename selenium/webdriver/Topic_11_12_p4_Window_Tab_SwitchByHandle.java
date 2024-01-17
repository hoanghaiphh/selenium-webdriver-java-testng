package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Date;
import java.util.Set;
import java.util.function.Function;

public class Topic_11_12_p4_Window_Tab_SwitchByHandle {
    WebDriver driver;
    WebDriverWait explicitWait;
    FluentWait<WebDriver> fluentDriver;

    public boolean isElementDisplayed(By locator) { // include wait for visibility
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        boolean status;
        try {
            status = explicitWait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (Exception e) {
            status = false;
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        return status;
        // visible --> status = true
        // not visible but presence in DOM --> run until explicitWait end, throw out TimeOutException --> status = false
        // not presence in DOM --> run until implicitWait end, throw out NoSuchElementException --> status = false
    }

    public WebElement findVisibleElement(By locator) {
        return explicitWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForInvisibility(By locator) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    public void clickOnElement(By locator) {
        explicitWait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public void waitForPageLoad() {
        /*boolean status = false;
        while (!status) {
            try {
                explicitWait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
                status = true;
            } catch (Exception e) {
                System.out.println(new Date());
                System.out.println(e.getMessage());
                System.out.println("Failed to wait for page load! Keep waiting ...\n");
            }
        }*/
        fluentDriver = new FluentWait<WebDriver>(driver);
        fluentDriver.withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofMillis(500))
                .ignoring(NullPointerException.class)
                .until(new Function<WebDriver, Boolean>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
                    }
                });
    }

    public void switchToWindowByClickingElement(By locator) {
        Set<String> beforeHandles = driver.getWindowHandles();
        explicitWait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        explicitWait.until(ExpectedConditions.numberOfWindowsToBe(beforeHandles.size() + 1));
        Set<String> afterHandles = driver.getWindowHandles();
        for (String windowHandle : afterHandles) {
            if (!beforeHandles.contains(windowHandle)) {
                driver.switchTo().window(windowHandle);
                waitForPageLoad();
            }
        }
    }

    public void closeWindowsAndSwitchTo(String parentWindowHandle) {
        Set<String> allWindowHandles = driver.getWindowHandles();
        for (String windowHandle : allWindowHandles) {
            if (!windowHandle.equals(parentWindowHandle)) {
                Set<String> currentWindowHandles = driver.getWindowHandles();
                driver.switchTo().window(windowHandle);
                driver.close();
                explicitWait.until(ExpectedConditions.numberOfWindowsToBe(currentWindowHandles.size() - 1));
            }
        }
        driver.switchTo().window(parentWindowHandle);
    }

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Test
    public void TC_13_Window_Tab() {
        driver.get("https://automationfc.github.io/basic-form/index.html");
        String parentWindowHandle = driver.getWindowHandle();

        // Google
        switchToWindowByClickingElement(By.xpath("//a[text()='GOOGLE']"));

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.google.com.vn/");
        Assert.assertEquals(driver.getTitle(), "Google");

        // Facebook
        driver.switchTo().window(parentWindowHandle);

        switchToWindowByClickingElement(By.xpath("//a[text()='FACEBOOK']"));

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.facebook.com/");
        Assert.assertEquals(driver.getTitle(), "Facebook – log in or sign up");

        // Tiki
        driver.switchTo().window(parentWindowHandle);

        switchToWindowByClickingElement(By.xpath("//a[text()='TIKI']"));

        Assert.assertEquals(driver.getCurrentUrl(), "https://tiki.vn/");
        Assert.assertEquals(driver.getTitle(), "Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");

        // close all except parent window/tab --> switch back to parent window/tab
        closeWindowsAndSwitchTo(parentWindowHandle);

        Assert.assertEquals(driver.getCurrentUrl(), "https://automationfc.github.io/basic-form/index.html");
        Assert.assertEquals(driver.getTitle(), "Selenium WebDriver");
    }

    @Test
    public void TC_14_Window_Tab() {
        driver.get("https://skills.kynaenglish.vn/");
        String parentWindowHandle = driver.getWindowHandle();

        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,document.body.scrollHeight)");

        // Facebook
        switchToWindowByClickingElement(By.cssSelector("div.hotline img[alt='facebook']"));
        findVisibleElement(By.cssSelector("form#login_popup_cta_form"));

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.facebook.com/kyna.vn");
        Assert.assertEquals(driver.getTitle(), "Kyna.vn | Ho Chi Minh City | Facebook");

        // Youtube
        driver.switchTo().window(parentWindowHandle);

        switchToWindowByClickingElement(By.cssSelector("div.hotline img[alt='youtube']"));

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.youtube.com/user/kynavn");
        Assert.assertEquals(driver.getTitle(), "Kyna.vn - YouTube");

        // close all except parent window/tab --> switch back to parent window/tab
        closeWindowsAndSwitchTo(parentWindowHandle);

        Assert.assertEquals(driver.getCurrentUrl(), "https://skills.kynaenglish.vn/");
        Assert.assertEquals(driver.getTitle(), "Kyna.vn - Học online cùng chuyên gia");
    }

    @Test
    public void TC_15_Window_Tab() {
        driver.get("http://live.techpanda.org/");
        String parentWindowHandle = driver.getWindowHandle();

        clickOnElement(By.xpath("//a[text()='Mobile']"));

        clickOnElement(By.xpath("//a[text()='IPhone']/parent::h2/parent::div//a[@class='link-compare']"));
        Assert.assertEquals(findVisibleElement(By.cssSelector("li.success-msg span")).getText(), "The product IPhone has been added to comparison list.");
        Assert.assertTrue(findVisibleElement(By.xpath("//ol[@id='compare-items']//a[text()='IPhone']")).isDisplayed());

        clickOnElement(By.xpath("//a[text()='Sony Xperia']/parent::h2/parent::div//a[@class='link-compare']"));
        Assert.assertEquals(findVisibleElement(By.cssSelector("li.success-msg span")).getText(), "The product Sony Xperia has been added to comparison list.");
        Assert.assertTrue(findVisibleElement(By.xpath("//ol[@id='compare-items']//a[text()='Sony Xperia']")).isDisplayed());

        switchToWindowByClickingElement(By.cssSelector("button[title='Compare']"));

        Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/catalog/product_compare/index/");
        Assert.assertEquals(driver.getTitle(), "Products Comparison List - Magento Commerce");
        Assert.assertEquals(findVisibleElement(By.cssSelector("h1")).getText(), "COMPARE PRODUCTS");

        closeWindowsAndSwitchTo(parentWindowHandle);

        Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/mobile.html");
        Assert.assertEquals(driver.getTitle(), "Mobile");

        clickOnElement(By.xpath("//a[text()='Clear All']"));
        explicitWait.until(ExpectedConditions.alertIsPresent()).accept();
        explicitWait.until(ExpectedConditions.stalenessOf(driver.findElement(By.cssSelector("li.success-msg span"))));
        Assert.assertEquals(findVisibleElement(By.cssSelector("li.success-msg span")).getText(), "The comparison list was cleared.");
    }

    @Test
    public void TC_16_Window_Tab() { // there is a not-in-DOM popup displayed randomly
        driver.get("https://dictionary.cambridge.org/vi/");
        // ((JavascriptExecutor) driver).executeScript("window.location = 'https://dictionary.cambridge.org/vi/'");
        String parentWindowHandle = driver.getWindowHandle();

        while (isElementDisplayed(By.xpath("//div[contains(@class, 'surveyContainer')]"))) {
            clickOnElement(By.xpath("//button[contains(@class, 'closeModalBtn')]"));
        }

        switchToWindowByClickingElement(By.cssSelector("span.cdo-login-button span"));

        findVisibleElement(By.cssSelector("input[value='Log in']")).click();
        Assert.assertEquals(findVisibleElement(By.cssSelector("div.gigya-composite-control-textbox>span.gigya-error-msg-active")).getText(), "This field is required");
        Assert.assertEquals(findVisibleElement(By.cssSelector("div.gigya-composite-control-password>span.gigya-error-msg-active")).getText(), "This field is required");

        closeWindowsAndSwitchTo(parentWindowHandle);

        findVisibleElement(By.cssSelector("input#searchword")).sendKeys("automation");
        findVisibleElement(By.cssSelector("button.cdo-search-button")).click();
        Assert.assertEquals(findVisibleElement(By.cssSelector("article#page-content h1")).getText(), "Ý nghĩa của automation trong tiếng Anh");
    }

    @Test
    public void TC_17_Window_Tab() { // close authentication popup before switch
        driver.get("https://courses.dce.harvard.edu/");
        String parentWindowHandle = driver.getWindowHandle();

        clickOnElement(By.cssSelector("div.banner__auth"));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", driver.findElement(By.cssSelector("button.sam-wait__close")));
        waitForInvisibility(By.cssSelector("div#sam-wait"));

        Set<String> allWindowHandles = driver.getWindowHandles();
        for (String windowHandle : allWindowHandles) {
            if (!windowHandle.equals(parentWindowHandle)) {
                driver.switchTo().window(windowHandle);
                driver.manage().window().maximize();
            }
        }

        clickOnElement(By.xpath("//div[text()='Log in with HarvardKey']"));
        clickOnElement(By.cssSelector("button.mdc-button--raised"));
        String errMsg = findVisibleElement(By.cssSelector("div.login-status-message")).getText();
        Assert.assertTrue(errMsg.contains("Password is a required field.") && errMsg.contains("Username is a required field."));

        closeWindowsAndSwitchTo(parentWindowHandle);

        findVisibleElement(By.cssSelector("input#crit-keyword")).sendKeys("law");
        clickOnElement(By.cssSelector("button#search-button"));
        waitForInvisibility(By.cssSelector("div.panel--kind-results.panel--busy"));
        Assert.assertEquals(findVisibleElement(By.cssSelector("div.panel--kind-results h2")).getText(), "Search Results");
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

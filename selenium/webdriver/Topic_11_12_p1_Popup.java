package webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Topic_11_12_p1_Popup {
    WebDriver driver;
    WebDriverWait explicitWait;

    public void closePopup(By locatorOfCloseBtn) {
        while (isElementDisplayed(locatorOfCloseBtn)) {
            clickOnElement(locatorOfCloseBtn);
        }
    }

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

    public boolean isElementInvisible(By locator) { // include wait for invisibility
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        boolean status;
        try {
            status = explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (Exception e) {
            status = false;
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        return status;
        // not presence in DOM --> run until implicitWait end --> status = true
        // presence in DOM but not visible --> status = true
        // visible --> run until explicitWait end, throw out TimeOutException --> status = false
    }

    public void clickOnElement(By locator) {
        explicitWait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public String getTextOfElement(By locator) {
        return explicitWait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    public WebElement findVisibleElement(By locator) {
        return explicitWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Test
    public void TC_01_Fixed_Popup_In_DOM() {
        driver.get("https://ngoaingu24h.vn/");

        clickOnElement(By.cssSelector("button.login_"));
        Assert.assertTrue(isElementDisplayed(By.cssSelector("div#modal-login-v1[style] div.modal-content")));

        findVisibleElement(By.cssSelector("div#modal-login-v1[style] input#account-input")).sendKeys("automation fc");
        findVisibleElement(By.cssSelector("div#modal-login-v1[style] input#password-input")).sendKeys("automation fc");
        clickOnElement(By.cssSelector("div#modal-login-v1[style] button[data-text='Đăng nhập']"));
        Assert.assertEquals(getTextOfElement(By.cssSelector("div#modal-login-v1[style] div.error-login-panel")), "Tài khoản không tồn tại!");

        clickOnElement(By.cssSelector("div#modal-login-v1[style] button.close"));
        Assert.assertTrue(isElementInvisible(By.cssSelector("div#modal-login-v1[style] div.modal-content")));
    }

    @Test
    public void TC_02_Fixed_Popup_In_DOM() {
        driver.get("https://skills.kynaenglish.vn/");

        clickOnElement(By.cssSelector("a.login-btn"));
        Assert.assertTrue(isElementDisplayed(By.cssSelector("div#k-popup-account-login-mb div.modal-content")));

        // fb-login iframe completely loaded --> popup completely loaded --> function of submit btn able to work
        findVisibleElement(By.cssSelector("div.fb-login-button iframe"));

        findVisibleElement(By.cssSelector("input#user-login")).sendKeys("automation@gmail.com");
        findVisibleElement(By.cssSelector("input#user-password")).sendKeys("123456");
        clickOnElement(By.cssSelector("div.button-submit")); // need to wait as above
        Assert.assertEquals(getTextOfElement(By.cssSelector("div#password-form-login-message")), "Sai tên đăng nhập hoặc mật khẩu");

        clickOnElement(By.cssSelector("button.k-popup-account-close"));
        Assert.assertTrue(isElementInvisible(By.cssSelector("div#k-popup-account-login-mb div.modal-content")));
    }

    @Test
    public void TC_03_Fixed_Popup_notIn_DOM() {
        driver.get("https://tiki.vn/");

        clickOnElement(By.xpath("//span[text()='Tài khoản']"));
        Assert.assertTrue(isElementDisplayed(By.cssSelector("div.ReactModal__Content>div")));

        clickOnElement(By.cssSelector("p.login-with-email"));
        clickOnElement(By.xpath("//button[text()='Đăng nhập']"));
        Assert.assertEquals(getTextOfElement(By.xpath("//span[@class='error-mess'][1]")), "Email không được để trống");
        Assert.assertEquals(getTextOfElement(By.xpath("//span[@class='error-mess'][2]")), "Mật khẩu không được để trống");
        // TEXT: should verify by .assertEquals(element.getText(),"text") / should not verify by .assertTrue(element.isDisplayed)

        clickOnElement(By.cssSelector("button.btn-close"));
        Assert.assertTrue(isElementInvisible(By.cssSelector("div.ReactModal__Content>div")));
    }

    @Test
    public void TC_04_Fixed_Popup_notIn_DOM() {
        driver.get("https://www.facebook.com/");

        clickOnElement(By.xpath("//a[@data-testid='open-registration-form-button']"));
        Assert.assertTrue(isElementDisplayed(By.xpath("//div[text()='Sign Up']/parent::div/parent::div")));

        clickOnElement(By.xpath("//div[text()='Sign Up']/parent::div/parent::div/img"));
        Assert.assertTrue(isElementInvisible(By.xpath("//div[text()='Sign Up']/parent::div/parent::div")));
    }

    @Test
    public void TC_05_Random_Popup_notIn_DOM() throws InterruptedException {
        // this case: before popup appeared --> not in DOM | after popup appeared --> in DOM | after close popup --> in DOM
        // popup NOT appeared immediately after page load

        // driver.get("https://www.javacodegeeks.com/"); --> PageLoadTimeOut after 3 minutes
        ((JavascriptExecutor) driver).executeScript("window.location = 'https://www.javacodegeeks.com/'");
        Thread.sleep(10000); // --> not necessary, just use for a chance popup displayed

        closePopup(By.cssSelector("div.lepopup-fadeIn a"));

        findVisibleElement(By.cssSelector("input#search-input")).sendKeys("Agile Testing Explained");
        clickOnElement(By.cssSelector("button#search-submit"));
        Assert.assertEquals(getTextOfElement(By.cssSelector("nav#breadcrumb>span")), "Search Results for: Agile Testing Explained");
    }

    /*public WebElement closePopupFindElement(By locator) { // override findElement
        if (driver.findElement(By.cssSelector("")).isDisplayed()) {
            driver.findElement(By.cssSelector("")).click();
        }
        return driver.findElement(locator);
    }*/
    @Test
    public void TC_06a_Random_Popup_In_DOM() {
        // this case: popup always in DOM
        // popup NOT appeared immediately after page load

        driver.get("https://vnk.edu.vn/");

        closePopup(By.cssSelector("svg.tcb-icon"));

        clickOnElement(By.xpath("//button[text()='Danh sách khóa học']"));
        Assert.assertEquals(getTextOfElement(By.cssSelector("div.title-content h1")), "Lịch Khai Giảng");
    }

    @Test
    public void TC_06b_Random_Popup_In_DOM() {
        // this case: popup always in DOM
        // popup NOT appeared immediately after page load

        driver.get("https://vnk.edu.vn/");

        // waiting for pop up appeared --> close pop up
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(60));
        clickOnElement(By.cssSelector("svg.tcb-icon"));
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));

        clickOnElement(By.xpath("//button[text()='Danh sách khóa học']"));
        Assert.assertEquals(getTextOfElement(By.cssSelector("div.title-content h1")), "Lịch Khai Giảng");
    }

    @Test
    public void TC_06c_Random_Popup_In_DOM() {
        // this case: popup always in DOM
        // popup appeared immediately after page load

        driver.get("http://www.kmplayer.com/");

        closePopup(By.cssSelector("div.close"));

        clickOnElement(By.xpath("//a[text()='PC']"));
        clickOnElement(By.xpath("//a[text()='KMPlayer']"));
        Assert.assertEquals(getTextOfElement(By.cssSelector("div.sub>h1")), "KMPlayer - Video Player for PC");
    }

    @Test
    public void TC_07_Random_Popup_notIn_DOM() {
        // this case: before popup appeared --> in DOM | after popup appeared --> in DOM | after close popup --> not in DOM
        // popup appeared immediately after page load

        driver.get("https://dehieu.vn/");

        closePopup(By.cssSelector("button#close-popup"));

        clickOnElement(By.xpath("//a[text()='Đăng ký']"));
        Assert.assertEquals(getTextOfElement(By.cssSelector("div.sign-up-form>h2")), "Đăng ký tài khoản");
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

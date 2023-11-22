package exercise;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class Exercise_11_12_p1_Popup {
    WebDriver driver;

    public void sleepInSeconds (long timeInSecond) {
        try {
            Thread.sleep(timeInSecond*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_Fixed_Popup_In_DOM() {
        driver.get("https://ngoaingu24h.vn/");

        driver.findElement(By.cssSelector("button.login_")).click();
        sleepInSeconds(1);

        Assert.assertTrue(driver.findElement(By.cssSelector("div#modal-login-v1[style] div.modal-content")).isDisplayed());

        driver.findElement(By.cssSelector("div#modal-login-v1[style] input#account-input")).sendKeys("automation fc");
        driver.findElement(By.cssSelector("div#modal-login-v1[style] input#password-input")).sendKeys("automation fc");
        driver.findElement(By.cssSelector("div#modal-login-v1[style] button[data-text='Đăng nhập']")).click();
        sleepInSeconds(1);

        Assert.assertEquals(driver.findElement(By.cssSelector("div#modal-login-v1[style] div.error-login-panel")).getText(), "Tài khoản không tồn tại!");

        driver.findElement(By.cssSelector("div#modal-login-v1[style] button.close")).click();
        sleepInSeconds(1);

        Assert.assertFalse(driver.findElement(By.cssSelector("div#modal-login-v1[style] div.modal-content")).isDisplayed());
    }

    @Test
    public void TC_02_Fixed_Popup_In_DOM() {
        driver.get("https://skills.kynaenglish.vn/");

        driver.findElement(By.cssSelector("a.login-btn")).click();
        sleepInSeconds(1);

        Assert.assertTrue(driver.findElement(By.cssSelector("div#k-popup-account-login-mb div.modal-content")).isDisplayed());

        driver.findElement(By.cssSelector("input#user-login")).sendKeys("automation@gmail.com");
        driver.findElement(By.cssSelector("input#user-password")).sendKeys("123456");
        driver.findElement(By.cssSelector("button#btn-submit-login")).click();
        sleepInSeconds(1);

        Assert.assertEquals(driver.findElement(By.cssSelector("div#password-form-login-message")).getText(), "Sai tên đăng nhập hoặc mật khẩu");

        driver.findElement(By.cssSelector("button.k-popup-account-close")).click();
        sleepInSeconds(1);

        Assert.assertFalse(driver.findElement(By.cssSelector("div#k-popup-account-login-mb div.modal-content")).isDisplayed());
    }

    @Test
    public void TC_03_Fixed_Popup_notIn_DOM() {
        driver.get("https://tiki.vn/");

        driver.findElement(By.xpath("//span[text()='Tài khoản']")).click();
        sleepInSeconds(1);

        Assert.assertTrue(driver.findElement(By.cssSelector("div.ReactModal__Content>div")).isDisplayed());

        driver.findElement(By.cssSelector("p.login-with-email")).click();
        sleepInSeconds(1);

        driver.findElement(By.xpath("//button[text()='Đăng nhập']")).click();
        sleepInSeconds(1);

        Assert.assertEquals(driver.findElement(By.xpath("//span[@class='error-mess'][1]")).getText(), "Email không được để trống");
        Assert.assertEquals(driver.findElement(By.xpath("//span[@class='error-mess'][2]")).getText(), "Mật khẩu không được để trống");
        // TEXT: should verify by .assertEquals(element.getText(),"text") / should not verify by .assertTrue(element.isDisplayed)

        driver.findElement(By.cssSelector("button.btn-close")).click();
        sleepInSeconds(1);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5)); // reduce time for running TC
        Assert.assertEquals(driver.findElements(By.cssSelector("div.ReactModal__Content>div")).size(), 0);
    }

    @Test
    public void TC_04_Fixed_Popup_notIn_DOM() {
        driver.get("https://www.facebook.com/");

        driver.findElement(By.xpath("//a[@data-testid='open-registration-form-button']")).click();
        sleepInSeconds(1);

        Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Sign Up']/parent::div/parent::div")).isDisplayed());

        driver.findElement(By.xpath("//div[text()='Sign Up']/parent::div/parent::div/img")).click();
        sleepInSeconds(1);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        Assert.assertEquals(driver.findElements(By.xpath("//div[text()='Sign Up']/parent::div/parent::div")).size(), 0);
    }

    @Test
    public void TC_05_Random_Popup_notIn_DOM() {
        driver.get("https://www.javacodegeeks.com/");

        // this case: before popup appeared --> not in DOM | after popup appeared --> in DOM | after close popup --> in DOM
        // popup NOT appeared immediately after page load
        List<WebElement> popup = driver.findElements(By.cssSelector("div.lepopup-element-rectangle.lepopup-animated"));
        if (!popup.isEmpty() && popup.get(0).isDisplayed()) {
            driver.findElement(By.cssSelector("div.lepopup-fadeIn a")).click();
            sleepInSeconds(1);
        }

        driver.findElement(By.cssSelector("input#search-input")).sendKeys("Agile Testing Explained");
        driver.findElement(By.cssSelector("button#search-submit")).click();
        sleepInSeconds(1);

        Assert.assertEquals(driver.findElements(By.cssSelector("h2.post-title")).get(0).getText(), "Agile Testing Explained");
    }

    /*public WebElement closePopupFindElement(By locator) { // override findElement
        if (driver.findElement(By.cssSelector("div.tve-leads-conversion-object")).isDisplayed()) {
            driver.findElement(By.cssSelector("svg.tcb-icon")).click();
            sleepInSeconds(1);
        }
        return driver.findElement(locator);
    }*/
    @Test
    public void TC_06a_Random_Popup_In_DOM() {
        driver.get("https://vnk.edu.vn/");

        // this case: popup always in DOM
        // popup NOT appeared immediately after page load
        if (driver.findElement(By.cssSelector("div.tve-leads-conversion-object")).isDisplayed()) {
            driver.findElement(By.cssSelector("svg.tcb-icon")).click();
            sleepInSeconds(1);
        }

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.xpath("//button[text()='Danh sách khóa học']")));
        sleepInSeconds(1);

        Assert.assertEquals(driver.findElement(By.cssSelector("div.title-content h1")).getText(), "Lịch Khai Giảng");
    }

    @Test
    public void TC_06b_Random_Popup_In_DOM() {
        driver.get("http://www.kmplayer.com/");
        sleepInSeconds(1);

        // this case: popup always in DOM
        // popup appeared immediately after page load
        if (driver.findElement(By.cssSelector("div.pop-container")).isDisplayed()) {
            driver.findElement(By.cssSelector("div.close")).click();
            sleepInSeconds(1);
        }

        driver.findElement(By.xpath("//a[text()='PC']")).click();
        driver.findElement(By.xpath("//a[text()='KMPlayer']")).click();
        sleepInSeconds(1);

        Assert.assertEquals(driver.findElement(By.cssSelector("div.sub>h1")).getText(), "KMPlayer - Video Player for PC");
    }

    @Test
    public void TC_07_Random_Popup_notIn_DOM() {
        driver.get("https://dehieu.vn/");
        sleepInSeconds(1);

        // this case: before popup appeared --> in DOM | after popup appeared --> in DOM | after close popup --> not in DOM
        // popup appeared immediately after page load
        if (driver.findElement(By.cssSelector("div.popup-content")).isDisplayed()) {
            driver.findElement(By.cssSelector("button#close-popup")).click();
            sleepInSeconds(1);
        }

        driver.findElement(By.xpath("//a[text()='Đăng ký']")).click();
        sleepInSeconds(1);

        Assert.assertEquals(driver.findElement(By.cssSelector("div.sign-up-form>h2")).getText(), "Đăng ký tài khoản");
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

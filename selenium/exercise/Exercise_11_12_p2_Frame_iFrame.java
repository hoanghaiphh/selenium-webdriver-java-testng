package exercise;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Exercise_11_12_p2_Frame_iFrame {
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
    public void TC_08_iFrame() {
        driver.get("https://skills.kynaenglish.vn/");

        driver.switchTo().frame(driver.findElement(By.cssSelector("div.face-content>iframe")));

        Assert.assertTrue(driver.findElement(By.cssSelector("html#facebook")).isDisplayed());
        Assert.assertEquals(driver.findElement(By.xpath("//a[text()='Kyna.vn']/parent::div/following-sibling::div")).getText(), "168K followers");

        driver.switchTo().defaultContent();

        driver.switchTo().frame("cs_chat_iframe");

        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", driver.findElement(By.cssSelector("div.button_text")));
        sleepInSeconds(2);

        driver.findElement(By.cssSelector("input.input_name")).sendKeys("Automation FC");
        driver.findElement(By.cssSelector("input.input_phone")).sendKeys("0905123456");
        Select dichVuHoTro = new Select(driver.findElement(By.cssSelector("select#serviceSelect")));
        dichVuHoTro.selectByVisibleText("TƯ VẤN TUYỂN SINH");
        driver.findElement(By.cssSelector("textarea.input_textarea")).sendKeys("Selenium WebDriver");
        sleepInSeconds(2);

        driver.switchTo().defaultContent();

        driver.findElement(By.cssSelector("input#live-search-bar")).sendKeys("Excel");
        driver.findElement(By.cssSelector("button.search-button")).click();
        sleepInSeconds(2);

        Assert.assertEquals(driver.findElement(By.cssSelector("ul.breadcrumb>li.active")).getText(), "Danh sách khóa học");
        Assert.assertTrue(driver.findElement(By.cssSelector("main span.menu-heading")).getText().contains("Kết quả tìm kiếm từ khóa 'Excel'"));
    }

    @Test
    public void TC_09_Frame() {
        driver.get("https://netbanking.hdfcbank.com/netbanking/");

        driver.switchTo().frame("login_page");

        driver.findElement(By.cssSelector("input.form-control")).sendKeys("123456");
        driver.findElement(By.cssSelector("a.login-btn")).click();
        sleepInSeconds(3);

        Assert.assertTrue(driver.findElement(By.cssSelector("input#fldPasswordDispId")).isDisplayed());
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

package exercise;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Exercise_11_12_p3_Frame_iFrame {
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

    // Page A contains ỉFrame B, iFrame B contains iFrame C:
    // A --> B, B --> C: .switchTo().frame(Index/ Id/ Name/ WebElement)
    // B --> A: .switchTo().defaultContent()
    // C --> B: .switchTo().parentFrame()

    @Test
    public void TC_10_iFrame() {
        driver.get("https://skills.kynaenglish.vn/");

        driver.switchTo().frame(driver.findElement(By.cssSelector("div.face-content>iframe")));

        Assert.assertTrue(driver.findElement(By.cssSelector("html#facebook")).isDisplayed());
        Assert.assertEquals(driver.findElement(By.xpath("//a[text()='Kyna.vn']/parent::div/following-sibling::div")).getText(), "169K followers");

        driver.switchTo().defaultContent();

        driver.switchTo().frame("cs_chat_iframe");

        driver.findElement(By.cssSelector("div.button_bar")).click();
        sleepInSeconds(2);

        driver.findElement(By.cssSelector("input.input_name")).sendKeys("Automation FC");
        driver.findElement(By.cssSelector("input.input_phone")).sendKeys("0905123456");
        new Select(driver.findElement(By.cssSelector("select#serviceSelect"))).selectByVisibleText("TƯ VẤN TUYỂN SINH");
        driver.findElement(By.cssSelector("textarea.input_textarea")).sendKeys("Selenium WebDriver");
        sleepInSeconds(2);

        driver.switchTo().defaultContent();

        driver.findElement(By.cssSelector("input#live-search-bar")).sendKeys("Excel");
        driver.findElement(By.cssSelector("button.search-button")).click();
        sleepInSeconds(2);

        Assert.assertEquals(driver.findElement(By.cssSelector("ul.breadcrumb>li.active")).getText(), "Danh sách khóa học");
        Assert.assertTrue(driver.findElement(By.cssSelector("main span.menu-heading")).getText().contains("Kết quả tìm kiếm từ khóa 'Excel'"));

        String[] result = driver.findElement(By.cssSelector("main span.menu-heading")).getText().split(" ");
        Assert.assertEquals(driver.findElements(By.cssSelector("div.k-box-card-wrap")).size(), Integer.parseInt(result[0]));
    }

    @Test
    public void TC_11_iFrame() {
        driver.get("https://www.formsite.com/templates/education/campus-safety-survey/");

        driver.findElement(By.cssSelector("div#imageTemplateContainer")).click();
        sleepInSeconds(3);

        WebElement iFrame = driver.findElement(By.cssSelector("div#formTemplateContainer>iframe"));
        Assert.assertTrue(iFrame.isDisplayed());

        driver.switchTo().frame(iFrame);

        new Select(driver.findElement(By.xpath("//label[contains(text(),'Year')]/following-sibling::select"))).selectByVisibleText("Senior");
        new Select(driver.findElement(By.xpath("//label[contains(text(),'Residence')]/following-sibling::select"))).selectByVisibleText("Off Campus");
        driver.findElement(By.xpath("//label[text()='Male']")).click();
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", driver.findElement(By.cssSelector("input.submit_button")));
        sleepInSeconds(3);

        Assert.assertEquals(driver.findElement(By.cssSelector("div.form_table>div.invalid_message")).getText(), "Please review the form and correct the highlighted items.");
        Assert.assertEquals(driver.findElements(By.xpath("//div[text()='Response required']")).size(), 16);

        driver.switchTo().defaultContent();

        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", driver.findElement(By.cssSelector("nav.header--desktop-floater a.menu-item-login")));
        sleepInSeconds(1);
        driver.findElement(By.cssSelector("button#login")).click();
        sleepInSeconds(1);

        Assert.assertEquals(driver.findElement(By.cssSelector("div#message-error")).getText(), "Username and password are both required.");
    }

    @Test
    public void TC_12_Frame() {
        driver.get("https://netbanking.hdfcbank.com/netbanking/");

        driver.switchTo().frame("login_page");

        driver.findElement(By.cssSelector("input[name='fldLoginUserId']")).sendKeys("automation_fc");
        driver.findElement(By.cssSelector("a.login-btn")).click();
        sleepInSeconds(3);

        Assert.assertTrue(driver.findElement(By.cssSelector("input#keyboard")).isDisplayed());
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

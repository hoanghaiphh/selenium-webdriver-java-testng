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

import java.io.File;
import java.time.Duration;
import java.util.List;

public class Topic_15_16_WebDriver_Wait {
    WebDriver driver;
    WebDriverWait explicitWait;

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
        driver.manage().window().maximize();
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Test
    public void TC_02_Implicit_Wait() {
        driver.get("https://automationfc.github.io/dynamic-loading/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        driver.findElement(By.cssSelector("div#start>button")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector("div#finish>h4")).getText(), "Hello World!");
        // set implicitWait < 5s --> failed

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
    }

    @Test
    public void TC_03_Static_Wait() {
        driver.get("https://automationfc.github.io/dynamic-loading/");

        driver.findElement(By.cssSelector("div#start>button")).click();

        sleepInSeconds(5);

        Assert.assertTrue(driver.findElement(By.cssSelector("div#finish>h4")).isDisplayed());
        Assert.assertEquals(driver.findElement(By.cssSelector("div#finish>h4")).getText(), "Hello World!");
    }

    @Test
    public void TC_04_Explicit_Wait() {
        driver.get("https://automationfc.github.io/dynamic-loading/");

        driver.findElement(By.cssSelector("div#start>button")).click();

        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#loading>img")));

        Assert.assertEquals(driver.findElement(By.cssSelector("div#finish>h4")).getText(), "Hello World!");
        // set implicitWait = 3s --> failed
        // set implicitWait = 5s --> passed
        // set implicitWait = 15s --> passed
    }

    @Test
    public void TC_05_Explicit_Wait() {
        driver.get("https://automationfc.github.io/dynamic-loading/");

        driver.findElement(By.cssSelector("div#start>button")).click();

        Assert.assertEquals(explicitWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div#finish>h4"))).getText(), "Hello World!");
        // set implicitWait = 3s --> failed
        // set implicitWait = 5s --> passed
        // set implicitWait = 15s --> passed
    }

    @Test
    public void TC_06_Explicit_Wait() {
        driver.get("https://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");

        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table.rcMainTable>tbody")));

        Assert.assertEquals(driver.findElement(By.cssSelector("div.datesContainer span")).getText(), "No Selected Dates to display.");

        driver.findElement(By.xpath("//a[text()='20']")).click();

        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div:not([style='display:none;'])>div.raDiv")));

        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class,'rcSelected')]//a[text()='20']")));

        Assert.assertEquals(driver.findElement(By.cssSelector("div.datesContainer span")).getText(), "Wednesday, December 20, 2023");
    }

    @Test
    public void TC_07_Explicit_Wait() {
        driver.get("https://gofile.io/?t=uploadFiles");

        String fileDir = System.getProperty("user.dir") + File.separator + "uploadFiles" + File.separator;
        String[] filesToUpload = new String[]{"avatar.jpg", "jQuery.txt", "topic13.png", "santa.ico", "snow.png", "large.jpg"};
        String allFilePath = fileDir + filesToUpload[0];
        for (int i = 1; i < filesToUpload.length; i++) {
            allFilePath = allFilePath + "\n" + (fileDir + filesToUpload[i]);
        }

        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[text()='Upload Files']"))).click();

        explicitWait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("input#filesUploadInput"))).sendKeys(allFilePath);

        Assert.assertEquals(explicitWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.mainUploadSuccess div.alert"))).getText(), "Your files have been successfully uploaded");

        driver.findElement(By.cssSelector("div.mainUploadSuccessLink a")).click();

        for (String fileName : filesToUpload) {
            Assert.assertTrue(explicitWait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//span[text()='" + fileName + "']/parent::a/parent::div/following-sibling::div/a/button/span[text()='Download']"))).isDisplayed());
            if (fileName.contains(".jpg") || fileName.contains(".png") || fileName.contains(".ico")) {
                Assert.assertTrue(explicitWait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//span[text()='" + fileName + "']/parent::a/parent::div/following-sibling::div/button/span[text()='Play']"))).isDisplayed());
            }
        }
    }

    @Test
    public void TC_10_Page_Ready() {
        driver.get("https://admin-demo.nopcommerce.com");

        driver.findElement(By.cssSelector("input#Email")).clear();
        driver.findElement(By.cssSelector("input#Email")).sendKeys("admin@yourstore.com");
        driver.findElement(By.cssSelector("input#Password")).clear();
        driver.findElement(By.cssSelector("input#Password")).sendKeys("admin");
        driver.findElement(By.cssSelector("button.login-button")).click();

        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#ajaxBusy")));

        driver.findElement(By.xpath("//a[text()='Logout']")).click();

        explicitWait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));

        Assert.assertEquals(driver.getTitle(), "Your store. Login");
    }

    @Test
    public void TC_11_Page_Ready() {
        driver.get("https://blog.testproject.io/");

        driver.findElement(By.cssSelector("section.widget_search input.search-field")).sendKeys("Selenium");
        driver.findElement(By.cssSelector("section.widget_search span.glass")).click();

        Assert.assertEquals(explicitWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("h2.page-title"))).getText(), "Search Results for: \"Selenium\":");

        List<WebElement> searchResults = driver.findElements(By.cssSelector("h3.post-title>a"));
        for (int i = 0; i < searchResults.size(); i++) {
            Assert.assertTrue(searchResults.get(i).getText().contains("Selenium"));
        }
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

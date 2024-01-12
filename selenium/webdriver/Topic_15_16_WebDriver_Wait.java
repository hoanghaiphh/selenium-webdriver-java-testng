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

    public void clickOnElement(By locator) {
        explicitWait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public void waitForInvisibility(By locator) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    public WebElement findVisibleElement(By locator) {
        return explicitWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void TC_02_Implicit_Wait() {
        driver.get("https://automationfc.github.io/dynamic-loading/");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        // set implicitWait < 5s --> fail

        driver.findElement(By.cssSelector("div#start>button")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector("div#finish>h4")).getText(), "Hello World!");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
    }

    @Test
    public void TC_03_Static_Wait() {
        driver.get("https://automationfc.github.io/dynamic-loading/");

        driver.findElement(By.cssSelector("div#start>button")).click();

        sleepInSeconds(5);
        // set sleep < 5s --> fail

        Assert.assertTrue(driver.findElement(By.cssSelector("div#finish>h4")).isDisplayed());
        Assert.assertEquals(driver.findElement(By.cssSelector("div#finish>h4")).getText(), "Hello World!");
    }

    @Test
    public void TC_04_Explicit_Wait() {
        driver.get("https://automationfc.github.io/dynamic-loading/");

        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(5));

        clickOnElement(By.cssSelector("div#start>button"));

        waitForInvisibility(By.cssSelector("div#loading>img"));

        Assert.assertEquals(driver.findElement(By.cssSelector("div#finish>h4")).getText(), "Hello World!");
    }

    @Test
    public void TC_05_Explicit_Wait() {
        driver.get("https://automationfc.github.io/dynamic-loading/");

        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(5));

        clickOnElement(By.cssSelector("div#start>button"));

        Assert.assertEquals(findVisibleElement(By.cssSelector("div#finish>h4")).getText(), "Hello World!");
    }

    @Test
    public void TC_06_Explicit_Wait() {
        driver.get("https://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));

        findVisibleElement(By.cssSelector("table.rcMainTable>tbody"));

        Assert.assertEquals(findVisibleElement(By.cssSelector("div.datesContainer span")).getText(), "No Selected Dates to display.");

        clickOnElement(By.xpath("//a[text()='20']"));

        waitForInvisibility(By.cssSelector("div:not([style='display:none;'])>div.raDiv"));

        // attribute class will be: class="rcSelected rcHover" --> USE: contains(@class, 'rcSelected') / NOT USE: @class='rcSelected'
        findVisibleElement(By.xpath("//td[contains(@class,'rcSelected')]/a[text()='20']"));

        Assert.assertEquals(findVisibleElement(By.cssSelector("div.datesContainer span")).getText(), "Saturday, January 20, 2024");
    }

    @Test
    public void TC_07_Explicit_Wait() {
        driver.get("https://gofile.io/?t=uploadFiles");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));

        String fileDir = System.getProperty("user.dir") + File.separator + "uploadFiles" + File.separator;
        String[] filesToUpload = new String[]{"avatar.jpg", "jQuery.txt", "topic13.png", "santa.ico", "snow.png", "large.jpg"};
        String allFilePath = fileDir + filesToUpload[0];
        for (int i = 1; i < filesToUpload.length; i++) {
            allFilePath = allFilePath + "\n" + (fileDir + filesToUpload[i]);
        }

        // wait for ajax loading invisible OR wait for element of next step visible...

        clickOnElement(By.xpath("//button[text()='Upload Files']"));

        driver.findElement(By.cssSelector("input#filesUploadInput")).sendKeys(allFilePath);

        Assert.assertEquals(findVisibleElement(By.cssSelector("div.mainUploadSuccess div.alert")).getText(), "Your files have been successfully uploaded");

        clickOnElement(By.cssSelector("div.mainUploadSuccessLink a"));

        for (String fileName : filesToUpload) {
            Assert.assertTrue(findVisibleElement(By.xpath("//span[text()='" + fileName + "']/parent::a/parent::div/following-sibling::div/a/button/span[text()='Download']")).isDisplayed());
            if (fileName.contains(".jpg") || fileName.contains(".png") || fileName.contains(".ico")) {
                Assert.assertTrue(findVisibleElement(By.xpath("//span[text()='" + fileName + "']/parent::a/parent::div/following-sibling::div/button/span[text()='Play']")).isDisplayed());
            }
        }
    }

    @Test
    public void TC_10_Page_Ready() {
        driver.get("https://admin-demo.nopcommerce.com");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));

        findVisibleElement(By.cssSelector("input#Email")).clear();
        findVisibleElement(By.cssSelector("input#Email")).sendKeys("admin@yourstore.com");
        findVisibleElement(By.cssSelector("input#Password")).clear();
        findVisibleElement(By.cssSelector("input#Password")).sendKeys("admin");
        clickOnElement(By.cssSelector("button.login-button"));

        waitForInvisibility(By.cssSelector("div#ajaxBusy"));

        clickOnElement(By.xpath("//a[text()='Logout']"));

        explicitWait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));

        Assert.assertEquals(driver.getTitle(), "Your store. Login");
    }

    @Test
    public void TC_11_Page_Ready() {
        driver.get("https://blog.testproject.io/");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));

        findVisibleElement(By.cssSelector("section.widget_search input.search-field")).sendKeys("Selenium");
        clickOnElement(By.cssSelector("section.widget_search span.glass"));

        Assert.assertEquals(findVisibleElement(By.cssSelector("h2.page-title")).getText(), "Search Results for: \"Selenium\":");

        List<WebElement> searchResults = explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("h3.post-title>a")));
        for (WebElement searchResult : searchResults) {
            Assert.assertTrue(searchResult.getText().contains("Selenium"));
        }
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

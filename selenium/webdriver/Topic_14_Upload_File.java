package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Topic_14_Upload_File {
    WebDriver driver;
    WebDriverWait explicitWait;
    String fileDir = System.getProperty("user.dir") + File.separator + "uploadFiles" + File.separator;
    String[] filesToUpload = new String[]{"avatar.jpg", "jQuery.txt", "topic13.png", "santa.ico", "snow.png", "large.jpg"};

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

    public WebElement findVisibleElement(By locator) {
        return explicitWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void clickOnElement(By locator) {
        explicitWait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public void openBrowser(WebDriver webDriver) {
        driver = webDriver;
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.get("https://blueimp.github.io/jQuery-File-Upload/");
    }

    @Test
    public void TC_01_Firefox() {
        openBrowser(new FirefoxDriver());

        // browse files
        for (String fileName : filesToUpload) {
            driver.findElement(By.cssSelector("input[type='file']")).sendKeys(fileDir + fileName);
            Assert.assertTrue(findVisibleElement(By.xpath("//p[text()='" + fileName + "']")).isDisplayed());
        }

        // verify that err.msg will be displayed below the file which is not allowed to upload --> cancel not-allowed files
        List<String> allowedFiles = new ArrayList<String>();
        for (String fileName : filesToUpload) {
            if (findVisibleElement(By.xpath("//p[text()='" + fileName + "']/parent::td/parent::tr//button[@class='btn btn-primary start']")).isEnabled()) {
                allowedFiles.add(fileName);
            } else {
                Assert.assertTrue(findVisibleElement(By.xpath("//p[text()='" + fileName +"']/following-sibling::strong")).isDisplayed());
                clickOnElement(By.xpath("//p[text()='" + fileName + "']/parent::td/parent::tr//button[@class='btn btn-warning cancel']"));
                Assert.assertTrue(isElementInvisible(By.xpath("//p[text()='" + fileName +"']")));
            }
        }

        // upload files
        for (String fileName : allowedFiles) {
            clickOnElement(By.xpath("//p[text()='" + fileName + "']/parent::td/parent::tr//button[@class='btn btn-primary start']"));
            Assert.assertTrue(findVisibleElement(By.xpath("//a[text()='" + fileName +"']")).isDisplayed());
        }

        // delete uploaded files
        for (String fileName : allowedFiles) {
            clickOnElement(By.xpath("//a[text()='" + fileName + "']/parent::p/parent::td/parent::tr//button[@class='btn btn-danger delete']"));
            Assert.assertTrue(isElementInvisible(By.xpath("//a[text()='" + fileName +"']")));
        }

        // multiple files
        String allFilePath = fileDir + filesToUpload[0];
        for (int i = 1; i < filesToUpload.length; i++) {
            allFilePath = allFilePath + "\n" + (fileDir + filesToUpload[i]);
        }

        // browse all files
        driver.findElement(By.cssSelector("input[type='file']")).sendKeys(allFilePath); // note: length of string to sendKeys should not be too long
        for (String fileName : filesToUpload) {
            Assert.assertTrue(findVisibleElement(By.xpath("//p[text()='" + fileName + "']")).isDisplayed());
        }

        // verify that err.msg will be displayed below the file which is not allowed to upload
        allowedFiles = new ArrayList<String>();
        for (String fileName : filesToUpload) {
            if (findVisibleElement(By.xpath("//p[text()='" + fileName + "']/parent::td/parent::tr//button[@class='btn btn-primary start']")).isEnabled()) {
                allowedFiles.add(fileName);
            } else {
                Assert.assertTrue(findVisibleElement(By.xpath("//p[text()='" + fileName +"']/following-sibling::strong")).isDisplayed());
            }
        }

        // upload all files
        clickOnElement(By.xpath("//span[text()='Start upload']"));
        for (String fileName : allowedFiles) {
            Assert.assertTrue(findVisibleElement(By.xpath("//a[text()='" + fileName +"']")).isDisplayed());
        }

        // cancel not-allowed files
        clickOnElement(By.xpath("//span[text()='Cancel upload']"));
        for (String fileName : filesToUpload) {
            if (!allowedFiles.contains(fileName)) {
                Assert.assertTrue(isElementInvisible(By.xpath("//p[text()='" + fileName +"']")));
            }
        }

        // delete uploaded files
        clickOnElement(By.cssSelector("div.fileupload-buttonbar input.toggle"));
        explicitWait.until(ExpectedConditions.elementToBeSelected(By.cssSelector("div.fileupload-buttonbar input.toggle")));

        clickOnElement(By.xpath("//span[text()='Delete selected']"));
        for (String fileName : allowedFiles) {
            Assert.assertTrue(isElementInvisible(By.xpath("//a[text()='" + fileName +"']")));
        }

        driver.quit();
    }

    @Test
    public void TC_02_Chrome() {
        openBrowser(new ChromeDriver());

        // browse files
        for (String fileName : filesToUpload) {
            driver.findElement(By.cssSelector("input[type='file']")).sendKeys(fileDir + fileName);
            Assert.assertTrue(findVisibleElement(By.xpath("//p[text()='" + fileName + "']")).isDisplayed());
        }

        // verify that err.msg will be displayed below the file which is not allowed to upload --> cancel not-allowed files
        List<String> allowedFiles = new ArrayList<String>();
        for (String fileName : filesToUpload) {
            if (findVisibleElement(By.xpath("//p[text()='" + fileName + "']/parent::td/parent::tr//button[@class='btn btn-primary start']")).isEnabled()) {
                allowedFiles.add(fileName);
            } else {
                Assert.assertTrue(findVisibleElement(By.xpath("//p[text()='" + fileName +"']/following-sibling::strong")).isDisplayed());
                clickOnElement(By.xpath("//p[text()='" + fileName + "']/parent::td/parent::tr//button[@class='btn btn-warning cancel']"));
                Assert.assertTrue(isElementInvisible(By.xpath("//p[text()='" + fileName +"']")));
            }
        }

        // upload files
        for (String fileName : allowedFiles) {
            clickOnElement(By.xpath("//p[text()='" + fileName + "']/parent::td/parent::tr//button[@class='btn btn-primary start']"));
            Assert.assertTrue(findVisibleElement(By.xpath("//a[text()='" + fileName +"']")).isDisplayed());
        }

        // delete uploaded files
        for (String fileName : allowedFiles) {
            clickOnElement(By.xpath("//a[text()='" + fileName + "']/parent::p/parent::td/parent::tr//button[@class='btn btn-danger delete']"));
            Assert.assertTrue(isElementInvisible(By.xpath("//a[text()='" + fileName +"']")));
        }

        // multiple files
        String allFilePath = fileDir + filesToUpload[0];
        for (int i = 1; i < filesToUpload.length; i++) {
            allFilePath = allFilePath + "\n" + (fileDir + filesToUpload[i]);
        }

        // browse all files
        driver.findElement(By.cssSelector("input[type='file']")).sendKeys(allFilePath); // note: length of string to sendKeys should not be too long
        for (String fileName : filesToUpload) {
            Assert.assertTrue(findVisibleElement(By.xpath("//p[text()='" + fileName + "']")).isDisplayed());
        }

        // verify that err.msg will be displayed below the file which is not allowed to upload
        allowedFiles = new ArrayList<String>();
        for (String fileName : filesToUpload) {
            if (findVisibleElement(By.xpath("//p[text()='" + fileName + "']/parent::td/parent::tr//button[@class='btn btn-primary start']")).isEnabled()) {
                allowedFiles.add(fileName);
            } else {
                Assert.assertTrue(findVisibleElement(By.xpath("//p[text()='" + fileName +"']/following-sibling::strong")).isDisplayed());
            }
        }

        // upload all files
        clickOnElement(By.xpath("//span[text()='Start upload']"));
        for (String fileName : allowedFiles) {
            Assert.assertTrue(findVisibleElement(By.xpath("//a[text()='" + fileName +"']")).isDisplayed());
        }

        // cancel not-allowed files
        clickOnElement(By.xpath("//span[text()='Cancel upload']"));
        for (String fileName : filesToUpload) {
            if (!allowedFiles.contains(fileName)) {
                Assert.assertTrue(isElementInvisible(By.xpath("//p[text()='" + fileName +"']")));
            }
        }

        // delete uploaded files
        clickOnElement(By.cssSelector("div.fileupload-buttonbar input.toggle"));
        explicitWait.until(ExpectedConditions.elementToBeSelected(By.cssSelector("div.fileupload-buttonbar input.toggle")));

        clickOnElement(By.xpath("//span[text()='Delete selected']"));
        for (String fileName : allowedFiles) {
            Assert.assertTrue(isElementInvisible(By.xpath("//a[text()='" + fileName +"']")));
        }

        driver.quit();
    }

    @Test
    public void TC_03_Edge() {
        openBrowser(new EdgeDriver());

        // browse files
        for (String fileName : filesToUpload) {
            driver.findElement(By.cssSelector("input[type='file']")).sendKeys(fileDir + fileName);
            Assert.assertTrue(findVisibleElement(By.xpath("//p[text()='" + fileName + "']")).isDisplayed());
        }

        // verify that err.msg will be displayed below the file which is not allowed to upload --> cancel not-allowed files
        List<String> allowedFiles = new ArrayList<String>();
        for (String fileName : filesToUpload) {
            if (findVisibleElement(By.xpath("//p[text()='" + fileName + "']/parent::td/parent::tr//button[@class='btn btn-primary start']")).isEnabled()) {
                allowedFiles.add(fileName);
            } else {
                Assert.assertTrue(findVisibleElement(By.xpath("//p[text()='" + fileName +"']/following-sibling::strong")).isDisplayed());
                clickOnElement(By.xpath("//p[text()='" + fileName + "']/parent::td/parent::tr//button[@class='btn btn-warning cancel']"));
                Assert.assertTrue(isElementInvisible(By.xpath("//p[text()='" + fileName +"']")));
            }
        }

        // upload files
        for (String fileName : allowedFiles) {
            clickOnElement(By.xpath("//p[text()='" + fileName + "']/parent::td/parent::tr//button[@class='btn btn-primary start']"));
            Assert.assertTrue(findVisibleElement(By.xpath("//a[text()='" + fileName +"']")).isDisplayed());
        }

        // delete uploaded files
        for (String fileName : allowedFiles) {
            clickOnElement(By.xpath("//a[text()='" + fileName + "']/parent::p/parent::td/parent::tr//button[@class='btn btn-danger delete']"));
            Assert.assertTrue(isElementInvisible(By.xpath("//a[text()='" + fileName +"']")));
        }

        // multiple files
        String allFilePath = fileDir + filesToUpload[0];
        for (int i = 1; i < filesToUpload.length; i++) {
            allFilePath = allFilePath + "\n" + (fileDir + filesToUpload[i]);
        }

        // browse all files
        driver.findElement(By.cssSelector("input[type='file']")).sendKeys(allFilePath); // note: length of string to sendKeys should not be too long
        for (String fileName : filesToUpload) {
            Assert.assertTrue(findVisibleElement(By.xpath("//p[text()='" + fileName + "']")).isDisplayed());
        }

        // verify that err.msg will be displayed below the file which is not allowed to upload
        allowedFiles = new ArrayList<String>();
        for (String fileName : filesToUpload) {
            if (findVisibleElement(By.xpath("//p[text()='" + fileName + "']/parent::td/parent::tr//button[@class='btn btn-primary start']")).isEnabled()) {
                allowedFiles.add(fileName);
            } else {
                Assert.assertTrue(findVisibleElement(By.xpath("//p[text()='" + fileName +"']/following-sibling::strong")).isDisplayed());
            }
        }

        // upload all files
        clickOnElement(By.xpath("//span[text()='Start upload']"));
        for (String fileName : allowedFiles) {
            Assert.assertTrue(findVisibleElement(By.xpath("//a[text()='" + fileName +"']")).isDisplayed());
        }

        // cancel not-allowed files
        clickOnElement(By.xpath("//span[text()='Cancel upload']"));
        for (String fileName : filesToUpload) {
            if (!allowedFiles.contains(fileName)) {
                Assert.assertTrue(isElementInvisible(By.xpath("//p[text()='" + fileName +"']")));
            }
        }

        // delete uploaded files
        clickOnElement(By.cssSelector("div.fileupload-buttonbar input.toggle"));
        explicitWait.until(ExpectedConditions.elementToBeSelected(By.cssSelector("div.fileupload-buttonbar input.toggle")));

        clickOnElement(By.xpath("//span[text()='Delete selected']"));
        for (String fileName : allowedFiles) {
            Assert.assertTrue(isElementInvisible(By.xpath("//a[text()='" + fileName +"']")));
        }

        driver.quit();
    }
}

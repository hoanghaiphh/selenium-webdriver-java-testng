package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Topic_14_Upload_File {
    WebDriver driver;
    String fileDir = System.getProperty("user.dir") + File.separator + "uploadFiles" + File.separator;
    String[] filesToUpload = new String[]{"avatar.jpg", "jQuery.txt", "topic13.png", "santa.ico", "snow.png", "large.jpg"};

    public void sleepInMilliSeconds (long timeInMilliSecond) {
        try {
            Thread.sleep(timeInMilliSecond);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void TC_01_Firefox() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
        driver.get("https://blueimp.github.io/jQuery-File-Upload/");

        // browse files
        for (String fileName : filesToUpload) {
            driver.findElement(By.cssSelector("input[type='file']")).sendKeys(fileDir + fileName);
            sleepInMilliSeconds(500);
        }

        // verify all files loaded
        for (String fileName : filesToUpload) {
            Assert.assertTrue(driver.findElement(By.xpath("//p[text()='" + fileName + "']")).isDisplayed());
        }

        // verify that err.msg will be displayed below the file which is not allowed to upload
        List<String> allowedFiles = new ArrayList<String>();
        for (String fileName : filesToUpload) {
            if (!driver.findElement(By.xpath("//p[text()='" + fileName + "']/parent::td/parent::tr//button[@class='btn btn-primary start']")).isEnabled()) {
                Assert.assertTrue(driver.findElement(By.xpath("//p[text()='" + fileName +"']/following-sibling::strong")).isDisplayed());
            } else {
                allowedFiles.add(fileName);
            }
        }

        // upload allowed files
        for (String fileName : allowedFiles) {
            driver.findElement(By.xpath("//p[text()='" + fileName + "']/parent::td/parent::tr//button[@class='btn btn-primary start']")).click();
            sleepInMilliSeconds(500);
        }

        // verify allowed files uploaded
        for (String fileName : allowedFiles) {
            Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + fileName +"']")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + fileName + "']/parent::p/parent::td/parent::tr//button[@class='btn btn-danger delete']")).isDisplayed());
        }

        // delete uploaded files / cancel not-allowed files (clear form)
        for (String fileName : filesToUpload) {
            if (allowedFiles.contains(fileName)) {
                driver.findElement(By.xpath("//a[text()='" + fileName + "']/parent::p/parent::td/parent::tr//button[@class='btn btn-danger delete']")).click();
                sleepInMilliSeconds(500);
            } else {
                driver.findElement(By.xpath("//p[text()='" + fileName + "']/parent::td/parent::tr//button[@class='btn btn-warning cancel']")).click();
                sleepInMilliSeconds(500);
            }
        }

        // multiple files
        String allFilePath = fileDir + filesToUpload[0];
        for (int i = 1; i < filesToUpload.length; i++) {
            allFilePath = allFilePath + "\n" + (fileDir + filesToUpload[i]);
        }

        driver.findElement(By.cssSelector("input[type='file']")).sendKeys(allFilePath); // note: length of string to sendKeys should not be too long
        sleepInMilliSeconds(2000);

        driver.findElement(By.xpath("//span[text()='Start upload']")).click();
        sleepInMilliSeconds(2000);

        driver.findElement(By.xpath("//span[text()='Cancel upload']")).click();
        sleepInMilliSeconds(500);

        driver.findElement(By.cssSelector("div.fileupload-buttonbar input.toggle")).click();
        sleepInMilliSeconds(500);

        driver.findElement(By.xpath("//span[text()='Delete selected']")).click();
        sleepInMilliSeconds(2000);

        driver.quit();
    }

    @Test
    public void TC_02_Chrome() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
        driver.get("https://blueimp.github.io/jQuery-File-Upload/");

        for (String fileName : filesToUpload) {
            driver.findElement(By.cssSelector("input[type='file']")).sendKeys(fileDir + fileName);
            sleepInMilliSeconds(500);
        }

        for (String fileName : filesToUpload) {
            Assert.assertTrue(driver.findElement(By.xpath("//p[text()='" + fileName + "']")).isDisplayed());
        }

        List<String> allowedFiles = new ArrayList<String>();
        for (String fileName : filesToUpload) {
            if (!driver.findElement(By.xpath("//p[text()='" + fileName + "']/parent::td/parent::tr//button[@class='btn btn-primary start']")).isEnabled()) {
                Assert.assertTrue(driver.findElement(By.xpath("//p[text()='" + fileName +"']/following-sibling::strong")).isDisplayed());
            } else {
                allowedFiles.add(fileName);
            }
        }

        for (String fileName : allowedFiles) {
            driver.findElement(By.xpath("//p[text()='" + fileName + "']/parent::td/parent::tr//button[@class='btn btn-primary start']")).click();
            sleepInMilliSeconds(500);
        }

        for (String fileName : allowedFiles) {
            Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + fileName +"']")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + fileName + "']/parent::p/parent::td/parent::tr//button[@class='btn btn-danger delete']")).isDisplayed());
        }

        for (String fileName : filesToUpload) {
            if (allowedFiles.contains(fileName)) {
                driver.findElement(By.xpath("//a[text()='" + fileName + "']/parent::p/parent::td/parent::tr//button[@class='btn btn-danger delete']")).click();
                sleepInMilliSeconds(500);
            } else {
                driver.findElement(By.xpath("//p[text()='" + fileName + "']/parent::td/parent::tr//button[@class='btn btn-warning cancel']")).click();
                sleepInMilliSeconds(500);
            }
        }

        String allFilePath = fileDir + filesToUpload[0];
        for (int i = 1; i < filesToUpload.length; i++) {
            allFilePath = allFilePath + "\n" + (fileDir + filesToUpload[i]);
        }

        driver.findElement(By.cssSelector("input[type='file']")).sendKeys(allFilePath);
        sleepInMilliSeconds(2000);

        driver.findElement(By.xpath("//span[text()='Start upload']")).click();
        sleepInMilliSeconds(2000);

        driver.findElement(By.xpath("//span[text()='Cancel upload']")).click();
        sleepInMilliSeconds(500);

        driver.findElement(By.cssSelector("div.fileupload-buttonbar input.toggle")).click();
        sleepInMilliSeconds(500);

        driver.findElement(By.xpath("//span[text()='Delete selected']")).click();
        sleepInMilliSeconds(2000);

        driver.quit();
    }

    @Test
    public void TC_03_Edge() {
        driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
        driver.get("https://blueimp.github.io/jQuery-File-Upload/");

        for (String fileName : filesToUpload) {
            driver.findElement(By.cssSelector("input[type='file']")).sendKeys(fileDir + fileName);
            sleepInMilliSeconds(500);
        }

        for (String fileName : filesToUpload) {
            Assert.assertTrue(driver.findElement(By.xpath("//p[text()='" + fileName + "']")).isDisplayed());
        }

        List<String> allowedFiles = new ArrayList<String>();
        for (String fileName : filesToUpload) {
            if (!driver.findElement(By.xpath("//p[text()='" + fileName + "']/parent::td/parent::tr//button[@class='btn btn-primary start']")).isEnabled()) {
                Assert.assertTrue(driver.findElement(By.xpath("//p[text()='" + fileName +"']/following-sibling::strong")).isDisplayed());
            } else {
                allowedFiles.add(fileName);
            }
        }

        for (String fileName : allowedFiles) {
            driver.findElement(By.xpath("//p[text()='" + fileName + "']/parent::td/parent::tr//button[@class='btn btn-primary start']")).click();
            sleepInMilliSeconds(500);
        }

        for (String fileName : allowedFiles) {
            Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + fileName +"']")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + fileName + "']/parent::p/parent::td/parent::tr//button[@class='btn btn-danger delete']")).isDisplayed());
        }

        for (String fileName : filesToUpload) {
            if (allowedFiles.contains(fileName)) {
                driver.findElement(By.xpath("//a[text()='" + fileName + "']/parent::p/parent::td/parent::tr//button[@class='btn btn-danger delete']")).click();
                sleepInMilliSeconds(500);
            } else {
                driver.findElement(By.xpath("//p[text()='" + fileName + "']/parent::td/parent::tr//button[@class='btn btn-warning cancel']")).click();
                sleepInMilliSeconds(500);
            }
        }

        String allFilePath = fileDir + filesToUpload[0];
        for (int i = 1; i < filesToUpload.length; i++) {
            allFilePath = allFilePath + "\n" + (fileDir + filesToUpload[i]);
        }

        driver.findElement(By.cssSelector("input[type='file']")).sendKeys(allFilePath);
        sleepInMilliSeconds(2000);

        driver.findElement(By.xpath("//span[text()='Start upload']")).click();
        sleepInMilliSeconds(2000);

        driver.findElement(By.xpath("//span[text()='Cancel upload']")).click();
        sleepInMilliSeconds(500);

        driver.findElement(By.cssSelector("div.fileupload-buttonbar input.toggle")).click();
        sleepInMilliSeconds(500);

        driver.findElement(By.xpath("//span[text()='Delete selected']")).click();
        sleepInMilliSeconds(2000);

        driver.quit();
    }

    @Test
    public void TC_04_Safari() {
        if (System.getProperty("os.name").contains("Mac")) {
            driver = new SafariDriver();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            driver.manage().window().maximize();
            driver.get("https://blueimp.github.io/jQuery-File-Upload/");

            for (String fileName : filesToUpload) {
                driver.findElement(By.cssSelector("input[type='file']")).sendKeys(fileDir + fileName);
                sleepInMilliSeconds(500);
            }

            for (String fileName : filesToUpload) {
                Assert.assertTrue(driver.findElement(By.xpath("//p[text()='" + fileName + "']")).isDisplayed());
            }

            List<String> allowedFiles = new ArrayList<String>();
            for (String fileName : filesToUpload) {
                if (!driver.findElement(By.xpath("//p[text()='" + fileName + "']/parent::td/parent::tr//button[@class='btn btn-primary start']")).isEnabled()) {
                    Assert.assertTrue(driver.findElement(By.xpath("//p[text()='" + fileName +"']/following-sibling::strong")).isDisplayed());
                } else {
                    allowedFiles.add(fileName);
                }
            }

            for (String fileName : allowedFiles) {
                driver.findElement(By.xpath("//p[text()='" + fileName + "']/parent::td/parent::tr//button[@class='btn btn-primary start']")).click();
                sleepInMilliSeconds(500);
            }

            for (String fileName : allowedFiles) {
                Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + fileName +"']")).isDisplayed());
                Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + fileName + "']/parent::p/parent::td/parent::tr//button[@class='btn btn-danger delete']")).isDisplayed());
            }

            for (String fileName : filesToUpload) {
                if (allowedFiles.contains(fileName)) {
                    driver.findElement(By.xpath("//a[text()='" + fileName + "']/parent::p/parent::td/parent::tr//button[@class='btn btn-danger delete']")).click();
                    sleepInMilliSeconds(500);
                } else {
                    driver.findElement(By.xpath("//p[text()='" + fileName + "']/parent::td/parent::tr//button[@class='btn btn-warning cancel']")).click();
                    sleepInMilliSeconds(500);
                }
            }

            String allFilePath = fileDir + filesToUpload[0];
            for (int i = 1; i < filesToUpload.length; i++) {
                allFilePath = allFilePath + "\n" + (fileDir + filesToUpload[i]);
            }

            driver.findElement(By.cssSelector("input[type='file']")).sendKeys(allFilePath);
            sleepInMilliSeconds(2000);

            driver.findElement(By.xpath("//span[text()='Start upload']")).click();
            sleepInMilliSeconds(2000);

            driver.findElement(By.xpath("//span[text()='Cancel upload']")).click();
            sleepInMilliSeconds(500);

            driver.findElement(By.cssSelector("div.fileupload-buttonbar input.toggle")).click();
            sleepInMilliSeconds(500);

            driver.findElement(By.xpath("//span[text()='Delete selected']")).click();
            sleepInMilliSeconds(2000);

            driver.quit();
        } else {
            System.out.println("Current OS: " + System.getProperty("os.name"));
            System.out.println("This computer does not have Safari Browser.");
        }
    }
}

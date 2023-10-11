package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class Topic_25_Custom_Dropdown {
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
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
        driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");

        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Test
    public void TC_01_Number_Dropdown() {

        // Click vào dropdown
        driver.findElement(By.cssSelector("span#number-button")).click();

        // Chờ tất cả options hiển thị đầy đủ trong HTML
        explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("ul#number-menu div")));

        // Kiểm tra text của từng item và click
        List<WebElement> numberDropdown = driver.findElements(By.cssSelector("ul#number-menu div"));

        for (WebElement number : numberDropdown) {
            String textNumber = number.getText();
            System.out.println("Number Selecting: " + textNumber + " ...");
            if (textNumber.equals("15")) {
                System.out.println("-- Number Selected: " + textNumber);
                number.click();
                break;
            }
        }
    }

    @Test
    public void TC_02_Rest_Dropdown() {
        driver.findElement(By.cssSelector("span#speed-button")).click();
        explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("ul#speed-menu div")));
        List<WebElement> speedDropdown = driver.findElements(By.cssSelector("ul#speed-menu div"));
        for (WebElement speed : speedDropdown) {
            speed.getText();
            System.out.println("Speed Selecting: " + speed.getText() + " ...");
            if (speed.getText().equals("Fast")) {
                System.out.println("-- Speed Selected: " + speed.getText());
                speed.click();
                break;
            }
        }
        driver.findElement(By.cssSelector("span#files-button")).click();
        explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("ul#files-menu div")));
        List<WebElement> filesDropdown = driver.findElements(By.cssSelector("ul#files-menu div"));
        for (WebElement files : filesDropdown) {
            files.getText();
            System.out.println("Files Selecting: " + files.getText() + " ...");
            if (files.getText().equals("ui.jQuery.js")) {
                System.out.println("-- Files Selected: " + files.getText());
                files.click();
                break;
            }
        }
        driver.findElement(By.cssSelector("span#salutation-button")).click();
        explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("ul#salutation-menu div")));
        List<WebElement> titleDropdown = driver.findElements(By.cssSelector("ul#salutation-menu div"));
        for (WebElement title : titleDropdown) {
            title.getText();
            System.out.println("Title Selecting: " + title.getText() + " ...");
            if (title.getText().equals("Dr.")) {
                System.out.println("-- Title Selected: " + title.getText());
                title.click();
                break;
            }
        }
    }

    @AfterClass
    public void afterClass() {
        // driver.quit();
    }
}

package note;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class Convert_StringToInt_SplitMerge_String {
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

    public String splitAndMerge(String text) {
        String[] textContainer = text.split(" 0");
        return textContainer[0] + " " + textContainer[1];
    }
    @Test
    public void TC_01() {
        driver.get("https://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");

        String[] selectedDates = new String[] {"9", "12", "22", "27"};
        for (String date : selectedDates) {
            driver.findElement(By.xpath("//a[text()='" + date +"']")).click();
            sleepInSeconds(2);
        }

        List<WebElement> allDates = driver.findElements(By.xpath("//a/parent::td"));
        for (WebElement date : allDates) {
            if (Integer.parseInt(date.getAttribute("innerText")) < 10 && date.getAttribute("class").contains("rcSelected")) {
                Assert.assertTrue(driver.findElement(By.cssSelector("span.label")).getText().contains(splitAndMerge(date.getAttribute("title"))));
            } else if (Integer.parseInt(date.getAttribute("innerText")) >= 10 && date.getAttribute("class").contains("rcSelected")) {
                Assert.assertTrue(driver.findElement(By.cssSelector("span.label")).getText().contains(date.getAttribute("title")));
            } else {
                Assert.assertFalse(driver.findElement(By.cssSelector("span.label")).getText().contains(date.getAttribute("title")));
            }
        }
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

package java_basic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Date;

public class JavaBasic_Surround_TryCatch {
    WebDriver driver;
    WebDriverWait explicitWait;

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Test
    public void TC_01_visibilityOfElementLocated() {
        driver.get("https://www.facebook.com/");

        Date startTime = new Date();
        System.out.println("Start:\t" + startTime);

        // Surround >> try/catch
        try {
            explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#test")));
        } catch (Exception e) {
            // throw new RuntimeException(e);     --> default: fail >> throw exception

            Date endTime = new Date();
            System.out.println("End:\t" + endTime);

            int duration = (endTime.getMinutes() == startTime.getMinutes())
                    ? (endTime.getSeconds() - startTime.getSeconds())
                    : (60 - startTime.getSeconds() + endTime.getSeconds());
            System.out.println("Duration: " + duration + " seconds");

            e.printStackTrace();
        }
    }

    @Test
    public void TC_02_visibilityOf() {
        driver.get("https://www.facebook.com/");

        System.out.println("\nStart: " + new Date() +"\n");

        try {
            explicitWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("input#test"))));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nEnd: " + new Date());
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

package webdriver;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_11_12X_Run_Multiple_Drivers {
    WebDriver driver1;
    WebDriver driver2;

    @BeforeClass
    public void beforeClass() {
        driver1 = new FirefoxDriver();
        driver1.manage().window().setPosition(new Point(0, 0));

        driver2 = new ChromeDriver();
        driver2.manage().window().setSize(driver1.manage().window().getSize());
        driver2.manage().window().setPosition(new Point(220, 70));
    }

    @Test
    public void TC_01_() throws InterruptedException {
        driver1.get("https://www.facebook.com/");
        driver2.get("https://www.youtube.com/");
        Thread.sleep(5000);
    }

    @AfterClass
    public void afterClass() {
        driver1.quit();
        driver2.quit();
    }
}

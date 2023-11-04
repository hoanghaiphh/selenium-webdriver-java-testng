package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Topic_14_Text {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
        }

        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @Test
    public void TC_01_Text() {
        driver.get("https://automationfc.github.io/basic-form/");

        // 1 - Truyen text vao locator de check hien thi (isDisplayed)
        driver.findElement(By.xpath("//h1[text()='Selenium WebDriver API']")).isDisplayed();

        // 2 - Get text  roi verify
        String textString = driver.findElement(By.xpath("//h5[@id='nine']/p[1]")).getText();
        System.out.println(textString);
        Assert.assertTrue(textString.contains("Mail Personal or Business Check"));

        String nestedText = driver.findElement(By.xpath("//h5[@id='nested']")).getText();
        System.out.println(nestedText);
        Assert.assertEquals(nestedText,"Hello World! (Ignore Me) @04:45 PM");

        // concat()
        String concatText = driver.findElement(By.xpath("//h5/span[@class='concat']")).getText();
        System.out.println(concatText);
        String concatText2 = driver.findElement(By.xpath("//span[text()=concat('Hello \"John\", ',\"What's happened?\")]")).getText();
        Assert.assertEquals(concatText2,"Hello \"John\", What's happened?");
        driver.findElement(By.xpath("//span[text()=concat('Hello \"John\", ',\"What's happened?\")]")).isDisplayed();
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
package javaTester;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class _03_Selenium_Text {
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
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://automationfc.github.io/basic-form/");
    }

    @Test
    public void TC_01_Demo() {

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

    @Test
    public void TC_02_Ex() {
        String textStr = driver.findElement(By.xpath("//h5[@id='nine']/p[4]")).getText();
        Assert.assertTrue(textStr.contains("P.S. You can edit this"));
        Assert.assertTrue(textStr.contains("text from admin panel"));
        Assert.assertFalse(textStr.contains("text from admin Apanel"));
        Assert.assertEquals(textStr,"P.S. You can edit this text from admin panel.");

    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

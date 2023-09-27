package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Topic_07_08_Textbox_TextArea_Dropdown {
    WebDriver driver;

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @Test
    public void TC_01_Textbox_TextArea() throws InterruptedException {
        driver.get("http://live.techpanda.org/");

        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        driver.findElement(By.xpath("//span[text()='Create an Account']")).click();

        driver.findElement(By.cssSelector("input[id='firstname']")).sendKeys("Hai");
        driver.findElement(By.cssSelector("input[id='middlename']")).sendKeys("Hoang");
        driver.findElement(By.cssSelector("input[id='lastname']")).sendKeys("Phan");
        String emailRandom = "automation" + Math.floor(Math.random()*111111) + "@gmail.com";
        driver.findElement(By.cssSelector("input[id='email_address']")).sendKeys(emailRandom);
        driver.findElement(By.cssSelector("input[id='password']")).sendKeys("Abc@1234");
        driver.findElement(By.cssSelector("input[id='confirmation']")).sendKeys("Abc@1234");
        driver.findElement(By.xpath("//span[text()='Register']")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector("li[class='success-msg'] span")).getText(),"Thank you for registering with Main Website Store.");

        String contactInfo = driver.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div/p")).getText();
        Assert.assertTrue(contactInfo.contains("Hai"));
        Assert.assertTrue(contactInfo.contains("Phan"));
        Assert.assertTrue(contactInfo.contains(emailRandom));

        driver.findElement(By.xpath("//header//span[text()='Account']")).click();
        driver.findElement(By.xpath("//a[text()='Log Out']")).click();

        Thread.sleep(7000);
        Assert.assertEquals(driver.getCurrentUrl(),"http://live.techpanda.org/index.php/");

    }

    @Test
    public void TC_02_() {

    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

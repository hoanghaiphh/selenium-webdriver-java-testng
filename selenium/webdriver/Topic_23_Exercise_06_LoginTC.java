package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Random;

public class Topic_23_Exercise_06_LoginTC {
    WebDriver driver;

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
    }
    public void sleepInSeconds (long timeInSecond) {
        try {
            Thread.sleep(timeInSecond*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public String getEmailRandom() {
        Random rand = new Random();
        return "automation" + rand.nextInt(999999) + "@gmail.com";
        // return "automation" + new Random().nextInt(999999) + "@gmail.com"
    }

    @Test
    public void Login_01_Success() {
        driver.get("http://live.techpanda.org/");
        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        driver.findElement(By.xpath("//span[text()='Create an Account']")).click();

        String firstName = "Hai", middleName = "Hoang", lastName = "Phan", password = "Abc@1234";
        String email = getEmailRandom();
        String fullName = firstName + " " + middleName + " " + lastName;

        // Register

        driver.findElement(By.cssSelector("input[id='firstname']")).sendKeys(firstName);
        driver.findElement(By.cssSelector("input[id='middlename']")).sendKeys(middleName);
        driver.findElement(By.cssSelector("input[id='lastname']")).sendKeys(lastName);
        driver.findElement(By.cssSelector("input[id='email_address']")).sendKeys(email);
        driver.findElement(By.cssSelector("input[id='password']")).sendKeys(password);
        driver.findElement(By.cssSelector("input[id='confirmation']")).sendKeys(password);
        driver.findElement(By.xpath("//span[text()='Register']")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector("li[class='success-msg'] span")).getText(),"Thank you for registering with Main Website Store.");

        Assert.assertEquals(driver.findElement(By.cssSelector("div.welcome-msg strong")).getText(),"Hello, " + fullName + "!");

        String contactInfo = driver.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div/p")).getText();
        Assert.assertTrue(contactInfo.contains(fullName));
        Assert.assertTrue(contactInfo.contains(email));
        System.out.println(contactInfo);

        driver.findElement(By.xpath("//header//span[text()='Account']")).click();
        driver.findElement(By.xpath("//a[text()='Log Out']")).click();
        sleepInSeconds(6);

        // Login success

        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("pass")).clear();
        driver.findElement(By.id("pass")).sendKeys(password);
        driver.findElement(By.id("send2")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector("div.welcome-msg strong")).getText(),"Hello, " + fullName + "!");

        contactInfo = driver.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div/p")).getText();
        Assert.assertTrue(contactInfo.contains(fullName));
        Assert.assertTrue(contactInfo.contains(email));

        driver.findElement(By.xpath("//a[text()='Account Information']")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector("input[id='firstname']")).getAttribute("value"),firstName);
        Assert.assertEquals(driver.findElement(By.cssSelector("input[id='middlename']")).getAttribute("value"),middleName);
        Assert.assertEquals(driver.findElement(By.cssSelector("input[id='lastname']")).getAttribute("value"),lastName);
        Assert.assertEquals(driver.findElement(By.cssSelector("input[id='email']")).getAttribute("value"),email);

        // Change password

        driver.findElement(By.cssSelector("input[id='current_password']")).sendKeys(password);
        if (!(driver.findElement(By.id("change_password")).isSelected())) {
            driver.findElement(By.id("change_password")).click();
        }

        String newPassword = "Def!5678";
        driver.findElement(By.id("password")).sendKeys(newPassword);
        driver.findElement(By.id("confirmation")).sendKeys(newPassword);
        driver.findElement(By.cssSelector("button[title='Save']")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector("li[class='success-msg'] span")).getText(),"The account information has been saved.");

        // Login with incorrect password (old password)

        driver.findElement(By.xpath("//header//span[text()='Account']")).click();
        driver.findElement(By.xpath("//a[text()='Log Out']")).click();
        sleepInSeconds(6);

        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("pass")).clear();
        driver.findElement(By.id("pass")).sendKeys(password);
        driver.findElement(By.id("send2")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector("li.error-msg span")).getText(),"Invalid login or password.");

        // Re login with new password

        driver.findElement(By.id("pass")).clear();
        driver.findElement(By.id("pass")).sendKeys(newPassword);
        driver.findElement(By.id("send2")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector("div.welcome-msg strong")).getText(),"Hello, " + fullName + "!");
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

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
    public void TC_02_Textbox_TextArea() throws InterruptedException {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        driver.findElement(By.cssSelector("input[name='username']")).sendKeys("Admin");
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys("admin123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        driver.findElement(By.xpath("//span[text()='PIM']")).click();
        driver.findElement(By.xpath("//a[text()='Add Employee']")).click();

        driver.findElement(By.cssSelector("input[placeholder='First Name']")).sendKeys("Hai");
        driver.findElement(By.cssSelector("input[placeholder='Last Name']")).sendKeys("Phan");
        String employeeID = driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input")).getText();

        /*WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
        WebElement toggleButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[text()='Create Login Details']/following-sibling::div//span"));
        toggleButton.click();*/

        Thread.sleep(3000);
        driver.findElement(By.xpath("//p[text()='Create Login Details']/following-sibling::div//span")).click();

        driver.findElement(By.xpath("//label[text()='Username']/parent::div/following-sibling::div/input")).clear();
        driver.findElement(By.xpath("//label[text()='Username']/parent::div/following-sibling::div/input")).sendKeys("Test" + Math.floor(Math.random()*10000));
        driver.findElement(By.xpath("//label[text()='Password']/parent::div/following-sibling::div/input")).clear();
        driver.findElement(By.xpath("//label[text()='Password']/parent::div/following-sibling::div/input")).sendKeys("Abcd@1234");
        driver.findElement(By.xpath("//label[text()='Confirm Password']/parent::div/following-sibling::div/input")).sendKeys("Abcd@1234");
        driver.findElement(By.xpath("//button[text()=' Save ']")).click();

        //Assert.assertEquals(driver.findElement(By.cssSelector("input[name='firstName']")).getText(),"Hai");
        //Assert.assertEquals(driver.findElement(By.cssSelector("input[name='lastName']")).getText(),"Phan");
        Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input")).getText(),employeeID);

        driver.findElement(By.xpath("//a[text()='Immigration']")).click();
        driver.findElement(By.xpath("//h6[text()='Assigned Immigration Records']/following-sibling::button")).click();
        driver.findElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div/input")).sendKeys("40517-402-96-7202");
        driver.findElement(By.cssSelector("textarea[placeholder='Type Comments here']")).sendKeys("This is generated data of real people");
        driver.findElement(By.xpath("//button[text()=' Save ']")).click();

        Thread.sleep(3000);
        driver.findElement(By.cssSelector("i.bi-pencil-fill")).click();
        //Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div/input")).getText(),"40517-402-96-7202");
        //Assert.assertEquals(driver.findElement(By.cssSelector("textarea[placeholder='Type Comments here']")).getText(),"This is generated data of real people");

        driver.findElement(By.cssSelector("span.oxd-userdropdown-tab")).click();
        driver.findElement(By.xpath("//a[text()='Logout']")).click();

    }

    @AfterClass
    public void afterClass() {
        //driver.quit();
    }
}

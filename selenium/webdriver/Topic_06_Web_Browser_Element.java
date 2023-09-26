package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Topic_06_Web_Browser_Element {
    WebDriver driver;

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @Test
    public void Browser_TC_01_Verify_URL() {
        driver.get("http://live.techpanda.org/");

        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        Assert.assertEquals(driver.getCurrentUrl(),"http://live.techpanda.org/index.php/customer/account/login/");

        driver.findElement(By.xpath("//span[text()='Create an Account']")).click();
        Assert.assertEquals(driver.getCurrentUrl(),"http://live.techpanda.org/index.php/customer/account/create/");

    }

    @Test
    public void Browser_TC_02_Verify_Title() {
        driver.get("http://live.techpanda.org/");

        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        Assert.assertEquals(driver.getTitle(),"Customer Login");

        driver.findElement(By.xpath("//span[text()='Create an Account']")).click();
        Assert.assertEquals(driver.getTitle(),"Create New Customer Account");

    }

    @Test
    public void Browser_TC_03_Navigate_Function() {
        driver.get("http://live.techpanda.org/");

        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        driver.findElement(By.xpath("//span[text()='Create an Account']")).click();
        Assert.assertEquals(driver.getCurrentUrl(),"http://live.techpanda.org/index.php/customer/account/create/");

        driver.navigate().back();
        Assert.assertEquals(driver.getCurrentUrl(),"http://live.techpanda.org/index.php/customer/account/login/");

        driver.navigate().forward();
        Assert.assertEquals(driver.getTitle(),"Create New Customer Account");

    }

    @Test
    public void Browser_TC_04_Get_Page_Source_Code() {
        driver.get("http://live.techpanda.org/");

        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        Assert.assertTrue(driver.getPageSource().contains("Login or Create an Account"));

        driver.findElement(By.xpath("//span[text()='Create an Account']")).click();
        Assert.assertTrue(driver.getPageSource().contains("Create an Account"));

    }

    @Test
    public void Element_TC_01_Displayed() {
        driver.get("https://automationfc.github.io/basic-form/index.html");

        WebElement Email = driver.findElement(By.id("mail"));
        Assert.assertTrue(Email.isDisplayed());
        if (Email.isDisplayed()) {
            System.out.println("Email is displayed");
            Email.sendKeys("Automation Testing");
        } else {
            System.out.println("Email is not displayed");
        }

        WebElement Under18 = driver.findElement(By.xpath("//label[text()='Under 18']"));
        Assert.assertTrue(Under18.isDisplayed());
        if (Under18.isDisplayed()) {
            System.out.println("Age (Under 18) Radio button is displayed");
            Under18.click();
        } else {
            System.out.println("Age (Under 18) Radio button is not displayed");
        }

        WebElement Education = driver.findElement(By.id("edu"));
        Assert.assertTrue(Education.isDisplayed());
        if (Education.isDisplayed()) {
            System.out.println("Education is displayed");
            Education.sendKeys("Automation Testing");
        } else {
            System.out.println("Education is not displayed");
        }

        WebElement nameUser5 = driver.findElement(By.xpath("//h5[text()='Name: User5']"));
        Assert.assertFalse(nameUser5.isDisplayed());
        if (nameUser5.isDisplayed()) {
            System.out.println("(Name: User5) is displayed");
        } else {
            System.out.println("(Name: User5) is not displayed");
        }
    }

    @Test
    public void Element_TC_02_Enabled() {
        driver.get("https://automationfc.github.io/basic-form/index.html");

        Assert.assertTrue(driver.findElement(By.id("mail")).isEnabled());
        if (driver.findElement(By.id("mail")).isEnabled()) {
            System.out.println("Email is enabled");
        } else {
            System.out.println("Email is not enabled");
        }

        Assert.assertTrue(driver.findElement(By.id("under_18")).isEnabled());
        if (driver.findElement(By.id("under_18")).isEnabled()) {
            System.out.println("Age (Under 18) Radio button is enabled");
        } else {
            System.out.println("Age (Under 18) Radio button is not enabled");
        }

        Assert.assertTrue(driver.findElement(By.id("edu")).isEnabled());
        if (driver.findElement(By.id("edu")).isEnabled()) {
            System.out.println("Education is enabled");
        } else {
            System.out.println("Education is not enabled");
        }

        Assert.assertTrue(driver.findElement(By.id("job1")).isEnabled());
        if (driver.findElement(By.id("job1")).isEnabled()) {
            System.out.println("Job Role 01 is enabled");
        } else {
            System.out.println("Job Role 01 is not enabled");
        }

        Assert.assertTrue(driver.findElement(By.id("job2")).isEnabled());
        if (driver.findElement(By.id("job2")).isEnabled()) {
            System.out.println("Job Role 02 is enabled");
        } else {
            System.out.println("Job Role 02 is not enabled");
        }

        Assert.assertTrue(driver.findElement(By.id("development")).isEnabled());
        if (driver.findElement(By.id("development")).isEnabled()) {
            System.out.println("Interest (Development) Checkbox is enabled");
        } else {
            System.out.println("Interest (Development) Checkbox is not enabled");
        }

        Assert.assertTrue(driver.findElement(By.id("slider-1")).isEnabled());
        if (driver.findElement(By.id("slider-1")).isEnabled()) {
            System.out.println("Slider 01 is enabled");
        } else {
            System.out.println("Slider 01 is not enabled");
        }

        Assert.assertFalse(driver.findElement(By.id("disable_password")).isEnabled());
        if (driver.findElement(By.id("disable_password")).isEnabled()) {
            System.out.println("Password is enabled");
        } else {
            System.out.println("Password is not enabled");
        }

        Assert.assertFalse(driver.findElement(By.id("radio-disabled")).isEnabled());
        if (driver.findElement(By.id("radio-disabled")).isEnabled()) {
            System.out.println("Age (Checkbox is disabled) Radio button is enabled");
        } else {
            System.out.println("Age (Checkbox is disabled) Radio button is not enabled");
        }

        Assert.assertFalse(driver.findElement(By.id("bio")).isEnabled());
        if (driver.findElement(By.id("bio")).isEnabled()) {
            System.out.println("Biography is enabled");
        } else {
            System.out.println("Biography is not enabled");
        }

        Assert.assertFalse(driver.findElement(By.id("job3")).isEnabled());
        if (driver.findElement(By.id("job3")).isEnabled()) {
            System.out.println("Job Role 03 is enabled");
        } else {
            System.out.println("Job Role 03 is not enabled");
        }

        Assert.assertFalse(driver.findElement(By.id("check-disbaled")).isEnabled());
        if (driver.findElement(By.id("check-disbaled")).isEnabled()) {
            System.out.println("Interest (Checkbox is disabled) Checkbox is enabled");
        } else {
            System.out.println("Interest (Checkbox is disabled) Checkbox is not enabled");
        }

        Assert.assertFalse(driver.findElement(By.id("slider-2")).isEnabled());
        if (driver.findElement(By.id("slider-2")).isEnabled()) {
            System.out.println("Slider 02 is enabled");
        } else {
            System.out.println("Slider 02 is not enabled");
        }
    }

    @Test
    public void Element_TC_03_Selected() {
        driver.get("https://automationfc.github.io/basic-form/index.html");

        WebElement under18Radio = driver.findElement(By.id("under_18"));
        WebElement javaCheckbox = driver.findElement(By.id("java"));

        under18Radio.click();
        javaCheckbox.click();
        Assert.assertTrue(under18Radio.isSelected());
        Assert.assertTrue(javaCheckbox.isSelected());

        javaCheckbox.click();
        Assert.assertFalse(javaCheckbox.isSelected());

    }

    @Test
    public void Element_TC_04_Register_Function() {
        driver.get("https://login.mailchimp.com/signup/");

        driver.findElement(By.id("email")).sendKeys("automation@gmail.com");
        WebElement password = driver.findElement(By.id("new_password"));

        password.clear();
        password.sendKeys("a");
        Assert.assertTrue(driver.findElement(By.cssSelector(".lowercase-char.completed")).isDisplayed());

        password.clear();
        password.sendKeys("A");
        Assert.assertTrue(driver.findElement(By.cssSelector(".uppercase-char.completed")).isDisplayed());

        password.clear();
        password.sendKeys("1");
        Assert.assertTrue(driver.findElement(By.cssSelector(".number-char.completed")).isDisplayed());

        password.clear();
        password.sendKeys("@");
        Assert.assertTrue(driver.findElement(By.cssSelector(".special-char.completed")).isDisplayed());

        password.clear();
        password.sendKeys("abcd1234");
        Assert.assertTrue(driver.findElement(By.cssSelector("li[class='8-char completed']")).isDisplayed());

        password.clear();
        password.sendKeys("Abcd@123");
        Assert.assertFalse(driver.findElement(By.cssSelector("div[id='passwordHint']>div")).isDisplayed());

    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

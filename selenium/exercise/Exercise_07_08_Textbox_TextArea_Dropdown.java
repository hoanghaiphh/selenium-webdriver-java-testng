package exercise;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Exercise_07_08_Textbox_TextArea_Dropdown {
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
        String emailRandom = "automation" + Math.round(Math.random()*1000000) + "@gmail.com";
        driver.findElement(By.cssSelector("input[id='email_address']")).sendKeys(emailRandom);
        driver.findElement(By.cssSelector("input[id='password']")).sendKeys("Abc@1234");
        driver.findElement(By.cssSelector("input[id='confirmation']")).sendKeys("Abc@1234");
        driver.findElement(By.xpath("//span[text()='Register']")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector("li[class='success-msg'] span")).getText(),"Thank you for registering with Main Website Store.");

        String contactInfo = driver.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div/p")).getText();
        Assert.assertTrue(contactInfo.contains("Hai"));
        Assert.assertTrue(contactInfo.contains("Phan"));
        Assert.assertTrue(contactInfo.contains(emailRandom));
        System.out.println(contactInfo);

        driver.findElement(By.xpath("//header//span[text()='Account']")).click();
        driver.findElement(By.xpath("//a[text()='Log Out']")).click();

        Thread.sleep(7000);
        Assert.assertEquals(driver.getCurrentUrl(),"http://live.techpanda.org/index.php/");

    }

    @Test
    public void TC_02_Textbox_TextArea() throws InterruptedException {
        // Step 01: Truy cập vào trang
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        // Step 02: Login vào hệ thống
        driver.findElement(By.cssSelector("input[name='username']")).sendKeys("Admin");
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys("admin123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Step 03: Mở trang PIM
        driver.findElement(By.xpath("//span[text()='PIM']")).click();

        // Step 04: Mở trang PIM
        driver.findElement(By.xpath("//a[text()='Add Employee']")).click();

        // Step 05: Nhập thông tin hợp lệ
        driver.findElement(By.cssSelector("input[placeholder='First Name']")).sendKeys("Hai");
        driver.findElement(By.cssSelector("input[placeholder='Last Name']")).sendKeys("Phan");
        String employeeID = driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input")).getText();
        /*WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
        WebElement toggleButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[text()='Create Login Details']/following-sibling::div//span"));
        toggleButton.click();*/
        Thread.sleep(3000);
        driver.findElement(By.xpath("//p[text()='Create Login Details']/following-sibling::div//span")).click();
        driver.findElement(By.xpath("//label[text()='Username']/parent::div/following-sibling::div/input")).clear();
        String userName = "test" + Math.round(Math.random()*1000000);
        driver.findElement(By.xpath("//label[text()='Username']/parent::div/following-sibling::div/input")).sendKeys(userName);
        driver.findElement(By.xpath("//label[text()='Password']/parent::div/following-sibling::div/input")).clear();
        driver.findElement(By.xpath("//label[text()='Password']/parent::div/following-sibling::div/input")).sendKeys("Abcd@1234");
        driver.findElement(By.xpath("//label[text()='Confirm Password']/parent::div/following-sibling::div/input")).sendKeys("Abcd@1234");

        // Step 06: Save
        driver.findElement(By.xpath("//button[text()=' Save ']")).click();

        // Step 07: Verify dữ liệu đã nhập
        /*Assert.assertEquals(driver.findElement(By.cssSelector("input[name='firstName']")).getText(),"Hai");
        Assert.assertEquals(driver.findElement(By.cssSelector("input[name='lastName']")).getText(),"Phan");
        Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input")).getText(),employeeID);*/

        // Step 08: CLick vào nút Immigration
        driver.findElement(By.xpath("//a[text()='Immigration']")).click();

        // Step 09: Click Add tại Assigned Immigration Records
        driver.findElement(By.xpath("//h6[text()='Assigned Immigration Records']/following-sibling::button")).click();

        // Step 10: Nhập dữ liệu và click save
        driver.findElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div/input")).sendKeys("40517-402-96-7202");
        driver.findElement(By.cssSelector("textarea[placeholder='Type Comments here']")).sendKeys("This is generated data of real people");
        driver.findElement(By.xpath("//button[text()=' Save ']")).click();

        // Step 11: Click vào nút Pencil (Edit)
        Thread.sleep(3000);
        driver.findElement(By.cssSelector("i.bi-pencil-fill")).click();

        // Step 12: Verify dữ liệu
        /*Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div/input")).getText(),"40517-402-96-7202");
        Assert.assertEquals(driver.findElement(By.cssSelector("textarea[placeholder='Type Comments here']")).getText(),"This is generated data of real people");*/

        // Step 14: Click vào tên user và Logout
        driver.findElement(By.cssSelector("span.oxd-userdropdown-tab")).click();
        driver.findElement(By.xpath("//a[text()='Logout']")).click();

        // Step 15: Tại màn hình login nhập thông tin hợp lệ đã tạo ở Step 05
        Thread.sleep(3000);
        driver.findElement(By.cssSelector("input[name='username']")).sendKeys(userName);
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys("Abcd@1234");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Step 16: Vào màn hình My Info
        driver.findElement(By.xpath("//span[text()='My Info']")).click();

        // Step 17: Verify thông tin hiển thị
        /*Assert.assertEquals(driver.findElement(By.cssSelector("input[name='firstName']")).getText(),"Hai");
        Assert.assertEquals(driver.findElement(By.cssSelector("input[name='lastName']")).getText(),"Phan");
        Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input")).getText(),employeeID);*/

        // Steo 18: Vào màn hình Immigration và click nút Pencil
        driver.findElement(By.xpath("//a[text()='Immigration']")).click();
        driver.findElement(By.cssSelector("i.bi-pencil-fill")).click();

        // Step 19: Verify thông tin hiển thị
        /*Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div/input")).getText(),"40517-402-96-7202");
        Assert.assertEquals(driver.findElement(By.cssSelector("textarea[placeholder='Type Comments here']")).getText(),"This is generated data of real people");*/

        System.out.println("User: " + userName);
        System.out.println("Password: Abcd@1234");
        System.out.println("ID: " + employeeID);

    }

    @AfterClass
    public void afterClass() {
        //driver.quit();
    }
}

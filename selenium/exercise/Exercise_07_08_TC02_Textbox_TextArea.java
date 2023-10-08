package exercise;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Exercise_07_08_TC02_Textbox_TextArea {
    WebDriver driver;

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    public void sleepInSeconds (long timeInSecond) {
        try {
            Thread.sleep(timeInSecond*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void TC_02_Textbox_TextArea() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        driver.findElement(By.cssSelector("input[name='username']")).sendKeys("Admin");
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys("admin123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        sleepInSeconds(3);
        driver.findElement(By.xpath("//span[text()='PIM']")).click();
        sleepInSeconds(3);
        driver.findElement(By.xpath("//a[text()='Add Employee']")).click();
        sleepInSeconds(3);

        // Step 05: Nhập thông tin hợp lệ
        String firstName = "Automation", lastName = "Testing", password = "Abcd@1234";
        String userName = "automation" + Math.round(Math.random()*1000000);
        String passport= "40517-402-96-7202", comments = "This is generated data of real people";

        driver.findElement(By.cssSelector("input[placeholder='First Name']")).sendKeys(firstName);
        driver.findElement(By.cssSelector("input[placeholder='Last Name']")).sendKeys(lastName);
        String employeeID = driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input")).getAttribute("value");

        sleepInSeconds(3);
        driver.findElement(By.xpath("//p[text()='Create Login Details']/following-sibling::div//span")).click();
        sleepInSeconds(3);
        driver.findElement(By.xpath("//label[text()='Username']/parent::div/following-sibling::div/input")).clear();
        driver.findElement(By.xpath("//label[text()='Username']/parent::div/following-sibling::div/input")).sendKeys(userName);
        driver.findElement(By.xpath("//label[text()='Password']/parent::div/following-sibling::div/input")).clear();
        driver.findElement(By.xpath("//label[text()='Password']/parent::div/following-sibling::div/input")).sendKeys(password);
        driver.findElement(By.xpath("//label[text()='Confirm Password']/parent::div/following-sibling::div/input")).sendKeys(password);
        driver.findElement(By.xpath("//button[text()=' Save ']")).click();
        sleepInSeconds(3);

        // Step 07: Verify dữ liệu đã nhập
        WebElement firstNameTextbox = driver.findElement(By.cssSelector("input[name='firstName']"));
        WebElement lastNameTextbox = driver.findElement(By.cssSelector("input[name='lastName']"));
        WebElement employeeIdTextbox = driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input"));
        Assert.assertEquals(firstNameTextbox.getAttribute("value"),firstName);
        Assert.assertEquals(lastNameTextbox.getAttribute("value"),lastName);
        Assert.assertEquals(employeeIdTextbox.getAttribute("value"),employeeID);
        System.out.println("Step 07 - Verify #1:");
        System.out.println("\tFullname: " + firstNameTextbox.getAttribute("value") + " " + lastNameTextbox.getAttribute("value"));
        System.out.println("\tID: " + employeeIdTextbox.getAttribute("value"));

        driver.findElement(By.xpath("//a[text()='Immigration']")).click();
        sleepInSeconds(3);
        driver.findElement(By.xpath("//h6[text()='Assigned Immigration Records']/following-sibling::button")).click();
        sleepInSeconds(3);

        // Step 10: Nhập dữ liệu và click save
        driver.findElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div/input")).sendKeys(passport);
        driver.findElement(By.cssSelector("textarea[placeholder='Type Comments here']")).sendKeys(comments);
        driver.findElement(By.xpath("//button[text()=' Save ']")).click();
        sleepInSeconds(3);

        driver.findElement(By.cssSelector("i.bi-pencil-fill")).click();
        sleepInSeconds(3);

        // Step 12: Verify dữ liệu đã nhập
        WebElement passportTextbox = driver.findElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div/input"));
        WebElement commentsTextarea = driver.findElement(By.cssSelector("textarea[placeholder='Type Comments here']"));
        Assert.assertEquals(passportTextbox.getAttribute("value"),passport);
        Assert.assertEquals(commentsTextarea.getAttribute("value"),comments);
        System.out.println("Step 12 - Verify #2:");
        System.out.println("\tPassport Number: " + passportTextbox.getAttribute("value"));
        System.out.println("\tComments: " + commentsTextarea.getAttribute("value"));

        // Step 14: Click vào tên user và Logout
        driver.findElement(By.cssSelector("span.oxd-userdropdown-tab")).click();
        sleepInSeconds(3);
        driver.findElement(By.xpath("//a[text()='Logout']")).click();
        sleepInSeconds(3);

        // Step 15: Tại màn hình login nhập thông tin hợp lệ đã tạo ở Step 05
        driver.findElement(By.cssSelector("input[name='username']")).sendKeys(userName);
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys(password);
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        sleepInSeconds(3);

        driver.findElement(By.xpath("//span[text()='My Info']")).click();
        sleepInSeconds(3);

        // Step 17: Verify thông tin hiển thị
        firstNameTextbox = driver.findElement(By.cssSelector("input[name='firstName']"));
        lastNameTextbox = driver.findElement(By.cssSelector("input[name='lastName']"));
        employeeIdTextbox = driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input"));
        Assert.assertEquals(firstNameTextbox.getAttribute("value"),firstName);
        Assert.assertEquals(lastNameTextbox.getAttribute("value"),lastName);
        Assert.assertEquals(employeeIdTextbox.getAttribute("value"),employeeID);
        System.out.println("Step 17 - Verify #3:");
        System.out.println("\tFullname: " + firstNameTextbox.getAttribute("value") + " " + lastNameTextbox.getAttribute("value"));
        System.out.println("\tID: " + employeeIdTextbox.getAttribute("value"));

        driver.findElement(By.xpath("//a[text()='Immigration']")).click();
        sleepInSeconds(3);
        driver.findElement(By.cssSelector("i.bi-pencil-fill")).click();
        sleepInSeconds(3);

        // Step 19: Verify thông tin hiển thị
        passportTextbox = driver.findElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div/input"));
        commentsTextarea = driver.findElement(By.cssSelector("textarea[placeholder='Type Comments here']"));
        Assert.assertEquals(passportTextbox.getAttribute("value"),passport);
        Assert.assertEquals(commentsTextarea.getAttribute("value"),comments);
        System.out.println("Step 19 - Verify #4:");
        System.out.println("\tPassport Number: " + passportTextbox.getAttribute("value"));
        System.out.println("\tComments: " + commentsTextarea.getAttribute("value"));
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

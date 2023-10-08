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
        sleepInSeconds(3);
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
        WebElement firstName = driver.findElement(By.cssSelector("input[name='firstName']"));
        WebElement lastName = driver.findElement(By.cssSelector("input[name='lastName']"));
        WebElement id = driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input"));
        // Assert..........

        // Step 08: CLick vào nút Immigration
        driver.findElement(By.xpath("//a[text()='Immigration']")).click();

        // Step 09: Click Add tại Assigned Immigration Records
        driver.findElement(By.xpath("//h6[text()='Assigned Immigration Records']/following-sibling::button")).click();

        // Step 10: Nhập dữ liệu và click save
        driver.findElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div/input")).sendKeys("40517-402-96-7202");
        driver.findElement(By.cssSelector("textarea[placeholder='Type Comments here']")).sendKeys("This is generated data of real people");
        driver.findElement(By.xpath("//button[text()=' Save ']")).click();

        // Step 11: Click vào nút Pencil (Edit)
        sleepInSeconds(3);
        driver.findElement(By.cssSelector("i.bi-pencil-fill")).click();

        // Step 12: Verify dữ liệu
        WebElement ppNumber = driver.findElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div/input"));
        WebElement comments = driver.findElement(By.cssSelector("textarea[placeholder='Type Comments here']"));
        // Assert..........

        // Step 14: Click vào tên user và Logout
        driver.findElement(By.cssSelector("span.oxd-userdropdown-tab")).click();
        driver.findElement(By.xpath("//a[text()='Logout']")).click();

        // Step 15: Tại màn hình login nhập thông tin hợp lệ đã tạo ở Step 05
        sleepInSeconds(3);
        driver.findElement(By.cssSelector("input[name='username']")).sendKeys(userName);
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys("Abcd@1234");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Step 16: Vào màn hình My Info
        driver.findElement(By.xpath("//span[text()='My Info']")).click();

        // Step 17: Verify thông tin hiển thị


        // Steo 18: Vào màn hình Immigration và click nút Pencil
        driver.findElement(By.xpath("//a[text()='Immigration']")).click();
        driver.findElement(By.cssSelector("i.bi-pencil-fill")).click();

        // Step 19: Verify thông tin hiển thị



        System.out.println("User: " + userName);
        System.out.println("Password: Abcd@1234");
        System.out.println("ID: " + employeeID);

    }

    @AfterClass
    public void afterClass() {
        //driver.quit();
    }
}

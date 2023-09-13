package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Topic_04_Register {
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

        driver.get("https://alada.vn/tai-khoan/dang-ky.html");

    }

    @Test
    public void Register_01_Empty_Data() {
        driver.findElement(By.cssSelector("input#txtFirstname")).clear();
        driver.findElement(By.cssSelector("input#txtEmail")).clear();
        driver.findElement(By.cssSelector("input#txtCEmail")).clear();
        driver.findElement(By.cssSelector("input#txtPassword")).clear();
        driver.findElement(By.cssSelector("input#txtCPassword")).clear();
        driver.findElement(By.cssSelector("input#txtPhone")).clear();

        driver.findElement(By.cssSelector("button[type='submit']")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector("label#txtFirstname-error")).getText(),"Vui lòng nhập họ tên");
        Assert.assertEquals(driver.findElement(By.cssSelector("label#txtEmail-error")).getText(),"Vui lòng nhập email");
        Assert.assertEquals(driver.findElement(By.cssSelector("label#txtCEmail-error")).getText(),"Vui lòng nhập lại địa chỉ email");
        Assert.assertEquals(driver.findElement(By.cssSelector("label#txtPassword-error")).getText(),"Vui lòng nhập mật khẩu");
        Assert.assertEquals(driver.findElement(By.cssSelector("label#txtCPassword-error")).getText(),"Vui lòng nhập lại mật khẩu");
        Assert.assertEquals(driver.findElement(By.cssSelector("label#txtPhone-error")).getText(),"Vui lòng nhập số điện thoại.");

    }

    @Test
    public void Register_02_Invalid_Email() {
        driver.findElement(By.cssSelector("input#txtFirstname")).clear();
        driver.findElement(By.cssSelector("input#txtEmail")).clear();
        driver.findElement(By.cssSelector("input#txtCEmail")).clear();
        driver.findElement(By.cssSelector("input#txtPassword")).clear();
        driver.findElement(By.cssSelector("input#txtCPassword")).clear();
        driver.findElement(By.cssSelector("input#txtPhone")).clear();

        driver.findElement(By.cssSelector("input#txtFirstname")).sendKeys("Hoàng Hải");
        driver.findElement(By.cssSelector("input#txtEmail")).sendKeys("abc123");
        driver.findElement(By.cssSelector("input#txtCEmail")).sendKeys("abc123");
        driver.findElement(By.cssSelector("input#txtPassword")).sendKeys("Abc@123");
        driver.findElement(By.cssSelector("input#txtCPassword")).sendKeys("Abc@123");
        driver.findElement(By.cssSelector("input#txtPhone")).sendKeys("0987654321");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector("label#txtEmail-error")).getText(),"Vui lòng nhập email hợp lệ");
        Assert.assertEquals(driver.findElement(By.cssSelector("label#txtCEmail-error")).getText(),"Email nhập lại không đúng");

    }

    @Test
    public void Register_03_Incorrect_Confirm_Email() {
        driver.findElement(By.cssSelector("input#txtFirstname")).clear();
        driver.findElement(By.cssSelector("input#txtEmail")).clear();
        driver.findElement(By.cssSelector("input#txtCEmail")).clear();
        driver.findElement(By.cssSelector("input#txtPassword")).clear();
        driver.findElement(By.cssSelector("input#txtCPassword")).clear();
        driver.findElement(By.cssSelector("input#txtPhone")).clear();

        driver.findElement(By.cssSelector("input#txtFirstname")).sendKeys("Hoàng Hải");
        driver.findElement(By.cssSelector("input#txtEmail")).sendKeys("hai@gmail.com");
        driver.findElement(By.cssSelector("input#txtCEmail")).sendKeys("haii@gmail.com");
        driver.findElement(By.cssSelector("input#txtPassword")).sendKeys("Abc@123");
        driver.findElement(By.cssSelector("input#txtCPassword")).sendKeys("Abc@123");
        driver.findElement(By.cssSelector("input#txtPhone")).sendKeys("0987654321");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector("label#txtCEmail-error")).getText(),"Email nhập lại không đúng");

    }

    @Test
    public void Register_04_Incorrect_Password() {
        driver.findElement(By.cssSelector("input#txtFirstname")).clear();
        driver.findElement(By.cssSelector("input#txtEmail")).clear();
        driver.findElement(By.cssSelector("input#txtCEmail")).clear();
        driver.findElement(By.cssSelector("input#txtPassword")).clear();
        driver.findElement(By.cssSelector("input#txtCPassword")).clear();
        driver.findElement(By.cssSelector("input#txtPhone")).clear();

        driver.findElement(By.cssSelector("input#txtFirstname")).sendKeys("Hoàng Hải");
        driver.findElement(By.cssSelector("input#txtEmail")).sendKeys("hai@gmail.com");
        driver.findElement(By.cssSelector("input#txtCEmail")).sendKeys("hai@gmail.com");
        driver.findElement(By.cssSelector("input#txtPassword")).sendKeys("Ab@12");
        driver.findElement(By.cssSelector("input#txtCPassword")).sendKeys("Ab@12");
        driver.findElement(By.cssSelector("input#txtPhone")).sendKeys("0987654321");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector("label#txtPassword-error")).getText(),"Mật khẩu phải có ít nhất 6 ký tự");
        Assert.assertEquals(driver.findElement(By.cssSelector("label#txtCPassword-error")).getText(),"Mật khẩu phải có ít nhất 6 ký tự");

    }

    @Test
    public void Register_05_Incorrect_Confirm_Password() {
        driver.findElement(By.cssSelector("input#txtFirstname")).clear();
        driver.findElement(By.cssSelector("input#txtEmail")).clear();
        driver.findElement(By.cssSelector("input#txtCEmail")).clear();
        driver.findElement(By.cssSelector("input#txtPassword")).clear();
        driver.findElement(By.cssSelector("input#txtCPassword")).clear();
        driver.findElement(By.cssSelector("input#txtPhone")).clear();

        driver.findElement(By.cssSelector("input#txtFirstname")).sendKeys("Hoàng Hải");
        driver.findElement(By.cssSelector("input#txtEmail")).sendKeys("hai@gmail.com");
        driver.findElement(By.cssSelector("input#txtCEmail")).sendKeys("hai@gmail.com");
        driver.findElement(By.cssSelector("input#txtPassword")).sendKeys("Abc@123");
        driver.findElement(By.cssSelector("input#txtCPassword")).sendKeys("Abc@124");
        driver.findElement(By.cssSelector("input#txtPhone")).sendKeys("0987654321");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector("label#txtCPassword-error")).getText(),"Mật khẩu bạn nhập không khớp");

    }

    @Test
    public void Register_06_Invalid_Phone_Number() {
        driver.findElement(By.cssSelector("input#txtFirstname")).clear();
        driver.findElement(By.cssSelector("input#txtEmail")).clear();
        driver.findElement(By.cssSelector("input#txtCEmail")).clear();
        driver.findElement(By.cssSelector("input#txtPassword")).clear();
        driver.findElement(By.cssSelector("input#txtCPassword")).clear();
        driver.findElement(By.cssSelector("input#txtPhone")).clear();

        driver.findElement(By.cssSelector("input#txtFirstname")).sendKeys("Hoàng Hải");
        driver.findElement(By.cssSelector("input#txtEmail")).sendKeys("hai@gmail.com");
        driver.findElement(By.cssSelector("input#txtCEmail")).sendKeys("hai@gmail.com");
        driver.findElement(By.cssSelector("input#txtPassword")).sendKeys("Abc@123");
        driver.findElement(By.cssSelector("input#txtCPassword")).sendKeys("Abc@123");

        // 9 digits
        driver.findElement(By.cssSelector("input#txtPhone")).sendKeys("098765432");

        driver.findElement(By.cssSelector("button[type='submit']")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector("label#txtPhone-error")).getText(),"Số điện thoại phải từ 10-11 số.");

        // 12 digits
        driver.findElement(By.cssSelector("input#txtPhone")).clear();
        driver.findElement(By.cssSelector("input#txtPhone")).sendKeys("098765432100");

        driver.findElement(By.cssSelector("button[type='submit']")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector("label#txtPhone-error")).getText(),"Số điện thoại phải từ 10-11 số.");

        // Start with
        driver.findElement(By.cssSelector("input#txtPhone")).clear();
        driver.findElement(By.cssSelector("input#txtPhone")).sendKeys("1987654321");

        driver.findElement(By.cssSelector("button[type='submit']")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector("label#txtPhone-error")).getText(),"Số điện thoại bắt đầu bằng: 09 - 03 - 012 - 016 - 018 - 019 - 088 - 03 - 05 - 07 - 08");

    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

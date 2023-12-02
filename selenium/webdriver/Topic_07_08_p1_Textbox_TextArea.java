package webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Random;

public class Topic_07_08_p1_Textbox_TextArea {
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
    public void TC_01_Register_Login() {
        // Login with empty data
        driver.get("http://live.techpanda.org/");

        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("pass")).clear();
        driver.findElement(By.id("send2")).click();

        Assert.assertEquals(driver.findElement(By.id("advice-required-entry-email")).getText(),"This is a required field.");
        Assert.assertEquals(driver.findElement(By.id("advice-required-entry-pass")).getText(),"This is a required field.");

        // Login with invalid email
        driver.get("http://live.techpanda.org/");

        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys("123@12.123");
        driver.findElement(By.id("pass")).clear();
        driver.findElement(By.id("pass")).sendKeys("123456");
        driver.findElement(By.id("send2")).click();

        Assert.assertEquals(driver.findElement(By.id("advice-validate-email-email")).getText(),"Please enter a valid email address. For example johndoe@domain.com.");

        // Login with invalid password
        driver.get("http://live.techpanda.org/");

        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys("automation@gmail.com");
        driver.findElement(By.id("pass")).clear();
        driver.findElement(By.id("pass")).sendKeys("123");
        driver.findElement(By.id("send2")).click();

        Assert.assertEquals(driver.findElement(By.id("advice-validate-password-pass")).getText(),"Please enter 6 or more characters without leading or trailing spaces.");

        // Login with incorrect email
        driver.get("http://live.techpanda.org/");

        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        driver.findElement(By.id("email")).clear();
        String incorrectEmail = getEmailRandom();
        System.out.println("Login_04:\n" + incorrectEmail);
        driver.findElement(By.id("email")).sendKeys(incorrectEmail);
        driver.findElement(By.id("pass")).clear();
        driver.findElement(By.id("pass")).sendKeys("123123123");
        driver.findElement(By.id("send2")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector("li.error-msg span")).getText(),"Invalid login or password.");

        // Register
        driver.get("http://live.techpanda.org/");
        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        driver.findElement(By.xpath("//span[text()='Create an Account']")).click();

        String firstName = "Hai", middleName = "Hoang", lastName = "Phan", password = "Abc@1234";
        String fullName = firstName + " " + middleName + " " + lastName;
        String email = getEmailRandom();

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
        System.out.println("Login_05:\n" + contactInfo);

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

        // Logout
        driver.findElement(By.xpath("//header//span[text()='Account']")).click();
        driver.findElement(By.xpath("//a[text()='Log Out']")).click();
        sleepInSeconds(6);

        // Login with incorrect password
        driver.get("http://live.techpanda.org/");

        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        driver.findElement(By.id("email")).clear();
        System.out.println("Login_06:\n" + email);
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("pass")).clear();
        driver.findElement(By.id("pass")).sendKeys("123123123");
        driver.findElement(By.id("send2")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector("li.error-msg span")).getText(),"Invalid login or password.");
    }

    @Test
    public void TC_02_NoShow_AttributeValue() {
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

    @Test
    public void TC_03_BlockAds_AcceptAlert() {
        driver.get("https://demo.guru99.com/");
        sleepInSeconds(1);

        String emailGuru = getEmailRandom();
        driver.findElement(By.cssSelector("input[name='emailid'")).sendKeys(emailGuru);
        driver.findElement(By.cssSelector("input[name='btnLogin']")).click();
        sleepInSeconds(1);

        String userID = driver.findElement(By.xpath("//td[text()='User ID :']/following-sibling::td")).getText();
        String userPassword = driver.findElement(By.xpath("//td[text()='Password :']/following-sibling::td")).getText();
        System.out.println("Email Guru: " + emailGuru);
        System.out.println("User ID: " + userID);
        System.out.println("Password: " + userPassword);

        driver.get("https://demo.guru99.com/v4/");
        driver.findElement(By.cssSelector("input[name='uid']")).sendKeys(userID);
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys(userPassword);
        driver.findElement(By.cssSelector("input[name='btnLogin']")).click();
        sleepInSeconds(1);

        driver.findElement(By.xpath("//a[text()='New Customer']")).click();
        sleepInSeconds(1);

        driver.findElement(By.cssSelector("input[name='name']")).sendKeys("Automation Testing");
        driver.findElement(By.xpath("//td[text()='Gender']/following-sibling::td/input[@value='m']")).click();
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('type');", driver.findElement(By.cssSelector("input#dob")));
        driver.findElement(By.cssSelector("input#dob")).sendKeys("10/20/1980");
        driver.findElement(By.cssSelector("textarea[name='addr']")).sendKeys("12 Ly Tu Trong");
        driver.findElement(By.cssSelector("input[name='city']")).sendKeys("Da Nang");
        driver.findElement(By.cssSelector("input[name='state']")).sendKeys("Viet Nam");
        driver.findElement(By.cssSelector("input[name='pinno']")).sendKeys("123456");
        driver.findElement(By.cssSelector("input[name='telephoneno']")).sendKeys("0905123456");
        String emailNewCustomer = getEmailRandom();
        driver.findElement(By.cssSelector("input[name='emailid']")).sendKeys(emailNewCustomer);
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys("Abcd@1234");
        driver.findElement(By.cssSelector("input[name='sub']")).click();
        sleepInSeconds(1);

        String customerID = driver.findElement(By.xpath("//td[text()='Customer ID']/following-sibling::td")).getText();
        System.out.println("Customer ID: " + customerID);

        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(),"Automation Testing");
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText(),"1980-10-20");
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(),"12 Ly Tu Trong");
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(),"Da Nang");
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(),"Viet Nam");
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(),"123456");
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(),"0905123456");
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(), emailNewCustomer);

        driver.findElement(By.xpath("//a[text()='Edit Customer']")).click();
        sleepInSeconds(1);

        driver.findElement(By.cssSelector("input[name='cusid']")).sendKeys(customerID);
        driver.findElement(By.cssSelector("input[name='AccSubmit']")).click();
        sleepInSeconds(1);

        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td/input")).getAttribute("value"),"Automation Testing");
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td/textarea")).getText(),"12 Ly Tu Trong");

        driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td/textarea")).clear();
        driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td/textarea")).sendKeys("12 Ly Tu Trong edited");
        driver.findElement(By.cssSelector("input[name='city']")).clear();
        driver.findElement(By.cssSelector("input[name='city']")).sendKeys("Da Nang edited");
        driver.findElement(By.cssSelector("input[name='state']")).clear();
        driver.findElement(By.cssSelector("input[name='state']")).sendKeys("Viet Nam edited");
        driver.findElement(By.cssSelector("input[name='pinno']")).clear();
        driver.findElement(By.cssSelector("input[name='pinno']")).sendKeys("654321");
        driver.findElement(By.cssSelector("input[name='telephoneno']")).clear();
        driver.findElement(By.cssSelector("input[name='telephoneno']")).sendKeys("0905654321");
        String emailEditCustomer = getEmailRandom();
        driver.findElement(By.cssSelector("input[name='emailid']")).clear();
        driver.findElement(By.cssSelector("input[name='emailid']")).sendKeys(emailEditCustomer);
        driver.findElement(By.cssSelector("input[name='sub']")).click();
        sleepInSeconds(1);

        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        explicitWait.until(ExpectedConditions.alertIsPresent()).accept();
        sleepInSeconds(1);

        driver.findElement(By.cssSelector("input[name='cusid']")).sendKeys(customerID);
        driver.findElement(By.cssSelector("input[name='AccSubmit']")).click();
        sleepInSeconds(1);

        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td/textarea")).getText(),"12 Ly Tu Trong edited");
        Assert.assertEquals(driver.findElement(By.cssSelector("input[name='city']")).getAttribute("value"),"Da Nang edited");
        Assert.assertEquals(driver.findElement(By.cssSelector("input[name='state']")).getAttribute("value"),"Viet Nam edited");
        Assert.assertEquals(driver.findElement(By.cssSelector("input[name='pinno']")).getAttribute("value"),"654321");
        Assert.assertEquals(driver.findElement(By.cssSelector("input[name='telephoneno']")).getAttribute("value"),"0905654321");
        Assert.assertEquals(driver.findElement(By.cssSelector("input[name='emailid']")).getAttribute("value"), emailEditCustomer);
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

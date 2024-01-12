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
    WebDriverWait explicitWait;

    public String getEmailRandom() {
        Random rand = new Random();
        return "automation" + rand.nextInt(999999) + "@gmail.com";
        // return "automation" + new Random().nextInt(999999) + "@gmail.com"
    }

    public WebElement findVisibleElement(By locator) {
        return explicitWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForInvisibility(By locator) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    public void clickOnElement(By locator) {
        explicitWait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Test
    public void TC_01_Register_Login() {
        // Login with empty data
        driver.get("http://live.techpanda.org/");

        clickOnElement(By.xpath("//div[@class='footer']//a[text()='My Account']"));
        findVisibleElement(By.id("email")).clear();
        findVisibleElement(By.id("pass")).clear();
        clickOnElement(By.id("send2"));

        Assert.assertEquals(findVisibleElement(By.id("advice-required-entry-email")).getText(),"This is a required field.");
        Assert.assertEquals(findVisibleElement(By.id("advice-required-entry-pass")).getText(),"This is a required field.");

        // Login with invalid email
        driver.get("http://live.techpanda.org/");

        clickOnElement(By.xpath("//div[@class='footer']//a[text()='My Account']"));
        findVisibleElement(By.id("email")).clear();
        findVisibleElement(By.id("email")).sendKeys("123@12.123");
        findVisibleElement(By.id("pass")).clear();
        findVisibleElement(By.id("pass")).sendKeys("123456");
        clickOnElement(By.id("send2"));

        Assert.assertEquals(findVisibleElement(By.id("advice-validate-email-email")).getText(),"Please enter a valid email address. For example johndoe@domain.com.");

        // Login with invalid password
        driver.get("http://live.techpanda.org/");

        clickOnElement(By.xpath("//div[@class='footer']//a[text()='My Account']"));
        findVisibleElement(By.id("email")).clear();
        findVisibleElement(By.id("email")).sendKeys("automation@gmail.com");
        findVisibleElement(By.id("pass")).clear();
        findVisibleElement(By.id("pass")).sendKeys("123");
        clickOnElement(By.id("send2"));

        Assert.assertEquals(findVisibleElement(By.id("advice-validate-password-pass")).getText(),"Please enter 6 or more characters without leading or trailing spaces.");

        // Login with incorrect email
        driver.get("http://live.techpanda.org/");

        clickOnElement(By.xpath("//div[@class='footer']//a[text()='My Account']"));
        findVisibleElement(By.id("email")).clear();
        String incorrectEmail = getEmailRandom();
        System.out.println("Login_04:\n" + incorrectEmail);
        findVisibleElement(By.id("email")).sendKeys(incorrectEmail);
        findVisibleElement(By.id("pass")).clear();
        findVisibleElement(By.id("pass")).sendKeys("123123123");
        clickOnElement(By.id("send2"));

        Assert.assertEquals(findVisibleElement(By.cssSelector("li.error-msg span")).getText(),"Invalid login or password.");

        // Register
        driver.get("http://live.techpanda.org/");
        clickOnElement(By.xpath("//div[@class='footer']//a[text()='My Account']"));
        clickOnElement(By.xpath("//span[text()='Create an Account']"));

        String firstName = "Hai", middleName = "Hoang", lastName = "Phan", password = "Abc@1234";
        String fullName = firstName + " " + middleName + " " + lastName;
        String email = getEmailRandom();

        findVisibleElement(By.cssSelector("input[id='firstname']")).sendKeys(firstName);
        findVisibleElement(By.cssSelector("input[id='middlename']")).sendKeys(middleName);
        findVisibleElement(By.cssSelector("input[id='lastname']")).sendKeys(lastName);
        findVisibleElement(By.cssSelector("input[id='email_address']")).sendKeys(email);
        findVisibleElement(By.cssSelector("input[id='password']")).sendKeys(password);
        findVisibleElement(By.cssSelector("input[id='confirmation']")).sendKeys(password);
        clickOnElement(By.xpath("//span[text()='Register']"));

        Assert.assertEquals(findVisibleElement(By.cssSelector("li[class='success-msg'] span")).getText(),"Thank you for registering with Main Website Store.");

        Assert.assertEquals(findVisibleElement(By.cssSelector("div.welcome-msg strong")).getText(),"Hello, " + fullName + "!");

        String contactInfo = findVisibleElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div/p")).getText();
        Assert.assertTrue(contactInfo.contains(fullName));
        Assert.assertTrue(contactInfo.contains(email));
        System.out.println("Login_05:\n" + contactInfo);

        clickOnElement(By.xpath("//header//span[text()='Account']"));
        clickOnElement(By.xpath("//a[text()='Log Out']"));

        // Login success
        clickOnElement(By.xpath("//div[@class='footer']//a[text()='My Account']"));
        findVisibleElement(By.id("email")).clear();
        findVisibleElement(By.id("email")).sendKeys(email);
        findVisibleElement(By.id("pass")).clear();
        findVisibleElement(By.id("pass")).sendKeys(password);
        clickOnElement(By.id("send2"));

        Assert.assertEquals(findVisibleElement(By.cssSelector("div.welcome-msg strong")).getText(),"Hello, " + fullName + "!");

        contactInfo = findVisibleElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div/p")).getText();
        Assert.assertTrue(contactInfo.contains(fullName));
        Assert.assertTrue(contactInfo.contains(email));

        clickOnElement(By.xpath("//a[text()='Account Information']"));
        Assert.assertEquals(findVisibleElement(By.cssSelector("input[id='firstname']")).getAttribute("value"),firstName);
        Assert.assertEquals(findVisibleElement(By.cssSelector("input[id='middlename']")).getAttribute("value"),middleName);
        Assert.assertEquals(findVisibleElement(By.cssSelector("input[id='lastname']")).getAttribute("value"),lastName);
        Assert.assertEquals(findVisibleElement(By.cssSelector("input[id='email']")).getAttribute("value"),email);

        // Change password
        findVisibleElement(By.cssSelector("input[id='current_password']")).sendKeys(password);
        if (!(findVisibleElement(By.id("change_password")).isSelected())) {
            clickOnElement(By.id("change_password"));
        }

        String newPassword = "Def!5678";
        findVisibleElement(By.id("password")).sendKeys(newPassword);
        findVisibleElement(By.id("confirmation")).sendKeys(newPassword);
        clickOnElement(By.cssSelector("button[title='Save']"));

        Assert.assertEquals(findVisibleElement(By.cssSelector("li[class='success-msg'] span")).getText(),"The account information has been saved.");

        // Login with incorrect password (old password)
        clickOnElement(By.xpath("//header//span[text()='Account']"));
        clickOnElement(By.xpath("//a[text()='Log Out']"));

        clickOnElement(By.xpath("//div[@class='footer']//a[text()='My Account']"));
        findVisibleElement(By.id("email")).clear();
        findVisibleElement(By.id("email")).sendKeys(email);
        findVisibleElement(By.id("pass")).clear();
        findVisibleElement(By.id("pass")).sendKeys(password);
        clickOnElement(By.id("send2"));

        Assert.assertEquals(findVisibleElement(By.cssSelector("li.error-msg span")).getText(),"Invalid login or password.");

        // Re login with new password
        findVisibleElement(By.id("pass")).clear();
        findVisibleElement(By.id("pass")).sendKeys(newPassword);
        clickOnElement(By.id("send2"));

        Assert.assertEquals(findVisibleElement(By.cssSelector("div.welcome-msg strong")).getText(),"Hello, " + fullName + "!");
    }

    @Test
    public void TC_02_NoShow_AttributeValue() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        findVisibleElement(By.cssSelector("input[name='username']")).sendKeys("Admin");
        findVisibleElement(By.cssSelector("input[name='password']")).sendKeys("admin123");
        clickOnElement(By.cssSelector("button[type='submit']"));

        clickOnElement(By.xpath("//span[text()='PIM']"));
        clickOnElement(By.xpath("//a[text()='Add Employee']"));

        // Step 05: Nhập thông tin hợp lệ
        String firstName = "Automation", lastName = "Testing", password = "Abcd@1234";
        String userName = "automation" + Math.round(Math.random()*1000000);
        String passport= "40517-402-96-7202", comments = "This is generated data of real people";

        findVisibleElement(By.cssSelector("input[placeholder='First Name']")).sendKeys(firstName);
        findVisibleElement(By.cssSelector("input[placeholder='Last Name']")).sendKeys(lastName);

        waitForInvisibility(By.cssSelector("div.oxd-form-loader"));
        String employeeID = findVisibleElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input")).getAttribute("value");

        clickOnElement(By.xpath("//p[text()='Create Login Details']/following-sibling::div//span"));

        findVisibleElement(By.xpath("//label[text()='Username']/parent::div/following-sibling::div/input")).clear();
        findVisibleElement(By.xpath("//label[text()='Username']/parent::div/following-sibling::div/input")).sendKeys(userName);
        findVisibleElement(By.xpath("//label[text()='Password']/parent::div/following-sibling::div/input")).clear();
        findVisibleElement(By.xpath("//label[text()='Password']/parent::div/following-sibling::div/input")).sendKeys(password);
        findVisibleElement(By.xpath("//label[text()='Confirm Password']/parent::div/following-sibling::div/input")).sendKeys(password);
        clickOnElement(By.xpath("//button[text()=' Save ']"));

        // Step 07: Verify dữ liệu đã nhập
        waitForInvisibility(By.cssSelector("div.oxd-form-loader"));
        WebElement firstNameTextbox = findVisibleElement(By.cssSelector("input[name='firstName']"));
        WebElement lastNameTextbox = findVisibleElement(By.cssSelector("input[name='lastName']"));
        WebElement employeeIdTextbox = findVisibleElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input"));
        Assert.assertEquals(firstNameTextbox.getAttribute("value"),firstName);
        Assert.assertEquals(lastNameTextbox.getAttribute("value"),lastName);
        Assert.assertEquals(employeeIdTextbox.getAttribute("value"),employeeID);
        System.out.println("Step 07 - Verify #1:");
        System.out.println("\tFullname: " + firstNameTextbox.getAttribute("value") + " " + lastNameTextbox.getAttribute("value"));
        System.out.println("\tID: " + employeeIdTextbox.getAttribute("value"));

        clickOnElement(By.xpath("//a[text()='Immigration']"));
        clickOnElement(By.xpath("//h6[text()='Assigned Immigration Records']/following-sibling::button"));

        // Step 10: Nhập dữ liệu và click save
        findVisibleElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div/input")).sendKeys(passport);
        findVisibleElement(By.cssSelector("textarea[placeholder='Type Comments here']")).sendKeys(comments);
        clickOnElement(By.xpath("//button[text()=' Save ']"));

        clickOnElement(By.cssSelector("i.bi-pencil-fill"));

        // Step 12: Verify dữ liệu đã nhập
        waitForInvisibility(By.cssSelector("div.oxd-form-loader"));
        WebElement passportTextbox = findVisibleElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div/input"));
        WebElement commentsTextarea = findVisibleElement(By.cssSelector("textarea[placeholder='Type Comments here']"));
        Assert.assertEquals(passportTextbox.getAttribute("value"),passport);
        Assert.assertEquals(commentsTextarea.getAttribute("value"),comments);
        System.out.println("Step 12 - Verify #2:");
        System.out.println("\tPassport Number: " + passportTextbox.getAttribute("value"));
        System.out.println("\tComments: " + commentsTextarea.getAttribute("value"));

        // Step 14: Click vào tên user và Logout
        clickOnElement(By.cssSelector("span.oxd-userdropdown-tab"));
        clickOnElement(By.xpath("//a[text()='Logout']"));

        // Step 15: Tại màn hình login nhập thông tin hợp lệ đã tạo ở Step 05
        findVisibleElement(By.cssSelector("input[name='username']")).sendKeys(userName);
        findVisibleElement(By.cssSelector("input[name='password']")).sendKeys(password);
        clickOnElement(By.cssSelector("button[type='submit']"));

        clickOnElement(By.xpath("//span[text()='My Info']"));

        // Step 17: Verify thông tin hiển thị
        waitForInvisibility(By.cssSelector("div.oxd-form-loader"));
        firstNameTextbox = findVisibleElement(By.cssSelector("input[name='firstName']"));
        lastNameTextbox = findVisibleElement(By.cssSelector("input[name='lastName']"));
        employeeIdTextbox = findVisibleElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input"));
        Assert.assertEquals(firstNameTextbox.getAttribute("value"),firstName);
        Assert.assertEquals(lastNameTextbox.getAttribute("value"),lastName);
        Assert.assertEquals(employeeIdTextbox.getAttribute("value"),employeeID);
        System.out.println("Step 17 - Verify #3:");
        System.out.println("\tFullname: " + firstNameTextbox.getAttribute("value") + " " + lastNameTextbox.getAttribute("value"));
        System.out.println("\tID: " + employeeIdTextbox.getAttribute("value"));

        clickOnElement(By.xpath("//a[text()='Immigration']"));
        clickOnElement(By.cssSelector("i.bi-pencil-fill"));

        // Step 19: Verify thông tin hiển thị
        waitForInvisibility(By.cssSelector("div.oxd-form-loader"));
        passportTextbox = findVisibleElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div/input"));
        commentsTextarea = findVisibleElement(By.cssSelector("textarea[placeholder='Type Comments here']"));
        Assert.assertEquals(passportTextbox.getAttribute("value"),passport);
        Assert.assertEquals(commentsTextarea.getAttribute("value"),comments);
        System.out.println("Step 19 - Verify #4:");
        System.out.println("\tPassport Number: " + passportTextbox.getAttribute("value"));
        System.out.println("\tComments: " + commentsTextarea.getAttribute("value"));
    }

    @Test
    public void TC_03_BlockAds_AcceptAlert() {
        driver.get("https://demo.guru99.com/");

        String emailGuru = getEmailRandom();
        findVisibleElement(By.cssSelector("input[name='emailid'")).sendKeys(emailGuru);
        clickOnElement(By.cssSelector("input[name='btnLogin']"));

        String userID = findVisibleElement(By.xpath("//td[text()='User ID :']/following-sibling::td")).getText();
        String userPassword = findVisibleElement(By.xpath("//td[text()='Password :']/following-sibling::td")).getText();
        System.out.println("Email Guru: " + emailGuru);
        System.out.println("User ID: " + userID);
        System.out.println("Password: " + userPassword);

        driver.get("https://demo.guru99.com/v4/");
        
        findVisibleElement(By.cssSelector("input[name='uid']")).sendKeys(userID);
        findVisibleElement(By.cssSelector("input[name='password']")).sendKeys(userPassword);
        clickOnElement(By.cssSelector("input[name='btnLogin']"));

        clickOnElement(By.xpath("//a[text()='New Customer']"));

        findVisibleElement(By.cssSelector("input[name='name']")).sendKeys("Automation Testing");
        clickOnElement(By.xpath("//td[text()='Gender']/following-sibling::td/input[@value='m']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('type');", driver.findElement(By.cssSelector("input#dob")));
        findVisibleElement(By.cssSelector("input#dob")).sendKeys("10/20/1980");
        findVisibleElement(By.cssSelector("textarea[name='addr']")).sendKeys("12 Ly Tu Trong");
        findVisibleElement(By.cssSelector("input[name='city']")).sendKeys("Da Nang");
        findVisibleElement(By.cssSelector("input[name='state']")).sendKeys("Viet Nam");
        findVisibleElement(By.cssSelector("input[name='pinno']")).sendKeys("123456");
        findVisibleElement(By.cssSelector("input[name='telephoneno']")).sendKeys("0905123456");
        String emailNewCustomer = getEmailRandom();
        findVisibleElement(By.cssSelector("input[name='emailid']")).sendKeys(emailNewCustomer);
        findVisibleElement(By.cssSelector("input[name='password']")).sendKeys("Abcd@1234");
        clickOnElement(By.cssSelector("input[name='sub']"));

        String customerID = findVisibleElement(By.xpath("//td[text()='Customer ID']/following-sibling::td")).getText();
        System.out.println("Customer ID: " + customerID);

        Assert.assertEquals(findVisibleElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(),"Automation Testing");
        Assert.assertEquals(findVisibleElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText(),"1980-10-20");
        Assert.assertEquals(findVisibleElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(),"12 Ly Tu Trong");
        Assert.assertEquals(findVisibleElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(),"Da Nang");
        Assert.assertEquals(findVisibleElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(),"Viet Nam");
        Assert.assertEquals(findVisibleElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(),"123456");
        Assert.assertEquals(findVisibleElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(),"0905123456");
        Assert.assertEquals(findVisibleElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(), emailNewCustomer);

        clickOnElement(By.xpath("//a[text()='Edit Customer']"));

        findVisibleElement(By.cssSelector("input[name='cusid']")).sendKeys(customerID);
        clickOnElement(By.cssSelector("input[name='AccSubmit']"));

        Assert.assertEquals(findVisibleElement(By.xpath("//td[text()='Customer Name']/following-sibling::td/input")).getAttribute("value"),"Automation Testing");
        Assert.assertEquals(findVisibleElement(By.xpath("//td[text()='Address']/following-sibling::td/textarea")).getText(),"12 Ly Tu Trong");

        findVisibleElement(By.xpath("//td[text()='Address']/following-sibling::td/textarea")).clear();
        findVisibleElement(By.xpath("//td[text()='Address']/following-sibling::td/textarea")).sendKeys("12 Ly Tu Trong edited");
        findVisibleElement(By.cssSelector("input[name='city']")).clear();
        findVisibleElement(By.cssSelector("input[name='city']")).sendKeys("Da Nang edited");
        findVisibleElement(By.cssSelector("input[name='state']")).clear();
        findVisibleElement(By.cssSelector("input[name='state']")).sendKeys("Viet Nam edited");
        findVisibleElement(By.cssSelector("input[name='pinno']")).clear();
        findVisibleElement(By.cssSelector("input[name='pinno']")).sendKeys("654321");
        findVisibleElement(By.cssSelector("input[name='telephoneno']")).clear();
        findVisibleElement(By.cssSelector("input[name='telephoneno']")).sendKeys("0905654321");
        String emailEditCustomer = getEmailRandom();
        findVisibleElement(By.cssSelector("input[name='emailid']")).clear();
        findVisibleElement(By.cssSelector("input[name='emailid']")).sendKeys(emailEditCustomer);
        clickOnElement(By.cssSelector("input[name='sub']"));

        explicitWait.until(ExpectedConditions.alertIsPresent()).accept();

        findVisibleElement(By.cssSelector("input[name='cusid']")).sendKeys(customerID);
        clickOnElement(By.cssSelector("input[name='AccSubmit']"));

        Assert.assertEquals(findVisibleElement(By.xpath("//td[text()='Address']/following-sibling::td/textarea")).getText(),"12 Ly Tu Trong edited");
        Assert.assertEquals(findVisibleElement(By.cssSelector("input[name='city']")).getAttribute("value"),"Da Nang edited");
        Assert.assertEquals(findVisibleElement(By.cssSelector("input[name='state']")).getAttribute("value"),"Viet Nam edited");
        Assert.assertEquals(findVisibleElement(By.cssSelector("input[name='pinno']")).getAttribute("value"),"654321");
        Assert.assertEquals(findVisibleElement(By.cssSelector("input[name='telephoneno']")).getAttribute("value"),"0905654321");
        Assert.assertEquals(findVisibleElement(By.cssSelector("input[name='emailid']")).getAttribute("value"), emailEditCustomer);
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

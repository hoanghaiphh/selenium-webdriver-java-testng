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

    public String getRandomEmail() {
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
    public void TC_01_techpanda() {
        driver.get("http://live.techpanda.org/");

        clickOnElement(By.xpath("//div[@class='footer']//a[text()='My Account']"));

        // Login with empty data
        findVisibleElement(By.cssSelector("input#email")).clear();
        findVisibleElement(By.cssSelector("input#pass")).clear();
        clickOnElement(By.cssSelector("button#send2"));
        Assert.assertEquals(findVisibleElement(By.cssSelector("div#advice-required-entry-email")).getText(),"This is a required field.");
        Assert.assertEquals(findVisibleElement(By.cssSelector("div#advice-required-entry-pass")).getText(),"This is a required field.");

        // Login with invalid email
        findVisibleElement(By.cssSelector("input#email")).sendKeys("123@12.123");
        findVisibleElement(By.cssSelector("input#pass")).sendKeys("123456");
        clickOnElement(By.cssSelector("button#send2"));
        Assert.assertEquals(findVisibleElement(By.cssSelector("div#advice-validate-email-email")).getText(),"Please enter a valid email address. For example johndoe@domain.com.");

        // Login with invalid password
        findVisibleElement(By.cssSelector("input#email")).clear();
        findVisibleElement(By.cssSelector("input#pass")).clear();
        findVisibleElement(By.cssSelector("input#email")).sendKeys("automation@gmail.com");
        findVisibleElement(By.cssSelector("input#pass")).sendKeys("123");
        clickOnElement(By.cssSelector("button#send2"));
        Assert.assertEquals(findVisibleElement(By.cssSelector("div#advice-validate-password-pass")).getText(),"Please enter 6 or more characters without leading or trailing spaces.");

        // Login with incorrect email
        findVisibleElement(By.cssSelector("input#email")).clear();
        findVisibleElement(By.cssSelector("input#pass")).clear();
        findVisibleElement(By.cssSelector("input#email")).sendKeys(getRandomEmail());
        findVisibleElement(By.cssSelector("input#pass")).sendKeys("123123123");
        clickOnElement(By.cssSelector("button#send2"));
        Assert.assertEquals(findVisibleElement(By.cssSelector("li.error-msg span")).getText(),"Invalid login or password.");

        // Register
        clickOnElement(By.xpath("//span[text()='Create an Account']"));

        String firstName = "Automation", middleName = "Selenium", lastName = "Testing", password = "Abc@1234";
        String fullName = firstName + " " + middleName + " " + lastName;
        String email = getRandomEmail();
        findVisibleElement(By.cssSelector("input#firstname")).sendKeys(firstName);
        findVisibleElement(By.cssSelector("input#middlename")).sendKeys(middleName);
        findVisibleElement(By.cssSelector("input#lastname")).sendKeys(lastName);
        findVisibleElement(By.cssSelector("input#email_address")).sendKeys(email);
        findVisibleElement(By.cssSelector("input#password")).sendKeys(password);
        findVisibleElement(By.cssSelector("input#confirmation")).sendKeys(password);
        clickOnElement(By.xpath("//span[text()='Register']"));

        Assert.assertEquals(findVisibleElement(By.cssSelector("li.success-msg span")).getText(),"Thank you for registering with Main Website Store.");
        Assert.assertEquals(findVisibleElement(By.cssSelector("div.welcome-msg strong")).getText(),"Hello, " + fullName + "!");
        String contactInfo = findVisibleElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div/p")).getText();
        Assert.assertTrue(contactInfo.contains(fullName));
        Assert.assertTrue(contactInfo.contains(email));

        clickOnElement(By.xpath("//header//span[text()='Account']"));
        clickOnElement(By.xpath("//a[text()='Log Out']"));

        // Login success
        clickOnElement(By.xpath("//div[@class='footer']//a[text()='My Account']"));

        findVisibleElement(By.cssSelector("input#email")).clear();
        findVisibleElement(By.cssSelector("input#pass")).clear();
        findVisibleElement(By.cssSelector("input#email")).sendKeys(email);
        findVisibleElement(By.cssSelector("input#pass")).sendKeys(password);
        clickOnElement(By.cssSelector("button#send2"));

        Assert.assertEquals(findVisibleElement(By.cssSelector("div.welcome-msg strong")).getText(),"Hello, " + fullName + "!");
        contactInfo = findVisibleElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div/p")).getText();
        Assert.assertTrue(contactInfo.contains(fullName));
        Assert.assertTrue(contactInfo.contains(email));

        clickOnElement(By.xpath("//a[text()='Account Information']"));

        Assert.assertEquals(findVisibleElement(By.cssSelector("input#firstname")).getAttribute("value"),firstName);
        Assert.assertEquals(findVisibleElement(By.cssSelector("input#middlename")).getAttribute("value"),middleName);
        Assert.assertEquals(findVisibleElement(By.cssSelector("input#lastname")).getAttribute("value"),lastName);
        Assert.assertEquals(findVisibleElement(By.cssSelector("input#email")).getAttribute("value"),email);

        // Change password
        findVisibleElement(By.cssSelector("input#current_password")).sendKeys(password);

        if (!(findVisibleElement(By.cssSelector("input#change_password")).isSelected())) {
            clickOnElement(By.cssSelector("input#change_password"));
        }
        explicitWait.until(ExpectedConditions.elementToBeSelected(By.cssSelector("input#change_password")));

        String newPassword = "Def!5678";
        findVisibleElement(By.cssSelector("input#password")).sendKeys(newPassword);
        findVisibleElement(By.cssSelector("input#confirmation")).sendKeys(newPassword);
        clickOnElement(By.cssSelector("button[title='Save']"));
        Assert.assertEquals(findVisibleElement(By.cssSelector("li.success-msg span")).getText(),"The account information has been saved.");

        clickOnElement(By.xpath("//header//span[text()='Account']"));
        clickOnElement(By.xpath("//a[text()='Log Out']"));

        // Login with incorrect password (old password)
        clickOnElement(By.xpath("//div[@class='footer']//a[text()='My Account']"));

        findVisibleElement(By.cssSelector("input#email")).clear();
        findVisibleElement(By.cssSelector("input#pass")).clear();
        findVisibleElement(By.cssSelector("input#email")).sendKeys(email);
        findVisibleElement(By.cssSelector("input#pass")).sendKeys(password);
        clickOnElement(By.cssSelector("button#send2"));
        Assert.assertEquals(findVisibleElement(By.cssSelector("li.error-msg span")).getText(),"Invalid login or password.");

        // Re login with new password
        findVisibleElement(By.cssSelector("input#pass")).clear();
        findVisibleElement(By.cssSelector("input#pass")).sendKeys(newPassword);
        clickOnElement(By.cssSelector("button#send2"));
        Assert.assertEquals(findVisibleElement(By.cssSelector("div.welcome-msg strong")).getText(),"Hello, " + fullName + "!");
    }

    @Test
    public void TC_02_orangehrmlive() {
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

        clickOnElement(By.xpath("//a[text()='Immigration']"));
        clickOnElement(By.cssSelector("i.bi-pencil-fill"));

        // Step 19: Verify thông tin hiển thị
        waitForInvisibility(By.cssSelector("div.oxd-form-loader"));

        passportTextbox = findVisibleElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div/input"));
        commentsTextarea = findVisibleElement(By.cssSelector("textarea[placeholder='Type Comments here']"));
        Assert.assertEquals(passportTextbox.getAttribute("value"),passport);
        Assert.assertEquals(commentsTextarea.getAttribute("value"),comments);
    }

    public boolean isAlertPresent() {
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        boolean status;
        try {
            explicitWait.until(ExpectedConditions.alertIsPresent());
            status = true;
        } catch (Exception e) {
            status = false;
        }
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        return status;
    }
    @Test
    public void TC_03_guru99() {
        driver.get("https://demo.guru99.com/");

        String emailGuru = getRandomEmail();
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
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('type')", driver.findElement(By.cssSelector("input#dob")));
        findVisibleElement(By.cssSelector("input#dob")).sendKeys("10/20/1980");
        findVisibleElement(By.cssSelector("textarea[name='addr']")).sendKeys("12 Ly Tu Trong");
        findVisibleElement(By.cssSelector("input[name='city']")).sendKeys("Da Nang");
        findVisibleElement(By.cssSelector("input[name='state']")).sendKeys("Viet Nam");
        findVisibleElement(By.cssSelector("input[name='pinno']")).sendKeys("123456");
        findVisibleElement(By.cssSelector("input[name='telephoneno']")).sendKeys("0905123456");
        String emailNewCustomer = getRandomEmail();
        findVisibleElement(By.cssSelector("input[name='emailid']")).sendKeys(emailNewCustomer);
        findVisibleElement(By.cssSelector("input[name='password']")).sendKeys("Abcd@1234");
        clickOnElement(By.cssSelector("input[name='sub']"));

        if (isAlertPresent()) {
            System.out.println(driver.switchTo().alert().getText());
            driver.switchTo().alert().accept();
        } else {
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
            String emailEditCustomer = getRandomEmail();
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
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

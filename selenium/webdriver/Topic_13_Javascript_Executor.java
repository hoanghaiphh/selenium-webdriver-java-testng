package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class Topic_13_Javascript_Executor {
    WebDriver driver;
    WebDriverWait explicitWait;
    JavascriptExecutor jsExecutor;

    public void sleepInMilliSeconds(long timeInMilliSecond) {
        try {
            Thread.sleep(timeInMilliSecond);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public WebElement waitPresenceOfElement(By locator) {
        return explicitWait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void navigateByJsExecutor(String pageUrl) {
        jsExecutor.executeScript("window.location = " + "'" + pageUrl + "'");
        explicitWait.until(ExpectedConditions.urlToBe(pageUrl));
        explicitWait.until(driver -> jsExecutor.executeScript("return document.readyState").equals("complete"));
    }

    public void highlightElement(WebElement webElement) {
        String originalStyle = webElement.getAttribute("style");
        jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", webElement, "border: 2px solid red; border-style: dashed;");
        sleepInMilliSeconds(500);
        jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", webElement, originalStyle);
    }

    public void clickByJsExecutor(By locator) {
        WebElement webElement = waitPresenceOfElement(locator);
        highlightElement(webElement);
        jsExecutor.executeScript("arguments[0].click()", webElement);
    }

    public void sendKeysByJsExecutor(By locator, String keysToSend) {
        WebElement webElement = waitPresenceOfElement(locator);
        highlightElement(webElement);
        jsExecutor.executeScript("arguments[0].setAttribute('value', '')", webElement);
        jsExecutor.executeScript("arguments[0].setAttribute('value', '" + keysToSend + "')", webElement);
    }

    public void assertInnerTextByJsExecutor(By locator, String expectedText) {
        WebElement webElement = waitPresenceOfElement(locator);
        highlightElement(webElement);
        String actualText = (String) jsExecutor.executeScript("return arguments[0].innerText", webElement);
        Assert.assertEquals(actualText, expectedText);
    }

    public void assertValidationMsgByJsExecutor(By locator, String expectedMsg) {
        WebElement webElement = waitPresenceOfElement(locator);
        highlightElement(webElement);
        String actualMsg = (String) jsExecutor.executeScript("return arguments[0].validationMessage", webElement);
        Assert.assertEquals(actualMsg, expectedMsg);
    }

    public boolean isImageLoaded(By locator) {
        WebElement webElement = waitPresenceOfElement(locator);
        highlightElement(webElement);
        return (boolean) jsExecutor.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0", webElement);
    }

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        jsExecutor = (JavascriptExecutor) driver;
    }

    @Test
    public void TC_01_Javascript_Executor() {
        navigateByJsExecutor("http://live.techpanda.org/");

        String pageDomain = (String) jsExecutor.executeScript("return document.domain");
        Assert.assertEquals(pageDomain, "live.techpanda.org");

        String pageUrl = (String) jsExecutor.executeScript("return document.URL");
        Assert.assertEquals(pageUrl, "http://live.techpanda.org/");

        clickByJsExecutor(By.xpath("//a[text()='Mobile']"));

        clickByJsExecutor(By.xpath("//a[@title='Samsung Galaxy']/following-sibling::div//button[@title='Add to Cart']"));

        assertInnerTextByJsExecutor(By.cssSelector("li.success-msg span"), "Samsung Galaxy was added to your shopping cart.");

        clickByJsExecutor(By.xpath("//a[text()='Customer Service']"));
        explicitWait.until(ExpectedConditions.stalenessOf(waitPresenceOfElement(By.xpath("//a[text()='Customer Service']"))));

        String pageTitle = (String) jsExecutor.executeScript("return document.title");
        Assert.assertEquals(pageTitle, "Customer Service");

        jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");

        sendKeysByJsExecutor(By.cssSelector("input#newsletter"), "automationfc@gmail.com");
        clickByJsExecutor(By.xpath("//span[text()='Subscribe']"));

        assertInnerTextByJsExecutor(By.cssSelector("li.error-msg span"), "There was a problem with the subscription: This email address is already assigned to another user.");

        navigateByJsExecutor("https://demo.guru99.com/v4/");

        pageDomain = (String) jsExecutor.executeScript("return document.domain");
        Assert.assertEquals(pageDomain, "demo.guru99.com");
    }

    @Test
    public void TC_02_HTML5_Validation_Msg() {
        navigateByJsExecutor("https://automationfc.github.io/html5/index.html");

        // empty Name
        clickByJsExecutor(By.cssSelector("input.btn"));
        assertValidationMsgByJsExecutor(By.cssSelector("input#fname"), "Please fill out this field.");

        // empty Password
        sendKeysByJsExecutor(By.cssSelector("input#fname"), "Automation FC");
        clickByJsExecutor(By.cssSelector("input.btn"));
        assertValidationMsgByJsExecutor(By.cssSelector("input#pass"), "Please fill out this field.");

        // empty Email
        sendKeysByJsExecutor(By.cssSelector("input#pass"), "Abcd@1234");
        clickByJsExecutor(By.cssSelector("input.btn"));
        assertValidationMsgByJsExecutor(By.cssSelector("input#em"), "Please fill out this field.");

        // wrong Email
        sendKeysByJsExecutor(By.cssSelector("input#em"), "123!@#$");
        clickByJsExecutor(By.cssSelector("input.btn"));
        assertValidationMsgByJsExecutor(By.cssSelector("input#em"), "Please enter an email address.");

        // correct Email
        sendKeysByJsExecutor(By.cssSelector("input#em"), "automationfc@gmail.com");
        clickByJsExecutor(By.cssSelector("input.btn"));
        assertValidationMsgByJsExecutor(By.cssSelector("select"), "Please select an item in the list.");
    }

    @Test
    public void TC_03_HTML5_Validation_Msg() {
        // site 1
        navigateByJsExecutor("https://login.ubuntu.com/");

        if (waitPresenceOfElement(By.cssSelector("div.p-modal__dialog")).isDisplayed()) {
            clickByJsExecutor(By.cssSelector("button#cookie-policy-button-accept"));
        }

        clickByJsExecutor(By.cssSelector("button[data-qa-id='login_button']"));
        assertValidationMsgByJsExecutor(By.cssSelector("div.login-form input#id_email"), "Please fill out this field.");

        sendKeysByJsExecutor(By.cssSelector("div.login-form input#id_email"), "automation");
        clickByJsExecutor(By.cssSelector("button[data-qa-id='login_button']"));
        assertValidationMsgByJsExecutor(By.cssSelector("div.login-form input#id_email"), "Please enter an email address.");

        sendKeysByJsExecutor(By.cssSelector("div.login-form input#id_email"), "automation@#!");
        clickByJsExecutor(By.cssSelector("button[data-qa-id='login_button']"));
        assertValidationMsgByJsExecutor(By.cssSelector("div.login-form input#id_email"), "Please enter an email address.");

        sendKeysByJsExecutor(By.cssSelector("div.login-form input#id_email"), "automation@gmail.com");
        clickByJsExecutor(By.cssSelector("button[data-qa-id='login_button']"));
        assertValidationMsgByJsExecutor(By.cssSelector("div.login-form input#id_password"), "Please fill out this field.");

        // site 2
        navigateByJsExecutor("https://sieuthimaymocthietbi.com/account/register");

        clickByJsExecutor(By.xpath("//button[text()='Đăng ký']"));
        assertValidationMsgByJsExecutor(By.cssSelector("input#lastName"), "Please fill out this field.");

        sendKeysByJsExecutor(By.cssSelector("input#lastName"), "Automation");
        clickByJsExecutor(By.xpath("//button[text()='Đăng ký']"));
        assertValidationMsgByJsExecutor(By.cssSelector("input#firstName"), "Please fill out this field.");

        sendKeysByJsExecutor(By.cssSelector("input#firstName"), "Testing");
        clickByJsExecutor(By.xpath("//button[text()='Đăng ký']"));
        assertValidationMsgByJsExecutor(By.cssSelector("input#email"), "Please fill out this field.");

        sendKeysByJsExecutor(By.cssSelector("input#email"), "automation");
        clickByJsExecutor(By.xpath("//button[text()='Đăng ký']"));
        assertValidationMsgByJsExecutor(By.cssSelector("input#email"), "Please enter an email address.");

        sendKeysByJsExecutor(By.cssSelector("input#email"), "automation@#!");
        clickByJsExecutor(By.xpath("//button[text()='Đăng ký']"));
        assertValidationMsgByJsExecutor(By.cssSelector("input#email"), "Please enter an email address.");

        sendKeysByJsExecutor(By.cssSelector("input#email"), "automation@gmail.com");
        clickByJsExecutor(By.xpath("//button[text()='Đăng ký']"));
        assertValidationMsgByJsExecutor(By.cssSelector("input#password"), "Please fill out this field.");

        // site 3
        navigateByJsExecutor("https://warranty.rode.com/login");

        clickByJsExecutor(By.cssSelector("button[type='submit']"));
        assertValidationMsgByJsExecutor(By.cssSelector("input#email"), "Please fill out this field.");

        sendKeysByJsExecutor(By.cssSelector("input#email"), "automation");
        clickByJsExecutor(By.cssSelector("button[type='submit']"));
        assertValidationMsgByJsExecutor(By.cssSelector("input#email"), "Please enter an email address.");

        sendKeysByJsExecutor(By.cssSelector("input#email"), "automation@#!");
        clickByJsExecutor(By.cssSelector("button[type='submit']"));
        assertValidationMsgByJsExecutor(By.cssSelector("input#email"), "Please enter an email address.");

        sendKeysByJsExecutor(By.cssSelector("input#email"), "automation@gmail.com");
        clickByJsExecutor(By.cssSelector("button[type='submit']"));
        assertValidationMsgByJsExecutor(By.cssSelector("input#password"), "Please fill out this field.");
    }

    @Test
    public void TC_04_Remove_Attribute() {
        navigateByJsExecutor("https://demo.guru99.com/");

        String emailGuru = "automation" + Math.round(Math.random()*1000000) + "@gmail.com";
        sendKeysByJsExecutor(By.cssSelector("input[name='emailid'"), emailGuru);
        clickByJsExecutor(By.cssSelector("input[name='btnLogin']"));

        String userID = (String) jsExecutor.executeScript("return arguments[0].innerText", waitPresenceOfElement(By.xpath("//td[text()='User ID :']/following-sibling::td")));
        String userPassword = (String) jsExecutor.executeScript("return arguments[0].innerText", waitPresenceOfElement(By.xpath("//td[text()='Password :']/following-sibling::td")));

        navigateByJsExecutor("https://demo.guru99.com/v4/");

        sendKeysByJsExecutor(By.cssSelector("input[name='uid']"), userID);
        sendKeysByJsExecutor(By.cssSelector("input[name='password']"), userPassword);
        clickByJsExecutor(By.cssSelector("input[name='btnLogin']"));

        clickByJsExecutor(By.xpath("//a[text()='New Customer']"));

        waitPresenceOfElement(By.cssSelector("input[name='name']")).sendKeys("Automation Testing");
        clickByJsExecutor(By.xpath("//td[text()='Gender']/following-sibling::td/input[@value='m']"));
        jsExecutor.executeScript("arguments[0].removeAttribute('type')", waitPresenceOfElement(By.cssSelector("input#dob")));
        waitPresenceOfElement(By.cssSelector("input#dob")).sendKeys("10/20/1980");
        waitPresenceOfElement(By.cssSelector("textarea[name='addr']")).sendKeys("12 Ly Tu Trong");
        waitPresenceOfElement(By.cssSelector("input[name='city']")).sendKeys("Da Nang");
        waitPresenceOfElement(By.cssSelector("input[name='state']")).sendKeys("Viet Nam");
        waitPresenceOfElement(By.cssSelector("input[name='pinno']")).sendKeys("123456");
        waitPresenceOfElement(By.cssSelector("input[name='telephoneno']")).sendKeys("0905123456");
        String emailNewCustomer = "automation" + Math.round(Math.random()*1000000) + "@gmail.com";
        waitPresenceOfElement(By.cssSelector("input[name='emailid']")).sendKeys(emailNewCustomer);
        waitPresenceOfElement(By.cssSelector("input[name='password']")).sendKeys("Abcd@1234");

        clickByJsExecutor(By.cssSelector("input[name='sub']"));

        assertInnerTextByJsExecutor(By.cssSelector("table#customer p"), "Customer Registered Successfully!!!");
        assertInnerTextByJsExecutor(By.xpath("//td[text()='Customer Name']/following-sibling::td"), "Automation Testing");
        assertInnerTextByJsExecutor(By.xpath("//td[text()='Birthdate']/following-sibling::td"), "1980-10-20");
        assertInnerTextByJsExecutor(By.xpath("//td[text()='Address']/following-sibling::td"), "12 Ly Tu Trong");
        assertInnerTextByJsExecutor(By.xpath("//td[text()='City']/following-sibling::td"), "Da Nang");
        assertInnerTextByJsExecutor(By.xpath("//td[text()='State']/following-sibling::td"), "Viet Nam");
        assertInnerTextByJsExecutor(By.xpath("//td[text()='Pin']/following-sibling::td"), "123456");
        assertInnerTextByJsExecutor(By.xpath("//td[text()='Mobile No.']/following-sibling::td"), "0905123456");
        assertInnerTextByJsExecutor(By.xpath("//td[text()='Email']/following-sibling::td"), emailNewCustomer);
    }

    @Test
    public void TC_05_Create_Account() {
        navigateByJsExecutor("http://live.techpanda.org/");

        clickByJsExecutor(By.cssSelector("div#header-account a[title='My Account']"));

        clickByJsExecutor(By.cssSelector("a[title='Create an Account']"));

        String firstName = "Hai", middleName = "Hoang", lastName = "Phan", password = "Abc@1234";
        String fullName = firstName + " " + middleName + " " + lastName;
        String email = "automation" + Math.round(Math.random()*1000000) + "@gmail.com";

        sendKeysByJsExecutor(By.cssSelector("input#firstname"), firstName);
        sendKeysByJsExecutor(By.cssSelector("input#middlename"), middleName);
        sendKeysByJsExecutor(By.cssSelector("input#lastname"), lastName);
        sendKeysByJsExecutor(By.cssSelector("input#email_address"), email);
        sendKeysByJsExecutor(By.cssSelector("input#password"), password);
        sendKeysByJsExecutor(By.cssSelector("input#confirmation"), password);

        clickByJsExecutor(By.cssSelector("button[title='Register']"));

        assertInnerTextByJsExecutor(By.cssSelector("li.success-msg span"), "Thank you for registering with Main Website Store.");
        assertInnerTextByJsExecutor(By.cssSelector("div.welcome-msg strong"), "Hello, " + fullName + "!");

        clickByJsExecutor(By.cssSelector("a[title='Log Out']"));
        explicitWait.until(ExpectedConditions.titleIs("Home page"));

        String pageUrl = (String) jsExecutor.executeScript("return document.URL");
        Assert.assertEquals(pageUrl, "http://live.techpanda.org/index.php/");
    }

    @Test
    public void TC_06_Broken_Image() {
        navigateByJsExecutor("https://automationfc.github.io/basic-form/index.html");

        jsExecutor.executeScript("arguments[0].scrollIntoView(false); window.scrollBy(0,400);", waitPresenceOfElement(By.cssSelector("img[alt='broken_01']")));

        Assert.assertFalse(isImageLoaded(By.cssSelector("img[alt='broken_01']")));
        Assert.assertFalse(isImageLoaded(By.cssSelector("img[alt='broken_02']")));
        Assert.assertFalse(isImageLoaded(By.cssSelector("img[alt='broken_03']")));
        Assert.assertFalse(isImageLoaded(By.cssSelector("img[alt='broken_04']")));
        Assert.assertTrue(isImageLoaded(By.cssSelector("img[alt='broken_05']")));
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

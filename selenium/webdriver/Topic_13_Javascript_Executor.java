package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
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

    public void navigateToUrl(String pageUrl) {
        jsExecutor.executeScript("window.location = " + "'" + pageUrl + "'");
        explicitWait.until(ExpectedConditions.urlToBe(pageUrl));
        // prevent javascript executed too fast
        explicitWait.until(driver -> jsExecutor.executeScript("return document.readyState").equals("complete"));
    }

    public void highlightElement(By locator) {
        String originalStyle = driver.findElement(locator).getAttribute("style");
        jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", driver.findElement(locator), "border: 2px solid red; border-style: dashed;");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", driver.findElement(locator), originalStyle);
    }

    public void clickByJs(By locator) {
        highlightElement(locator);
        jsExecutor.executeScript("arguments[0].click()", driver.findElement(locator));
    }

    public void sendKeysByJs(By locator, String keysToSend) {
        highlightElement(locator);
        jsExecutor.executeScript("arguments[0].setAttribute('value', '')", driver.findElement(locator));
        jsExecutor.executeScript("arguments[0].setAttribute('value', '" + keysToSend + "')", driver.findElement(locator));
    }

    public String innerTextOfElement(By locator) {
        return (String) jsExecutor.executeScript("return arguments[0].innerText", driver.findElement(locator));
    }

    public void assertInnerText(By locator, String expectedText) {
        highlightElement(locator);
        String actualText = (String) jsExecutor.executeScript("return arguments[0].innerText", driver.findElement(locator));
        Assert.assertEquals(actualText, expectedText);
    }

    public void assertValidationMsg(By locator, String expectedMsg) {
        highlightElement(locator);
        String actualMsg = (String) jsExecutor.executeScript("return arguments[0].validationMessage", driver.findElement(locator));
        Assert.assertEquals(actualMsg, expectedMsg);
    }

    public void assertPageComponent(String pageComponent, String expected) {
        String component = (String) jsExecutor.executeScript("return document." + pageComponent);
        Assert.assertEquals(component, expected);
    }

    public boolean isImageLoaded(By locator) {
        highlightElement(locator);
        return (boolean) jsExecutor.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0",
                driver.findElement(locator));
    }

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        jsExecutor = (JavascriptExecutor) driver;
    }

    @Test
    public void TC_01_Javascript_Executor() {
        navigateToUrl("http://live.techpanda.org/");
        assertPageComponent("domain", "live.techpanda.org");
        assertPageComponent("URL", "http://live.techpanda.org/");

        clickByJs(By.xpath("//a[text()='Mobile']"));
        clickByJs(By.xpath("//a[@title='Samsung Galaxy']/following-sibling::div//button[@title='Add to Cart']"));
        assertInnerText(By.cssSelector("li.success-msg span"), "Samsung Galaxy was added to your shopping cart.");

        clickByJs(By.xpath("//a[text()='Customer Service']"));
        explicitWait.until(ExpectedConditions.titleIs("Customer Service"));
        assertPageComponent("title", "Customer Service");

        jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");

        sendKeysByJs(By.cssSelector("input#newsletter"), "automationfc@gmail.com");
        clickByJs(By.xpath("//span[text()='Subscribe']"));
        assertInnerText(By.cssSelector("li.error-msg span"), "There was a problem with the subscription: This email address is already assigned to another user.");

        navigateToUrl("https://demo.guru99.com/v4/");
        assertPageComponent("domain", "demo.guru99.com");
    }

    @Test
    public void TC_02_HTML5_Validation_Msg() {
        navigateToUrl("https://automationfc.github.io/html5/index.html");

        // empty Name
        clickByJs(By.cssSelector("input.btn"));
        assertValidationMsg(By.cssSelector("input#fname"), "Please fill out this field.");

        // empty Password
        sendKeysByJs(By.cssSelector("input#fname"), "Automation FC");
        clickByJs(By.cssSelector("input.btn"));
        assertValidationMsg(By.cssSelector("input#pass"), "Please fill out this field.");

        // empty Email
        sendKeysByJs(By.cssSelector("input#pass"), "Abcd@1234");
        clickByJs(By.cssSelector("input.btn"));
        assertValidationMsg(By.cssSelector("input#em"), "Please fill out this field.");

        // wrong Email
        sendKeysByJs(By.cssSelector("input#em"), "123!@#$");
        clickByJs(By.cssSelector("input.btn"));
        assertValidationMsg(By.cssSelector("input#em"), "Please enter an email address.");

        // correct Email
        sendKeysByJs(By.cssSelector("input#em"), "automationfc@gmail.com");
        clickByJs(By.cssSelector("input.btn"));
        assertValidationMsg(By.cssSelector("select"), "Please select an item in the list.");
    }

    @Test
    public void TC_03_HTML5_Validation_Msg() {
        // site 1
        navigateToUrl("https://login.ubuntu.com/");

        // popup does not affect actions by javascript executor --> closing popup is not necessary
        /*if (driver.findElement(By.cssSelector("div.p-modal__dialog")).isDisplayed()) {
            clickByJs(By.cssSelector("button#cookie-policy-button-accept"));
        }*/

        clickByJs(By.cssSelector("button[data-qa-id='login_button']"));
        assertValidationMsg(By.cssSelector("div.login-form input#id_email"), "Please fill out this field.");

        sendKeysByJs(By.cssSelector("div.login-form input#id_email"), "automation");
        clickByJs(By.cssSelector("button[data-qa-id='login_button']"));
        assertValidationMsg(By.cssSelector("div.login-form input#id_email"), "Please enter an email address.");

        sendKeysByJs(By.cssSelector("div.login-form input#id_email"), "automation@#!");
        clickByJs(By.cssSelector("button[data-qa-id='login_button']"));
        assertValidationMsg(By.cssSelector("div.login-form input#id_email"), "Please enter an email address.");

        sendKeysByJs(By.cssSelector("div.login-form input#id_email"), "automation@gmail.com");
        clickByJs(By.cssSelector("button[data-qa-id='login_button']"));
        assertValidationMsg(By.cssSelector("div.login-form input#id_password"), "Please fill out this field.");

        // site 2
        navigateToUrl("https://sieuthimaymocthietbi.com/account/register");

        clickByJs(By.xpath("//button[text()='Đăng ký']"));
        assertValidationMsg(By.cssSelector("input#lastName"), "Please fill out this field.");

        sendKeysByJs(By.cssSelector("input#lastName"), "Automation");
        clickByJs(By.xpath("//button[text()='Đăng ký']"));
        assertValidationMsg(By.cssSelector("input#firstName"), "Please fill out this field.");

        sendKeysByJs(By.cssSelector("input#firstName"), "Testing");
        clickByJs(By.xpath("//button[text()='Đăng ký']"));
        assertValidationMsg(By.cssSelector("input#email"), "Please fill out this field.");

        sendKeysByJs(By.cssSelector("input#email"), "automation");
        clickByJs(By.xpath("//button[text()='Đăng ký']"));
        assertValidationMsg(By.cssSelector("input#email"), "Please enter an email address.");

        sendKeysByJs(By.cssSelector("input#email"), "automation@#!");
        clickByJs(By.xpath("//button[text()='Đăng ký']"));
        assertValidationMsg(By.cssSelector("input#email"), "Please enter an email address.");

        sendKeysByJs(By.cssSelector("input#email"), "automation@gmail.com");
        clickByJs(By.xpath("//button[text()='Đăng ký']"));
        assertValidationMsg(By.cssSelector("input#password"), "Please fill out this field.");

        // site 3
        navigateToUrl("https://warranty.rode.com/login");

        clickByJs(By.cssSelector("button[type='submit']"));
        assertValidationMsg(By.cssSelector("input#email"), "Please fill out this field.");

        sendKeysByJs(By.cssSelector("input#email"), "automation");
        clickByJs(By.cssSelector("button[type='submit']"));
        assertValidationMsg(By.cssSelector("input#email"), "Please enter an email address.");

        sendKeysByJs(By.cssSelector("input#email"), "automation@#!");
        clickByJs(By.cssSelector("button[type='submit']"));
        assertValidationMsg(By.cssSelector("input#email"), "Please enter an email address.");

        sendKeysByJs(By.cssSelector("input#email"), "automation@gmail.com");
        clickByJs(By.cssSelector("button[type='submit']"));
        assertValidationMsg(By.cssSelector("input#password"), "Please fill out this field.");
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
    public void TC_04_Remove_Attribute() {
        navigateToUrl("https://demo.guru99.com/");

        String emailGuru = "automation" + Math.round(Math.random()*1000000) + "@gmail.com";
        sendKeysByJs(By.cssSelector("input[name='emailid'"), emailGuru);
        clickByJs(By.cssSelector("input[name='btnLogin']"));

        explicitWait.until(ExpectedConditions.not(ExpectedConditions.urlToBe("https://demo.guru99.com/")));

        String userID = innerTextOfElement(By.xpath("//td[contains(text(),'User ID')]/following-sibling::td"));
        String userPassword = innerTextOfElement(By.xpath("//td[contains(text(),'Password')]/following-sibling::td"));

        navigateToUrl("https://demo.guru99.com/v4/");

        sendKeysByJs(By.cssSelector("input[name='uid']"), userID);
        sendKeysByJs(By.cssSelector("input[name='password']"), userPassword);
        clickByJs(By.cssSelector("input[name='btnLogin']"));

        clickByJs(By.xpath("//a[text()='New Customer']"));

        driver.findElement(By.cssSelector("input[name='name']")).sendKeys("Automation Testing");
        clickByJs(By.xpath("//td[text()='Gender']/following-sibling::td/input[@value='m']"));
        jsExecutor.executeScript("arguments[0].removeAttribute('type')", driver.findElement(By.cssSelector("input#dob")));
        driver.findElement(By.cssSelector("input#dob")).sendKeys("10/20/1980");
        driver.findElement(By.cssSelector("textarea[name='addr']")).sendKeys("12 Ly Tu Trong");
        driver.findElement(By.cssSelector("input[name='city']")).sendKeys("Da Nang");
        driver.findElement(By.cssSelector("input[name='state']")).sendKeys("Viet Nam");
        driver.findElement(By.cssSelector("input[name='pinno']")).sendKeys("123456");
        driver.findElement(By.cssSelector("input[name='telephoneno']")).sendKeys("0905123456");
        String emailNewCustomer = "automation" + Math.round(Math.random()*1000000) + "@gmail.com";
        driver.findElement(By.cssSelector("input[name='emailid']")).sendKeys(emailNewCustomer);
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys("Abcd@1234");

        clickByJs(By.cssSelector("input[name='sub']"));

        if (isAlertPresent()) {
            System.out.println("TC_04: " + driver.switchTo().alert().getText());
            driver.switchTo().alert().accept();
        } else {
            assertInnerText(By.cssSelector("table#customer p"), "Customer Registered Successfully!!!");
            assertInnerText(By.xpath("//td[text()='Customer Name']/following-sibling::td"), "Automation Testing");
            assertInnerText(By.xpath("//td[text()='Birthdate']/following-sibling::td"), "1980-10-20");
            assertInnerText(By.xpath("//td[text()='Address']/following-sibling::td"), "12 Ly Tu Trong");
            assertInnerText(By.xpath("//td[text()='City']/following-sibling::td"), "Da Nang");
            assertInnerText(By.xpath("//td[text()='State']/following-sibling::td"), "Viet Nam");
            assertInnerText(By.xpath("//td[text()='Pin']/following-sibling::td"), "123456");
            assertInnerText(By.xpath("//td[text()='Mobile No.']/following-sibling::td"), "0905123456");
            assertInnerText(By.xpath("//td[text()='Email']/following-sibling::td"), emailNewCustomer);
        }
    }

    @Test
    public void TC_05_Create_Account() {
        navigateToUrl("http://live.techpanda.org/");

        clickByJs(By.cssSelector("div#header-account a[title='My Account']"));
        clickByJs(By.cssSelector("a[title='Create an Account']"));

        String firstName = "Hai", middleName = "Hoang", lastName = "Phan", password = "Abc@1234";
        String fullName = firstName + " " + middleName + " " + lastName;
        String email = "automation" + Math.round(Math.random()*1000000) + "@gmail.com";

        sendKeysByJs(By.cssSelector("input#firstname"), firstName);
        sendKeysByJs(By.cssSelector("input#middlename"), middleName);
        sendKeysByJs(By.cssSelector("input#lastname"), lastName);
        sendKeysByJs(By.cssSelector("input#email_address"), email);
        sendKeysByJs(By.cssSelector("input#password"), password);
        sendKeysByJs(By.cssSelector("input#confirmation"), password);

        clickByJs(By.cssSelector("button[title='Register']"));
        assertInnerText(By.cssSelector("li.success-msg span"), "Thank you for registering with Main Website Store.");
        assertInnerText(By.cssSelector("div.welcome-msg strong"), "Hello, " + fullName + "!");

        clickByJs(By.cssSelector("a[title='Log Out']"));
        explicitWait.until(ExpectedConditions.titleIs("Home page"));
        assertPageComponent("URL", "http://live.techpanda.org/index.php/");
    }

    @Test
    public void TC_06_Broken_Image() {
        navigateToUrl("https://automationfc.github.io/basic-form/index.html");

        jsExecutor.executeScript("arguments[0].scrollIntoView(false); window.scrollBy(0,400);", driver.findElement(By.cssSelector("img[alt='broken_01']")));

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

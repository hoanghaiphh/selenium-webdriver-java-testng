package testng_classes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Random;

public class TestNG_Invocation_DataProvider {
    WebDriver driver;
    WebDriverWait explicitWait;
    HashMap<String, String> userAndPassword = new HashMap<>();

    public String getRandomEmail() {
        return "automation" + new Random().nextInt(999999) + "@gmail.com";
    }

    public WebElement findVisibleElement(By locator) {
        return explicitWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void clickOnElement(By locator) {
        explicitWait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Test (invocationCount = 3)
    public void TC_01_Register() {
        driver.get("http://live.techpanda.org/customer/account/create");

        String email = getRandomEmail(), password = "Abc@1234";
        findVisibleElement(By.cssSelector("input#firstname")).sendKeys("Automation");
        findVisibleElement(By.cssSelector("input#middlename")).sendKeys("Selenium");
        findVisibleElement(By.cssSelector("input#lastname")).sendKeys("Testing");
        findVisibleElement(By.cssSelector("input#email_address")).sendKeys(email);
        findVisibleElement(By.cssSelector("input#password")).sendKeys(password);
        findVisibleElement(By.cssSelector("input#confirmation")).sendKeys(password);
        clickOnElement(By.xpath("//span[text()='Register']"));

        Assert.assertEquals(findVisibleElement(By.cssSelector("li.success-msg span")).getText(),"Thank you for registering with Main Website Store.");
        System.out.println("Email: " + email + " - Password: " + password);
        userAndPassword.put(email, password);

        clickOnElement(By.xpath("//header//span[text()='Account']"));
        clickOnElement(By.xpath("//a[text()='Log Out']"));
    }

    /*@DataProvider(name = "loginData")
    public Object[][] UserAndPasswordData() {
        return new Object[][]{
                {"selenium_11_01@gmail.com", "111111"},
                {"selenium_11_02@gmail.com", "111111"},
                {"selenium_11_03@gmail.com", "111111"}
        };
    }*/

    @DataProvider(name = "loginData")
    public Object[][] UserAndPasswordData() {
        return userAndPassword.entrySet().stream()
                .map(e -> new Object[]{e.getKey(), e.getValue()})
                .toArray(Object[][]::new);
    }

    @Test(dataProvider = "loginData")
    public void TC_02_Login(String username, String password)  {
        driver.get("http://live.techpanda.org/index.php/customer/account/login/");

        findVisibleElement(By.xpath("//*[@id='email']")).sendKeys(username);
        findVisibleElement(By.xpath("//*[@id='pass']")).sendKeys(password);
        clickOnElement(By.xpath("//*[@id='send2']"));
        Assert.assertTrue(findVisibleElement(By.xpath("//div[@class='col-1']//p")).getText().contains(username));

        clickOnElement(By.xpath("//header[@id='header']//span[text()='Account']"));
        clickOnElement(By.xpath("//a[text()='Log Out']"));
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

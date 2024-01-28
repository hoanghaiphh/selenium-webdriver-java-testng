package webdriver_Topic_17_TestNG;

import org.openqa.selenium.By;
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
import java.util.Random;

public class TestNG_11_Invocation {
    WebDriver driver;
    WebDriverWait explicitWait;

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

    @Test (invocationCount = 5)
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

        clickOnElement(By.xpath("//header//span[text()='Account']"));
        clickOnElement(By.xpath("//a[text()='Log Out']"));
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

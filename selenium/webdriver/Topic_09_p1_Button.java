package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Topic_09_p1_Button {
    WebDriver driver;

    public WebElement findVisibleElement(By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @Test
    public void TC_01a_Button() {
        driver.get("https://www.fahasa.com/customer/account/create");

        findVisibleElement(By.cssSelector("li.popup-login-tab-login")).click();

        WebElement loginBtn = findVisibleElement(By.cssSelector("button.fhs-btn-login"));
        // A1 = loginBtn.getCssValue("background-color"): get background color --> String (Hexa/ RGB/ RGBA...)
        // A2 = Color.fromString(A1): String --> Color
        // A3 = (A2).asHex(): Color --> String (Hexa)
        String btnColor = Color.fromString(loginBtn.getCssValue("background-color")).asHex();
        Assert.assertFalse(loginBtn.isEnabled());
        Assert.assertEquals(btnColor.toUpperCase(), "#000000");
        System.out.println("Color of Login Button (Disable):\t" + btnColor.toUpperCase());

        findVisibleElement(By.cssSelector("input#login_username")).sendKeys("automation@gmail.com");
        findVisibleElement(By.cssSelector("input#login_password")).sendKeys("Abcd@1234");

        loginBtn = findVisibleElement(By.cssSelector("button.fhs-btn-login"));
        btnColor = Color.fromString(loginBtn.getCssValue("background-color")).asHex();
        Assert.assertTrue(loginBtn.isEnabled());
        Assert.assertEquals(btnColor.toUpperCase(), "#C92127");
        System.out.println("Color of Login Button (Enable):\t\t" + btnColor.toUpperCase());
    }

    @Test
    public void TC_01b_Button() {
        driver.get("https://egov.danang.gov.vn/reg");

        WebElement registerBtn = findVisibleElement(By.cssSelector("input.egov-button"));
        String btnColor = Color.fromString(registerBtn.getCssValue("background-color")).asHex();
        Assert.assertFalse(registerBtn.isEnabled());
        Assert.assertEquals(btnColor.toUpperCase(),"#A0A0A0");

        findVisibleElement(By.cssSelector("input#chinhSach")).click();

        registerBtn = findVisibleElement(By.cssSelector("input.egov-button"));
        btnColor = Color.fromString(registerBtn.getCssValue("background-color")).asHex();
        Assert.assertTrue(registerBtn.isEnabled());
        Assert.assertEquals(btnColor.toLowerCase(),"#ef5a00");
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

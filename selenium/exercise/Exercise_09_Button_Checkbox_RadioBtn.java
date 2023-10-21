package exercise;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Exercise_09_Button_Checkbox_RadioBtn {
    WebDriver driver;

    public void sleepInSeconds (long timeInSecond) {
        try {
            Thread.sleep(timeInSecond*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_Button() {
        driver.get("https://www.fahasa.com/customer/account/create");
        driver.findElement(By.cssSelector("li.popup-login-tab-login")).click();
        sleepInSeconds(1);

        WebElement loginBtn = driver.findElement(By.cssSelector("button.fhs-btn-login"));
        // A1 = loginBtn.getCssValue("background-color"): get background color --> String (Hexa/ RGB/ RGBA...)
        // A2 = Color.fromString(A1): String --> Color
        // A3 = (A2).asHex(): Color --> String (Hexa)
        String btnColor = Color.fromString(loginBtn.getCssValue("background-color")).asHex();
        Assert.assertFalse(loginBtn.isEnabled());
        Assert.assertEquals(btnColor.toUpperCase(),"#000000");
        System.out.println("Color of Login Button (Disable):\t" + btnColor.toUpperCase());

        driver.findElement(By.cssSelector("input#login_username")).sendKeys("automation@gmail.com");
        driver.findElement(By.cssSelector("input#login_password")).sendKeys("Abcd@1234");
        sleepInSeconds(1);

        btnColor = Color.fromString(loginBtn.getCssValue("background-color")).asHex();
        Assert.assertTrue(loginBtn.isEnabled());
        Assert.assertEquals(btnColor.toUpperCase(),"#C92127");
        System.out.println("Color of Login Button (Enable):\t\t" + btnColor.toUpperCase());

        driver.get("https://egov.danang.gov.vn/reg");
        WebElement registerBtn = driver.findElement(By.cssSelector("input.egov-button"));
        btnColor = Color.fromString(registerBtn.getCssValue("background-color")).asHex();
        Assert.assertFalse(registerBtn.isEnabled());
        Assert.assertEquals(btnColor.toUpperCase(),"#A0A0A0");
        driver.findElement(By.cssSelector("input#chinhSach")).click();
        sleepInSeconds(1);
        btnColor = Color.fromString(registerBtn.getCssValue("background-color")).asHex();
        Assert.assertTrue(registerBtn.isEnabled());
        Assert.assertEquals(btnColor.toLowerCase(),"#ef5a00");
    }

    @Test
    public void TC_02_Default_Checkbox_RadioBtn() {
        driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");
        WebElement checkbox = driver.findElement(By.cssSelector("input#eq5"));
        do {
            checkbox.click();
            sleepInSeconds(1);
        } while (!checkbox.isSelected());
        Assert.assertTrue(checkbox.isSelected());
        checkbox.click();
        sleepInSeconds(1);
        Assert.assertFalse(checkbox.isSelected());

        driver.get("https://demos.telerik.com/kendo-ui/radiobutton/index");
        WebElement radioBtn = driver.findElement(By.cssSelector("input#engine3"));
        do {
            radioBtn.click();
            sleepInSeconds(1);
        } while (!radioBtn.isSelected());
        Assert.assertTrue(radioBtn.isSelected());
    }

    @Test
    public void TC_03_Default_Checkbox_RadioBtn() {
        driver.get("https://material.angular.io/components/radio/examples");
        WebElement radioBtn = driver.findElement(By.cssSelector("input[value='Summer']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", radioBtn);
        sleepInSeconds(1);
        do {
            radioBtn.click();
            sleepInSeconds(1);
        } while (!radioBtn.isSelected());
        Assert.assertTrue(radioBtn.isSelected());

        driver.get("https://material.angular.io/components/checkbox/examples");
        sleepInSeconds(1);
        WebElement checkbox1 = driver.findElement(By.cssSelector("input#mat-mdc-checkbox-1-input"));
        WebElement checkbox2 = driver.findElement(By.cssSelector("input#mat-mdc-checkbox-2-input"));
        do {
            checkbox1.click();
            sleepInSeconds(1);
        } while (!checkbox1.isSelected());
        Assert.assertTrue(checkbox1.isSelected());
        do {
            checkbox2.click();
            sleepInSeconds(1);
        } while (!checkbox2.isSelected());
        Assert.assertTrue(checkbox2.isSelected());
        checkbox1.click();
        checkbox2.click();
        sleepInSeconds(1);
        Assert.assertFalse(checkbox1.isSelected());
        Assert.assertFalse(checkbox2.isSelected());
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

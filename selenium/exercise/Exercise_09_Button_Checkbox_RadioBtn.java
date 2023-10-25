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
import java.util.List;

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

    public void checkboxSelect(String checkboxLocator, Boolean checkboxStatus) {
        if (driver.findElement(By.xpath(checkboxLocator)).isSelected() == checkboxStatus) {
            driver.findElement(By.xpath(checkboxLocator)).click();
            sleepInSeconds(1);
        }
    }

    @Test
    public void TC_02_Default_Checkbox_RadioBtn() {
        driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");
        sleepInSeconds(1);

        String checkbox = "//label[text()='Dual-zone air conditioning']/preceding-sibling::input";

        checkboxSelect(checkbox, false);
        Assert.assertTrue(driver.findElement(By.xpath(checkbox)).isSelected());

        checkboxSelect(checkbox, true);
        Assert.assertFalse(driver.findElement(By.xpath(checkbox)).isSelected());

        driver.get("https://demos.telerik.com/kendo-ui/radiobutton/index");
        sleepInSeconds(1);

        String radioBtn = "//label[text()='2.0 Petrol, 147kW']/preceding-sibling::input";
        checkboxSelect(radioBtn, false);
        Assert.assertTrue(driver.findElement(By.xpath(radioBtn)).isSelected());
    }

    @Test
    public void TC_03_Default_Checkbox_RadioBtn() {
        driver.get("https://material.angular.io/components/radio/examples");
        sleepInSeconds(1);

        WebElement radioBtnElement = driver.findElement(By.xpath("//input[@value='Summer']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", radioBtnElement);
        sleepInSeconds(1);

        radioBtnElement.click();
        sleepInSeconds(1);
        Assert.assertTrue(radioBtnElement.isSelected());

        driver.get("https://material.angular.io/components/checkbox/examples");
        sleepInSeconds(1);

        String checkedCheckbox = "//label[text()='Checked']/preceding-sibling::div/input";
        String indeterminateCheckbox = "//label[text()='Indeterminate']/preceding-sibling::div/input";

        checkboxSelect(checkedCheckbox, false);
        Assert.assertTrue(driver.findElement(By.xpath(checkedCheckbox)).isSelected());
        checkboxSelect(indeterminateCheckbox, false);
        Assert.assertTrue(driver.findElement(By.xpath(indeterminateCheckbox)).isSelected());

        checkboxSelect(checkedCheckbox, true);
        Assert.assertFalse(driver.findElement(By.xpath(checkedCheckbox)).isSelected());
        checkboxSelect(indeterminateCheckbox, true);
        Assert.assertFalse(driver.findElement(By.xpath(indeterminateCheckbox)).isSelected());
    }

    @Test
    public void TC_04_Select_All_Select_Only() {
        driver.get("https://automationfc.github.io/multiple-fields/");
        List<WebElement> allCheckboxes = driver.findElements(By.xpath("//label[text()=' Have you ever had (Please check all that apply) ']/following-sibling::div//span[@class='form-checkbox-item']//input"));
        for (WebElement checkbox : allCheckboxes) {
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        }
        for (WebElement checkbox : allCheckboxes) {
            Assert.assertTrue(checkbox.isSelected());
        }

        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
        allCheckboxes = driver.findElements(By.xpath("//label[text()=' Have you ever had (Please check all that apply) ']/following-sibling::div//span[@class='form-checkbox-item']//input"));
        for (WebElement checkbox : allCheckboxes) {
            if (checkbox.isSelected()) {
                checkbox.click();
            }
        }
        for (WebElement checkbox : allCheckboxes) {
            Assert.assertFalse(checkbox.isSelected());
        }
        for (WebElement checkbox : allCheckboxes) {
            if (!checkbox.isSelected() && checkbox.getAttribute("value").equals("Heart Attack")) {
                checkbox.click();
            }
        }
        for (WebElement checkbox : allCheckboxes) {
            if (checkbox.getAttribute("value").equals("Heart Attack")) {
                Assert.assertTrue(checkbox.isSelected());
            } else {
                Assert.assertFalse(checkbox.isSelected());
            }
        }
    }

    @Test
    public void TC_05_Custom_RadioBtn() {
        driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");
        sleepInSeconds(1);
        WebElement radioBtnElement = driver.findElement(By.xpath("//div[text()='Đăng ký cho người thân']/preceding-sibling::div/input"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", radioBtnElement);
        sleepInSeconds(1);
        Assert.assertTrue(radioBtnElement.isSelected());
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

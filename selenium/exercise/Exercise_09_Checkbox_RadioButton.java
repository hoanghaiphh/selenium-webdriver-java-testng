package exercise;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class Exercise_09_Checkbox_RadioButton {
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
    public void TC_04_SelectAll_SelectOnly() {
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
    public void TC_05_Custom_RadioBtn_jsClick() {
        driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");
        sleepInSeconds(1);
        WebElement radioBtnElement = driver.findElement(By.xpath("//div[text()='Đăng ký cho người thân']/preceding-sibling::div/input"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", radioBtnElement);
        sleepInSeconds(1);
        Assert.assertTrue(radioBtnElement.isSelected());
    }

    @Test
    public void TC_06_Custom_Checkbox_RadioBtn_GoogleDocs() {
        driver.get("https://docs.google.com/forms/d/e/1FAIpQLSfiypnd69zhuDkjKgqvpID9kwO29UCzeCVrGGtbNPZXQok0jA/viewform");
        sleepInSeconds(2);
        List<WebElement> radioGroup = driver.findElements(By.cssSelector("div[role='radio']"));
        for (WebElement radio : radioGroup) {
            Assert.assertEquals(radio.getAttribute("aria-checked"), "false");
        }
        for (WebElement radio : radioGroup) {
            if (radio.getAttribute("data-value").equals("Hà Nội")) {
                radio.click();
                sleepInSeconds(1);
                Assert.assertEquals(radio.getAttribute("aria-checked"), "true");
            } else {
                Assert.assertEquals(radio.getAttribute("aria-checked"), "false");
            }
        }

        WebElement sendBtn = driver.findElement(By.xpath("//span[text()='Gửi']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", sendBtn);
        sleepInSeconds(1);
        List<WebElement> checkboxGroup = driver.findElements(By.cssSelector("div[role='checkbox']"));
        for (WebElement checkbox : checkboxGroup) {
            Assert.assertEquals(checkbox.getAttribute("aria-checked"), "false");
        }
        for (WebElement checkbox : checkboxGroup) {
            if (checkbox.getAttribute("aria-label").startsWith("Quảng") && checkbox.getAttribute("aria-checked").equals("false")
                    || !checkbox.getAttribute("aria-label").startsWith("Quảng") && checkbox.getAttribute("aria-checked").equals("true")) {
                checkbox.click();
                sleepInSeconds(1);
            }
        }
        for (WebElement checkbox : checkboxGroup) {
            if (checkbox.getAttribute("aria-label").startsWith("Quảng")) {
                Assert.assertEquals(checkbox.getAttribute("aria-checked"), "true");
            } else {
                Assert.assertEquals(checkbox.getAttribute("aria-checked"), "false");
            }
        }

        sendBtn.click();
        sleepInSeconds(1);
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

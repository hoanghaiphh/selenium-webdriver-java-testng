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
import java.util.List;

public class Topic_09_p2_Checkbox_RadioButton {
    WebDriver driver;
    WebDriverWait explicitWait;

    public WebElement findVisibleElement(By locator) {
        return explicitWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void selectOrDeselectCheckbox(By checkboxLocator, Boolean expectedStatus) {
        if (findVisibleElement(checkboxLocator).isSelected() != expectedStatus) {
            explicitWait.until(ExpectedConditions.elementToBeClickable(checkboxLocator)).click();
            explicitWait.until(ExpectedConditions.elementSelectionStateToBe(checkboxLocator, expectedStatus));
        }
    }

    public void selectRadioBtn(By radioLocator) {
        if (!findVisibleElement(radioLocator).isSelected()) {
            explicitWait.until(ExpectedConditions.elementToBeClickable(radioLocator)).click();
            explicitWait.until(ExpectedConditions.elementToBeSelected(radioLocator));
        }
    }

    public void selectOrDeselectCheckboxAngular(By checkboxLocator, Boolean expectedStatus) {
        if (driver.findElement(checkboxLocator).isSelected() != expectedStatus) {
            driver.findElement(checkboxLocator).click();
            explicitWait.until(ExpectedConditions.elementSelectionStateToBe(checkboxLocator, expectedStatus));
        }
    }

    public void selectRadioBtnAngular(By radioLocator) {
        if (!driver.findElement(radioLocator).isSelected()) {
            driver.findElement(radioLocator).click();
            explicitWait.until(ExpectedConditions.elementToBeSelected(radioLocator));
        }
    }

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Test
    public void TC_02_Default() {
        driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");

        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,300)");

        By checkboxLocator = By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input");

        selectOrDeselectCheckbox(checkboxLocator, true);
        Assert.assertTrue(findVisibleElement(checkboxLocator).isSelected());

        selectOrDeselectCheckbox(checkboxLocator, false);
        Assert.assertFalse(findVisibleElement(checkboxLocator).isSelected());

        driver.get("https://demos.telerik.com/kendo-ui/radiobutton/index");

        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,300)");

        By radioLocator = By.xpath("//label[text()='2.0 Petrol, 147kW']/preceding-sibling::input");

        selectRadioBtn(radioLocator);
        Assert.assertTrue(findVisibleElement(radioLocator).isSelected());
    }

    @Test
    public void TC_03_Angular() {
        driver.get("https://material.angular.io/components/radio/examples");
        // can not wait for visibility on this page

        By radioLocator = By.xpath("//input[@value='Summer']");
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", driver.findElement(radioLocator));

        selectRadioBtnAngular(radioLocator);
        Assert.assertTrue(driver.findElement(radioLocator).isSelected());

        driver.get("https://material.angular.io/components/checkbox/examples");
        // can not wait for visibility on this page

        By checkedCheckbox = By.xpath("//label[text()='Checked']/preceding-sibling::div/input");
        By indeterminateCheckbox = By.xpath("//label[text()='Indeterminate']/preceding-sibling::div/input");

        selectOrDeselectCheckboxAngular(checkedCheckbox, true);
        Assert.assertTrue(driver.findElement(checkedCheckbox).isSelected());

        selectOrDeselectCheckboxAngular(indeterminateCheckbox, true);
        Assert.assertTrue(driver.findElement(indeterminateCheckbox).isSelected());

        selectOrDeselectCheckboxAngular(checkedCheckbox, false);
        Assert.assertFalse(driver.findElement(checkedCheckbox).isSelected());

        selectOrDeselectCheckboxAngular(indeterminateCheckbox, false);
        Assert.assertFalse(driver.findElement(indeterminateCheckbox).isSelected());
    }

    @Test
    public void TC_04_SelectAll_SelectOnly() {
        driver.get("https://automationfc.github.io/multiple-fields/");
        List<WebElement> allCheckboxes = explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//label[text()=' Have you ever had (Please check all that apply) ']/following-sibling::div//span[@class='form-checkbox-item']//input")));
        for (WebElement checkbox : allCheckboxes) {
            if (!checkbox.isSelected()) {
                explicitWait.until(ExpectedConditions.elementToBeClickable(checkbox)).click();
                explicitWait.until(ExpectedConditions.elementToBeSelected(checkbox));
            }
            Assert.assertTrue(checkbox.isSelected());
        }

        driver.manage().deleteAllCookies();
        driver.navigate().refresh();

        allCheckboxes = explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//label[text()=' Have you ever had (Please check all that apply) ']/following-sibling::div//span[@class='form-checkbox-item']//input")));
        for (WebElement checkbox : allCheckboxes) {
            if (checkbox.isSelected()) {
                explicitWait.until(ExpectedConditions.elementToBeClickable(checkbox)).click();
                explicitWait.until(ExpectedConditions.elementSelectionStateToBe(checkbox, false));
            }
            Assert.assertFalse(checkbox.isSelected());
        }

        allCheckboxes = explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//label[text()=' Have you ever had (Please check all that apply) ']/following-sibling::div//span[@class='form-checkbox-item']//input")));
        for (WebElement checkbox : allCheckboxes) {
            if (!checkbox.isSelected() && checkbox.getAttribute("value").equals("Heart Attack")) {
                explicitWait.until(ExpectedConditions.elementToBeClickable(checkbox)).click();
                explicitWait.until(ExpectedConditions.elementToBeSelected(checkbox));
            }
            if (checkbox.getAttribute("value").equals("Heart Attack")) {
                Assert.assertTrue(checkbox.isSelected());
            } else {
                Assert.assertFalse(checkbox.isSelected());
            }
        }
    }

    // this page unavailable since 01/01/2024
    /*@Test
    public void TC_05_Custom_jsClick() {
        driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");

        By radioLocator = By.xpath("//div[text()='Đăng ký cho người thân']/preceding-sibling::div/input");
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", driver.findElement(radioLocator));
        explicitWait.until(ExpectedConditions.elementToBeSelected(radioLocator));
        Assert.assertTrue(driver.findElement(radioLocator).isSelected());
    }*/

    @Test
    public void TC_06_Custom_GoogleDocs() {
        driver.get("https://docs.google.com/forms/d/e/1FAIpQLSfiypnd69zhuDkjKgqvpID9kwO29UCzeCVrGGtbNPZXQok0jA/viewform");

        List<WebElement> radioGroup = explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div[role='radio']")));
        for (WebElement radio : radioGroup) {
            if (radio.getAttribute("data-value").equals("Hà Nội")) {
                explicitWait.until(ExpectedConditions.elementToBeClickable(radio)).click();
                explicitWait.until(ExpectedConditions.attributeToBe(radio, "aria-checked", "true"));
            }
        }
        radioGroup = explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div[role='radio']")));
        for (WebElement radio : radioGroup) {
            if (radio.getAttribute("data-value").equals("Hà Nội")) {
                Assert.assertEquals(radio.getAttribute("aria-checked"), "true");
            } else {
                Assert.assertEquals(radio.getAttribute("aria-checked"), "false");
            }
        }

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false)", driver.findElement(By.xpath("//span[text()='Gửi']")));

        List<WebElement> checkboxGroup = explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div[role='checkbox']")));
        for (WebElement checkbox : checkboxGroup) {
            if (checkbox.getAttribute("aria-label").startsWith("Quảng") && checkbox.getAttribute("aria-checked").equals("false")) {
                explicitWait.until(ExpectedConditions.elementToBeClickable(checkbox)).click();
                explicitWait.until(ExpectedConditions.attributeToBe(checkbox, "aria-checked", "true"));
            } else if (!checkbox.getAttribute("aria-label").startsWith("Quảng") && checkbox.getAttribute("aria-checked").equals("true")) {
                explicitWait.until(ExpectedConditions.elementToBeClickable(checkbox)).click();
                explicitWait.until(ExpectedConditions.attributeToBe(checkbox, "aria-checked", "false"));
            }
        }
        checkboxGroup = explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div[role='checkbox']")));
        for (WebElement checkbox : checkboxGroup) {
            if (checkbox.getAttribute("aria-label").startsWith("Quảng")) {
                Assert.assertEquals(checkbox.getAttribute("aria-checked"), "true");
            } else {
                Assert.assertEquals(checkbox.getAttribute("aria-checked"), "false");
            }
        }

        explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Gửi']"))).click();
        Assert.assertTrue(findVisibleElement(By.xpath("//div[text()='Câu trả lời của bạn đã được ghi lại.']")).isDisplayed());
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

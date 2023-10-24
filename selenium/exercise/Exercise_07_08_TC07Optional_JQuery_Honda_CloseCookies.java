package exercise;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class Exercise_07_08_TC07Optional_JQuery_Honda_CloseCookies {
    WebDriver driver;
    WebDriverWait explicitWait;

    public void sleepInSeconds (long timeInSecond) {
        try {
            Thread.sleep(timeInSecond*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropdownSelect (String dropdownLocator, String optionsLocator, String optionValue) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", driver.findElement(By.cssSelector(dropdownLocator)));
        sleepInSeconds(1);
        driver.findElement(By.cssSelector(dropdownLocator)).click();
        List<WebElement> dropdownOptions = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(optionsLocator)));
        for (WebElement option : dropdownOptions) {
            if (option.getText().equals(optionValue)) {
                option.click();
                break;
            }
        }
        sleepInSeconds(1);
    }

    @BeforeClass
    public void beforeClass() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();

        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Test
    public void TC_01_JQuery_Honda() {
        driver.get("https://www.honda.com.vn/o-to/du-toan-chi-phi");
        sleepInSeconds(1);

        driver.findElement(By.cssSelector("i[class='fa fa-times x-cookie']")).click();
        sleepInSeconds(1);

        dropdownSelect("button#selectize-input", "div.dropdown-menu.show>a", "HR-V RS (Đen ánh độc tôn/ xám phong cách)");
        Assert.assertEquals(driver.findElement(By.cssSelector("button#selectize-input")).getText(), "HR-V RS (Đen ánh độc tôn/ xám phong cách)");
        dropdownSelect("select#province", "select#province>option", "Đà Nẵng");
        Assert.assertTrue(driver.findElement(By.xpath("//option[text()='Đà Nẵng']")).isSelected());
        dropdownSelect("select#registration_fee", "select#registration_fee>option", "Khu vực III");
        Assert.assertTrue(driver.findElement(By.xpath("//option[text()='Khu vực III']")).isSelected());

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", driver.findElement(By.cssSelector("div.cost-note")));
        sleepInSeconds(1);

        WebElement totalCost = driver.findElement(By.cssSelector("div.cost-price span.total"));
        String totalCostString = totalCost.getText();
        Assert.assertEquals(totalCostString, "");
        driver.findElement(By.cssSelector("input.btn-cost-estimates")).click();
        sleepInSeconds(1);
        Assert.assertNotEquals(totalCost.getText(), "");
        System.out.println("Before: " + totalCostString + "\tAfter: " + totalCost.getText());

        driver.navigate().refresh();
        sleepInSeconds(1);

        dropdownSelect("button#selectize-input", "div.dropdown-menu.show>a", "CIVIC TYPE R (Trắng/Đỏ/Xanh/Xám/Đen)");
        Assert.assertEquals(driver.findElement(By.cssSelector("button#selectize-input")).getText(), "CIVIC TYPE R (Trắng/Đỏ/Xanh/Xám/Đen)");
        Select provinceDropdown = new Select(driver.findElement(By.cssSelector("select#province")));
        provinceDropdown.selectByVisibleText("Điện Biên");
        Assert.assertEquals(provinceDropdown.getFirstSelectedOption().getText(), "Điện Biên");
        Select areaDropdown = new Select(driver.findElement(By.cssSelector("select#registration_fee")));
        areaDropdown.selectByVisibleText("Khu vực II");
        Assert.assertEquals(areaDropdown.getFirstSelectedOption().getText(), "Khu vực II");

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", driver.findElement(By.cssSelector("div.cost-note")));
        sleepInSeconds(1);

        totalCost = driver.findElement(By.cssSelector("div.cost-price span.total"));
        totalCostString = totalCost.getText();
        Assert.assertEquals(totalCostString, "");
        driver.findElement(By.cssSelector("input.btn-cost-estimates")).click();
        sleepInSeconds(1);
        Assert.assertNotEquals(totalCost.getText(), "");
        System.out.println("Before: " + totalCostString + "\tAfter: " + totalCost.getText());
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

package exercise;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class Exercise_07_08_TC07Optional_Multiple_Select {
    WebDriver driver;
    WebDriverWait explicitWait;

    public void sleepInSeconds (long timeInSecond) {
        try {
            Thread.sleep(timeInSecond*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropdownMultipleSelect(String optionsLocator, String optionValue) {
        List<WebElement> dropdownOptions = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(optionsLocator)));
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
    public void TC_03_Multiple_Select() {
        driver.get("https://multiple-select.wenzhixin.net.cn/templates/template.html?v=189&url=basic.html");
        sleepInSeconds(1);

        driver.findElement(By.xpath("//div[2]/div/div/button")).click();
        sleepInSeconds(1);

        dropdownMultipleSelect("//div[2]/div/div/button/following-sibling::div//span", "January");
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='January']/preceding-sibling::input")).isSelected());
        Assert.assertEquals(driver.findElement(By.xpath("//div[2]/div/div/button/span")).getText(), "January");

        dropdownMultipleSelect("//div[2]/div/div/button/following-sibling::div//span", "February");
        dropdownMultipleSelect("//div[2]/div/div/button/following-sibling::div//span", "March");
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='February']/preceding-sibling::input")).isSelected());
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='March']/preceding-sibling::input")).isSelected());
        Assert.assertEquals(driver.findElement(By.xpath("//div[2]/div/div/button/span")).getText(), "January, February, March");

        dropdownMultipleSelect("//div[2]/div/div/button/following-sibling::div//span", "April");
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='April']/preceding-sibling::input")).isSelected());
        Assert.assertEquals(driver.findElement(By.xpath("//div[2]/div/div/button/span")).getText(), "4 of 12 selected");

        dropdownMultipleSelect("//div[2]/div/div/button/following-sibling::div//span", "May");
        dropdownMultipleSelect("//div[2]/div/div/button/following-sibling::div//span", "June");
        dropdownMultipleSelect("//div[2]/div/div/button/following-sibling::div//span", "July");
        dropdownMultipleSelect("//div[2]/div/div/button/following-sibling::div//span", "August");
        dropdownMultipleSelect("//div[2]/div/div/button/following-sibling::div//span", "September");
        dropdownMultipleSelect("//div[2]/div/div/button/following-sibling::div//span", "October");
        dropdownMultipleSelect("//div[2]/div/div/button/following-sibling::div//span", "November");
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='May']/preceding-sibling::input")).isSelected());
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='June']/preceding-sibling::input")).isSelected());
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='July']/preceding-sibling::input")).isSelected());
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='August']/preceding-sibling::input")).isSelected());
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='September']/preceding-sibling::input")).isSelected());
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='October']/preceding-sibling::input")).isSelected());
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='November']/preceding-sibling::input")).isSelected());
        Assert.assertEquals(driver.findElement(By.xpath("//div[2]/div/div/button/span")).getText(), "11 of 12 selected");

        dropdownMultipleSelect("//div[2]/div/div/button/following-sibling::div//span", "December");
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='December']/preceding-sibling::input")).isSelected());
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='[Select all]']/preceding-sibling::input")).isSelected());
        Assert.assertEquals(driver.findElement(By.xpath("//div[2]/div/div/button/span")).getText(), "All selected");

        driver.navigate().refresh();
        sleepInSeconds(1);

        driver.findElement(By.xpath("//div[2]/div/div/button")).click();
        sleepInSeconds(1);

        dropdownMultipleSelect("//div[2]/div/div/button/following-sibling::div//span", "[Select all]");
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='January']/preceding-sibling::input")).isSelected());
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='February']/preceding-sibling::input")).isSelected());
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='March']/preceding-sibling::input")).isSelected());
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='April']/preceding-sibling::input")).isSelected());
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='May']/preceding-sibling::input")).isSelected());
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='June']/preceding-sibling::input")).isSelected());
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='July']/preceding-sibling::input")).isSelected());
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='August']/preceding-sibling::input")).isSelected());
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='September']/preceding-sibling::input")).isSelected());
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='October']/preceding-sibling::input")).isSelected());
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='November']/preceding-sibling::input")).isSelected());
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='December']/preceding-sibling::input")).isSelected());
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='[Select all]']/preceding-sibling::input")).isSelected());
        Assert.assertEquals(driver.findElement(By.xpath("//div[2]/div/div/button/span")).getText(), "All selected");
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

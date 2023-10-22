package exercise;

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

public class Exercise_07_08_TC07Optional_Angular_Covid {
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
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", driver.findElement(By.xpath(dropdownLocator)));
        sleepInSeconds(1);
        driver.findElement(By.xpath(dropdownLocator)).click();
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
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();

        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Test
    public void TC_02_Angular() {
        driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");
        sleepInSeconds(1);

        dropdownSelect("//div[text()='Đăng kí mũi tiêm thứ']/parent::div", "//ng-dropdown-panel//span", "Mũi tiêm thứ nhất");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Đăng kí mũi tiêm thứ']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "Mũi tiêm thứ nhất");

        dropdownSelect("//div[text()='Giới tính']/parent::div", "//ng-dropdown-panel//span", "Nam");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Giới tính']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "Nam");

        dropdownSelect("//div[text()='Tỉnh/Thành phố']/parent::div", "//ng-dropdown-panel//span", "Thành phố Đà Nẵng");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Tỉnh/Thành phố']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "Thành phố Đà Nẵng");

        dropdownSelect("//div[text()='Quận/Huyện']/parent::div", "//ng-dropdown-panel//span", "Quận Cẩm Lệ");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Quận/Huyện']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "Quận Cẩm Lệ");

        dropdownSelect("//div[text()='Xã/Phường']/parent::div", "//ng-dropdown-panel//span", "Phường Hòa Xuân");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Xã/Phường']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "Phường Hòa Xuân");

        dropdownSelect("//div[text()='Dân tộc']/parent::div", "//ng-dropdown-panel//span", "Lào");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Dân tộc']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "Lào");

        dropdownSelect("//div[text()='Quốc tịch']/parent::div", "//ng-dropdown-panel//span", "Cuba");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Quốc tịch']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "Cuba");

        dropdownSelect("//div[text()='Nhóm ưu tiên']/parent::div", "//ng-dropdown-panel//div[@class='ng-star-inserted']", "15. Người lao động tự do");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Nhóm ưu tiên']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "15. Người lao động tự do");

        dropdownSelect("//div[text()='Quan hệ']/parent::div", "//ng-dropdown-panel//span", "Người giám hộ");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Quan hệ']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "Người giám hộ");

        dropdownSelect("//div[text()='Buổi tiêm mong muốn']/parent::div", "//ng-dropdown-panel//span", "Cả ngày");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Buổi tiêm mong muốn']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "Cả ngày");

        dropdownSelect("//div[text()='Đăng kí mũi tiêm thứ']/parent::div", "//ng-dropdown-panel//span", "Mũi tiêm tiếp theo");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Đăng kí mũi tiêm thứ']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "Mũi tiêm tiếp theo");

        dropdownSelect("//div[text()='Tên vắc xin']/parent::div", "//ng-dropdown-panel//span", "COVID-19 Vaccine Astrazeneca");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Tên vắc xin']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "COVID-19 Vaccine Astrazeneca");

        dropdownSelect("//div[text()='Địa điểm tiêm']/parent::div", "//ng-dropdown-panel//span", "01015-Bệnh viện Quân Y 354");
        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Địa điểm tiêm']/parent::div//span[@class='ng-value-label ng-star-inserted']")).getText(), "01015-Bệnh viện Quân Y 354");
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

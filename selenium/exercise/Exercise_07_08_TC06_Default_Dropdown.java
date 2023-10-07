package exercise;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Random;

public class Exercise_07_08_TC06_Default_Dropdown {
    WebDriver driver;

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
    }

    public void sleepInSeconds (long timeInSecond) {
        try {
            Thread.sleep(timeInSecond*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getEmailRandom() {
        Random rand = new Random();
        return "automation" + rand.nextInt(999999) + "@gmail.com";
    }

    @Test
    public void TC_06_Register() {
        driver.get("https://applitools.com/automating-tests-chrome-devtools-recorder-webinar/");

        String firstName = "Automation", lastName = "Testing", company = "Automation FC";
        String email = getEmailRandom();

        driver.findElement(By.cssSelector("input#Email")).sendKeys(email);
        driver.findElement(By.cssSelector("input#FirstName")).sendKeys(firstName);
        driver.findElement(By.cssSelector("input#LastName")).sendKeys(lastName);

        Select jobFunction = new Select(driver.findElement(By.xpath("//option[text()='*Job Function']/parent::select")));
        Assert.assertFalse(jobFunction.isMultiple());
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", jobFunction);
        sleepInSeconds(1);
        jobFunction.selectByVisibleText("SDET / Test Automation Engineer");
        System.out.println("Job Function:\n" + jobFunction.getFirstSelectedOption().getText());
        System.out.println("Number of Options: " + jobFunction.getOptions().size());

        driver.findElement(By.cssSelector("input#Company")).sendKeys(company);

        Select testFramework = new Select(driver.findElement(By.xpath("//option[text()='*Existing Test Framework']/parent::select")));
        Assert.assertFalse(testFramework.isMultiple());
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", testFramework);
        sleepInSeconds(1);
        testFramework.selectByVisibleText("Selenium");
        System.out.println("Existing Test Framework:\n" + testFramework.getFirstSelectedOption().getText());
        System.out.println("Number of Options: " + testFramework.getOptions().size());

        Select country = new Select(driver.findElement(By.xpath("//option[text()='*Country']/parent::select")));
        Assert.assertFalse(country.isMultiple());
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", country);
        sleepInSeconds(1);
        country.selectByVisibleText("Greece");
        System.out.println("Country:\n" + country.getFirstSelectedOption().getText());
        System.out.println("Number of Options: " + country.getOptions().size());

        WebElement checkBox = driver.findElement(By.cssSelector("input#Opt_In_Compliance__c"));
        if (checkBox.isSelected()) {
            checkBox.click();
        }
        driver.findElement(By.xpath("//button[text()='REGISTER NOW']")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector("div.mktoErrorMsg")).getText(),"This field is required.");
    }

    @AfterClass
    public void afterClass() {
        //driver.quit();
    }
}

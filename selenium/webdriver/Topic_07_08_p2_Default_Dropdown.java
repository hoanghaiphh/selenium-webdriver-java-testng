package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Random;

public class Topic_07_08_p2_Default_Dropdown {
    WebDriver driver;
    WebDriverWait explicitWait;

    public String getEmailRandom() {
        Random rand = new Random();
        return "automation" + rand.nextInt(999999) + "@gmail.com";
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
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Test
    public void TC_04_Default_Dropdown() {
        driver.get("https://demo.nopcommerce.com/");

        String firstName = "Automation", lastName = "Testing", password = "Abcd@1234";
        String email = getEmailRandom();

        clickOnElement(By.cssSelector("a.ico-register"));
        findVisibleElement(By.cssSelector("input#FirstName")).sendKeys(firstName);
        findVisibleElement(By.cssSelector("input#LastName")).sendKeys(lastName);

        Select day = new Select(findVisibleElement(By.cssSelector("select[name='DateOfBirthDay']")));
        Assert.assertFalse(day.isMultiple());
        Assert.assertEquals(day.getOptions().size(),32);
        day.selectByVisibleText("1");
        explicitWait.until(ExpectedConditions.elementToBeSelected(By.xpath("//option[text()='1']")));

        Select month = new Select(findVisibleElement(By.cssSelector("select[name='DateOfBirthMonth']")));
        Assert.assertFalse(month.isMultiple());
        Assert.assertEquals(month.getOptions().size(),13);
        month.selectByVisibleText("May");
        explicitWait.until(ExpectedConditions.elementToBeSelected(By.xpath("//option[text()='May']")));

        Select year = new Select(findVisibleElement(By.cssSelector("select[name='DateOfBirthYear']")));
        Assert.assertFalse(year.isMultiple());
        Assert.assertEquals(year.getOptions().size(),112);
        year.selectByVisibleText("1980");
        explicitWait.until(ExpectedConditions.elementToBeSelected(By.xpath("//option[text()='1980']")));

        findVisibleElement(By.cssSelector("input#Email")).sendKeys(email);
        findVisibleElement(By.cssSelector("input#Password")).sendKeys(password);
        findVisibleElement(By.cssSelector("input#ConfirmPassword")).sendKeys(password);
        clickOnElement(By.cssSelector("button#register-button"));

        Assert.assertEquals(findVisibleElement(By.cssSelector("div.result")).getText(),"Your registration completed");

        clickOnElement(By.xpath("//a[text()='Continue']"));
        explicitWait.until(ExpectedConditions.urlToBe("https://demo.nopcommerce.com/"));
        Assert.assertEquals(driver.getCurrentUrl(),"https://demo.nopcommerce.com/");

        clickOnElement(By.cssSelector("a.ico-login"));
        findVisibleElement(By.cssSelector("input#Email")).sendKeys(email);
        findVisibleElement(By.cssSelector("input#Password")).sendKeys(password);
        clickOnElement(By.xpath("//button[text()='Log in']"));
        clickOnElement(By.cssSelector("a.ico-account"));

        Select verifyDay = new Select(findVisibleElement(By.cssSelector("select[name='DateOfBirthDay']")));
        Assert.assertEquals(verifyDay.getFirstSelectedOption().getText(),"1");

        Select verifyMonth = new Select(findVisibleElement(By.cssSelector("select[name='DateOfBirthMonth']")));
        Assert.assertEquals(verifyMonth.getFirstSelectedOption().getText(),"May");

        Select verifyYear = new Select(findVisibleElement(By.cssSelector("select[name='DateOfBirthYear']")));
        Assert.assertEquals(verifyYear.getFirstSelectedOption().getText(),"1980");
    }

    // 404 - Page not found
    /*@Test
    public void TC_06_Default_Dropdown() {
        driver.get("https://applitools.com/automating-tests-chrome-devtools-recorder-webinar/");

        String firstName = "Automation", lastName = "Testing", company = "Automation FC";
        String email = getEmailRandom();

        findVisibleElement(By.cssSelector("input#Email")).sendKeys(email);
        findVisibleElement(By.cssSelector("input#FirstName")).sendKeys(firstName);
        findVisibleElement(By.cssSelector("input#LastName")).sendKeys(lastName);

        Select jobFunction = new Select(findVisibleElement(By.xpath("//option[text()='*Job Function']/parent::select")));
        Assert.assertFalse(jobFunction.isMultiple());
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", jobFunction);
        jobFunction.selectByVisibleText("SDET / Test Automation Engineer");
        System.out.println("Job Function:\n" + jobFunction.getFirstSelectedOption().getText());
        System.out.println("Number of Options: " + jobFunction.getOptions().size());

        findVisibleElement(By.cssSelector("input#Company")).sendKeys(company);

        Select testFramework = new Select(findVisibleElement(By.xpath("//option[text()='*Existing Test Framework']/parent::select")));
        Assert.assertFalse(testFramework.isMultiple());
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", testFramework);
        testFramework.selectByVisibleText("Selenium");
        System.out.println("Existing Test Framework:\n" + testFramework.getFirstSelectedOption().getText());
        System.out.println("Number of Options: " + testFramework.getOptions().size());

        Select country = new Select(findVisibleElement(By.xpath("//option[text()='*Country']/parent::select")));
        Assert.assertFalse(country.isMultiple());
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", country);
        country.selectByVisibleText("Greece");
        System.out.println("Country:\n" + country.getFirstSelectedOption().getText());
        System.out.println("Number of Options: " + country.getOptions().size());

        if (findVisibleElement(By.cssSelector("input#Opt_In_Compliance__c")).isSelected()) {
            clickOnElement(By.cssSelector("input#Opt_In_Compliance__c"));
        }
        clickOnElement(By.xpath("//button[text()='REGISTER NOW']"));
        Assert.assertEquals(findVisibleElement(By.cssSelector("div.mktoErrorMsg")).getText(),"This field is required.");
    }*/

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

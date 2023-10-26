package exercise;

import org.openqa.selenium.By;
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

public class Exercise_07_08_TC_07_Custom_Dropdown {
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
        driver.findElement(By.cssSelector(dropdownLocator)).click();
        sleepInSeconds(1);
        /*explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(optionsLocator)));
        List<WebElement> dropdownOptions = driver.findElements(By.cssSelector(optionsLocator));
        explicitWait...presenceOfAllElementsLocatedBy... cũng trả về list elements*/
        List<WebElement> dropdownOptions = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(optionsLocator)));
        for (WebElement option : dropdownOptions) {
            if (option.getText().equals(optionValue)) {
                option.click();
                break;
            }
        }
        sleepInSeconds(1);
    }

    public void editableDropdownSelect (String dropdownLocator, String optionsLocator, String optionValue) {
        driver.findElement(By.cssSelector(dropdownLocator)).clear();
        driver.findElement(By.cssSelector(dropdownLocator)).sendKeys(optionValue);
        sleepInSeconds(1);
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
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();

        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Test
    public void TC_01_JQuery() {
        driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");
        sleepInSeconds(1);

        dropdownSelect("span#speed-button", "ul#speed-menu div", "Fast");
        Assert.assertEquals(driver.findElement(By.cssSelector("span#speed-button span.ui-selectmenu-text")).getText(),"Fast");

        dropdownSelect("span#files-button", "ul#files-menu div", "ui.jQuery.js");
        Assert.assertEquals(driver.findElement(By.cssSelector("span#files-button span.ui-selectmenu-text")).getText(),"ui.jQuery.js");

        dropdownSelect("span#number-button", "ul#number-menu div", "16");
        Assert.assertEquals(driver.findElement(By.cssSelector("span#number-button span.ui-selectmenu-text")).getText(),"16");

        dropdownSelect("span#salutation-button", "ul#salutation-menu div", "Dr.");
        Assert.assertEquals(driver.findElement(By.cssSelector("span#salutation-button span.ui-selectmenu-text")).getText(),"Dr.");
    }

    @Test
    public void TC_02_ReactJS() {
        driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
        sleepInSeconds(1);

        dropdownSelect("i.dropdown.icon", "div.item span.text", "Stevie Feliciano");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(),"Stevie Feliciano");

        driver.navigate().refresh();
        sleepInSeconds(1);

        dropdownSelect("i.dropdown.icon", "div.item span.text", "Justen Kitsune");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(),"Justen Kitsune");
    }

    @Test
    public void TC_03_VueJS() {
        driver.get("https://mikerodham.github.io/vue-dropdowns/");
        sleepInSeconds(1);

        dropdownSelect("li.dropdown-toggle", "ul.dropdown-menu a", "Third Option");
        Assert.assertEquals(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText(),"Third Option");

        driver.navigate().refresh();
        sleepInSeconds(1);

        dropdownSelect("li.dropdown-toggle", "ul.dropdown-menu a", "Second Option");
        Assert.assertEquals(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText(),"Second Option");
    }

    @Test
    public void TC_04_Editable() {
        driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");
        sleepInSeconds(1);

        dropdownSelect("input.search", "div.item span", "Angola");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(),"Angola");

        driver.navigate().refresh();
        sleepInSeconds(1);

        editableDropdownSelect("input.search", "div.item span", "Argentina");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(),"Argentina");

        driver.navigate().refresh();
        sleepInSeconds(1);

        dropdownSelect("input.search", "div.item span", "Belarus");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(),"Belarus");

        driver.navigate().refresh();
        sleepInSeconds(1);

        editableDropdownSelect("input.search", "div.item span", "Bangladesh");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(),"Bangladesh");

        driver.navigate().refresh();
        sleepInSeconds(1);

        editableDropdownSelect("input.search", "div.item span", "Andorra");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(),"Andorra");
    }

    @Test
    public void TC_05_nopcommerce() {
        driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");
        sleepInSeconds(1);

        dropdownSelect("select[name='DateOfBirthDay']", "select[name='DateOfBirthDay']>option", "15");
        Assert.assertTrue(driver.findElement(By.xpath("//select[@name='DateOfBirthDay']/option[text() = '15']")).isSelected());
        dropdownSelect("select[name='DateOfBirthMonth']", "select[name='DateOfBirthMonth']>option", "October");
        Assert.assertTrue(driver.findElement(By.xpath("//select[@name='DateOfBirthMonth']/option[text() = 'October']")).isSelected());
        dropdownSelect("select[name='DateOfBirthYear']", "select[name='DateOfBirthYear']>option", "1989");
        Assert.assertTrue(driver.findElement(By.xpath("//select[@name='DateOfBirthYear']/option[text() = '1989']")).isSelected());

        driver.navigate().refresh();
        sleepInSeconds(1);

        dropdownSelect("select[name='DateOfBirthDay']", "select[name='DateOfBirthDay']>option", "25");
        Assert.assertTrue(driver.findElement(By.xpath("//select[@name='DateOfBirthDay']/option[text() = '25']")).isSelected());
        dropdownSelect("select[name='DateOfBirthMonth']", "select[name='DateOfBirthMonth']>option", "June");
        Assert.assertTrue(driver.findElement(By.xpath("//select[@name='DateOfBirthMonth']/option[text() = 'June']")).isSelected());
        dropdownSelect("select[name='DateOfBirthYear']", "select[name='DateOfBirthYear']>option", "2021");
        Assert.assertTrue(driver.findElement(By.xpath("//select[@name='DateOfBirthYear']/option[text() = '2021']")).isSelected());
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

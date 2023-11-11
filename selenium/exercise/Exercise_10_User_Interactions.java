package exercise;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Exercise_10_User_Interactions {
    WebDriver driver;
    Actions actions;
    Keys cmdCtrl = Platform.getCurrent().is(Platform.MAC) ? Keys.COMMAND : Keys.CONTROL;
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
        actions = new Actions(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_Hover_Tooltip() {
        driver.get("https://automationfc.github.io/jquery-tooltip/");

        actions.moveToElement(driver.findElement(By.cssSelector("input#age"))).perform();
        sleepInSeconds(1);
        // option 1: verify tooltip with text is displayed
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='ui-tooltip-content' and text()='We ask for your age only for statistical purposes.']")).isDisplayed());
        // option 2: verify text in tooltip
        Assert.assertEquals(driver.findElement(By.cssSelector("div.ui-tooltip-content")).getText(), "We ask for your age only for statistical purposes.");
    }

    @Test
    public void TC_03_Hover_Menu() {
        driver.get("https://www.fahasa.com/");

        actions.moveToElement(driver.findElement(By.cssSelector("span.icon_menu"))).perform();
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='fhs_stretch_stretch']//div[text()='Danh mục sản phẩm']")).isDisplayed());
        sleepInSeconds(1);

        actions.moveToElement(driver.findElement(By.xpath("//a[@title='Đồ Chơi']"))).perform();
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='fhs_column_stretch']//span[text()='Đồ Chơi']")).isDisplayed());
        sleepInSeconds(1);

        driver.findElement(By.xpath("//div[@class='fhs_column_stretch']//a[text()='Máy Bay']")).click();
        sleepInSeconds(1);
        Assert.assertTrue(driver.findElement(By.xpath("//ol[@class='breadcrumb']//strong[text()='Máy Bay - Tàu Vũ Trụ']")).isDisplayed());
    }

    @Test
    public void TC_04a_Click_and_Hold() { // draw a square start at 1 -> end at 15
        driver.get("https://automationfc.github.io/jquery-selectable/");

        List<WebElement> allNumbers = driver.findElements(By.cssSelector("li.ui-state-default"));
        actions.clickAndHold(allNumbers.get(0)).moveToElement(allNumbers.get(14)).release().perform(); // can use .pause() if...
        sleepInSeconds(1);

        String[] expectedNumbers = new String[] {"1","2","3","5","6","7","9","10","11","13","14","15"};

        // Verify #1:
        for (WebElement number : allNumbers) {
            if (List.of(expectedNumbers).contains(number.getText())) {
                Assert.assertTrue(number.getAttribute("class").contains("ui-selected"));
                Assert.assertTrue(Color.fromString(number.getCssValue("background")).asHex().equalsIgnoreCase("#F39814"));
            } else {
                Assert.assertFalse(number.getAttribute("class").contains("ui-selected"));
                Assert.assertTrue(Color.fromString(number.getCssValue("background")).asHex().equalsIgnoreCase("#f6f6f6"));
            }
        }
        // Verify #2:
        List<WebElement> selectedNumbers = driver.findElements(By.cssSelector("li.ui-selected"));
        Assert.assertEquals(selectedNumbers.size(), 12);
        List<String> actualNumbers = new ArrayList<String>();
        for (WebElement number : selectedNumbers) {
            actualNumbers.add(number.getText());
            Assert.assertTrue(Color.fromString(number.getCssValue("background")).asHex().equalsIgnoreCase("#F39814"));
        }
        List<String> expectedNumbersList = Arrays.asList(expectedNumbers); // Convert from Array to ArrayList (List)
        Assert.assertEquals(expectedNumbersList, actualNumbers);
    }

    @Test
    public void TC_04b_Click_and_Hold() { // select all numbers 1 -> 15
        driver.get("https://automationfc.github.io/jquery-selectable/");

        List<WebElement> allNumbers = driver.findElements(By.cssSelector("li.ui-state-default"));

        actions.keyDown(cmdCtrl).perform();
        for (int i = 0; i < 15; i++) {
            actions.click(allNumbers.get(i));
        }
        actions.keyUp(cmdCtrl).perform();
        sleepInSeconds(1);

        for (int i = 0; i < allNumbers.size(); i++) {
            if (i < 15) {
                Assert.assertTrue(allNumbers.get(i).getAttribute("class").contains("ui-selected"));
                Assert.assertTrue(Color.fromString(allNumbers.get(i).getCssValue("background")).asHex().equalsIgnoreCase("#F39814"));
            } else {
                Assert.assertFalse(allNumbers.get(i).getAttribute("class").contains("ui-selected"));
                Assert.assertTrue(Color.fromString(allNumbers.get(i).getCssValue("background")).asHex().equalsIgnoreCase("#f6f6f6"));
            }
        }
    }

    @Test
    public void TC_05_Select_Random_Items() {
        driver.get("https://automationfc.github.io/jquery-selectable/");

        String[] selectedNumbers = new String[] {"1","3","6","11"};

        List<WebElement> allNumbers = driver.findElements(By.cssSelector("li.ui-state-default"));

        actions.keyDown(cmdCtrl).perform();
        for (WebElement number : allNumbers) {
            if (List.of(selectedNumbers).contains(number.getText())) {
                actions.click(number).perform();
            }
        }
        actions.keyUp(cmdCtrl).perform();
        sleepInSeconds(1);

        for (WebElement number : allNumbers) {
            if (List.of(selectedNumbers).contains(number.getText())) {
                Assert.assertTrue(number.getAttribute("class").contains("ui-selected"));
                Assert.assertTrue(Color.fromString(number.getCssValue("background")).asHex().equalsIgnoreCase("#F39814"));
            } else {
                Assert.assertFalse(number.getAttribute("class").contains("ui-selected"));
                Assert.assertTrue(Color.fromString(number.getCssValue("background")).asHex().equalsIgnoreCase("#f6f6f6"));
            }
        }
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

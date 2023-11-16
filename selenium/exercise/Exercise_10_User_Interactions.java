package exercise;

import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
        // option 2: verify text in tooltip --> recommended for Text
        Assert.assertEquals(driver.findElement(By.cssSelector("div.ui-tooltip-content")).getText(), "We ask for your age only for statistical purposes.");
    }

    @Test
    public void TC_03_Hover_Menu() {
        driver.get("https://www.fahasa.com/");

        actions.moveToElement(driver.findElement(By.cssSelector("span.icon_menu"))).perform();
        sleepInSeconds(1);
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='fhs_stretch_stretch']//div[text()='Danh mục sản phẩm']")).isDisplayed());

        actions.moveToElement(driver.findElement(By.cssSelector("a[title='Đồ Chơi']"))).perform();
        sleepInSeconds(1);
        Assert.assertEquals(driver.findElement(By.cssSelector("div.fhs_column_stretch span.menu-title")).getText(), "Đồ Chơi");

        driver.findElement(By.xpath("//div[@class='fhs_column_stretch']//a[text()='Máy Bay']")).click();
        sleepInSeconds(1);
        Assert.assertEquals(driver.findElement(By.cssSelector("ol.breadcrumb strong")).getText(),"MÁY BAY - TÀU VŨ TRỤ");
    }

    @Test
    public void TC_04a_Click_and_Hold() { // draw a square start at 1 -> end at 15
        driver.get("https://automationfc.github.io/jquery-selectable/");

        List<WebElement> allNumbers = driver.findElements(By.cssSelector("li.ui-state-default"));

        actions.clickAndHold(allNumbers.get(0)).moveToElement(allNumbers.get(14)).release().perform(); // can use .pause(x millisecond) if...
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
        List<String> expectedNumbersList = Arrays.asList(expectedNumbers); // Convert from Array to ArrayList (List)
        List<WebElement> selectedNumbers = driver.findElements(By.cssSelector("li.ui-selected"));
        Assert.assertEquals(selectedNumbers.size(), 12);
        List<String> actualNumbersList = new ArrayList<String>();
        for (WebElement number : selectedNumbers) {
            actualNumbersList.add(number.getText());
        }
        Assert.assertEquals(expectedNumbersList, actualNumbersList);
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

    @Test
    public void TC_06a_Double_Click() {
        driver.get("https://automationfc.github.io/basic-form/index.html");

        WebElement doubleClickBtn = driver.findElement(By.xpath("//button[text()='Double click me']"));

        // scroll to element if run on FF - not necessary if run on Chromium: Chrome/ Edge
        // actions.scrollToElement().perform(); --> only support for Chromium Browser/ Selenium v4.2+
        if (driver.toString().contains("firefox")) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", doubleClickBtn);
            sleepInSeconds(1);
        }
        actions.doubleClick(doubleClickBtn).perform();
        sleepInSeconds(1);

        Assert.assertEquals(driver.findElement(By.cssSelector("p#demo")).getText(), "Hello Automation Guys!");
    }

    @Test
    public void TC_06b_Double_Click() {
        driver.quit();
        driver = new ChromeDriver();
        actions = new Actions(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();

        driver.get("https://automationfc.github.io/basic-form/index.html");

        actions.doubleClick(driver.findElement(By.xpath("//button[text()='Double click me']"))).perform();
        sleepInSeconds(1);

        Assert.assertEquals(driver.findElement(By.cssSelector("p#demo")).getText(), "Hello Automation Guys!");
    }

    @Test
    public void TC_07_Right_Click() {
        driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");

        actions.contextClick(driver.findElement(By.xpath("//span[text()='right click me']"))).perform();
        sleepInSeconds(1);

        WebElement quitContextMenu = driver.findElement(By.cssSelector("li.context-menu-icon-quit"));
        Assert.assertTrue(quitContextMenu.isDisplayed());

        actions.moveToElement(quitContextMenu).perform();
        sleepInSeconds(1);

        Assert.assertTrue(driver.findElement(By.cssSelector("li.context-menu-icon-quit.context-menu-visible.context-menu-hover")).isDisplayed());

        quitContextMenu.click();
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.alertIsPresent()).accept();
        sleepInSeconds(1);

        Assert.assertFalse(quitContextMenu.isDisplayed());
    }

    @Test
    public void TC_08_DragDrop_HTML4() {
        driver.get("https://automationfc.github.io/kendo-drag-drop/");

        WebElement targetCircle = driver.findElement(By.cssSelector("div#droptarget"));
        WebElement sourceCircle = driver.findElement(By.cssSelector("div#draggable"));

        Assert.assertEquals(targetCircle.getText(), "Drag the small circle here.");

        actions.dragAndDrop(sourceCircle, targetCircle).perform();
        sleepInSeconds(1);

        Assert.assertEquals(targetCircle.getText(), "You did great!");
        Assert.assertTrue(Color.fromString(targetCircle.getCssValue("background-color")).asHex().equalsIgnoreCase("#03a9f4"));
    }

    public String getContentFile(String filePath) throws IOException { // get content of jQuery file as String
        Charset cs = StandardCharsets.UTF_8;
        try (FileInputStream stream = new FileInputStream(filePath)) {
            Reader reader = new BufferedReader(new InputStreamReader(stream, cs));
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[8192];
            int read;
            while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
                builder.append(buffer, 0, read);
            }
            return builder.toString();
        }
    }
    @Test
    public void TC_09a_DragDrop_HTML5_jQuery() throws IOException { // run content of jQuery file + ...
        // only work with CSS (jQuery)
        // only work for jQuery site
        driver.get("https://automationfc.github.io/drag-drop-html5/");
        sleepInSeconds(3);

        WebElement sourceElement = driver.findElement(By.cssSelector("div#column-a>header"));
        WebElement targetElement = driver.findElement(By.cssSelector("div#column-b>header"));
        Assert.assertEquals(sourceElement.getText(), "A");
        Assert.assertEquals(targetElement.getText(), "B");

        String jsFilePath = System.getProperty("user.dir") + "/jQuery/drag_and_drop_helper.js";

        String jsDragDropHTML5 = getContentFile(jsFilePath) + "$('div#column-a').simulateDragDrop({dropTarget: 'div#column-b'});";

        ((JavascriptExecutor) driver).executeScript(jsDragDropHTML5);
        sleepInSeconds(3);

        Assert.assertEquals(driver.findElement(By.cssSelector("div#column-a>header")).getText(), "B");
        Assert.assertEquals(driver.findElement(By.cssSelector("div#column-b>header")).getText(), "A");

        ((JavascriptExecutor) driver).executeScript(jsDragDropHTML5);
        sleepInSeconds(3);

        Assert.assertEquals(driver.findElement(By.cssSelector("div#column-a>header")).getText(), "A");
        Assert.assertEquals(driver.findElement(By.cssSelector("div#column-b>header")).getText(), "B");
    }

    public void dragAndDropHTML5ByXpath(String sourceLocator, String targetLocator) throws AWTException {
        WebElement source = driver.findElement(By.xpath(sourceLocator));
        WebElement target = driver.findElement(By.xpath(targetLocator));

        // Setup robot
        Robot robot = new Robot();
        robot.setAutoDelay(500);

        // Get size of elements
        Dimension sourceSize = source.getSize();
        Dimension targetSize = target.getSize();

        // Get center distance
        int xCentreSource = sourceSize.width / 2;
        int yCentreSource = sourceSize.height / 2;
        int xCentreTarget = targetSize.width / 2;
        int yCentreTarget = targetSize.height / 2;

        Point sourceLocation = source.getLocation();
        Point targetLocation = target.getLocation();

        // Make Mouse coordinate center of element
        sourceLocation.x += 20 + xCentreSource;
        sourceLocation.y += 110 + yCentreSource;
        targetLocation.x += 20 + xCentreTarget;
        targetLocation.y += 110 + yCentreTarget;

        // Move mouse to drag from location
        robot.mouseMove(sourceLocation.x, sourceLocation.y);

        // Click and drag
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseMove(((sourceLocation.x - targetLocation.x) / 2) + targetLocation.x, ((sourceLocation.y - targetLocation.y) / 2) + targetLocation.y);

        // Move to final position
        robot.mouseMove(targetLocation.x, targetLocation.y);

        // Drop
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }
    @Test
    public void TC_09b_DragDrop_HTML5_Offset() throws AWTException {
        driver.get("https://automationfc.github.io/drag-drop-html5/");

        String sourceXpath = "//div[@id='column-a']";
        String targetXpath = "//div[@id='column-b']";

        dragAndDropHTML5ByXpath(sourceXpath, targetXpath);
        sleepInSeconds(3);

        dragAndDropHTML5ByXpath(sourceXpath, targetXpath);
        sleepInSeconds(3);

        dragAndDropHTML5ByXpath(sourceXpath, targetXpath);
        sleepInSeconds(3);
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

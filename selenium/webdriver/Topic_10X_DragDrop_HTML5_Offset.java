package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.InputEvent;
import java.time.Duration;

public class Topic_10X_DragDrop_HTML5_Offset {
    WebDriver driver;

    public void sleepInSeconds (long timeInSecond) {
        try {
            Thread.sleep(timeInSecond*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void dragAndDropHTML5ByXpath(WebElement source, WebElement target) throws AWTException {
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

    // Linux: Firefox (FAILED: do nothing) - Edge/Chrome (FAILED: drag & drop but not switch)
    // Windows: Firefox (FAILED: drag & drop but not switch) - Chrome/Edge (PASSED)
    // Mac: Firefox (PASSED) - Edge/Chrome (PASSED) - Safari (FAILED:...)

    @Test
    public void TC_01_Firefox() throws AWTException {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.get("https://automationfc.github.io/drag-drop-html5/");

        WebElement sourceXpath = driver.findElement(By.xpath("//div[@id='column-a']"));
        WebElement targetXpath = driver.findElement(By.xpath("//div[@id='column-b']"));

        dragAndDropHTML5ByXpath(sourceXpath, targetXpath);
        sleepInSeconds(3);

        dragAndDropHTML5ByXpath(sourceXpath, targetXpath);
        sleepInSeconds(3);

        dragAndDropHTML5ByXpath(sourceXpath, targetXpath);
        sleepInSeconds(3);

        driver.quit();
    }

    @Test
    public void TC_02_Chrome() throws AWTException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.get("https://automationfc.github.io/drag-drop-html5/");

        WebElement sourceXpath = driver.findElement(By.xpath("//div[@id='column-a']"));
        WebElement targetXpath = driver.findElement(By.xpath("//div[@id='column-b']"));

        dragAndDropHTML5ByXpath(sourceXpath, targetXpath);
        sleepInSeconds(3);

        dragAndDropHTML5ByXpath(sourceXpath, targetXpath);
        sleepInSeconds(3);

        dragAndDropHTML5ByXpath(sourceXpath, targetXpath);
        sleepInSeconds(3);

        driver.quit();
    }

    @Test
    public void TC_03_Edge() throws AWTException {
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.get("https://automationfc.github.io/drag-drop-html5/");

        WebElement sourceXpath = driver.findElement(By.xpath("//div[@id='column-a']"));
        WebElement targetXpath = driver.findElement(By.xpath("//div[@id='column-b']"));

        dragAndDropHTML5ByXpath(sourceXpath, targetXpath);
        sleepInSeconds(3);

        dragAndDropHTML5ByXpath(sourceXpath, targetXpath);
        sleepInSeconds(3);

        dragAndDropHTML5ByXpath(sourceXpath, targetXpath);
        sleepInSeconds(3);

        driver.quit();
    }

    @Test
    public void TC_04_Safari() throws AWTException {
        if (System.getProperty("os.name").contains("Mac")) {
            driver = new SafariDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            driver.get("https://automationfc.github.io/drag-drop-html5/");

            WebElement sourceXpath = driver.findElement(By.xpath("//div[@id='column-a']"));
            WebElement targetXpath = driver.findElement(By.xpath("//div[@id='column-b']"));

            dragAndDropHTML5ByXpath(sourceXpath, targetXpath);
            sleepInSeconds(3);

            dragAndDropHTML5ByXpath(sourceXpath, targetXpath);
            sleepInSeconds(3);

            dragAndDropHTML5ByXpath(sourceXpath, targetXpath);
            sleepInSeconds(3);

            driver.quit();
        } else {
            System.out.println("Current OS: " + System.getProperty("os.name"));
            System.out.println("This computer does not have Safari Browser.");
        }
    }
}

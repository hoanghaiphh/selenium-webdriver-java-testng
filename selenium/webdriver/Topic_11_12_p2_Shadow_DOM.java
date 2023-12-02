package webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class Topic_11_12_p2_Shadow_DOM {
    WebDriver driver;

    public void sleepInSeconds (long timeInSecond) {
        try {
            Thread.sleep(timeInSecond*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<WebElement> reduceWaitFindElements(By by) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        List<WebElement> listResult = driver.findElements(by);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        return listResult;
    }

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
    }

    @Test
    public void TC_08_Shadow_DOM() {
        driver.get("https://automationfc.github.io/shadow-dom");

        Assert.assertEquals(driver.findElement(By.cssSelector("a")).getText(), "scroll.html");

        SearchContext shadowHost = driver.findElement(By.cssSelector("div#shadow_host")).getShadowRoot();
        Assert.assertEquals(shadowHost.findElement(By.cssSelector("span.info")).getText(), "some text");
        Assert.assertEquals(shadowHost.findElement(By.cssSelector("a")).getText(), "nested scroll.html");
        Assert.assertFalse(shadowHost.findElement(By.cssSelector("input[type='checkbox']")).isSelected());

        SearchContext nestedShadowHost = shadowHost.findElement(By.cssSelector("div#nested_shadow_host")).getShadowRoot();
        Assert.assertEquals(nestedShadowHost.findElement(By.cssSelector("div#nested_shadow_content>div")).getText(), "nested text");
    }

    @Test
    public void TC_09_Shadow_DOM_Popup() {
        // before close --> in DOM | after close --> not in DOM

        driver.get("https://shopee.vn/");
        sleepInSeconds(1); // set more sleep time to close popup || Debug --> no popup case

        SearchContext shadowRoot = driver.findElement(By.cssSelector("shopee-banner-popup-stateful")).getShadowRoot();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        List<WebElement> popup = shadowRoot.findElements(By.cssSelector("div.home-popup__content")); // Selenium v4.14.1 not support By.xpath/ By.tagName
        if (!popup.isEmpty() && popup.get(0).isDisplayed()) {
            shadowRoot.findElement(By.cssSelector("div.shopee-popup__close-btn")).click();
            sleepInSeconds(1);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        driver.findElement(By.cssSelector("input.shopee-searchbar-input__input")).sendKeys("Iphone 15 Pro Max");
        driver.findElement(By.cssSelector("button.shopee-searchbar__search-button")).click();
        sleepInSeconds(2);
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

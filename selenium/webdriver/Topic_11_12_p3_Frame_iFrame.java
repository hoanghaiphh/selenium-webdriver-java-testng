package webdriver;

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

public class Topic_11_12_p3_Frame_iFrame {
    WebDriver driver;
    WebDriverWait explicitWait;

    public void sleepInSeconds (long timeInSecond) {
        try {
            Thread.sleep(timeInSecond*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public WebElement findVisibleElement(By locator) {
        return explicitWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public List<WebElement> findVisibleElements(By locator) {
        return explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public void clickOnElement(By locator) {
        explicitWait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    @BeforeClass
    public void beforeClass() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // Page A contains ỉFrame B, iFrame B contains iFrame C:
    // A --> B, B --> C: .switchTo().frame(Index/ Id/ Name/ WebElement)
    // B --> A: .switchTo().defaultContent()
    // C --> B: .switchTo().parentFrame()

    @Test
    public void TC_10_iFrame() {
        driver.get("https://skills.kynaenglish.vn/");

        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,document.body.scrollHeight)");
        explicitWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector("div.face-content>iframe")));

        Assert.assertTrue(findVisibleElement(By.cssSelector("html#facebook")).isDisplayed());
        Assert.assertTrue(findVisibleElement(By.xpath("//a[text()='Kyna.vn']/parent::div/following-sibling::div")).getText().contains("K followers"));

        driver.switchTo().defaultContent();

        explicitWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("cs_chat_iframe"));

        clickOnElement(By.cssSelector("div.button_bar"));

        findVisibleElement(By.cssSelector("input.input_name")).sendKeys("Automation FC");
        findVisibleElement(By.cssSelector("input.input_phone")).sendKeys("0905123456");
        new Select(findVisibleElement(By.cssSelector("select#serviceSelect"))).selectByVisibleText("TƯ VẤN TUYỂN SINH");
        findVisibleElement(By.cssSelector("textarea.input_textarea")).sendKeys("Selenium WebDriver");

        driver.switchTo().defaultContent();

        findVisibleElement(By.cssSelector("input#live-search-bar")).sendKeys("Excel");
        clickOnElement(By.cssSelector("button.search-button"));

        Assert.assertEquals(findVisibleElement(By.cssSelector("ul.breadcrumb>li.active")).getText(), "Danh sách khóa học");
        Assert.assertTrue(findVisibleElement(By.cssSelector("main span.menu-heading")).getText().contains("Kết quả tìm kiếm từ khóa 'Excel'"));

        String[] numbersOfResults = findVisibleElement(By.cssSelector("main span.menu-heading")).getText().split(" ");
        Assert.assertEquals(findVisibleElements(By.cssSelector("div.k-box-card-wrap")).size(), Integer.parseInt(numbersOfResults[0]));
    }

    @Test
    public void TC_11_iFrame() {
        driver.get("https://www.formsite.com/templates/education/campus-safety-survey/");

        clickOnElement(By.cssSelector("div#imageTemplateContainer"));

        Assert.assertTrue(findVisibleElement(By.cssSelector("div#formTemplateContainer>iframe")).isDisplayed());

        explicitWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector("div#formTemplateContainer>iframe")));

        new Select(findVisibleElement(By.xpath("//label[contains(text(),'Year')]/following-sibling::select"))).selectByVisibleText("Senior");
        new Select(findVisibleElement(By.xpath("//label[contains(text(),'Residence')]/following-sibling::select"))).selectByVisibleText("Off Campus");
        clickOnElement(By.xpath("//label[text()='Male']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", driver.findElement(By.cssSelector("input.submit_button")));

        Assert.assertEquals(findVisibleElement(By.cssSelector("div.form_table>div.invalid_message")).getText(), "Please review the form and correct the highlighted items.");
        Assert.assertEquals(findVisibleElements(By.xpath("//div[text()='Response required']")).size(), 16);

        driver.switchTo().defaultContent();

        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", driver.findElement(By.cssSelector("nav.header--desktop-floater a.menu-item-login")));

        clickOnElement(By.cssSelector("button#login"));

        Assert.assertEquals(findVisibleElement(By.cssSelector("div#message-error")).getText(), "Username and password are both required.");
    }

    @Test
    public void TC_12_Frame() {
        driver.get("https://netbanking.hdfcbank.com/netbanking/");

        explicitWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("login_page"));

        findVisibleElement(By.cssSelector("input[name='fldLoginUserId']")).sendKeys("automation_fc");
        clickOnElement(By.cssSelector("a.login-btn"));

        explicitWait.until(ExpectedConditions.urlToBe("https://netportal.hdfcbank.com/nb-login/login.jsp"));
        Assert.assertEquals(driver.getTitle(), "Nb Login");

        // cannot run on FF Browser due to security of bank website: context click blocked while manual, findElement blocked while auto
        Assert.assertTrue(findVisibleElement(By.cssSelector("input#keyboard")).isDisplayed());
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}

package note;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.Test;

public class Ignore_Certificate_Errors {
    WebDriver driver;
    EdgeOptions edgeOptions;

    // Chromium Browser: ignore certificate errors
    @Test
    public void TC_01_Edge() throws InterruptedException {
        edgeOptions = new EdgeOptions();
        edgeOptions.addArguments("--ignore-certificate-errors");
        driver = new EdgeDriver(edgeOptions);
        driver.manage().window().maximize();
        driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");
        Thread.sleep(5000);
        driver.quit();
    }
    @Test
    public void TC_02_Chrome() throws InterruptedException {
        driver = new ChromeDriver(new ChromeOptions().addArguments("--ignore-certificate-errors"));
        driver.manage().window().maximize();
        driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");
        Thread.sleep(5000);
        driver.quit();
    }

    // other browser: not necessary
    @Test
    public void TC_03_Firefox() throws InterruptedException {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");
        Thread.sleep(5000);
        driver.quit();
    }
    @Test
    public void TC_04_Safari() throws InterruptedException {
        String osName = System.getProperty("os.name");
        if (osName.contains("Mac")) {
            driver = new SafariDriver();
            driver.manage().window().maximize();
            driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");
            Thread.sleep(5000);
            driver.quit();
        } else {
            System.out.println("Current OS: " + osName);
            System.out.println("This computer does not run on macOS.");
        }
    }
}
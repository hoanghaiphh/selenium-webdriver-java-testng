package exercise;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.Test;

public class Exercise_00_Ignore_Certificate_Err {
    WebDriver driver;
    EdgeOptions edgeOptions;
    ChromeOptions chromeOptions;

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
        chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--ignore-certificate-errors");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");
        Thread.sleep(5000);
        driver.quit();
    }
}
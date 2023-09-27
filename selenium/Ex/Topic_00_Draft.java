package Ex;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Topic_00_Draft {
    WebDriver driver;

    @BeforeClass
    public void beforeClass() {
        /*driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));*/
    }

    @Test
    public void TC_01_() {
        // Random Email:
        Double test1 = Math.floor(Math.random()*111111);
        String email = "testmail" + Math.floor(Math.random()*111111) + "@gmail.com";
        /*// Random 10 ky tu
        String test2 = Math.random().toString(36).substring(2,12);
        // Random 5 ky tu
        String test3 = Math.random().toString(36).substring(2,7);
        // Random 5 so
        String test4 = Math.random().toString(9).substring(2,7);*/
        Double intTest = Math.floor(Math.random()*10000);

        System.out.println(intTest);


    }

    @Test
    public void TC_02_() {

    }

    @AfterClass
    public void afterClass() {
        //driver.quit();
    }
}

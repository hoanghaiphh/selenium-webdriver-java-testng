package exercise;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.UUID;

public class Draft {
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

        Double random1 = Math.random();
        Double random2 = random1*1000000;
        Double random3 = Math.floor(random2);
        Long longNum = Math.round(random3);
        Long longNum2 = Math.round(random2);

        System.out.println(random1);
        System.out.println(random2);
        System.out.println(random3);
        System.out.println(longNum2);

        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);

    }

    @Test
    public void TC_02_() {

    }

    @AfterClass
    public void afterClass() {
        //driver.quit();
    }
}

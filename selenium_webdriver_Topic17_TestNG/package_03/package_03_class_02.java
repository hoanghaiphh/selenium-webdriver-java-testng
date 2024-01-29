package package_03;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class package_03_class_02 {
    @BeforeClass
    public void beforeClass() {

    }

    @Test
    public void TC_01() {

    }

    @Test (groups = "feature_02")
    public void TC_02() {

    }

    @Test
    public void TC_03() {

    }

    @AfterClass (alwaysRun = true)
    public void afterClass() {
        System.out.println("package_03 class_02 AfterClass");
    }
}

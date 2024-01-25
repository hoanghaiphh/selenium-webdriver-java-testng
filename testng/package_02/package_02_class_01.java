package package_02;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class package_02_class_01 {
    @BeforeClass (alwaysRun = true)
    public void beforeClass() {
        System.out.println("package_02 class_01 BeforeClass");
    }

    @Test (groups = "feature_01")
    public void TC_01() {

    }

    @Test
    public void TC_02() {

    }

    @Test
    public void TC_03() {

    }

    @AfterClass
    public void afterClass() {

    }
}

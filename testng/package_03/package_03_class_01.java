package package_03;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class package_03_class_01 {
    @BeforeClass
    public void beforeClass() {

    }

    @Test (groups = "feature_01", description = "test description")
    public void TC_01() {
        System.out.println("package_03 class_01 test_01");
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

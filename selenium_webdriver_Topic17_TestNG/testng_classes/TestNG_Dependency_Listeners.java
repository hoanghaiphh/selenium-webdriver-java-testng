package testng_classes;

import graphql.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

// @Listeners (listeners.ExtentReport.class)
public class TestNG_Dependency_Listeners {
    // CRUD: Create - Read - Update - Delete --> Dependencies Test

    @BeforeClass
    public void beforeClass() {
        System.out.println("Before Class");
    }

    @Test
    public void TC_01_Create_New_User() {

    }

    @Test (dependsOnMethods = "TC_01_Create_New_User")
    public void TC_02_View_And_Search_User() {
        // Assert.assertTrue(false);
    }

    @Test (dependsOnMethods = "TC_01_Create_New_User")
    public void TC_03_Update_Existing_User() {
        Assert.assertTrue(false);
    }

    @Test (dependsOnMethods = "TC_03_Update_Existing_User")
    public void TC_04_Move_Existing_User() {

    }

    @Test (dependsOnMethods = "TC_04_Move_Existing_User")
    public void TC_05_Delete_Existing_User() {

    }

    @AfterClass (alwaysRun = true)
    public void afterClass() {
        System.out.println("After Class");
    }
}

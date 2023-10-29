package exercise;

import org.openqa.selenium.WebDriver;

public class Exercise_00_Check_osName {
    static String osName = System.getProperty("os.name");
    public static void main(String[] args) {
        System.out.println(osName);
    }
}

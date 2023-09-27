package draft;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class Draft_11_Data_Type {
    // Kieu du lieu trong java - 2 nhom

    // I - Kieu du lieu nguyen thuy (Primitive Type)
    // 8 loai
    // so nguyen: byte - short - int - long
    byte bNumber = 30;
    short sNumber = 12345;
    int iNumber = 1234567890;
    long lNumber = 1234567890;

    // so thuc: float - double
    float fNumber = 9.99f;
    double dNumber = 35.123456789d;

    // ki tu: char - dai dien cho 1 ky tu - nam trong dau ''
    char c = 'm';
    char d = '$';

    // logic: boolean
    boolean status = true;

    // II - Kieu du lieu tham chieu (Reference Type)
    // Class
    FirefoxDriver firefoxDriver = new FirefoxDriver();
    Select select = new Select(firefoxDriver.findElement(By.className("")));
    Draft_11_Data_Type topic01 = new Draft_11_Data_Type();

    // Interface
    WebDriver driver;
    JavascriptExecutor jsExecutor;
    Object name = "Automation FC";

    // Array (nguyen thuy/ tham chieu)
    int[] studentAge = {15, 20, 8};
    String[] studentName = {"Automation", "Testing"};

    // Collection: List/ Set/ Queue
    List<String> studentAddress = new ArrayList<String>();
    List<String> studentCity = new LinkedList<>();
    List<String> studentPhone = new Vector<>();

    // string - chuoi ky tu - nam trong dau ""
    String fullname = "Minh";

    // Khai bao bien de luu tru 1 loai du lieu nao do
    // Access Modifier: pham vi truy cap (public/ private/ protected/ default)
    // Kieu du lieu
    // Ten bien
    // Gia tri cua bien - Neu nhu khong gan thi lay du lieu mac dinh
    public int studentNumber = 200;
}

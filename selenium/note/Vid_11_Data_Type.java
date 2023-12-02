package note;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class Vid_11_Data_Type {

    // Kiểu dữ liệu trong Java có 2 nhóm:

    // 1 - Kiểu dữ liệu nguyên thủy (Primitive Type): 8 loại

    // Số nguyên: byte - short - int - long
    byte bNumber = 30; short sNumber = 12345; int iNumber = 1234567890; long lNumber = 1234567890;

    // Số thực: float - double
    float fNumber = 9.99f; double dNumber = 35.123456789d;

    // Kí tự: char (đại diện cho 1 kí tự - nằm trong dấu ' ')
    char c = 'm'; char d = '$';

    // Logic: boolean
    boolean status = true;

    // 2 - Kiểu dữ liệu tham chiếu (Reference Type)

    // Class
    FirefoxDriver firefoxDriver = new FirefoxDriver();
    Select select = new Select(firefoxDriver.findElement(By.className("")));
    Vid_11_Data_Type topic01 = new Vid_11_Data_Type();

    // Interface
    WebDriver driver;
    JavascriptExecutor jsExecutor;
    Object name = "Automation FC";

    // Array (nguyên thủy/ tham chiếu)
    int[] studentAge = {15, 20, 8}; String[] studentName = {"Automation", "Testing"};

    // Collection: List/ Set/ Queue
    List<String> studentAddress = new ArrayList<String>();
    List<String> studentCity = new LinkedList<>();
    List<String> studentPhone = new Vector<>();

    // String (Chuỗi kí tự - nằm trong dấu " ")
    String fullName = "Minh";


    // Access Modifier: Phạm vi truy cập (public/ private/ protected/ default)
    // Khai báo biến: Kiểu dữ liệu - Tên biến - Giá trị của biến (Nếu như không gán thì lấy dữ liệu mặc định)
    public int studentNumber = 200;
}

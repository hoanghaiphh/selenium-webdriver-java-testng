package java_basic;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.*;

public class Java_Data_Type {
    public int studentNumber = 200;
    // Khai báo biến: Phạm vi truy cập - Kiểu dữ liệu - Tên biến - Giá trị của biến (Nếu như không gán thì lấy dữ liệu mặc định)
    // Phạm vi truy cập: Access Modifier (public/ private/ protected/ default)
    // Kiểu dữ liệu trong Java có 2 nhóm: Nguyên thủy (Primitive Type) & Tham chiếu (Reference Type)

    // 1.  Kiểu dữ liệu nguyên thủy (Primitive Type):
    // 1.1 Số nguyên: byte - short - int - long
    byte bNumber = 30; short sNumber = 12345; int iNumber = 1234567890; long lNumber = 1234567890;
    // 1.2 Số thực: float - double
    float fNumber = 9.99f; double dNumber = 35.123456789d;
    // 1.3 Kí tự: char (đại diện cho 1 kí tự - nằm trong dấu ' ')
    char c = 'm'; char d = '$';
    // 1.4 Logic: boolean
    boolean status = true;

    // 2.  Kiểu dữ liệu tham chiếu (Reference Type)
    // 2.1 Class
    FirefoxDriver firefoxDriver;
    Select select;
    Java_Data_Type topic01;
    Object name = "Automation FC";

    // 2.2 Interface
    WebDriver driver;
    JavascriptExecutor jsExecutor;

    // 2.3 String (Chuỗi kí tự - nằm trong dấu " ")
    String emailAddress = "abc@gmail.com";

    // 2.4 Array (nguyên thủy/ tham chiếu)
    int[] age = {1, 2, 3, 4, 5, 6, 7};
    String[] season = {"Spring", "Summer", "Autumn", "Winter"};

    // 2.5 Collection: List - Set - Queue
    List<String> arrayList = new ArrayList<>(); // Truy xuất dữ liệu (query)
    List<String> linkedList = new LinkedList<>(); // Thêm/ Sửa/ Xóa
    List<String> vector = new Vector<>();
    Set<String> hashSet = new HashSet<>();
    Set<String> treeSet = new TreeSet<>();

}

package java_basic;

public class Java_Parameter {

    static String fullNameGlobal = "First";

    public static String getFullName() {
        return fullNameGlobal; // hàm getFullName() sẽ trả về giá trị của biến fullNameGlobal
    }

    public static void setFullName(String fullName) {
        fullNameGlobal = fullName; // ham setFullName(...) sẽ cập nhật fullNameGlobal bằng giá trị trong ngoặc (fullName)
    }

    public static void main(String[] args) {
        setFullName("Second"); // cập nhật "Second" là giá trị mới của biến fullNameGlobal
        System.out.println(getFullName()); // in ra fullNameGlobal từ hàm getFullName()


        Java_Parameter topic = new Java_Parameter(); // khai báo biến object để sử dụng
        topic.setFullName2("Third");
        System.out.println(topic.getFullName2());

    }

    // trong 1 class để chạy phải sử dụng hàm main: Java Application/ Java Console (giao diện dòng lệnh)
    // hoặc chạy thông qua TestNG (Test Runner) dưới dạng test case @Test

    // hàm main phải chạy dưới dạng static
    // muốn gọi trực tiếp sang hàm khác trong cùng class cũng bắt buộc cũng phải là hàm static mới gọi đc
    // nếu không thì phải khai báo biến object



    public void setFullName2(String fullName2) {
        fullNameGlobal = fullName2;
    }
    public String getFullName2() {
        return fullNameGlobal;
    }


    // R-Click >> Generate >> Getter Setter...
}

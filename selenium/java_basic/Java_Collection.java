package java_basic;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

public class Java_Collection {
    // COLLECTION: List - Set - Queue

    // LIST: --------------------------------------------------
    List<String> arrayList = new ArrayList<>(); // Truy xuất dữ liệu (query)
    List<String> linkedList = new LinkedList<>(); // Thêm/ Sửa/ Xóa
    List<String> vector = new Vector<>();

    @Test
    public void TC_01_List() {
        List<String> studentName = new ArrayList<>();

        studentName.add("Nguyen Van A");
        studentName.add("Le Van B");
        studentName.add("Nguyen C");
        studentName.add("Phan Van D");
        studentName.add("Truong Thi E");

        System.out.println(studentName.size());

        System.out.println(studentName.get(0)); // giá trị đầu tiên
        System.out.println(studentName.get(2));
        System.out.println(studentName.get(studentName.size()-1)); // giá trị cuối cùng

        // ArrayList có hàm contains:
        Assert.assertTrue(studentName.contains("Le Van B"));

    }

    // SET: --------------------------------------------------
    Set<String> hashSet = new HashSet<>();
    Set<String> treeSet = new TreeSet<>();

    @Test
    public void TC_02_Set() {
        Set<String> studentID = new TreeSet<>();

        studentID.add("Nguyen Van A");
        studentID.add("Le Van B");
        studentID.add("Nguyen C");
        studentID.add("Phan Van D");
        studentID.add("Truong Thi E");

        System.out.println(studentID.size());

        // Không lấy dữ liệu trong Set theo Index (không có .get(i)) --> Convert sang Array/List
        // Convert Set --> ArrayList:
        List<String> studentIDList = new ArrayList<>(studentID);
        System.out.println(studentIDList.get(0));
        System.out.println(studentIDList.get(3));

        // Convert Set --> Array:
        String[] studentIDArray = studentID.toArray(new String[0]);
        System.out.println(studentIDArray[studentIDArray.length-1]);
        System.out.println(studentIDArray[3]);

        // Set có hàm contains:
        Assert.assertTrue(studentID.contains("Le Van B"));

    }

}

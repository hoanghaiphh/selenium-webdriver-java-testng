package note;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

public class Array_Collection {

    // Array:
    int[] age = {1, 2, 3, 4, 5, 6, 7}; String[] season = {"Spring", "Summer", "Autumn", "Winter"};
    String[] month = new String[12];
    @Test
    public void Array() {
        System.out.println(season.length);

        System.out.println(season[0]); // giá trị đầu tiên
        System.out.println(season[1]);
        System.out.println(season[season.length-1]); // giá trị cuối cùng

        for (String s : season) { // for each
            System.out.println(s);
        }
        for (int i = 0; i < season.length; i++) { // for classic
            System.out.println(season[i]);
        }

        Array_Collection topic = new Array_Collection();
        for (int i = 0; i < topic.age.length; i++) {
            System.out.println(topic.age[i]);
        }
        for (int s : topic.age) {
            System.out.println(s);
        }

        // Convert Array -> ArrayList
        List<String> seasonConvert = Arrays.asList(season);
        Assert.assertTrue(seasonConvert.contains("Spring"));
        Assert.assertTrue(Arrays.asList(season).contains("Summer"));
        Assert.assertTrue(List.of(season).contains("Autumn"));
    }


    // Collection: List - Set - Queue
    // List:
    List<String> arrayList = new ArrayList<>(); // Truy xuất dữ liệu (query)
    List<String> linkedList = new LinkedList<>(); // Thêm/ Sửa/ Xóa
    List<String> vector = new Vector<>();
    @Test
    public void List() {
        List<String> studentName = new ArrayList<String>();

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

    // Set:
    Set<String> hashSet = new HashSet<>();
    Set<String> treeSet = new TreeSet<>();
    @Test
    public void Set() {
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
        System.out.println(studentIDList.get(2)); // thứ tự ???

        // Convert Set --> Array:
        String[] studentIDArray = studentID.toArray(new String[0]);
        System.out.println(studentIDArray[2]); // thứ tự ???

        // Set có hàm contains:
        Assert.assertTrue(studentID.contains("Le Van B"));
    }
}

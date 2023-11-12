package webdriver;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Topic_30_31_Array_ArrayList {
    int[] studentAge = {19,20,21,22,23};
    String[] season = {"Xuân","Hạ","Thu","Đông"};
    String[] test = new String[5];

    @Test
    public void Topic_30_Array() {
        System.out.println(season[0]);
        System.out.println(season[1]);
        System.out.println(season[3]);

        for (String s : season) {
            System.out.println(s);
        }
        for (int i = 0; i < season.length; i++) {
            System.out.println(season[i]);
        }

        Topic_30_31_Array_ArrayList topic = new Topic_30_31_Array_ArrayList();
        for (int i = 0; i < topic.studentAge.length; i++) {
            System.out.println(topic.studentAge[i]);
        }
        for (int s : topic.studentAge) {
            System.out.println(s);
        }

        // Convert Array -> ArrayList (List)
        List<String> seasonConvert = Arrays.asList(season);

        // Array ko có hàm contains -> List.of("Array").contains("...") /or convert sang ArrayList rồi dùng hàm contains
        // ArrayList có hàm contains -> "ArrayList".contains("...")

        // index trong Array: Array[i] / index trong List: List.get(i)

    }

    @Test
    public void Topic_31_List() {
        // ArrayList - truy xuất dữ liệu (query)
        // LinkedList - thêm sửa xóa

        List<String> studentName = new ArrayList<String>();
        studentName.add("Nguyen Van A");
        studentName.add("Le Van B");
        studentName.add("Nguyen C");
        studentName.add("Phan Van D");
        studentName.add("Truong Thi E");

        System.out.println(studentName.size());

        System.out.println(studentName.get(0));
        System.out.println(studentName.get(2));
        System.out.println(studentName.get(studentName.size()-1));
    }
}

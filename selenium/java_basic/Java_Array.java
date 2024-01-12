package java_basic;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class Java_Array {
    int[] age = {1, 2, 3, 4, 5, 6, 7};  String[] season = {"Spring", "Summer", "Autumn", "Winter"};
    String[] month = new String[12];

    @Test
    public void TC_01_Array() {

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

        Java_Array topic = new Java_Array();

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
}

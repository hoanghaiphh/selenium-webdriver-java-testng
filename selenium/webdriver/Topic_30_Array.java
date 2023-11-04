package webdriver;

public class Topic_30_Array {
    int[] studentAge = {19,20,21,22,23};
    static String[] season = {"Xuân","Hạ","Thu","Đông"};

    String[] test = new String[5];

    public static void main(String[] args) {

        System.out.println(season[0]);
        System.out.println(season[1]);
        System.out.println(season[3]);

        for (String s : season) {
            System.out.println(s);
        }
        for (int i = 0; i < season.length; i++) {
            System.out.println(season[i]);
        }

        Topic_30_Array topic = new Topic_30_Array();
        for (int i = 0; i < topic.studentAge.length; i++) {
            System.out.println(topic.studentAge[i]);
        }
        for (int s : topic.studentAge) {
            System.out.println(s);
        }
    }
}

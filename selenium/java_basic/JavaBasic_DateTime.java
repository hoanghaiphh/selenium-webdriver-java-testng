package java_basic;

import java.util.Date;

public class JavaBasic_DateTime {

    public static String getDateTimeNow() {
        Date date = new Date();
        return date.toString();
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Start time:\t" + getDateTimeNow());
        Thread.sleep(3000);
        System.out.println("End time:\t" + getDateTimeNow());

        System.out.println(new Date());
    }

}

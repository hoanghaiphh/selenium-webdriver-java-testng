package java_basic;

public class Java_Constructor {
    // function name = class name
    // do not have value return/ void...
    // Polymorphism: Tính đa hình
    // not define --> default empty constructor

    public Java_Constructor(String text) {
        System.out.println(text);
    }

    public Java_Constructor(int number) {
        System.out.println(number);
    }

    public Java_Constructor(String text1, int number1, String text2) {
        System.out.println(text1 + " & " + number1 + " $$ " + text2);
    }

    public static void main(String[] args) {
        Java_Constructor jb_constructor1 = new Java_Constructor("automation");
        Java_Constructor jb_constructor2 = new Java_Constructor(15);
        Java_Constructor jb_constructor3 = new Java_Constructor("automation", 29, "selenium full stack");
    }
}

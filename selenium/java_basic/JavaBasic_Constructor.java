package java_basic;

public class JavaBasic_Constructor {
    // function name = class name
    // do not have value return/ void...
    // Polymorphism: Tính đa hình
    // not define --> default empty constructor

    public JavaBasic_Constructor(String text) {
        System.out.println(text);
    }

    public JavaBasic_Constructor(int number) {
        System.out.println(number);
    }

    public JavaBasic_Constructor(String text1, int number1, String text2) {
        System.out.println(text1 + " & " + number1 + " $$ " + text2);
    }

    public static void main(String[] args) {
        JavaBasic_Constructor jb_constructor1 = new JavaBasic_Constructor("automation");
        JavaBasic_Constructor jb_constructor2 = new JavaBasic_Constructor(15);
        JavaBasic_Constructor jb_constructor3 = new JavaBasic_Constructor("automation", 29, "selenium full stack");
    }
}

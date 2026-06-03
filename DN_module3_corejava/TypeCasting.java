public class TypeCasting {
    public static void main(String[] args) {
        double d = 9.78;
        int i = (int) d;  // double to int
        System.out.println("Double value: " + d);
        System.out.println("After casting to int: " + i);

        int x = 42;
        double y = x;     // int to double (widening)
        System.out.println("Int value: " + x);
        System.out.println("After casting to double: " + y);
    }
}
/*
Double value: 9.78
After casting to int: 9
Int value: 42
After casting to double: 42.0
*/
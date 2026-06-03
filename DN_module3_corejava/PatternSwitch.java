public class PatternSwitch {
    static void checkType(Object obj) {
        String result = switch (obj) {
            case Integer i -> "Integer: " + i;
            case String s -> "String of length " + s.length();
            case Double d -> "Double: " + d;
            case null -> "Null object";
            default -> "Unknown type";
        };
        System.out.println(result);
    }

    public static void main(String[] args) {
        checkType(42);
        checkType("Hello");
        checkType(3.14);
        checkType(null);
        checkType(true);
    }
}
/*
Integer: 42
String of length 5
Double: 3.14
Null object
Unknown type
*/
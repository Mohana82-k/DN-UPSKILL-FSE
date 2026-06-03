import java.util.*;

public class LambdaSort {
    public static void main(String[] args) {
        List<String> fruits = Arrays.asList("Banana", "Apple", "Grape", "Cherry");
        Collections.sort(fruits, (a, b) -> a.compareTo(b));
        System.out.println("Sorted list: " + fruits);
    }
}
/*
Sorted list: [Apple, Banana, Cherry, Grape]
*/
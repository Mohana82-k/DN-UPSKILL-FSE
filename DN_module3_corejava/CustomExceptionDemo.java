import java.util.Scanner;
class InvalidAgeException extends Exception {
    InvalidAgeException(String msg) { super(msg); }
}

public class CustomExceptionDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter age: ");
        int age = sc.nextInt();
        try {
            validateAge(age);
        } catch (InvalidAgeException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
        sc.close();
    }
    static void validateAge(int age) throws InvalidAgeException {
        if (age < 18)
            throw new InvalidAgeException("Age must be 18 or above.");
        else
            System.out.println("Valid age.");
    }

    
}
/*
Enter age: 16
Exception caught: Age must be 18 or above.
*/
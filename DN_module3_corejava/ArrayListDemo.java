import java.util.ArrayList;
import java.util.Scanner;

public class ArrayListDemo {
    public static void main(String[] args) {
        ArrayList<String> students = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.print("How many names to add? ");
        int n = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.print("Enter name: ");
            students.add(sc.nextLine());
        }
        System.out.println("\nStudent list:");
        for (String name : students) {
            System.out.println(name);
        }
        sc.close();
    }
}
/*
How many names to add? 3
Enter name: Mohana
Enter name: Tiara
Enter name: Clara
Student list:
Mohana
Tiara
Clara
*/
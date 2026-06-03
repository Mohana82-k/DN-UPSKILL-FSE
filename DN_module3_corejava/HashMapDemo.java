import java.util.HashMap;
import java.util.Scanner;

public class HashMapDemo {
    public static void main(String[] args) {
        HashMap<Integer, String> map = new HashMap<>();
        Scanner sc = new Scanner(System.in);
        System.out.print("How many entries? ");
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            System.out.print("Enter ID: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter name: ");
            String name = sc.nextLine();
            map.put(id, name);
        }
        System.out.print("Enter ID to retrieve name: ");
        int searchId = sc.nextInt();
        String name = map.get(searchId);
        if (name != null)
            System.out.println("Name: " + name);
        else
            System.out.println("ID not found.");
        sc.close();
    }
}
/*
How many entries? 3
Enter ID: 101
Enter name: Mohana
Enter ID: 102
Enter name: Tiara
Enter ID: 103
Enter name: Clara
Enter ID to retrieve name: 102
Name: Tiara
*/
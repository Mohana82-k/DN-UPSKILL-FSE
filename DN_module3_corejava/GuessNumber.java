import java.util.Random;
import java.util.Scanner;

public class GuessNumber {
    public static void main(String[] args) {
        Random rand = new Random();
        int secret = rand.nextInt(100) + 1;
        Scanner sc = new Scanner(System.in);
        int guess;
        do {
            System.out.print("Guess a number (1-100): ");
            guess = sc.nextInt();
            if (guess < secret) System.out.println("Too low!");
            else if (guess > secret) System.out.println("Too high!");
        } while (guess != secret);
        System.out.println("Correct! The number was " + secret);
        sc.close();
    }
}
/*Guess a number (1-100): 50
Too low!
Guess a number (1-100): 75
Too high!
Guess a number (1-100): 62
Too low!
Guess a number (1-100): 68
Correct! The number was 68
*/
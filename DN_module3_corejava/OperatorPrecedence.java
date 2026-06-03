public class OperatorPrecedence {
    public static void main(String[] args) {
        int result = 10 + 5 * 2;   // multiplication before addition
        System.out.println("10 + 5 * 2 = " + result);
        result = (10 + 5) * 2;     // parentheses change order
        System.out.println("(10 + 5) * 2 = " + result);
        result = 20 / 4 * 2;       // left to right
        System.out.println("20 / 4 * 2 = " + result);
    }
}
/*
10 + 5 * 2 = 20
(10 + 5) * 2 = 30
20 / 4 * 2 = 10
*/
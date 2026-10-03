import java.util.Scanner;
public class FactorialRecursion {
    static int factorial(int n) {
        if (n == 1) {
            return 1; 
        } 
        return n * factorial(n - 1);
    }
    public static void main(String[] args ) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int number = sc.nextInt();
        int result = factorial(number);
        System.out.println("Factorial of " + number + " is: " + result);
    }
}

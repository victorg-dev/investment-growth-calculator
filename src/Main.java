import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int initialInvestment = (int) readNumber("Initial Investment Amount ($1K - $1M): ", 1000, 1_000_000);

    }

    // Return a double and cast to a different type
    public static double readNumber(String prompt, double min, double max) {
        Scanner scanner = new Scanner(System.in);
        double value;
        while (true) {
            System.out.print(prompt);
            try {
                value = scanner.nextDouble();
                if (value >= min && value <= max)
                    break;
                System.out.println("Enter a value between " + min + " and " + max);
            }
            // catch invalid inputs like Strings
            catch (InputMismatchException e) {
                System.out.println("Enter a valid number");
                scanner.nextLine(); // consume the InputMismatchException
            }
        }
        return value;
    }
}
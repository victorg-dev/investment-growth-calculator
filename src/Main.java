/**
 * Inputs:
 * 	•	Initial investment amount
 * 	•	Annual contribution amount
 * 	•	Expected annual return rate (e.g., 7%)
 * 	•	Time horizon in years
 *
 * Outputs:
 * 	•	[X]Total portfolio value at the end
 * 	•	[]Total contributions vs total growth
 * 	•	[]A year-by-year breakdown
 * 	    - function to print each year
 * 	    - function to calculate new total for each period
 * 	•	[]Inflation adjusted returns
 *
 * Why it’s useful: Shows how your investments can grow over time.
 */

import java.text.NumberFormat;
import java.util.*;

public class Main {

    final static int PERCENT_TO_DECIMAL = 100;

    public static void main(String[] args) {
        int principal = (int) readNumber("Initial Investment Amount ($1K - $1M): ", 1000, 1_000_000);
        int annualContributionAmount = (int) readNumber("Annual Contribution ($0 - $1M): ", 0, 1_000_000);
        double annualInterestRatePercent = readNumber("Expected Annual Return Rate (1% - 100%): ", 1, 100);
        int years = (int) readNumber("Time Horizon (Years): ", 1, 100);

        double portfolioValue = calculatePortfolioValue(
                principal,
                annualContributionAmount,
                annualInterestRatePercent,
                years);

        printPortfolioValue(portfolioValue);
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

    public static double calculatePortfolioValue(
            int principal,
            int yearlyContributionAmount,
            double yearlyInterestRatePercent,
            int years) {

        // A = P * (1 + r)^y + YMT * [((1 + r)^y - 1) / r]
        //
        // P = Initial investment, r = Yearly interest rate
        // y = Years elapsed, YMT = Yearly contribution amount

        double yearlyInterestRateDecimal = yearlyInterestRatePercent / PERCENT_TO_DECIMAL;

        return principal
                * Math.pow(1 + yearlyInterestRateDecimal, years)
                + yearlyContributionAmount
                * ((Math.pow(1 + yearlyInterestRateDecimal, years) - 1) / yearlyInterestRateDecimal);
    }

    public static void printPortfolioValue(double portfolioValue) {
        String portfolioValueFormatted = NumberFormat.getCurrencyInstance().format(portfolioValue);
        System.out.println("Portfolio Value: " + portfolioValueFormatted);
    }
}
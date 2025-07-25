/**
 * Inputs:
 * 	•	Initial investment amount
 * 	•	Annual contribution amount
 * 	•	Expected annual return rate (e.g., 7%)
 * 	•	Time horizon in years
 *
 * Outputs:
 * 	•	[X]Total portfolio value at the end
 * 	•	[X]A year-by-year breakdown
 * 	    - function to print each year
 * 	•   []Total contributions vs total growth
 * 	•	[]Inflation adjusted returns
 * 	•   []Loop program with option to exit out (options menu?)
 *
 * Why it’s useful: Shows how your investments can grow over time.
 */

import java.text.NumberFormat;
import java.util.*;

public class Main {

    final static int PERCENT_TO_DECIMAL = 100;

    public static void main(String[] args) {
        // TODO: make a function for taking all these inputs
        int principal = (int) readNumber("Initial Investment Amount ($1K - $1M): ", 1000, 1_000_000);
        int annualContributionAmount = (int) readNumber("Annual Contribution ($0 - $1M): ", 0, 1_000_000);
        double annualInterestRatePercent = readNumber("Expected Annual Return Rate (1% - 100%): ", 1, 100);
        int years = (int) readNumber("Time Horizon (Years): ", 1, 100);

        double endPortfolioValue = calculatePortfolioValue(
                principal,
                annualContributionAmount,
                annualInterestRatePercent,
                years);

        printEndPortfolioValue(endPortfolioValue);

        printMenuOptions();

        navigateMenuOptions(
                principal,
                annualContributionAmount,
                annualInterestRatePercent,
                years);
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

        // A = P * (1 + r)^y + C * [((1 + r)^y - 1) / r]
        //
        // P = Initial investment, r = Yearly interest rate
        // y = Years elapsed, C = Contribution amount

        double yearlyInterestRateDecimal = yearlyInterestRatePercent / PERCENT_TO_DECIMAL;

        return principal
                * Math.pow(1 + yearlyInterestRateDecimal, years)
                + yearlyContributionAmount
                * ((Math.pow(1 + yearlyInterestRateDecimal, years) - 1) / yearlyInterestRateDecimal);
    }

    public static void printYearlyPortfolioValues(
            int principal,
            int yearlyContributionAmount,
            double yearlyInterestRatePercent,
            int years) {

        System.out.println();
        System.out.println("YEAR-BY-YEAR BREAKDOWN");
        System.out.println("----------------------");
        System.out.println();

        // Print the portfolio value at the end of each year
        for (int year = 1; year <= years; year++) {
            System.out.println("YEAR " + year + ": " +
                    formatToCurrency(calculatePortfolioValue(
                        principal,
                        yearlyContributionAmount,
                        yearlyInterestRatePercent,
                        year)));
        }
    }

    public static void printEndPortfolioValue(double endPortfolioValue) {
        System.out.println();
        System.out.println("End Portfolio Value: " + formatToCurrency(endPortfolioValue));
    }

    public static String formatToCurrency(double number) {
        return NumberFormat.getCurrencyInstance().format(number);
    }

    public static void navigateMenuOptions(
            int principal,
            int yearlyContributionAmount,
            double yearlyInterestRatePercent,
            int years) {

        Scanner scanner = new Scanner(System.in);

        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> printYearlyPortfolioValues(
                    principal,
                    yearlyContributionAmount,
                    yearlyInterestRatePercent,
                    years);
            case 2 -> calculateContributionsVsGrowth();
        }
    }

    public static void calculateContributionsVsGrowth() {

    }

    public static void printMenuOptions() {
        System.out.println();
        System.out.println("======== MENU OPTIONS ========");
        System.out.println("1. Show Investment Growth Summary");
        System.out.println("2. Show Contributions vs. Growth Breakdown");
        System.out.println("3. Show Inflation-Adjusted Returns");
        System.out.println("--------------------------------------");
        System.out.println("4. Start New Calculation");
        System.out.println("5. Exit");
        System.out.println("================================");
        System.out.println();
    }
}
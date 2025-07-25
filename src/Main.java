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
 * 	•   [X]Loop program with option to exit out (options menu?)
 * 	    [] - Separate the reading numbers logic from main method
 * 	    [] - Total contributions vs total growth
 * 		[] - Inflation adjusted returns
 */

import java.text.NumberFormat;
import java.util.*;

public class Main {

    final static int PERCENT_TO_DECIMAL = 100;
    final static double INFLATION_RATE = 0.032; // long term avg in the US

    public static void main(String[] args) {
        // TODO: make a function for taking all these inputs
        int principal = (int) readNumber("Initial Investment Amount ($1K - $1M): ", 1000, 1_000_000);
        int annualContributionAmount = (int) readNumber("Annual Contribution ($0 - $1M): ", 0, 1_000_000);
        double annualInterestRatePercent = readNumber("Expected Annual Return Rate (1% - 100%): ", 1, 100);
        int years = (int) readNumber("Time Horizon (Years): ", 1, 100);

        double portfolioValue = calculatePortfolioValue(
                principal,
                annualContributionAmount,
                annualInterestRatePercent,
                years);

        double inflationAdjustedPortfolioValue = calculateInflationAdjustedPortfolioValue(
                principal,
                annualContributionAmount,
                annualInterestRatePercent,
                years);

        printPortfolioValue(portfolioValue, "End Portfolio Value: ");

        // program loop
        while (true) {
            printMenuOptions();

            int usersChoice = navigateMenuOptions(
                    principal,
                    annualContributionAmount,
                    annualInterestRatePercent,
                    years,
                    portfolioValue,
                    inflationAdjustedPortfolioValue);

            if (usersChoice == 5)
                break;
        }
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
            int annualContributionAmount,
            double annualInterestRatePercent,
            int years) {

        // A = P * (1 + r)^y + C * [((1 + r)^y - 1) / r]
        //
        // P = Initial investment, r = Yearly interest rate
        // y = Years elapsed, C = Contribution amount

        double annualInterestRateDecimal = annualInterestRatePercent / PERCENT_TO_DECIMAL;

        return principal
                * Math.pow(1 + annualInterestRateDecimal, years)
                + annualContributionAmount
                * ((Math.pow(1 + annualInterestRateDecimal, years) - 1) / annualInterestRateDecimal);
    }

    public static double calculateInflationAdjustedPortfolioValue(
            int principal,
            int annualContributionAmount,
            double annualInterestRatePercent,
            int years) {

        double annualInterestRateDecimal = annualInterestRatePercent / PERCENT_TO_DECIMAL;

        double realReturnPercent = (((1 + annualInterestRateDecimal) / (1 + INFLATION_RATE)) - 1) * PERCENT_TO_DECIMAL;

        return calculatePortfolioValue(
                principal,
                annualContributionAmount,
                realReturnPercent,
                years);
    }

    public static void printYearlyPortfolioValues(
            int principal,
            int annualContributionAmount,
            double annualInterestRatePercent,
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
                            annualContributionAmount,
                            annualInterestRatePercent,
                            year)));
        }
    }

    public static void printPortfolioValue(double portfolioValue, String prompt) {
        System.out.println();
        System.out.println(prompt + formatToCurrency(portfolioValue));
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

    public static void printInflationAdjustedPortfolioValue(
            double inflationAdjustedPortfolioValue,
            String prompt) {
        System.out.println(prompt + formatToCurrency(inflationAdjustedPortfolioValue));
    }

    public static String formatToCurrency(double number) {
        return NumberFormat.getCurrencyInstance().format(number);
    }

    public static int navigateMenuOptions(
            int principal,
            int yearlyContributionAmount,
            double yearlyInterestRatePercent,
            int years,
            double portfolioValue,
            double inflationAdjustedPortfolioValue) {

        // TODO: ADD INPUT VALIDATION

        Scanner scanner = new Scanner(System.in);

        int usersChoice = scanner.nextInt();

        // TODO: ADD MORE SWITCH CASES
        switch (usersChoice) {
            case 1:
                printYearlyPortfolioValues(
                        principal,
                        yearlyContributionAmount,
                        yearlyInterestRatePercent,
                        years);
                break;
            case 3:
                printPortfolioValue(
                        portfolioValue,
                        "Portfolio Value (Nominal): ");
                printInflationAdjustedPortfolioValue(
                        inflationAdjustedPortfolioValue,
                        "Portfolio Value (Inflation-Adjusted): ");
                break;
        }

        return usersChoice;
    }
}
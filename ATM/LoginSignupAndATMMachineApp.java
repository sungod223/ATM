import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LoginSignupAndATMMachineApp {
    private static Map<String, String> users = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    private static boolean isUserSignedUp = false; // Track if the user has signed up

    public static void main(String[] args) {
        while (true) {
            System.out.println("Welcome to the Login/Signup System");
            if (!isUserSignedUp) {
                System.out.println("1. Sign Up");
                System.out.println("2. Exit");
                System.out.print("Please choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        signUp();
                        break;
                    case 2:
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } else {
                System.out.println("1. Login");
                System.out.println("2. Exit");
                System.out.print("Please choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        if (login()) {
                            runATM();
                        }
                        break;
                    case 2:
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        }
    }

    public static boolean login() {
        int attempts = 3;
        while (attempts > 0) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (users.containsKey(username) && users.get(username).equals(password)) {
                System.out.println("Login successful! Welcome, " + username + ".");
                return true;
            } else {
                attempts--;
                System.out.println("Invalid username or password. You have " + attempts + " attempt(s) left.");
            }
        }

        System.out.println("You have exceeded the maximum number of login attempts.");
        return false;
    }

    public static void signUp() {
        System.out.print("Enter a new username: ");
        String username = scanner.nextLine();

        if (users.containsKey(username)) {
            System.out.println("Username already exists. Please choose a different username.");
            return;
        }

        System.out.print("Enter a password: ");
        String password = scanner.nextLine();

        System.out.print("Confirm your password: ");
        String confirmPassword = scanner.nextLine();

        if (password.equals(confirmPassword)) {
            users.put(username, password);
            System.out.println("Sign up successful! You can now log in with your new credentials.");
            isUserSignedUp = true; // Set this to true after a successful sign-up
        } else {
            System.out.println("Passwords do not match. Please try again.");
        }
    }

    public static void runATM() {
        ATMMachine atm = new ATMMachine(1000, 1234);

        while (true) {
            atm.showMenu();
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    atm.checkBalance();
                    break;
                case 2:
                    atm.depositMoney();
                    break;
                case 3:
                    atm.withdrawMoney();
                    break;
                case 4:
                    atm.changePin();
                    break;
                case 5:
                    atm.resetPin();
                    break;
                case 6:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    return;
                case 7:
                    atm.showTransactionHistory();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}

class ATMMachine {
    private double balance;
    private int pin;
    private Scanner scanner;
    private ArrayList<String> transactionHistory;

    public ATMMachine(double initialBalance, int initialPin) {
        this.balance = initialBalance;
        this.pin = initialPin;
        this.scanner = new Scanner(System.in);
        this.transactionHistory = new ArrayList<>();
    }

    public void withdrawMoney() {
        System.out.print("Enter your PIN to withdraw money: ");
        int enteredPin = scanner.nextInt();

        if (enteredPin != pin) {
            System.out.println("Incorrect PIN! Withdrawal denied.");
            return;
        }

        System.out.print("Enter the amount to withdraw: $");
        double amount = scanner.nextDouble();
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("$" + amount + " withdrawn successfully.");
            transactionHistory.add("Withdrew: $" + amount);
        } else if (amount > balance) {
            System.out.println("Insufficient funds. Please try a smaller amount.");
        } else {
            System.out.println("Invalid amount. Withdrawal failed.");
        }
    }

    public void showMenu() {
        System.out.println("\nWelcome to the ATM Machine!");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Change PIN");
        System.out.println("5. Reset PIN");
        System.out.println("6. Exit");
        System.out.println("7. Show Transaction History");
        System.out.print("Please choose an option: ");
    }

    public void checkBalance() {
        System.out.println("Your current balance is: $" + balance);
        transactionHistory.add("Checked balance: $" + balance);
    }

    public void depositMoney() {
        System.out.print("Enter the amount to deposit: $");
        double amount = scanner.nextDouble();
        if (amount > 0) {
            balance += amount;
            System.out.println("$" + amount + " deposited successfully.");
            transactionHistory.add("Deposited: $" + amount);
        } else {
            System.out.println("Invalid amount. Deposit failed.");
        }
    }

    public void changePin() {
        System.out.print("Enter your current PIN: ");
        int currentPin = scanner.nextInt();
        if (currentPin == pin) {
            System.out.print("Enter a new PIN: ");
            pin = scanner.nextInt();
            System.out.println("Your PIN has been changed successfully.");
            transactionHistory.add("Changed PIN");
        } else {
            System.out.println("Incorrect PIN. PIN change failed.");
        }
    }

    public void resetPin() {
        System.out.print("To reset your PIN, please enter your current PIN: ");
        int currentPin = scanner.nextInt();

        if (currentPin == pin) {
            System.out.print("Enter a new PIN to reset: ");
            pin = scanner.nextInt();
            System.out.println("Your PIN has been reset successfully.");
            transactionHistory.add("Reset PIN");
        } else {
            System.out.println("Incorrect PIN. PIN reset failed.");
        }
    }

    public void showTransactionHistory() {
        System.out.println("\nTransaction History:");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }
}

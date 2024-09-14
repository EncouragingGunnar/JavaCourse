import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int firstNumber = 0;
        int secondNumber = 0;
        boolean validInput = false;

        while (!validInput) {
            if (scan.hasNextInt()) {
                firstNumber = scan.nextInt();
                firstNumber = factoradicToBaseTen(firstNumber);
                if (scan.hasNextInt()) {
                    secondNumber = scan.nextInt();
                    secondNumber = factoradicToBaseTen(secondNumber);
                    validInput = true;
                } else {
                    System.out.println("Invalid input. Please enter two integers.");
                    scan.next();
                }
            } else {
                System.out.println("Invalid input. Please enter two integers.");
                scan.next();
            }
        }

        System.out.println(firstNumber);
        System.out.println(secondNumber);
    }

    public static int factorial(int number) {
        int result = 1;
        for (int i = 1; i <= number; i++) {
            result *= i;
        }
        return result;
    }

    public static int factoradicToBaseTen(int number) {
        int result = 0;
        int length = String.valueOf(number).length();
        for (int i = 0; i < length; i++) {
            int digit = number % 10;
            result += digit * factorial(i);
            number /= 10;
        }
        return result;
    }
}
import java.lang.Math;
import java.util.Scanner;

public class MathematicalMethods {
    private static final Scanner scan = new Scanner(System.in);

    public static void mathematicalMethods(String[] args) {
        System.out.println(
                """
                 ----------------------------------
                 # Test of area and volume methods
                 ----------------------------------""");
        while (true) {
            int radius = input();
            if (radius == -1) {
                break;
            }
            int height = input();
            if (height == -1) {
                break;
            }
            height = Math.abs(height);
            radius = Math.abs(radius);
            System.out.printf("Circle area: %.2f\n", area(radius));
            System.out.printf("Cone area: %.2f\n", area(radius, height));
            System.out.printf("Cone volume: %.2f\n\n", volume(radius, height));
        }
        System.out.println(
                """
                 ----------------------------------
                 # Test of the fractional methods
                 ----------------------------------""");
        while (true) {
            int numerator = input();
            if (numerator == -1) {
                break;
            }
            int denominator = input();
            if (denominator == -1) {
                break;
            }
            System.out.printf("%d / %d = ", numerator, denominator);
            printFractions(fraction(numerator, denominator));
            System.out.println();
        }
        scan.close();
    }

    private static int input() {
        while (true) {
            try {
                String input = scan.next();
                if (input.equalsIgnoreCase("q")) {
                    return -1;
                } else {
                    return Integer.parseInt(input);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please try again!");
                scan.nextLine();
            }
        }
    }

    private static float area(int radius) {
        return (float) (radius * radius * Math.PI);
    }

    private static float area(int radius, int height) {
        return (float) (Math.PI * radius * pythagoras(radius, height));
    }

    private static float volume(int radius, int height) {
        return (float) (Math.PI * radius * radius * height) / 3;
    }

    private static float pythagoras(int sideA, int sideB) {
        return (float) Math.sqrt((sideA * sideA) + (sideB * sideB));
    }

    private static int[] fraction(int numerator, int denominator) {
        int[] mixedNumber = {0, 0, 0};
        if (denominator == 0) {
            return null;
        } else if (numerator == 0) {
            return mixedNumber;
        }
        mixedNumber[0] = numerator / denominator;
        mixedNumber[1] = numerator % denominator;
        mixedNumber[2] = denominator;
        mixedNumber[1] = mixedNumber[1] / gcd(mixedNumber[1], mixedNumber[2]);
        mixedNumber[2] = mixedNumber[2] / gcd(mixedNumber[1], mixedNumber[2]);
        return mixedNumber;
    }

    private static int gcd(int a, int b) {
        if (b > a) {
            int temp = a;
            a = b;
            b = temp;
        }
        int c;
        while (b != 0) {
            c = a % b;
            a = b;
            b = c;
        }
        return a;
    }

    private static void printFractions(int[] parts) {
        if (parts == null || parts.length != 3) {
            System.out.print("\"Error\"");
        } else if (parts[0] == 0 && parts[1] == 0) {
            System.out.print("0");
        } else if (parts[0] == 0) {
            System.out.printf("%d/%d", parts[1], parts[2]);
        } else if (parts[1] == 0) {
            System.out.print(parts[0]);
        } else {
            System.out.printf("%d %d/%d", parts[0], parts[1], parts[2]);
        }
    }
}

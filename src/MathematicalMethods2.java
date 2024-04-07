import java.util.Scanner;

// Please don't forget to add pseudocode to your methods and classes.

/**
 * This class contains methods to calculate the area and volume of a circle and a cone.
 * It also contains methods to calculate the fraction of a number and the greatest common divisor of two numbers.
 *
 * @author Adasch-8
 */
public class MathematicalMethods2 {
    //Creation of scanner object.
    private static Scanner userInputScanner = new Scanner(System.in);

    //Constants
    static final int QUIT = -1;
    static final int CONE_VOLUME = 3;

    /**
     * This method should be used only for unit testing on CodeGrade. Do not change this method!
     * Do not remove this method!
     * Swaps userInputScanner with a custom scanner object bound to a test input stream
     *
     * @param inputScanner - test scanner object
     */
    public static void injectInput(final Scanner inputScanner) {
        userInputScanner = inputScanner;
    }

    /**
     * Main method of the program
     * @param args
     */
    public static void main(final String[] args) {
        int radius = 0;
        int height = 0;
        int numerator = 0;
        int denominator = 0;

        //Print the header of the program for area and volume.
        System.out.println("----------------------------------");
        System.out.println("# Test of area and volume methods");
        System.out.println("----------------------------------");

        // While loop that runs until user enters "q" for area and volume.

        while (true) {

            radius = input();
            if (radius == QUIT) {
                break;
            }

            height = input();
            if (height == QUIT) {
                break;
            }

            System.out.println("r = " + radius + ", h = " + height);
            System.out.printf("Circle area: %.2f %n", area(radius));
            System.out.printf("Cone area: %.2f %n", area(radius, height));
            System.out.printf("Cone volume: %.2f %n", volume(radius, height));
        }

        //Print the header of the program for area and volume.
        System.out.println("----------------------------------");
        System.out.println("# Test of the fractional methods");
        System.out.println("----------------------------------");


        // While loop that runs until user enters "q" for the fraction part
        while (true) {

            numerator = input();
            if (numerator == QUIT) {
                break;
            }

            denominator = input();
            if (denominator == QUIT) {
                break;
            }

            System.out.printf("%d/%d = ", numerator, denominator);
            printFraction(fraction(numerator, denominator));
        }
    }

    /**
     * Method to get the user input
     * @return integer value of the user input
     */
    public static int input() {
        while (true) {
            try {
                if (userInputScanner.hasNextInt()) {
                    return Math.abs(userInputScanner.nextInt());
                } else if (userInputScanner.hasNext()) {
                    String input = userInputScanner.next();
                    if (input.equalsIgnoreCase("q")) {
                        return QUIT;
                    } else {
                        throw new IllegalArgumentException("Invalid input, please try again!");
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    /**
     * Method to calculate the area of a circle
     * @param radius - radius of the circle
     * @return float value of the circle area
     */
    public static double area(final int radius) {
        return radius * radius * Math.PI;
    }

    /**
     * Method to calculate the area of a cone
     * @param radius - radius of the cone
     * @param height - height of the cone
     * @return float value of the cone area
     */
    public static double area(final int radius, final int height) {
        return Math.PI * radius * pythagoras(radius, height);
    }
    /**
     * Method to calculate the volume of a cone
     * @param radius - radius of the cone
     * @param height - height of the cone
     * @return float value of the cone volume
     */
    public static double volume(final int radius, final int height) {
        return Math.PI * radius * radius * height / CONE_VOLUME;
    }

    /**
     * Method to calculate the hypotenuse of a right triangle
     * @param sideA cathetus of the triangle
     * @param sideB the other cathetus of the triangle
     * @return float value of the hypotenuse
     */
    public static double pythagoras(final int sideA, final int sideB) {
        return Math.sqrt((sideA * sideA) + (sideB * sideB));
    }
    /**
     * Method to calculate the fraction of a number
     * @param numerator - numerator of the fraction
     * @param denominator - denominator of the fraction
     * @return integer array of the fraction
     */
    public static int[] fraction(final int numerator, final int denominator) {
        int[] mixedNumber = new int[3];
        if (denominator == 0) {
            return null;
        } else if (numerator == 0) {
            return mixedNumber;
        }
        mixedNumber[0] = numerator / denominator;
        mixedNumber[1] = numerator % denominator;
        mixedNumber[2] = denominator;
        if (mixedNumber[1] != 0) {
            int gcd = gcd(mixedNumber[1], mixedNumber[2]);
            mixedNumber[1] = mixedNumber[1] / gcd;
            mixedNumber[2] = mixedNumber[2] / gcd;
        } else {
            mixedNumber[2] = 1;
        }
        return mixedNumber;
    }
    /**
     * Method to calculate the greatest common divisor of two numbers
     * @param a - first number
     * @param b - second number
     * @return integer value of the greatest common divisor
     */
    public static int gcd(final int a, final int b) {
        int number1 = a;
        int number2 = b;
        if (number2 > number1) {
            int temp = number1;
            number1 = number2;
            number2 = temp;
        }
        int remainder;
        while (number2 != 0) {
            remainder = number1 % number2;
            number1 = number2;
            number2 = remainder;
        }
        return number1;
    }
    /**
     * Method to print the fraction
     * @param parts - integer array of the fraction
     */
    public static void printFraction(final int[] parts) {
        if (parts == null || parts.length != 3) {
            System.out.print("Error");
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

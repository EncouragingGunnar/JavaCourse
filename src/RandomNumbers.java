import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Random;

/** The program generates an inputted amount of random numbers between 0 and
 * 999. It makes an array with all the numbers, then makes arrays of even and
 * odd numbers. It then sorts the even numbers from smallest to largest and the
 * odd numbers from largest to smallest. It then prints the arrays.
 *
 * @author adasch-8
 */
public class RandomNumbers {
    static final int MAX_VALUE = 1000;
    static final String USER_INPUT_PROMPT =
            "How many random numbers in the range 0 - 999 are desired?";
    static final String RANDOM_NUMBERS_LIST_MESSAGE =
        "Here are the random numbers:";
    static final String RANDOM_NUMBERS_SORTED_MESSAGE =
        "Here are the random numbers arranged:";
    static final String NO_ODD_NUMBERS_MESSAGE = "No Odd Numbers";
    static final String NO_EVEN_NUMBERS_MESSAGE = "No Even Numbers";
    static final String INVALID_INPUT_MESSAGE = "Invalid Input";
    /**
    *Main method of the program
    *@param args
    */
    public static void randomNumbers(final String[] args) {
        System.out.println(USER_INPUT_PROMPT);
        Scanner scan = new Scanner(System.in);
        Random rand = new Random();
        while (true) {

            try {
                int randomNumberCount = scan.nextInt();
                if (randomNumberCount <= 0) {
                    System.out.println(INVALID_INPUT_MESSAGE);
                    continue;
                }
                int[] numberArray = new int[randomNumberCount];

                int evenNumberAmount = 0;
                int oddNumberAmount = 0;
                for (int i = 0; i < randomNumberCount; i++) {
                    numberArray[i] = rand.nextInt(MAX_VALUE) + 1;
                    if (numberArray[i] % 2 == 0) {
                        evenNumberAmount++;
                    } else {
                        oddNumberAmount++;
                    }
                }
                //filter even and odd numbers
                int[] evenArray = new int[evenNumberAmount];
                int[] oddArray = new int[oddNumberAmount];
                evenNumberAmount = 0;
                oddNumberAmount = 0;
                for (int i : numberArray) {
                    if (i % 2 != 0) {
                        oddArray[oddNumberAmount] = i;
                        oddNumberAmount++;
                    } else {
                        evenArray[evenNumberAmount] = i;
                        evenNumberAmount++;
                    }
                }

                // bubble sort
                for (int i = 0; i < evenArray.length; i++) {
                    for (int j = 0; j < evenArray.length - 1; j++) {
                        if (evenArray[j] > evenArray[j + 1]) {
                            int temp = evenArray[j];
                            evenArray[j] = evenArray[j + 1];
                            evenArray[j + 1] = temp;
                        }
                    }
                }

                // reverse bubble sort
                for (int i = 0; i < oddArray.length; i++) {
                    for (int j = 0; j < oddArray.length - 1; j++) {
                        if (oddArray[j] < oddArray[j + 1]) {
                            int temp = oddArray[j];
                            oddArray[j] = oddArray[j + 1];
                            oddArray[j + 1] = temp;
                        }
                    }
                }

                System.out.println(RANDOM_NUMBERS_LIST_MESSAGE);
                for (int j : numberArray) {
                    System.out.print(j + " ");
                }
                System.out.println();
                System.out.println(RANDOM_NUMBERS_SORTED_MESSAGE);
                if (evenArray.length == 0 && oddArray.length == 0) {
                    System.out.println(NO_EVEN_NUMBERS_MESSAGE
                        + " - " + NO_ODD_NUMBERS_MESSAGE);
                } else if (evenArray.length == 0) {
                    System.out.print(NO_EVEN_NUMBERS_MESSAGE + " - ");
                    for (int j : oddArray) {
                        System.out.print(j + " ");
                    }
                } else if (oddArray.length == 0) {
                    for (int j : evenArray) {
                        System.out.print(j + " ");
                    }
                    System.out.print(" - No Odd Numbers");
                } else {
                    for (int j : evenArray) {
                        System.out.print(j + " ");
                    }
                    System.out.print("- ");
                    for (int j : oddArray) {
                        System.out.print(j + " ");
                    }
                }
                System.out.printf(
                    "\nOf the remaining %d numbers, %d were even and %d odd",
                    numberArray.length, evenNumberAmount, oddNumberAmount);
                break;

            } catch (OutOfMemoryError e) {
                System.out.println(
                    "Unable to allocate enough memory. Try again. \n");

            } catch (InputMismatchException e) {
                System.out.println(INVALID_INPUT_MESSAGE);
                scan.next();
            }
        }
        scan.close();
    }

}


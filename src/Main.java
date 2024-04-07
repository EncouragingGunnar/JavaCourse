import java.util.Random;
import java.util.Scanner;

/**
 * The program validates an input entered by the user. If the input is 1, 2 or 3 it rolls a die. 3
 * dice can be rolled, the same dice can not be rolled multiple times. When all three dice have been
 * rolled: If the sum is less than 12 a loss is added, if the sum is 12 a win is added. If the sum
 * is above 12, no win or loss gets added. Dice values reset after this check ran. If q is entered
 * in, the program exits.
 *
 * @author adasch-8
 */
public class Main {
    static final int WIN_AMOUNT = 12;
    static final int DICE_MAX_VALUE = 6;
    static final int FIRST_DICE = 1;
    static final int SECOND_DICE = 2;
    static final int THIRD_DICE = 3;

    public static void main(final String[] args) {
        System.out.println(
                "Welcome to dice game 12. "
                        + "You must roll 1-3 dice and try to get the sum of 12 ...\n");
        Scanner scan = new Scanner(System.in);
        Random rand = new Random();
        int lossCount = 0;
        int winCount = 0;
        int firstDie = 0;
        int secondDie = 0;
        int thirdDie = 0;
        int diceSum = 0;

        while (true) {
            System.out.println("Enter which dice you want to roll [1,2,3] " + "(exit with q)\n");
            if (scan.hasNextInt()) {
                int userInput = scan.nextInt();

                if (firstDie != 0 && userInput == FIRST_DICE) {
                    System.out.println(
                            "Sorry, you have already " + "rolled that dice. Try again\n");
                } else if (secondDie != 0 && userInput == SECOND_DICE) {
                    System.out.println(
                            "Sorry, you have already " + "rolled that dice. Try again\n");
                } else if (thirdDie != 0 && userInput == THIRD_DICE) {
                    System.out.println(
                            "Sorry, you have already " + "rolled that dice. Try again\n");
                } else if (userInput == FIRST_DICE) {
                    firstDie = rand.nextInt(DICE_MAX_VALUE) + 1;
                    diceSum += firstDie;
                    System.out.printf(
                            "%d %d %d sum: %d #win: %d #loss %d\n",
                            firstDie, secondDie, thirdDie, diceSum, winCount, lossCount);
                } else if (userInput == SECOND_DICE) {
                    secondDie = rand.nextInt(DICE_MAX_VALUE) + 1;
                    diceSum += secondDie;
                    System.out.printf(
                            "%d %d %d sum: %d #win: %d #loss %d\n",
                            firstDie, secondDie, thirdDie, diceSum, winCount, lossCount);
                } else if (userInput == THIRD_DICE) {
                    thirdDie = rand.nextInt(DICE_MAX_VALUE) + 1;
                    diceSum += thirdDie;
                    System.out.printf(
                            "%d %d %d sum: %d #win: %d #loss %d\n",
                            firstDie, secondDie, thirdDie, diceSum, winCount, lossCount);

                } else {
                    System.out.println(
                            """
                        Sorry, that is an invalid entry."
                        "Try again. Valid entries are 1, 2, 3, and q

                        """);
                }

                if (firstDie != 0 && secondDie != 0 && thirdDie != 0) {
                    if (diceSum < WIN_AMOUNT) {
                        System.out.println(
                                """
                                You neither won nor lost the game

                                """);
                        System.out.println("Next Round.\n\n");
                        firstDie = 0;
                        secondDie = 0;
                        thirdDie = 0;
                        diceSum = 0;
                    } else if (diceSum > WIN_AMOUNT) {
                        System.out.println("You lost!!\n\n");
                        System.out.println("Next Round.\n\n");
                        firstDie = 0;
                        secondDie = 0;
                        thirdDie = 0;
                        diceSum = 0;
                        lossCount++;
                    } else {
                        System.out.println("You won!!\n\n");
                        System.out.println("Next Round.\n\n");
                        firstDie = 0;
                        secondDie = 0;
                        thirdDie = 0;
                        diceSum = 0;
                        winCount++;
                    }
                }
            } else {
                String input = scan.next();

                if ("q".equalsIgnoreCase(input)) {
                    System.out.printf("#win: %d #loss: %d\n", winCount, lossCount);
                    System.out.println("Game Over!");
                    break;
                } else {
                    System.out.println(
                            """
                        Sorry, that is an invalid entry."
                        "Try again. Valid entries are 1, 2, 3, and q

                        """);
                }
            }
        }
        scan.close();
    }
}

import java.util.Scanner;
import java.util.Date;
import java.util.Random;
 /**
 * Describe briefly your program in steps.
 *
 @author Your Name (Your LTU username)
 */
public class CashRegister2
{
    private static Scanner userInputScanner = new Scanner(System.in);

    /**
     * This method should be used only for unit testing on CodeGrade. Do not change this method!
     * Swaps userInputScanner with a custom scanner object bound to a test input stream
     * @param inputScanner:  test scanner object
     */
    public static void injectInput(final Scanner inputScanner){
		userInputScanner = inputScanner;
	}

    private static Random rand = new Random();
    private static final int ITEM_MAX_VALUE = 1000;
    private static final int ITEM_MAX_AMOUNT = 10;

    public static void main(final String args[]) {
        //items[0] = itemId, items[1] = itemCount, items[2] = itemPrice
        int[][] items = new int[10][3];
        Date[] salesDate = new Date[10];
        //sales[0] = itemId, sales[1] = numberOfItems
        // sales[2] = sum, here sum = numberOfItems *itemPrice
        int[][] sales = new int[1000][3];
        while (true) {
            int itemId = 1000;
            switch(menu()) {
                case 1:
                    System.out.println("How many items do you want to add?");
                    int noOfItems = input();
                    items = insertItems(items, itemId, noOfItems);
                    itemId += noOfItems;
                    break;
                case 2:
                    System.out.println("Enter the article number you want to remove");
                    itemId = input();
                    removeItem(items, itemId);
                    break;
                case 3:
                    printItems(items);
                    break;
                case 4:
                    System.out.println("Enter the item number you want to sell");
                    itemId = input();
                    System.out.println("Enter the amount you want to sell");
                    int amountToSell = input();
                    int result = sellItem(sales, salesDate, itemId, amountToSell, items);
                    if (result == -1) {
                        System.out.println("Could not find item, please try again!");
                    } else if (result > 0) {
                        System.out.println("Failed to sell specified amount (" + result + " items)" +
                                ", please try again!");
                    }
                    break;
                case 5:
                    printSales(sales, salesDate);
                    break;
                case 6:
                    sortedTable(sales, salesDate);
                    break;
                case -1:
                    System.out.println("Exiting program");
                    System.exit(0);
                default:
                    System.out.println("Invalid input, please try again!");
                    break;

            }
        }
}

    public static int menu() {
        System.out.println("""
                1. Insert items
                2. Remove an item
                3. Display a list of items
                4. Register a sale
                5. Display sales history
                6. Sort and display sales history table
                q. Quit
                Ditt val:
                """);
        return input();
    }
    public static int input() {
        while (true) {
            try {
                String input = userInputScanner.next();
                if (input.equalsIgnoreCase("q")) {
                    return -1;
                } else {
                    if (!input.equals("0"))
                        return Integer.parseInt(input);
                    else {
                        System.out.println("Invalid input, please try again!");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please try again!");
                userInputScanner.nextLine();
            }
        }

    }
    public static boolean checkFull(final int[][] items, final int noOfItems) {
        int freeSlots = 0;
        for (int[] item : items) {
            if (item[0] == 0) {
                freeSlots++;
            }
        }
        return freeSlots <= noOfItems;
    }
    public static int[][] extendArray(final int[][]items, final int noOfItems) {
        int[][] extendedArray = new int[items.length + noOfItems][3];
        System.arraycopy(items, 0, extendedArray, 0, items.length);
        return extendedArray;
    }


    public static int[][] insertItems (final int[][]items, final int itemId, final int noOfItems) {
        int[][] itemList = items;
        if (checkFull(items, noOfItems)) {
            itemList = extendArray(items, noOfItems);
        }
        int newItemId = itemId;
        for (int i = 0; i < noOfItems; i++) {
            int newItemUnits = rand.nextInt(ITEM_MAX_AMOUNT) + 1;
            int newItemPrice = rand.nextInt(ITEM_MAX_VALUE) + 1;
            for (int j = 0; j < itemList.length; j++) {
                if (itemList[j][0] == 0) {
                    itemList[j][0] = newItemId;
                    itemList[j][1] = newItemUnits;
                    itemList[j][2] = newItemPrice;
                    newItemId++;
                    break;
                }

            }
        }
        return itemList;
    }

    // user enters 1002
    // if items[i][0] = 1002
    // items[i][0][1][2] = 0
    public static int removeItem(final int[][] items, final int itemId) {
        if (itemId < items[0][0] || itemId > items[items.length - 1][0]) {
            System.out.println("Could not find item, please try again!");
            return -1;
        } else {
            for (int i = 0; i < items.length; i++) {
                if (items[i][0] == itemId) {
                    items[i][0] = 0;
                    items[i][1] = 0;
                    items[i][2] = 0;
                }
            }
            return 0;
        }
    }



    public static void printItems (final int[][]items) {
        System.out.printf("%10s %10s %10s\n", "Item", "Quantity", "Total Price");
        for (int[] item : items) {
            if (item[0] == 0) {
                continue;
            }
            System.out.printf("%10s %10s %10s\n", item[0], item[1],
                    item[2] * item[1]);
        }
    }

    public static int sellItem(final int[][]sales, final Date[] salesDate, final int itemIdToSell,
                               final int amountToSell, final int [][] items) {
        int totalPrice;
        for (int i = 0; i < items.length; i++) {
            if (items[i][0] == itemIdToSell) {
                if (items[i][1] >= amountToSell) {
                    items[i][1] -= amountToSell;
                    totalPrice = items[i][2] * amountToSell;
                    for (int j = 0; j < sales.length; j++) {
                        if (sales[j][0] == 0) {
                            sales[j][0] = itemIdToSell;
                            sales[j][1] = amountToSell;
                            sales[j][2] = totalPrice;
                            salesDate[j] = new Date();
                            return 0;
                        }
                    }
                } else {
                    return items[i][1];
                }
            }
        }
        return -1;
    }



    public static void printSales(final int[][]sales, final Date[] salesDate) {
        System.out.printf("%10s %10s %10s %10s\n", "Item", "Quantity", "Total", "Date" );
        for (int i = 0; i < sales.length && sales[i][0] != 0; i++) {
            System.out.printf("%10s %10s %10s %10s\n",sales[i][0], sales[i][1],
                    sales[i][2], salesDate[i] );
        }


    }
    public static void sortedTable(final int[][]sales, final Date[] salesDate) {
        for (int i = 0; i < sales.length; i++) {
            for (int j = 0; j < sales.length - i - 1; j++) {
                if (sales[j][0] > sales[j + 1][0]) {
                    int[] temp = sales[j];
                    sales[j] = sales[j + 1];
                    sales[j + 1] = temp;
                    Date tempDate = salesDate[j];
                    salesDate[j] = salesDate[j + 1];
                    salesDate[j + 1] = tempDate;
                }
            }
        }
        printSales(sales, salesDate);



    }

}

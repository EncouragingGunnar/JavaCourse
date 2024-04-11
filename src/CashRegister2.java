import java.util.Scanner;
import java.util.Date;
import java.util.Random;

/**
 * Describe briefly your program in steps.
 *
 * @author Your Name (Your LTU username)
 */
public class CashRegister2 {
    private static Scanner userInputScanner = new Scanner(System.in);

    /**
     * This method should be used only for unit testing on CodeGrade. Do not change this method!
     * Swaps userInputScanner with a custom scanner object bound to a test input stream
     *
     * @param inputScanner: test scanner object
     */
    public static void injectInput(final Scanner inputScanner) {
        userInputScanner = inputScanner;
    }

    private static Random rand = new Random();
    // Constants for the item array
    public static final int ITEM_ID = 0;
    public static final int ITEM_COUNT = 1;
    public static final int ITEM_PRICE = 2;
    public static final int ITEM_COLUMN_SIZE = 3;
    public static final int INITIAL_ITEM_SIZE = 10;

    // Constants for the sales array
    public static final int SALE_ITEM_ID = 0;
    public static final int SALE_ITEM_COUNT = 1;
    public static final int SALE_ITEM_PRICE = 2;
    public static final int SALE_COLUMN_SIZE = 3;
    public static final int MAX_SALES = 1000;

    // Other constants
    public static final int MENU_ITEM_1 = 1;
    public static final int MENU_ITEM_2 = 2;
    public static final int MENU_ITEM_3 = 3;
    public static final int MENU_ITEM_4 = 4;
    public static final int MENU_ITEM_5 = 5;
    public static final int MENU_ITEM_6 = 6;
    public static final int MENU_ITEM_Q = -1;

    public static final int INITIAL_ITEM_NUMBER = 999;
    public static final int ITEM_MAX_VALUE = 1000;
    public static final int ITEM_MIN_VALUE = 100;
    public static final int ITEM_MAX_AMOUNT = 10;

    public static void main(final String[] args) {
        int[][] items = new int[INITIAL_ITEM_SIZE][ITEM_COLUMN_SIZE];
        Date[] salesDate = new Date[MAX_SALES];
        int[][] sales = new int[MAX_SALES][SALE_COLUMN_SIZE];
        int lastItemId = INITIAL_ITEM_NUMBER;
        int itemId;
        while (true) {
            switch (menu()) {
                case MENU_ITEM_1:
                    System.out.println("How many items do you want to add?");
                    int noOfItems = input();
                    items = insertItems(items, lastItemId, noOfItems);
                    lastItemId += noOfItems;
                    break;
                case MENU_ITEM_2:
                    System.out.println("Enter the article number you want to remove");
                    itemId = input();
                    removeItem(items, itemId);
                    break;
                case MENU_ITEM_3:
                    printItems(items);
                    break;
                case MENU_ITEM_4:
                    System.out.println("Enter the item number you want to sell");
                    itemId = input();
                    System.out.println("Enter the amount you want to sell");
                    int amountToSell = input();
                    int result = sellItem(sales, salesDate, items, itemId, amountToSell);
                    if (result == -1) {
                        System.out.println("Could not find item, please try again!");
                    } else if (result > 0) {
                        System.out.println(
                                "Failed to sell specified amount ("
                                        + result
                                        + " items)"
                                        + ", please try again!");
                    }
                    break;
                case MENU_ITEM_5:
                    printSales(sales, salesDate);
                    break;
                case MENU_ITEM_6:
                    sortedTable(sales, salesDate);
                    break;
                case MENU_ITEM_Q:
                    System.out.println("Exiting program");
                    System.exit(0);
                default:
                    System.out.println("Invalid input, please try again!");
                    break;
            }
        }
    }

    public static int menu() {
        System.out.println(
                """
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
                if (userInputScanner.hasNextInt()) {
                    return Math.abs(userInputScanner.nextInt());
                } else if (userInputScanner.hasNext()) {
                    String input = userInputScanner.next();
                    if (input.equalsIgnoreCase("q")) {
                        return MENU_ITEM_Q;
                    } else {
                        throw new IllegalArgumentException("Invalid input, please try again!");
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static boolean checkFull(final int[][] items, final int noOfItems) {
        int freeSlots = 0;
        for (int[] item : items) {
            if (item[ITEM_ID] == 0) {
                freeSlots++;
            }
        }
        return freeSlots < noOfItems;
    }

    public static int[][] extendArray(final int[][] items, final int noOfItems) {
        int[][] extendedArray = new int[items.length + noOfItems][ITEM_COLUMN_SIZE];
        System.arraycopy(items, 0, extendedArray, 0, items.length);
        return extendedArray;
    }

    public static int[][] insertItems(
            final int[][] items, final int lastItemId, final int noOfItems) {
        int[][] itemList = items;
        if (checkFull(items, noOfItems)) {
            itemList = extendArray(items, noOfItems);
        }
        int newItemId = lastItemId + 1;
        for (int i = 0; i < noOfItems; i++) {
            int newItemUnits = rand.nextInt(ITEM_MAX_AMOUNT) + 1;
            int newItemPrice = rand.nextInt((ITEM_MAX_VALUE - ITEM_MIN_VALUE) + 1) + ITEM_MIN_VALUE;
            for (int j = 0; j < itemList.length; j++) {
                if (itemList[j][ITEM_ID] == 0) {
                    itemList[j][ITEM_ID] = newItemId;
                    itemList[j][ITEM_COUNT] = newItemUnits;
                    itemList[j][ITEM_PRICE] = newItemPrice;
                    newItemId++;
                    break;
                }
            }
        }
        return itemList;
    }

    public static int removeItem(final int[][] items, final int itemId) {
        for (int i = 0; i < items.length; i++) {
            if (items[i][ITEM_ID] == itemId) {
                items[i][ITEM_ID] = 0;
                items[i][ITEM_COUNT] = 0;
                items[i][ITEM_PRICE] = 0;
                return 0;
            }
        }
        System.out.println("Could not find item, please try again!");
        return -1;
    }

    public static void printItems(final int[][] items) {
        System.out.printf("%10s %10s %10s\n", "Item", "Quantity", "Total Price");
        for (int[] item : items) {
            if (item[ITEM_ID] == 0) {
                continue;
            }
            System.out.printf(
                    "%10s %10s %10s\n",
                    item[ITEM_ID], item[ITEM_COUNT], item[ITEM_PRICE] * item[ITEM_COUNT]);
        }
    }

    public static int sellItem(
            final int[][] sales,
            final Date[] salesDate,
            final int[][] items,
            final int itemIdToSell,
            final int amountToSell) {
        int totalPrice;
        for (int i = 0; i < items.length; i++) {
            if (items[i][ITEM_ID] == itemIdToSell) {
                if (items[i][ITEM_COUNT] >= amountToSell) {
                    items[i][ITEM_COUNT] -= amountToSell;
                    totalPrice = items[i][ITEM_PRICE] * amountToSell;
                } else {
                    return items[i][1];
                }
                for (int j = 0; j < sales.length; j++) {
                    if (sales[j][SALE_ITEM_ID] == 0) {
                        sales[j][SALE_ITEM_ID] = itemIdToSell;
                        sales[j][SALE_ITEM_COUNT] = amountToSell;
                        sales[j][SALE_ITEM_PRICE] = totalPrice;
                        salesDate[j] = new Date();
                        return 0;
                    }
                }
            }
        }
        return -1;
    }

    public static void printSales(final int[][] sales, final Date[] salesDate) {
        System.out.printf("%10s %10s %10s %10s\n", "Item", "Quantity", "Total", "Date");
        for (int i = 0; i < sales.length && sales[i][SALE_ITEM_ID] != 0; i++) {
            System.out.printf(
                    "%10s %10s %10s %10s\n",
                    sales[i][SALE_ITEM_ID],
                    sales[i][SALE_ITEM_COUNT],
                    sales[i][SALE_ITEM_PRICE],
                    salesDate[i]);
        }
    }

    public static void sortedTable(final int[][] sales, final Date[] salesDate) {
        for (int i = 0; i < sales.length; i++) {
            for (int j = 0; j < sales.length - i - 1; j++) {
                if (sales[j][SALE_ITEM_ID] > sales[j + 1][SALE_ITEM_ID]) {
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

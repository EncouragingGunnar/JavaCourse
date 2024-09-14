import java.util.Scanner;
import java.util.Date;
import java.util.Random;

/**
 * This program is a cash register system that allows the user to add items to the inventory, remove
 * items from the inventory, sell items, display the list of items, display the sales history, and
 * sort and display the sales history table. The program uses a menu system to allow the user to
 * choose the desired operation. The program uses arrays to store the items and sales data. Item
 * prices and quantities are randomly generated.
 *
 * @author Adasch-8
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
    public static final int INVALID_INPUT = -1;
    public static final int OPERATION_SUCCESSFUL = 0;

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

    /**
     * Main method of the program, contains the main while-true loop of the program
     *
     * @param args string args
     */
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
                    if (result == INVALID_INPUT) {
                        System.out.println("Could not find item, please try again!");
                    } else if (result > 0) {
                        System.out.println(
                                "Failed to sell specified amount, only "
                                        + result
                                        + " units left in stock");
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

    /**
     * This method prints the menu and returns the user's choice
     *
     * @return input from the user
     */
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

    /**
     * This method reads the user input and returns the integer value It contains appropriate error
     * handling for invalid inputs
     *
     * @return the integer value of the user input
     */
    public static int input() {
        while (true) {
            try {
                if (userInputScanner.hasNextInt()) {
                    return userInputScanner.nextInt();
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

    /**
     * This method checks if the item array is full
     *
     * @param items array of items
     * @param noOfItems number of items to be added
     * @return true if the array is full, false otherwise
     */
    public static boolean checkFull(final int[][] items, final int noOfItems) {
        int freeSlots = 0;
        for (int[] item : items) {
            if (item[ITEM_ID] == 0) {
                freeSlots++;
            }
        }
        return freeSlots < noOfItems;
    }

    /**
     * This method extends the item array by the number of items to be added
     *
     * @param items array of items
     * @param noOfItems number of items to be added
     * @return the extended array
     */
    public static int[][] extendArray(final int[][] items, final int noOfItems) {
        int[][] extendedArray = new int[items.length + noOfItems][ITEM_COLUMN_SIZE];
        System.arraycopy(items, 0, extendedArray, 0, items.length);
        return extendedArray;
    }

    /**
     * This method adds items to the item array
     *
     * @param items array of items
     * @param lastItemId last item id
     * @param noOfItems number of items to be added
     * @return the updated array
     */
    public static int[][] addItems(final int[][] items, final int lastItemId, final int noOfItems) {
        int[][] itemList = items.clone();
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

    /**
     * This method inserts items into the item array
     *
     * @param items array of items
     * @param lastItemId last item id
     * @param noOfItems number of items to be added
     * @return the updated array with the new items inserted
     */
    public static int[][] insertItems(
            final int[][] items, final int lastItemId, final int noOfItems) {
        int[][] itemList = items;
        if (checkFull(items, noOfItems)) {
            itemList = extendArray(items, noOfItems);
        }
        return addItems(itemList, lastItemId, noOfItems);
    }

    /**
     * This method removes an item from the item array
     *
     * @param items array of items
     * @param itemId item id to be removed
     * @return 0 if the item is removed successfully, -1 otherwise
     */
    public static int removeItem(final int[][] items, final int itemId) {
        int itemIndex = findItem(items, itemId);
        if (itemIndex != INVALID_INPUT) {
            items[itemIndex][ITEM_ID] = 0;
            items[itemIndex][ITEM_COUNT] = 0;
            items[itemIndex][ITEM_PRICE] = 0;
            return OPERATION_SUCCESSFUL;
        } else {
            System.out.println("Could not find item, please try again!");
            return INVALID_INPUT;
        }
    }

    /**
     * This method prints the items in the item array
     *
     * @param items array of items
     */
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

    /**
     * This method finds the item by its item id in the item array
     *
     * @param items array of items
     * @param itemIdToFind item id to be found
     * @return the index of the item in the array, -1 if the item is not found
     */
    public static int findItem(final int[][] items, final int itemIdToFind) {
        for (int i = 0; i < items.length; i++) {
            if (items[i][ITEM_ID] == itemIdToFind) {
                return i;
            }
        }
        return INVALID_INPUT;
    }

    /**
     * This method updates the quantity of the item in the item array
     *
     * @param items array of items
     * @param itemIndex index of the item in the array
     * @param amountToSell amount of the item to be sold
     * @return the amount of the item sold
     */
    public static int updateItemQuantity(
            final int[][] items, final int itemIndex, final int amountToSell) {
        if (items[itemIndex][ITEM_COUNT] >= amountToSell) {
            items[itemIndex][ITEM_COUNT] -= amountToSell;
            return amountToSell;
        } else {
            return items[itemIndex][ITEM_COUNT];
        }
    }

    /**
     * This method records the sale in the sales array
     *
     * @param sales array of sales
     * @param salesDate array of sales date
     * @param itemIdToSell item id to be sold
     * @param amountToSell amount of the item to be sold
     * @param totalPrice total price of the item sold
     */
    public static void recordSale(
            final int[][] sales,
            final Date[] salesDate,
            final int itemIdToSell,
            final int amountToSell,
            final int totalPrice) {
        for (int j = 0; j < sales.length; j++) {
            if (sales[j][SALE_ITEM_ID] == 0) {
                sales[j][SALE_ITEM_ID] = itemIdToSell;
                sales[j][SALE_ITEM_COUNT] = amountToSell;
                sales[j][SALE_ITEM_PRICE] = totalPrice;
                salesDate[j] = new Date();
                break;
            }
        }
    }

    /**
     * This method sells an item from the item array
     *
     * @param sales array of sales
     * @param salesDate array of sales date
     * @param items array of items
     * @param itemIdToSell item id to be sold
     * @param amountToSell amount of the item to be sold
     * @return 0 if the item is sold successfully, -1 if the item is not found, or the amount of the
     *     item sold when there is not enough stock
     */
    public static int sellItem(
            final int[][] sales,
            final Date[] salesDate,
            final int[][] items,
            final int itemIdToSell,
            final int amountToSell) {
        int itemIndex = findItem(items, itemIdToSell);
        if (itemIndex == INVALID_INPUT) {
            return INVALID_INPUT;
        }
        int itemsInStock = updateItemQuantity(items, itemIndex, amountToSell);
        if (itemsInStock == 0) {
            return INVALID_INPUT;
        }
        if (itemsInStock < amountToSell) {
            return itemsInStock;
        }
        int totalPrice = itemsInStock * items[itemIndex][ITEM_PRICE];
        recordSale(sales, salesDate, itemIdToSell, amountToSell, totalPrice);
        return OPERATION_SUCCESSFUL;
    }

    /**
     * This method prints the sales array with appropriate formatting
     *
     * @param sales array of sales
     * @param salesDate array of sales date
     */
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

    /**
     * This method sorts the sales array by item id, and then prints the sales array
     *
     * @param sales array of sales
     * @param salesDate array of sales date
     */
    public static void sortedTable(final int[][] sales, final Date[] salesDate) {
        int[][] sortedSales = sales.clone();
        Date[] sortedSalesDate = salesDate.clone();
        for (int i = 0; i < sortedSales.length; i++) {
            for (int j = 0; j < sortedSales.length - i - 1; j++) {
                if (sortedSales[j][SALE_ITEM_ID] > sortedSales[j + 1][SALE_ITEM_ID]) {
                    int[] temp = sortedSales[j];
                    sortedSales[j] = sortedSales[j + 1];
                    sortedSales[j + 1] = temp;
                    Date tempDate = sortedSalesDate[j];
                    sortedSalesDate[j] = sortedSalesDate[j + 1];
                    sortedSalesDate[j + 1] = tempDate;
                }
            }
        }
        int count = 0;
        for (int[] sale : sortedSales) {
            if (sale[SALE_ITEM_ID] != 0) {
                count++;
            }
        }

        int[][] nonZeroSales = new int[count][SALE_COLUMN_SIZE];
        Date[] nonZeroSalesDate = new Date[count];

        int index = 0;
        for (int i = 0; i < sortedSales.length; i++) {
            if (sortedSales[i][SALE_ITEM_ID] != 0) {
                nonZeroSales[index] = sortedSales[i];
                nonZeroSalesDate[index] = sortedSalesDate[i];
                index++;
            }
        }

        printSales(nonZeroSales, nonZeroSalesDate);
    }
}

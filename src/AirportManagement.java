import java.util.Scanner;

//public class AirportManagement {
//
//    private static Scanner scan = new Scanner(System.in);
//
//    private static enum menuItem {
//        MENU_ITEM_1,
//        MENU_ITEM_2,
//        MENU_ITEM_3,
//        MENU_ITEM_4,
//        MENU_ITEM_5,
//        MENU_ITEM_Q;
//    }
//
//    public static void main(String[] args) {
//        //error is due to menu items in the other class
//        while (true) {
//            switch (printMenu()) {
//                case MENU_ITEM_1:
//                    registerScheduledArrival();
//                    break;
//                case MENU_ITEM_2:
//                    registerScheduledDeparture();
//                    break;
//                case MENU_ITEM_3:
//                    registerFlightArrival();
//                    break;
//                case MENU_ITEM_4:
//                    registerFlightDeparture();
//                    break;
//                case MENU_ITEM_5:
//                    printAirportOperations();
//                    break;
//                case MENU_ITEM_Q:
//                    System.out.println("Exiting program");
//                    System.exit(0);
//                default:
//                    System.out.println("Invalid input, please try again!");
//                    break;
//            }
//        }
//    }
//
//    public static int printMenu() {
//        System.out.println(
//                """
//---------------------------------
//# LTU Airport AD Management System
//----------------------------------
//1. Register the scheduled arrival
//2. Register the scheduled departure
//3. Register the actual arrival of a flight
//4. Register the actual departure of a flight
//5. Print operations summary
//q. End program
//> Enter your option:
//""");
//        return getIntInput();
//    }
//
//    public static menuItem getMenuItemInput() {
//        while (true) {
//            try {
//                if (scan.hasNextInt()) {
//                    int input = scan.nextInt();
//                    return menuItem.values()[input - 1];
//                } else if (scan.hasNext()) {
//                    String input = scan.next();
//                    if (input.equalsIgnoreCase("q")) {
//                        return menuItem.MENU_ITEM_Q;
//                    } else {
//                        throw new IllegalArgumentException("Invalid input, please try again!");
//                    }
//                }
//            } catch (IllegalArgumentException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//    }
//
//    public static void registerScheduledArrival() {}
//
//    public static void registerScheduledDeparture() {}
//
//    public static void registerFlightArrival() {}
//
//    public static void registerFlightDeparture() {}
//
//    public static void printAirportOperations() {}
//}

import java.util.Scanner;

public class AirportManagement2 {

    private static Scanner scan = new Scanner(System.in);

    public static final int MAXIMUM_DAILY_FLIGHT_AMOUNT = 100;
    public static final int ARRIVALS_COLUMN_SIZE = 2;
    public static final int DEPARTURES_COLUMN_SIZE = 2;
    public static final int FLIGHT_NUMBER_COLUMN = 0;
    public static final int FLIGHT_AIRPORT_COLUMN = 1;
    public static final int FLIGHT_SCHEDULED_TIME_COLUMN = 0;
    public static final int FLIGHT_ARRIVAL_TIME_COLUMN = 1;

    public static final int MENU_ITEM_1 = 1;
    public static final int MENU_ITEM_2 = 2;
    public static final int MENU_ITEM_3 = 3;
    public static final int MENU_ITEM_4 = 4;
    public static final int MENU_ITEM_5 = 5;
    public static final int MENU_ITEM_6 = 6;
    public static final int MENU_ITEM_Q = -1;

    public static void main(String[] args) {
        int[][] departures = new int[MAXIMUM_DAILY_FLIGHT_AMOUNT][ARRIVALS_COLUMN_SIZE];
        int[][] departureTimes = new int[MAXIMUM_DAILY_FLIGHT_AMOUNT][DEPARTURES_COLUMN_SIZE];
        int[][] arrivals = new int[MAXIMUM_DAILY_FLIGHT_AMOUNT][ARRIVALS_COLUMN_SIZE];
        while (true) {
            switch (printMenu()) {
                case MENU_ITEM_1:
                    System.out.println("Enter flight number:\n");
                    String flightNumber = getStringInput();
                    System.out.println("Enter airport of origin:\n");
                    String airportOfOrigin = getStringInput();
                    System.out.println("Enter scheduled arrival time:\n");
                    int arrivalTime = getIntInput();
                    registerScheduledArrival();
                    System.out.println("Arrival of flight " + flightNumber + " from " + airportOfOrigin + " is scheduled for " + arrivalTime);
                    break;
                case MENU_ITEM_2:
                    registerScheduledDeparture();
                    break;
                case MENU_ITEM_3:
                    registerFlightArrival();
                    break;
                case MENU_ITEM_4:
                    registerFlightDeparture();
                    break;
                case MENU_ITEM_5:
                    printAirportOperations();
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

    public static int printMenu() {
        System.out.println(
                """
---------------------------------
# LTU Airport AD Management System
----------------------------------
1. Register the scheduled arrival
2. Register the scheduled departure
3. Register the actual arrival of a flight
4. Register the actual departure of a flight
5. Print operations summary
q. End program
> Enter your option:
""");
        return getIntInput();
    }

    public static int getIntInput() {
        while (true) {
            try {
                if (scan.hasNextInt()) {
                    int input = scan.nextInt();
                    return input;
                } else if (scan.hasNext()) {
                    String input = scan.next();
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

    public static String getStringInput() {
        return scan.next();

    }

    public static void registerScheduledArrival() {}

    public static void registerScheduledDeparture() {}

    public static void registerFlightArrival() {}

    public static void registerFlightDeparture() {}

    public static void printAirportOperations() {}
}

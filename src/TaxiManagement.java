import java.util.Scanner;

/**
 * @author Adasch-8, Adam Schedin* Taxi management system, works by adding cars to a fleet, starting
 *     rides, ending rides and printing 6 different options in a menu.
 */
public class TaxiManagement {
    private static final Scanner scan = new Scanner(System.in);

    public static final int MAX_DAILY_RIDES = 100;
    public static final int MAX_CARS = 100;
    public static final int CAR_FLEET_COLUMN_SIZE = 3;
    public static final int RIDES_COLUMN_SIZE = 4;
    public static final int CAR_NOT_FOUND = -1;

    public static final int CAR_REGISTRATION_NUMBER = 0;
    public static final int CAR_MAKE_AND_MODEL = 1;
    public static final int CAR_STATUS = 2;
    public static final int RIDER_NAME = 1;
    public static final int RIDER_DISTANCE = 2;
    public static final int RIDER_PICKUP_ADDRESS = 3;

    public static final int RIDE_BASE_COST = 200;
    public static final int RIDE_COST_PER_KM = 35;
    public static final int REGISTRATION_NUMBER_LENGTH = 6;

    public static final String CAR_AVAILABLE_STATUS = "Available";
    public static final String CAR_TAKEN_STATUS = "Taken";
    public static final String INVALID_REGISTRATION_NUMBER_MESSAGE =
            "Invalid registration number, please try again!";

    public static final int MENU_ITEM_1 = 1;
    public static final int MENU_ITEM_2 = 2;
    public static final int MENU_ITEM_3 = 3;
    public static final int MENU_ITEM_4 = 4;
    public static final int MENU_ITEM_5 = 5;
    public static final int MENU_ITEM_Q = -1;
    public static final String QUIT_COMMAND = "q";

    public static void main(String[] args) {
        String[][] carFleet = new String[MAX_CARS][CAR_FLEET_COLUMN_SIZE];
        String[][] rides = new String[MAX_DAILY_RIDES][RIDES_COLUMN_SIZE];
        String registrationNumber;
        while (true) {
            switch (printMenu()) {
                case MENU_ITEM_1:
                    System.out.println("Enter registration number:\n");
                    registrationNumber = scan.next();
                    if (!validateRegistrationNumber(registrationNumber)) {
                        System.out.println(INVALID_REGISTRATION_NUMBER_MESSAGE);
                        break;
                    }
                    if (findCar(carFleet, registrationNumber) != CAR_NOT_FOUND) {
                        System.out.println("Car already exists in the fleet, please try again!");
                        break;
                    }

                    System.out.println("Enter make and model:\n");
                    String carMakeAndModel = scan.next();
                    registerCar(carFleet, registrationNumber, carMakeAndModel);
                    System.out.println(
                            carMakeAndModel
                                    + " with registration number "
                                    + registrationNumber
                                    + " was added to car fleet.");

                    break;

                case MENU_ITEM_2:
                    System.out.println("Enter registration number:\n");
                    registrationNumber = scan.next();
                    if (!validateRegistrationNumber(registrationNumber)) {
                        System.out.println(INVALID_REGISTRATION_NUMBER_MESSAGE);
                        break;
                    }
                    if (findCar(carFleet, registrationNumber) == CAR_NOT_FOUND) {
                        System.out.println("Car does not exist in the fleet, please try again!");
                        break;
                    }
                    if (isCarOnRide(carFleet, registrationNumber)) {
                        System.out.println("Car is already on a ride, please try again!");
                        break;
                    }
                    System.out.println("Enter pickup address:\n");
                    String pickupAddress = scan.next();
                    System.out.println("Enter rider's name:\n");
                    String riderName = scan.next();
                    startRide(carFleet, rides, registrationNumber, riderName, pickupAddress);
                    System.out.println(
                            "Taxi with registration number "
                                    + registrationNumber
                                    + " "
                                    + "picked up "
                                    + riderName
                                    + " at "
                                    + pickupAddress
                                    + ".");
                    break;
                case MENU_ITEM_3:
                    System.out.println("Enter registration number:\n");
                    registrationNumber = scan.next();
                    if (!validateRegistrationNumber(registrationNumber)) {
                        System.out.println(INVALID_REGISTRATION_NUMBER_MESSAGE);
                        break;
                    }
                    if (findCar(carFleet, registrationNumber) == CAR_NOT_FOUND) {
                        System.out.println("Car does not exist in the fleet, please try again!");
                        break;
                    }
                    if (!isCarOnRide(carFleet, registrationNumber)) {
                        System.out.println("Car is not currently on a ride, please try again!");
                        break;
                    }
                    if (validateRegistrationNumber(registrationNumber))
                        System.out.println("Distance covered in km:\n");
                    if (scan.hasNextInt()) {
                        int rideDistance = scan.nextInt();
                        endRide(carFleet, rides, registrationNumber, String.valueOf(rideDistance));
                    } else {
                        System.out.println(
                                "Invalid distance, distance needs to be rounded to nearest "
                                        + "integer!");
                    }
                    break;
                case MENU_ITEM_4:
                    printCarFleet(carFleet);
                    break;
                case MENU_ITEM_5:
                    printRideSummary(rides);
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

    private static int printMenu() {
        System.out.println(
                "----------------------------------\n"
                        + "# LTU Taxi\n"
                        + "----------------------------------\n"
                        + "1. Add car to fleet\n"
                        + "2. Start a ride\n"
                        + "3. End a ride\n"
                        + "4. Print car fleet\n"
                        + "5. Print ride summary\n"
                        + "q. End program\n"
                        + "> Enter your option:\n");
        return input();
    }

    // gets integer input from the user
    private static int input() {
        while (true) {
            try {
                if (scan.hasNextInt()) {
                    return scan.nextInt();
                } else if (scan.hasNext()) {
                    String input = scan.next();
                    if (input.equalsIgnoreCase(QUIT_COMMAND)) {
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

    // checks if a car's registration number is valid
    private static Boolean validateRegistrationNumber(String registrationNumber) {
        return registrationNumber.length() == REGISTRATION_NUMBER_LENGTH
                && Character.isUpperCase(registrationNumber.charAt(0))
                && Character.isUpperCase(registrationNumber.charAt(1))
                && Character.isUpperCase(registrationNumber.charAt(2))
                && Character.isDigit(registrationNumber.charAt(3))
                && Character.isDigit(registrationNumber.charAt(4))
                && Character.isDigit(registrationNumber.charAt(5));
    }

    // registers a car to the fleet
    private static void registerCar(
            String[][] carFleet, String registrationNumber, String carMakeAndModel) {
        for (int i = 0; i < carFleet.length; i++) {
            if (carFleet[i][CAR_REGISTRATION_NUMBER] == null) {
                carFleet[i][CAR_REGISTRATION_NUMBER] = registrationNumber;
                carFleet[i][CAR_MAKE_AND_MODEL] = carMakeAndModel;
                carFleet[i][CAR_STATUS] = CAR_AVAILABLE_STATUS;
                break;
            }
        }
    }

    // starts a ride with a car, adds it to the rides list and changes status of the car
    private static void startRide(
            String[][] carFleet,
            String[][] rides,
            String registrationNumber,
            String riderName,
            String pickupAdress) {
        carFleet[findCar(carFleet, registrationNumber)][CAR_STATUS] = CAR_TAKEN_STATUS;
        for (int i = 0; i < rides.length; i++) {
            if (rides[i][CAR_REGISTRATION_NUMBER] == null) {
                rides[i][CAR_REGISTRATION_NUMBER] = registrationNumber;
                rides[i][RIDER_NAME] = riderName;
                rides[i][RIDER_PICKUP_ADDRESS] = pickupAdress;
                break;
            }
        }
    }

    // finds the latest ride the car made, adds distance and prints a receipt
    private static void endRide(
            String[][] carFleet, String[][] rides, String registrationNumber, String rideDistance) {
        carFleet[findCar(carFleet, registrationNumber)][CAR_STATUS] = CAR_AVAILABLE_STATUS;
        int latestRideIndex = 0;
        for (int i = 0; i < rides.length; i++) {
            if (rides[i][CAR_REGISTRATION_NUMBER] == null) {
                continue;
            }
            if (rides[i][CAR_REGISTRATION_NUMBER].equals(registrationNumber)) {
                latestRideIndex = i;
            }
        }
        rides[latestRideIndex][RIDER_DISTANCE] = rideDistance;
        System.out.println("===================================");
        System.out.println("LTU Taxi");
        System.out.println("===================================");
        System.out.println("Name: " + rides[latestRideIndex][RIDER_NAME]);
        System.out.println(
                "Car: " + carFleet[findCar(carFleet, registrationNumber)][CAR_MAKE_AND_MODEL]);
        System.out.println("Distance: " + rides[latestRideIndex][RIDER_DISTANCE]);
        System.out.println(
                "Total cost: " + calculateRideCost(Integer.parseInt(rideDistance)) + " SEK");
        System.out.println("===================================");
    }

    // finds a car in the carFleet array
    private static int findCar(String[][] carFleet, String registrationNumber) {
        for (int i = 0; i < carFleet.length; i++) {
            if (carFleet[i][CAR_REGISTRATION_NUMBER] == null) {
                continue;
            }
            if (carFleet[i][CAR_REGISTRATION_NUMBER].equals(registrationNumber)) {
                return i;
            }
        }
        return CAR_NOT_FOUND;
    }

    // calculates the cost of a ride
    private static int calculateRideCost(int rideDistance) {
        return rideDistance * RIDE_COST_PER_KM + RIDE_BASE_COST;
    }

    // checks if a car is on a ride
    private static boolean isCarOnRide(String[][] carFleet, String registrationNumber) {
        for (String[] strings : carFleet) {
            if (strings[CAR_REGISTRATION_NUMBER] == null) {
                continue;
            }
            if (strings[CAR_REGISTRATION_NUMBER].equals(registrationNumber)
                    && strings[CAR_STATUS].equals(CAR_TAKEN_STATUS)) {
                return true;
            }
        }
        return false;
    }

    // findCar should not provide a -1 in this case because the car exists
    // prints the car fleet
    private static void printCarFleet(String[][] carFleet) {
        System.out.printf("%15s %15s %15s\n", "Model", "Numberplate", "Status");
        int carsOnRide = 0;
        int totalCars = 0;
        for (String[] strings : carFleet) {
            if (strings[CAR_REGISTRATION_NUMBER] == null) {
                continue;
            }
            if (isCarOnRide(carFleet, strings[CAR_REGISTRATION_NUMBER])) {
                carsOnRide++;
                totalCars++;
                System.out.printf(
                        "%15s %15s %15s\n",
                        strings[CAR_MAKE_AND_MODEL],
                        strings[CAR_REGISTRATION_NUMBER],
                        CAR_TAKEN_STATUS);
            } else {
                totalCars++;
                System.out.printf(
                        "%15s %15s %15s\n",
                        strings[CAR_MAKE_AND_MODEL],
                        strings[CAR_REGISTRATION_NUMBER],
                        CAR_AVAILABLE_STATUS);
            }
        }
        System.out.println("\nTotal number of cars: " + totalCars);
        System.out.println("\nTotal number of available cars: " + (totalCars - carsOnRide));
    }

    // gets rid of the null values in the rides array and sorts it by name of driver using
    // comparedTo method with bubble sort algorithm then prints it
    private static void printRideSummary(String[][] rides) {
        int rideAmount = 0;
        for (int i = 0; i < rides.length; i++) {
            if (rides[i][CAR_REGISTRATION_NUMBER] != null) {
                rideAmount++;
            }
        }
        int index = 0;
        String[][] sortedRides = new String[rideAmount][RIDES_COLUMN_SIZE];
        for (int i = 0; i < rides.length; i++) {
            if (rides[i][CAR_REGISTRATION_NUMBER] != null) {
                sortedRides[index] = rides[i];
                index++;
            }
        }

        for (int i = 0; i < sortedRides.length; i++) {
            for (int j = 0; j < sortedRides.length - i - 1; j++) {
                if (sortedRides[j][RIDER_NAME].compareTo(sortedRides[j + 1][RIDER_NAME]) > 0) {
                    String[] temp = sortedRides[j];
                    sortedRides[j] = sortedRides[j + 1];
                    sortedRides[j + 1] = temp;
                }
            }
        }
        System.out.printf("%15s, %15s, %15s, %15s\n", "Name", "Numberplate", "Distance", "Cost");
        int totalRides = 0;
        int totalRevenue = 0;
        for (String[] sortedRide : sortedRides) {
            if (sortedRide[CAR_REGISTRATION_NUMBER] == null) {
                continue;
            }
            if (sortedRide[RIDER_DISTANCE] == null) {
                totalRides++;
                System.out.printf(
                        "%15s, %15s\n",
                        sortedRide[RIDER_NAME], sortedRide[CAR_REGISTRATION_NUMBER]);
            } else {
                totalRides++;
                totalRevenue += calculateRideCost(Integer.parseInt(sortedRide[RIDER_DISTANCE]));
                System.out.printf(
                        "%15s, %15s, %15s, %15s\n",
                        sortedRide[RIDER_NAME],
                        sortedRide[CAR_REGISTRATION_NUMBER],
                        sortedRide[RIDER_DISTANCE],
                        calculateRideCost(Integer.parseInt(sortedRide[RIDER_DISTANCE])));
            }
        }
        System.out.println("\nTotal number of rides: " + totalRides);
        System.out.println("Total revenue: " + totalRevenue + " SEK");
    }
}

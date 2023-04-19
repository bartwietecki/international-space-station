import db.DbInitializer;
import repository.AstronautsRepository;
import service.AstronautsService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/";

    private static final String DB_NAME_ENV = "DB_NAME";

    private static final String DB_USER_ENV = "DB_USER";

    private static final String DB_PASSWORD_ENV = "DB_PASSWORD";

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) throws IOException, SQLException, URISyntaxException, InterruptedException {
        System.out.println("Going to connect to DB...");

        final Connection connection = DriverManager.getConnection(JDBC_URL + System.getenv(DB_NAME_ENV),
                System.getenv(DB_USER_ENV), System.getenv(DB_PASSWORD_ENV));

        DbInitializer dbInitializer = new DbInitializer(connection);
        dbInitializer.initDb();

        AstronautsService astronautsService = initializeAstronautsServiceFromRepository();

        boolean isProgramRunning = true;
        while (isProgramRunning) {
            showMenu();
            final String userChoice = SCANNER.nextLine();
            try {
                final int option = Integer.parseInt(userChoice);
                switch (option) {
                    case 1:
                        // Obliczanie prędkości ISS - metoda
                        break;
                    case 2:
                        // Pokazanie gdzie obecnie jest ISS - metoda
                        break;
                    case 3:
                        int numberOfAstronauts = astronautsService.getNumberOfCurrentAstronautsFromApi();
                        System.out.println("Number of astronauts in space: " + numberOfAstronauts);
                        break;
                    case 4:
                        System.out.print("Enter date (YYYY-MM-DD): ");
                        String inputDate = SCANNER.next();
                        break;
                    case 5:
                        isProgramRunning = false;
                        break;
                    default:
                        System.err.println(userChoice + "is invalid option. Please try again!");
                        break;
                }
            } catch (NumberFormatException e) {
                System.err.println(userChoice + "is invalid option. Please try again!");
            }
        }
    }

    private static void showMenu() {
        System.out.println("Welcome in ISS Tracker Program!");
        System.out.println("Choose one of the options:");
        System.out.println("1. Calculate ISS velocity");
        System.out.println("2. Return a list of upcoming ISS passes for a specified location");
        System.out.println("3. Return the number of astronauts in space withing the ISS");
        System.out.println("4. Check ISS position at a specific date");
        System.out.println("5. Exit the ISS Tracker Program");
        System.out.print("Your choice: ");
    }

    private static AstronautsService initializeAstronautsServiceFromRepository() {
        AstronautsRepository astronautsRepository = new AstronautsRepository();
        return new AstronautsService(astronautsRepository);
    }


}


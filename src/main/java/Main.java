import api.AstronautsApi;
import api.LocationApi;
import api.SatteliteApi;
import db.DbInitializer;
import dto.SimpleAstronautsDto;
import repository.AstronautsRepository;
import repository.LocationRepository;
import service.AstronautsService;
import service.LocationService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
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


        // -------------------------------------------------------------------------------

        boolean isProgramRunning = true;
        while (isProgramRunning) {
            showMenu();
            final String userChoice = SCANNER.nextLine();
            try {
                final int option = Integer.parseInt(userChoice);
                switch (option) {
                    case 1:
                        SatteliteApi.showCurrentSatteliteVelocity();
                        break;
                    case 2:
                        // Nad jakim krajem jest ISS
                        SatteliteApi.showCurrentSatteliteLocationFromApi();
                        break;
                    case 3:
                        // Liczba osób w kosmosie - metoda
                        break;
                    case 4:
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




        // -------------------------------------------------------------------------------

        LocationRepository locationRepository = new LocationRepository();
        LocationService locationService = new LocationService(locationRepository);

        locationService.saveLocationFromResponse(LocationApi.getCurrentLocationFromApi());

        AstronautsRepository astronautsRepository = new AstronautsRepository();
        AstronautsService astronautsService = new AstronautsService(astronautsRepository);
        try {
            astronautsService.saveAstronautsFromResponse(AstronautsApi.getCurrentAstronautsFromApi());
        } catch (Exception e) {
            System.err.println("Error while saving astronauts: " + e.getMessage());
        }
        List<SimpleAstronautsDto> astronautsList = astronautsService.findAll();
        for (SimpleAstronautsDto astronaut : astronautsList) {
            System.out.println(astronaut.getId() + " - " + astronaut.getName());
        }

        System.out.println("HERE TEST for astronauts in space");

        int numberOfAstronauts = astronautsService.getNumberOfCurrentAstronautsFromApi();
        System.out.println("Liczba astronautów " + numberOfAstronauts);

        System.out.println("Test again");
        System.out.println(astronautsService.getNumberOfCurrentAstronautsFromApi());

    }

    private static void showMenu() {
        System.out.println("Welcome in ISS Tracker Program!");
        System.out.println("Choose one of the options:");
        System.out.println("1. Calculate ISS velocity");
        System.out.println("2. Return a list of upcoming ISS passes for a specified location");
        System.out.println("3. Return the number of astronauts in space withing the ISS");
        System.out.println("4. Exit the ISS Tracker Program");
        System.out.print("Your choice: ");
    }

}


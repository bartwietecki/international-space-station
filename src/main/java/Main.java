import api.AstronautsApi;
import api.LocationApi;
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
    }

}


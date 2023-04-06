import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbInitializer {
    private final Connection connection;

    public DbInitializer(Connection connection) {
        this.connection = connection;
    }

    public void initDb() throws IOException, SQLException {
        try (InputStream ddlLocation = getClass().getResourceAsStream("/sql/location_ddl.sql");
             InputStream ddlAstronauts = getClass().getResourceAsStream("/sql/astronauts_ddl.sql")) {
            executeSqlFromStream(ddlLocation);
            executeSqlFromStream(ddlAstronauts);
        }
    }

    private void executeSqlFromStream(InputStream inputStream) throws IOException, SQLException {
        if (inputStream == null) {
            System.err.println("Failed to open resource");
            return;
        }
        String sql = new String(inputStream.readAllBytes());

        System.out.println("Going to execute sql: \n" + sql);

        PreparedStatement statement = connection.prepareStatement(sql);

        statement.executeUpdate();

        System.out.println("SQL executed successfully!");
    }
}
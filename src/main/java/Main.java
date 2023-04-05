import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        issApiConnection.connect();
     }

    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/";

    private static final String DB_NAME_ENV = "DB_NAME";

    private static final String DB_USER_ENV = "DB_USER";

    private static final String DB_PASSWORD_ENV = "DB_PASSWORD";


}

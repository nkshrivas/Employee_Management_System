import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {
    
    java.sql.Connection conn;
    private final String DB_URL = "jdbc:sqlserver://OBITO;databaseName=employees;integratedSecurity=true;encrypt=true;trustServerCertificate=true";
    private final String USER = "admin";
    private final String PASSWORD = "admin";

   public Connection(){
         try {
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Successfully connected");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("Error in database connection");
            e.printStackTrace();
        }
    }
}

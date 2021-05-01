import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NativeUtil {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://39.100.2.247:3306/test?user=root&password=root";

    private static Connection conn = null;

    static {
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return conn;
    }
}

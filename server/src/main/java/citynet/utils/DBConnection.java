/**
 *
 * @author Francisco Javier Diaz Garzon
 */
package citynet.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * Class to make connection with DB
 */
public class DBConnection {

    private static Connection connection = null;

    /**
     * Create connection with DB
     *
     * @return connection
     */
    public static Connection getConnection() {
        connection = null;
        try {
            //Connexió amb dades en fitxer de configuració.
            //Cal provar al servidor públic si funciona correctament.
            Properties prop = new Properties();
            InputStream inputStream = DBConnection.class.getClassLoader().getResourceAsStream("/db.properties");
            prop.load(inputStream);
            String driver = prop.getProperty("driver");
            String url = prop.getProperty("url");
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
            /*Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/citynet", "citiynet_user", "aV~d#wG$5R");*/

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }
}

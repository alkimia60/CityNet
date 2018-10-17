/**
 *
 * @author Francisco Javier Diaz Garzon
 * Class to make connection with DB
 */
package citynet.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection connection = null;

    public static Connection getConnection() {
        connection=null;
//        if (connection != null) {
//            return connection;
//        } else {
            try {

//                //Connexió amb dades en fitxer de configuració.
//                //Cal provar al servidor públic si funciona correctament.
//                Properties prop = new Properties();
//                InputStream inputStream = DBConnection.class.getClassLoader().getResourceAsStream("/db.properties");
//                prop.load(inputStream);
//                String driver = prop.getProperty("driver");
//                String url = prop.getProperty("url");
//                String user = prop.getProperty("user");
//                String password = prop.getProperty("password");
//                Class.forName(driver);
//                connection = DriverManager.getConnection(url, user, password);
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/citynet", "citiynet_user", "aV~d#wG$5R");

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
//                              
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
            }
            return connection;
//        }

    }

}

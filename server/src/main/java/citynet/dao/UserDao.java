/**
 *
 * @author Francisco Javier Diaz Garzon
 * Class to manage user table queries
 */
package citynet.dao;

import citynet.model.User;
import citynet.Utils.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private Connection connection;

    public UserDao() {
        connection = DBConnection.getConnection();
    }

    public List<User> getAllUsers(int n, int m) {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from users order by email limit " + n + "," + m);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setAddress(rs.getString("address"));
                user.setPostcode(rs.getString("postcode"));
                user.setCity(rs.getString("city"));
                user.setUserLevel(rs.getString("user_level"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public int totalUserRows() {
         try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS rowcount FROM users");
            rs.next();
            return rs.getInt("rowcount") ;
         }catch(SQLException e){
             System.out.println("totalUserRows");
             e.printStackTrace();
         }
        return 0;
    }
}

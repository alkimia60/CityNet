/**
 *
 * @author Francisco Javier Diaz Garzon
 * Class to manage user table queries
 */
package citynet.dao;

import citynet.Utils.AuthenticationUtils;
import citynet.Utils.DBConnection;
import citynet.Utils.StringUtils;
import citynet.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.Gson;
import java.sql.PreparedStatement;

public class UserDao {

    private Connection connection;

    public UserDao() {
        connection = DBConnection.getConnection();
    }

    public List<User> getAllUsers(int n, int m) {
        //Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        try {
            //connection = DBConnection.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery("select * from users order by email limit " + n + "," + m);
            while (rs.next()) {
                User user = new User();
                //user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
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
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                /* ignored */ }
            try {
                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                /* ignored */ }
//            try {
//                if (connection != null && !connection.isClosed()) {
//                    connection.close();
//                }
//            } catch (Exception e) {
//                /* ignored */ }
        }

        return users;
    }

    public int totalUserRows() {
        //Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        int result = 0;
        try {
            //connection = DBConnection.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT COUNT(*) AS rowcount FROM users");
            rs.next();
            result = rs.getInt("rowcount");
            return result;
        } catch (SQLException e) {
            System.out.println("totalUserRows");
            e.printStackTrace();
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (Exception e) {
                /* ignored */ }
            try {
                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }
            } catch (Exception e) {
                /* ignored */ }
//            try {
//                if (connection != null && !connection.isClosed()) {
//                    connection.close();
//                }
//            } catch (Exception e) {
//                /* ignored */ }
        }
        return result;
    }

    public String userRegister(String userStr) {
        //Connection connection = null;
        PreparedStatement preparedStmt = null;
        Gson gson = new Gson();

        User user = (User) gson.fromJson(userStr, User.class);
        String query = "INSERT INTO users (email, password, name, surname, "
                + "address, postcode, city, user_level) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        AuthenticationUtils au = new AuthenticationUtils();
        try {
            //connection = DBConnection.getConnection();
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, user.getEmail());
            preparedStmt.setString(2, au.hashPassword(user.getPassword()));
            preparedStmt.setString(3, user.getName());
            preparedStmt.setString(4, user.getSurname());
            preparedStmt.setString(5, user.getAddress());
            preparedStmt.setString(6, user.getPostcode());
            preparedStmt.setString(7, user.getCity());
            preparedStmt.setString(8, "user");
            preparedStmt.execute();
            return StringUtils.jsonOkMessage("User registered");
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (preparedStmt != null && !preparedStmt.isClosed()) {
                    preparedStmt.close();
                }
            } catch (Exception e) {
                /* ignored */ }
//            try {
//                if (connection != null && !connection.isClosed()) {
//                    connection.close();
//                }
//            } catch (Exception e) {
//                /* ignored */ }
        }
        return StringUtils.jsonErrorMessage("The user could not be registered");
    }
    
    public String userDelete(String userEmail) {
        //Connection connection = null;
        //DELETE FROM table_name WHERE condition; 
        //PreparedStatement preparedStmt = null;
        Statement statement = null;
        

        String query = "DELETE FROM users WHERE email = \'" + userEmail +"\'" ;

        try {
            //connection = DBConnection.getConnection();
            statement = connection.createStatement();
            int deleted = statement.executeUpdate(query);
            String result=StringUtils.jsonErrorMessage("Username does not exist");
            if (deleted==1)result= StringUtils.jsonOkMessage("User deleted");                    
            
            //preparedStmt.setString(1, userStr);
            //boolean val= preparedStmt.execute();
            //return "{" + preparedStmt.execute() + "}";
            //return "{"Status":"Ok"}";
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }
            } catch (Exception e) {
                /* ignored */ }
//            try {
//                if (connection != null && !connection.isClosed()) {
//                    connection.close();
//                }
//            } catch (Exception e) {
//                /* ignored */ }
        }
        return StringUtils.jsonErrorMessage("The user could not be deleted");
    }

    public String userLogin(String user, String password) {
        String token="";
        return token;
    }
}

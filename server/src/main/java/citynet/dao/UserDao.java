/**
 *
 * @author Francisco Javier Diaz Garzon
 * Class to manage user table queries
 */
package citynet.dao;

import citynet.utils.AuthenticationUtils;
import citynet.utils.DBConnection;
import citynet.utils.TextUtils;
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

    //private Connection connection;
//    public UserDao() {
//        connection = DBConnection.getConnection();
//    }
    public List<User> getAllUsers(int n, int m, String rol) {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM users WHERE user_level LIKE \'" + rol + "\'ORDER BY email LIMIT " + n + "," + m);
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
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception e) {
                /* ignored */ }
        }

        return users;
    }

    public int totalUserRows() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        int result = 0;
        try {
            connection = DBConnection.getConnection();
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
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception e) {
                /* ignored */ }
        }
        return result;
    }

    public String userRegister(String userStr) {
        Connection connection = null;
        PreparedStatement preparedStmt = null;
        Gson gson = new Gson();

        User user = (User) gson.fromJson(userStr, User.class);
        if (TextUtils.isJsonEmptyValues(userStr)) {
            return TextUtils.jsonErrorMessage("Empty fields in User object");
        }

        String query = "INSERT INTO users (email, password, name, surname, "
                + "address, postcode, city, user_level) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        AuthenticationUtils au = new AuthenticationUtils();
        try {
            connection = DBConnection.getConnection();
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, user.getEmail().trim());
            preparedStmt.setString(2, au.hashPassword(user.getPassword().trim()));
            preparedStmt.setString(3, user.getName().trim());
            preparedStmt.setString(4, user.getSurname().trim());
            preparedStmt.setString(5, user.getAddress().trim());
            preparedStmt.setString(6, user.getPostcode().trim());
            preparedStmt.setString(7, user.getCity().trim());
            preparedStmt.setString(8, "user");
            preparedStmt.execute();
            return TextUtils.jsonOkMessage("User registered");
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (preparedStmt != null && !preparedStmt.isClosed()) {
                    preparedStmt.close();
                }
            } catch (Exception e) {
                /* ignored */ }
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception e) {
                /* ignored */ }
        }
        return TextUtils.jsonErrorMessage("User could not be registered");
    }

    public String userDelete(String userEmail) {
        Connection connection = null;
        Statement statement = null;

        String query = "DELETE FROM users WHERE email = \'" + userEmail + "\'";

        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            int deleted = statement.executeUpdate(query);
            String result = TextUtils.jsonErrorMessage("Username does not exist");
            if (deleted == 1) {
                result = TextUtils.jsonOkMessage("User deleted");
            }

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
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception e) {
                /* ignored */ }
        }
        return TextUtils.jsonErrorMessage("The user could not be deleted");
    }

    public String findUserHashedPassword(String user) {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        String query = "SELECT password FROM users WHERE email =\'" + user + "\'";
        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            return rs.getString(1);
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
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception e) {
                /* ignored */ }
        }

        return TextUtils.jsonErrorMessage("No hashed password");
    }

    public String findUserRol(String user) {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        String query = "SELECT user_level FROM users WHERE email =\'" + user + "\'";
        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            return rs.getString(1);
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
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception e) {
                /* ignored */ }
        }

        return TextUtils.jsonErrorMessage("No user rol");
    }

    public boolean isValidUser(String user) {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        String query = "SELECT email FROM users WHERE email =\'" + user + "\'";
        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            if ((rs.getString(1)).equals(user)) {
                return true;
            } else {
                return false;
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
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception e) {
                /* ignored */ }
        }
        return false;
    }

    public String changePassword(String user, String password) {
        Connection connection = null;
        PreparedStatement preparedStmt = null;

        //String query = "UPDATE SET (password = \'" + new AuthenticationUtils().hashPassword(password) + ") WHERE email = \'" + user + "\'";
        String query = "UPDATE users SET password = ? WHERE email = ?";
        /*String query = "INSERT INTO users (email, password, name, surname, "
                + "address, postcode, city, user_level) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";*/
        try {
            connection = DBConnection.getConnection();
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, new AuthenticationUtils().hashPassword(password.trim()));
            preparedStmt.setString(2, user);
            preparedStmt.execute();
            return TextUtils.jsonOkMessage("Password Changed");
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (preparedStmt != null && !preparedStmt.isClosed()) {
                    preparedStmt.close();
                }
            } catch (Exception e) {
                /* ignored */ }
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception e) {
                /* ignored */ }
        }
        return TextUtils.jsonErrorMessage("Password can not be changed");
    }

    public User findUserData(String userEmail) {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        User user = new User();
        String query = "SELECT email, name, surname, address, postcode, city FROM users WHERE email =\'" + userEmail + "\'";
        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            user.setEmail(rs.getString("email"));
            user.setName(rs.getString("name"));
            user.setSurname(rs.getString("surname"));
            user.setAddress(rs.getString("address"));
            user.setPostcode(rs.getString("postcode"));
            user.setCity(rs.getString("city"));
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
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception e) {
                /* ignored */ }
        }
        return user;
    }

    public String UpdateUserData(String userStr) {
        Connection connection = null;
        PreparedStatement preparedStmt = null;
        ResultSet rs = null;
        Gson gson = new Gson();

        User user = (User) gson.fromJson(userStr, User.class);
        //Comprova si hi ha camps buits
        if (TextUtils.isJsonEmptyValues(userStr)) {
            return TextUtils.jsonErrorMessage("Empty fields in User object");
        }
        String query = "UPDATE users SET name = ?, surname = ?, address = ?, postcode = ?, city= ? WHERE email = ?";
        try {
            connection = DBConnection.getConnection();
            preparedStmt = connection.prepareStatement(query);

            preparedStmt.setString(1, user.getName().trim());
            preparedStmt.setString(2, user.getSurname().trim());
            preparedStmt.setString(3, user.getAddress().trim());
            preparedStmt.setString(4, user.getPostcode().trim());
            preparedStmt.setString(5, user.getCity().trim());
            preparedStmt.setString(6, user.getEmail().trim());
            preparedStmt.execute();
            return TextUtils.jsonOkMessage("User updated");
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
                if (preparedStmt != null && !preparedStmt.isClosed()) {
                    preparedStmt.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                /* ignored */ }
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception e) {
                /* ignored */ }
        }
        return TextUtils.jsonErrorMessage("User could not be updated");
    }

}

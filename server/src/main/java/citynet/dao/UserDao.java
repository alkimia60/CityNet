/**
 *
 * @author Francisco Javier Diaz Garzon
 */
package citynet.dao;

import citynet.utils.AuthUtils;
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

/**
 *
 * Class to manage user table queries
 */
public class UserDao {

    /**
     * List of all users
     *
     * @param n cursor position
     * @param m number of rows
     * @param rol user role
     * @return List<User> of m rows, starting in n, filtered by rol
     */
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
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                /* ignored */ }
            try {
                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }
            } catch (SQLException e) {
                /* ignored */ }
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                /* ignored */ }
        }

        return users;
    }

    /**
     * Number of rows of the getAllUsers query
     * @return number of rows
     */
    public int totalUserRows(String rol) {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        int result = 0;
        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT COUNT(*) AS rowcount FROM users WHERE user_level LIKE \'" + rol + "\'");
            rs.next();
            result = rs.getInt("rowcount");
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                /* ignored */ }
            try {
                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }
            } catch (SQLException e) {
                /* ignored */ }
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                /* ignored */ }
        }
        return result;
    }

    /**
     * Register user in DB
     * @param userStr user profile data to register
     * @return ok or error message
     */
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
        try {
            connection = DBConnection.getConnection();
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, user.getEmail().trim());
            preparedStmt.setString(2, AuthUtils.hashPassword(user.getPassword().trim()));
            preparedStmt.setString(3, user.getName().trim());
            preparedStmt.setString(4, user.getSurname().trim());
            preparedStmt.setString(5, user.getAddress().trim());
            preparedStmt.setString(6, user.getPostcode().trim());
            preparedStmt.setString(7, user.getCity().trim());
            preparedStmt.setString(8, "user");
            preparedStmt.execute();
            return TextUtils.jsonOkMessage("User registered");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (preparedStmt != null && !preparedStmt.isClosed()) {
                    preparedStmt.close();
                }
            } catch (SQLException e) {
                /* ignored */ }
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                /* ignored */ }
        }
        return TextUtils.jsonErrorMessage("User could not be registered");
    }

    /**
     * Delete user in DB
     * @param userEmail email of user to delete
     * @return ok or error message
     */
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
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }
            } catch (SQLException e) {
                /* ignored */ }
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                /* ignored */ }
        }
        return TextUtils.jsonErrorMessage("The user could not be deleted");
    }

    /**
     * Search hashed user password in DB
     * @param user 
     * @return hashed password or error
     */
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
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                /* ignored */ }
            try {
                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }
            } catch (SQLException e) {
                /* ignored */ }
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                /* ignored */ }
        }

        return TextUtils.jsonErrorMessage("No hashed password");
    }

    /**
     * Search user role in DB
     * @param user
     * @return user role or error
     */
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
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                /* ignored */ }
            try {
                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                /* ignored */ }
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                /* ignored */ }
        }

        return TextUtils.jsonErrorMessage("No user rol");
    }

    /**
     * Checks if user is in DB
     * @param user
     * @return true if user is in DB
     */
    public boolean isUserInDB(String user) {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        String query = "SELECT email FROM users WHERE email =\'" + user + "\'";
        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                /* ignored */ }
            try {
                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }
            } catch (SQLException e) {
                /* ignored */ }
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                /* ignored */ }
        }
        return false;
    }

    /**
     * Updates user password in DB
     * @param user
     * @param password
     * @return ok or error message
     */
    public String changePassword(String user, String password) {
        Connection connection = null;
        PreparedStatement preparedStmt = null;

        String query = "UPDATE users SET password = ? WHERE email = ?";
        try {
            connection = DBConnection.getConnection();
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, AuthUtils.hashPassword(password.trim()));
            preparedStmt.setString(2, user);
            preparedStmt.execute();
            return TextUtils.jsonOkMessage("Password Changed");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (preparedStmt != null && !preparedStmt.isClosed()) {
                    preparedStmt.close();
                }
            } catch (SQLException e) {
                /* ignored */ }
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                /* ignored */ }
        }
        return TextUtils.jsonErrorMessage("Password can not be changed");
    }

    /**
     * Search user data in DB
     * @param userEmail
     * @return User data
     */
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
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                /* ignored */ }
            try {
                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }
            } catch (SQLException e) {
                /* ignored */ }
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                /* ignored */ }
        }
        return user;
    }

    /**
     * Update user data in DB
     * @param userStr user profile data
     * @return ok or error message
     */
    public String UpdateUserData(String userStr) {
        Connection connection = null;
        PreparedStatement preparedStmt = null;
        Gson gson = new Gson();

        User user = (User) gson.fromJson(userStr, User.class);
        //Check for empty values
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
            System.out.println(e.getMessage());
        } finally {
            try {
                if (preparedStmt != null && !preparedStmt.isClosed()) {
                    preparedStmt.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                /* ignored */ }
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                /* ignored */ }
        }
        return TextUtils.jsonErrorMessage("User could not be updated");
    }

    /**
     * Update user role in DB
     * @param username username of user
     * @param rol new role
     * @return ok or error message
     */
    public String UpdateUserRol(String username, String rol) {
        Connection connection = null;
        PreparedStatement preparedStmt = null;

        String query = "UPDATE users SET user_level = ? WHERE email = ?";
        try {
            connection = DBConnection.getConnection();
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, rol.trim());
            preparedStmt.setString(2, username.trim());
            preparedStmt.execute();
            return TextUtils.jsonOkMessage("Rol updated");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (preparedStmt != null && !preparedStmt.isClosed()) {
                    preparedStmt.close();
                }
            } catch (SQLException e) {
                /* ignored */ }
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                /* ignored */ }
        }
        return TextUtils.jsonErrorMessage("Rol could not be updated");
    }
}

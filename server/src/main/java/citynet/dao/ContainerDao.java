/**
 *
 * @author Francisco Javier Diaz Garzon
 * Class to manage container table queries
 */
package citynet.dao;

import citynet.model.Container;
import citynet.model.User;
import citynet.utils.AuthUtils;
import citynet.utils.DBConnection;
import citynet.utils.TextUtils;
import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContainerDao {

    /**
     * Checks if container exists in DB
     *
     * @param container
     * @return true if container is in DB
     */
    public boolean isContainerInDB(String srContainer) {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        String query = "SELECT id FROM container WHERE id =\'" + srContainer + "\'";
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
     * Register container in DB
     *
     * @param containerStr container data to register
     * @return ok or error message
     */
    public String containerRegister(String containerStr) {
        Connection connection = null;
        PreparedStatement preparedStmt = null;
        Gson gson = new Gson();
        Container container = (Container) gson.fromJson(containerStr, Container.class);

        String query = "INSERT INTO container (id, type, latitude, longitude)"
                + " VALUES (?, ?, ?, ?)";
        try {
            connection = DBConnection.getConnection();
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, container.getId().trim());
            preparedStmt.setString(2, container.getType().trim());
            preparedStmt.setDouble(3, container.getLatitude());
            preparedStmt.setDouble(4, container.getLongitude());
            preparedStmt.execute();
            return TextUtils.jsonOkMessage("Container registered");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Logger.getLogger(ContainerDao.class.getName()).log(Level.SEVERE, null, e);
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
        return TextUtils.jsonErrorMessage("Container could not be registered");
    }

    /**
     * List of all containers
     *
     * @param n cursor position
     * @param m number of rows
     * @param filterField field that has to be filtered
     * @param filterValue value of the field for which it is to be filtered
     * @return List<Container> of m rows, starting in n, filtered by filterField
     */
    public List<Container> getAllContainers(int n, int m, String filterField, String filterValue) {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        List<Container> containers = new ArrayList<>();
        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM container WHERE " + filterField + " LIKE \'" + filterValue + "\'ORDER BY id LIMIT " + n + "," + m);
            while (rs.next()) {
                Container container = new Container();
                container.setId(rs.getString("id"));
                container.setType(rs.getString("type"));
                container.setLatitude(rs.getDouble("latitude"));
                container.setLongitude(rs.getDouble("longitude"));
                container.setOperative(rs.getBoolean("operative"));
                container.setActive_incident(rs.getInt("active_incident"));
                containers.add(container);
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

        return containers;
    }

    /**
     * Number of rows of the getAllContainers query
     *
     * @param filterField field that has to be filtered
     * @param filterValue value of the field for which it is to be filtered
     * @return number of rows
     */
    public int totalContainerRows(String filterField, String filterValue) {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        int result = 0;
        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT COUNT(*) AS rowcount FROM container WHERE " + filterField + " LIKE \'" + filterValue + "\'");
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
     * Checks if a field exists in a table
     *
     * @param table
     * @param field
     * @return true if field exists
     */
    public boolean isFieldInDB(String table, String field) {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        String query = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS "
                + "WHERE TABLE_NAME = \'" + table + "\' AND COLUMN_NAME = \'" + field + "\'";
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
     * Checks if a value exists in a field on a table
     * @param table
     * @param field
     * @param value
     * @return true if value exists
     */
    public boolean isValueInField(String table, String field, String value) {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        String query = "SELECT * FROM " + table + " WHERE " + field + " = \'" + value + "\'";
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

}

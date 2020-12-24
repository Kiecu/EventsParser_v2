package Infrastructure.Services;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseQueryExecutor {

    public static ResultSet ExecuteQuery(String query) {
        try (Connection connection = GetConnection()) {
            try (Statement stmt = connection.createStatement()) {
                ResultSet resultSet = stmt.executeQuery(query);
                return resultSet;
            }
        } catch (SQLException e) {
            e.printStackTrace();

            return null;
        }
    }

    public static void ExecuteCommand(String command) {
        try (Connection connection = GetConnection()) {
            try (Statement stmt = connection.createStatement()) {
                stmt.execute(command);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void ExecuteBatchInsert(ArrayList<String> commands) {
        try (Connection connection = GetConnection()) {
            try (Statement stmt = connection.createStatement()) {
                for (String command : commands) {
                    stmt.addBatch(command);
                }

                stmt.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection GetConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:mem:logs", "SA", "");
    }
}

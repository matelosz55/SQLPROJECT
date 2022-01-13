import java.sql.*;

public class DbUtil {

    private static final String DB_HOSTNAME = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    public static Connection connect(String database) throws SQLException {
        return DriverManager.getConnection(getUrl(database), DB_USERNAME, DB_PASSWORD);
    }

    private static String getUrl(String database) {
        String parameters = "useSSL=false&characterEncoding=utf8&serverTimezone=UTC";
        return String.format("jdbc:mysql://%s:%s/%s?%s", DB_HOSTNAME, DB_PORT, database, parameters);
    }

    //      QUERIES
    private static final String INSERT_QUERY = "INSERT INTO users (email, username, password) VALUES (?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM tableName where id = ?";
    private static final String SELECT_QUERY = "SELECT * FROM tableName where id = ?";
    private static final String LIST_QUERY = "SELECT * FROM tableName";
    private static final String CHANGE_QUERY = "UPDATE users SET tableColumnName = ? WHERE id = ?";


    //      INSERT NEW USER
    public static void insertData(Connection connection, String tableName, String... params) {
        try (PreparedStatement statement =
                     connection.prepareStatement(INSERT_QUERY.replace("tableName", tableName));) {
            statement.setString(1, params[0]);
            statement.setString(2, params[1]);
            statement.setString(3, params[2]);
            statement.executeUpdate();
            System.out.println("New user added.");
        } catch (Exception e) {
            System.out.println("User already exists in database.");
        }
    }

    //      REMOVE EXISTING USER
    public static void remove(Connection connection, String tableName, int id) {
        try (PreparedStatement statement =
                     connection.prepareStatement(DELETE_QUERY.replace("tableName", tableName));) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //      LIST ONE USER
    public static void list(Connection connection, String tableName, int id) {
        try (PreparedStatement statement =
                     connection.prepareStatement(SELECT_QUERY.replace("tableName", tableName));) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            rs.next();
            int id2 = rs.getInt("id");
            String email = rs.getString("email");
            String username = rs.getString("username");
            String password = rs.getString("password");
            System.out.println("ID: " + id2 + "   email: " + email + "  username: " + username + "    password: " + password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //      LIST ALL USERS
    public static void listAll(Connection connection, String tableName) {
        try (PreparedStatement statement =
                     connection.prepareStatement(LIST_QUERY.replace("tableName", tableName));) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id2 = rs.getInt("id");
                String email = rs.getString("email");
                String username = rs.getString("username");
                String password = rs.getString("password");
                System.out.println("ID: " + id2 + "   email: " + email + "  username: " + username + "    password: " + password);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //      CHANGE USER DATA
    public static void changeData(Connection connection, String tableColumnName, int id, String... params) {
        try (PreparedStatement statement =
                     connection.prepareStatement(CHANGE_QUERY.replace("tableColumnName", tableColumnName));) {
            statement.setString(1, params[0]);
            statement.setInt(2, id);
            statement.executeUpdate();
            System.out.println("User credentials udpated.");
        } catch (Exception e) {
            System.out.println("User with this credentials already exists in database.");
        }
    }


}
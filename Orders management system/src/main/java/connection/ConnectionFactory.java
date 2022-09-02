package connection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {
    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/schooldb";
    private static final String USER = "root";
    private static final String PASS = "BBCMoDzZ99!";

    public static ConnectionFactory connectionFactory = new ConnectionFactory();
    private ConnectionFactory(){
        try {
            Class.forName(DRIVER);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * <p>
     *      Creates the connection with the database
     * </p>
     * @return connection object
     */
    private Connection createConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DBURL,USER,PASS);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "An error has occurred while trying to connect to the database");
            e.printStackTrace();
        }
        return connection;
    }

    public static Connection getConnection(){
        return connectionFactory.createConnection();
    }

    /**
     * <p>
     *      Closes the connection given as a parameter
     * </p>
     * @param connection connection object
     */
    public static void close(Connection connection){
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error has occurred while trying to close the connection");
                e.printStackTrace();
            }
        }
    }
    /**
     * <p>
     *      Closes the statement given as a parameter
     * </p>
     * @param statement connection object
     */
    public static void close(Statement statement){
        if(statement!=null){
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error has occurred while trying to close the statement");
                e.printStackTrace();
            }
        }
    }

    /**
     * <p>
     *      Closes the resultSet given as a parameter
     * </p>
     * @param resultSet connection object
     */
    public static void close(ResultSet resultSet){
        if (resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error has occurred while trying to close the ResultSet");
                e.printStackTrace();
            }
        }
    }

}

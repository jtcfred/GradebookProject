package DBManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import GradebookMenu.*;

public class DBUtil {
    
    private static Connection connection;
    
    private DBUtil() {}

    public static synchronized Connection getConnection() {
        if (connection != null) {
            return connection;
        }
        else {
            do {
            	try {
                    // set the db url, username, and password
                    String url = "jdbc:mysql://cpsc2810-2.cpz47hs5tike.us-east-1.rds.amazonaws.com/cpsc2810schema";
                    //String username = "ctadmin";
                    //String password = "ban11anaS";
                    
                    
                    System.out.println("Enter your database username: ");
                    String username = Gradebook.sc.nextLine();
                    System.out.println("Enter your database password: ");
                    String password = Gradebook.sc.nextLine();
                    
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    // get and return connection
                    connection = DriverManager.getConnection(
                            url, username, password);
                } catch (SQLException  | ClassNotFoundException e) {
                    System.out.println("Could not make the connection. Please retry your credentials");
                    continue;
                }
            	return connection;
            }while(true);
        }
    }
    
    public static synchronized void closeConnection(){
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e);
            } finally {
                connection = null;                
            }
        }
    }
}
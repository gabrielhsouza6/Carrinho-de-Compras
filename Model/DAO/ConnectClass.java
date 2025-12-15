package Model.DAO;

import java.sql.*;

public class ConnectClass {
    private static Connection connection;

    public static Connection getInstance(){
        if(connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection =  DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/myshoppingcarproject",
                            "root",
                            "Fnaf1987");
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }
}

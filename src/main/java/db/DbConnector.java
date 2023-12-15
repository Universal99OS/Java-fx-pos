package db;

import java.sql.*;

public class DbConnector {
    private static DbConnector dbc;
    private Connection connection = null;
    private DbConnector(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "Panduka@98");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static DbConnector getInstance() throws ClassNotFoundException, SQLException{
        return dbc!=null? dbc :(dbc=new DbConnector());
    }

    public Connection getConnection(){
        return connection;
    }

}

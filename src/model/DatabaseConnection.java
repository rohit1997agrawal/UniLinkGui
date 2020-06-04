package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {


    //To create database and get connection to the same

    public static Connection getConnection(String dbName)
            throws SQLException, ClassNotFoundException {
        //Registering the HSQLDB JDBC driver
        Class.forName("org.hsqldb.jdbc.JDBCDriver");

        // Database files will be created in the "database"

        Connection con = DriverManager.getConnection
                ("jdbc:hsqldb:file:database/" + dbName, "SA", "");
        return con;
    }
}

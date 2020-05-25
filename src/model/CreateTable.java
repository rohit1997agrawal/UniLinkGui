package model;

//IMPORTAMT

//The syntax is supported by recent HSQLDB 2.3.X versions.

import com.sun.glass.ui.Window;
import model.ConnectionTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateTable {

    public static void createTable()
    {

        Connection con = null;
        Statement stmt = null;
        Statement stmt2 = null;
        Statement stmt3 = null;
        Statement stmt4 = null;

        int result = 0;
        int result2 = 0;

        try {
            ConnectionTest test = new ConnectionTest();
            test.createDb();
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            con = DriverManager.getConnection("jdbc:hsqldb:file:database/testDB", "SA", "");
            stmt3 = con.createStatement();
            stmt4 = con.createStatement();
            result = stmt4.executeUpdate("DROP TABLE reply");
            result = stmt3.executeUpdate("DROP TABLE post");

            stmt = con.createStatement();

            result = stmt.executeUpdate("CREATE TABLE post (" +
                    "            id VARCHAR(10) NOT NULL, title VARCHAR(100) NOT NULL," +
                    "            description VARCHAR(100) NOT NULL, creator_id VARCHAR(50) NOT NULL, status VARCHAR(10) NOT NULL , image VARCHAR(50), event_venue VARCHAR(100) , event_date VARCHAR(20) , " +
                    "event_capacity INT , event_attendee_count INT , sale_asking_price FLOAT , sale_highest_offer FLOAT , sale_minimum_raise FLOAT , job_proposed_price FLOAT , " +
                    "            job_lowest_offer FLOAT , PRIMARY KEY (id))");
            System.out.println(result);

            stmt2 = con.createStatement();
            result2 = stmt.executeUpdate("CREATE TABLE reply (" +
                    "           id INTEGER IDENTITY PRIMARY KEY, post_id VARCHAR(10) NOT NULL, value FLOAT NOT NULL , " +
                    "             responder_id VARCHAR(50) NOT NULL,  " +
                    "           FOREIGN KEY (post_id) REFERENCES post(id) ) ");
            System.out.println(result2);


        }  catch (Exception e) {
            e.printStackTrace(System.out);
        }
        System.out.println("Table created successfully");
    }





    public static void insertData()
    {

        Connection con = null;
        Statement stmt = null;
        Statement stmt2 = null;
        int result = 0;
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            con = DriverManager.getConnection( "jdbc:hsqldb:file:database/testDB", "SA", "");
            stmt = con.createStatement();
            stmt2 = con.createStatement();
            result = stmt.executeUpdate("INSERT INTO post (id,title,description,creator_id,status,image,event_venue,event_date,event_capacity,event_attendee_count) VALUES ('EVE001','First Event','Event description','ROH001','OPEN','rohit','rohits house','10/10/2020',10,0)");
            result = stmt2.executeUpdate("INSERT INTO post (id,title,description,creator_id,status,image,event_venue,event_date,event_capacity,event_attendee_count) VALUES ('EVE002','Second Event','Event description','ROH002','OPEN','rohit2','rohits house2','10/10/2021',8,0)");
                    con.commit();
        }catch (Exception e) {
            e.printStackTrace(System.out);
        }
        System.out.println(result+" rows effected");
        System.out.println("Rows inserted successfully");
    }
}
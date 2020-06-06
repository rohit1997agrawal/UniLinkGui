package model;


//The syntax is supported by recent HSQLDB 2.3.X versions.

import com.sun.xml.internal.bind.v2.TODO;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class DatabaseHandling {


    //To create database if not exists and get connection to it

    public static Connection getConnection(String dbName)
            throws SQLException, ClassNotFoundException {
        //Registering the HSQLDB JDBC driver
        Class.forName("org.hsqldb.jdbc.JDBCDriver");

        // Database files will be created in the "database"

        Connection con = DriverManager.getConnection
                ("jdbc:hsqldb:file:database/" + dbName, "SA", "");
        return con;
    }

    //Function to get connection to database and  create table "post" and "reply"
    public void createTable() {
        Connection con = null;
        Statement stmt = null;
        int result = 0;
        int result2 = 0;
        try {
            con = getConnection("unilinkDB");
            stmt = con.createStatement();

            //Create table "Post" with "id" as primary key
            result = stmt.executeUpdate("CREATE TABLE IF NOT EXISTS post (" +
                    "            id VARCHAR(10) NOT NULL, title VARCHAR(100) NOT NULL," +
                    "            description VARCHAR(100) NOT NULL, creator_id VARCHAR(50) NOT NULL, status VARCHAR(10) NOT NULL , image VARCHAR(50), event_venue VARCHAR(100) , event_date VARCHAR(20) , " +
                    "event_capacity INT , event_attendee_count INT , sale_asking_price FLOAT , sale_highest_offer FLOAT , sale_minimum_raise FLOAT , job_proposed_price FLOAT , " +
                    "            job_lowest_offer FLOAT , PRIMARY KEY (id))");
            System.out.println(result);

            //Create table "Reply" with auto increment id , and "post id" as foreign key
            result2 = stmt.executeUpdate("CREATE TABLE IF NOT EXISTS reply (" +
                    "           id INTEGER IDENTITY PRIMARY KEY, post_id VARCHAR(10) NOT NULL, value FLOAT NOT NULL , " +
                    "             responder_id VARCHAR(50) NOT NULL,  " +
                    "           FOREIGN KEY (post_id) REFERENCES post(id) ) ");
            con.commit();
            System.out.println(result2);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

    }


    //TODO Function to insert data initially to hard code values (2 events , 2 jobs , 2 sales)
    public void insertData() {

        Connection con = null;
        Statement stmt = null;
        Statement stmt2 = null;
        int result = 0;
        try {
            con = getConnection("unilinkDB");
            stmt = con.createStatement();
            stmt2 = con.createStatement();
            //  result = stmt.executeUpdate("INSERT INTO post (id,title,description,creator_id,status,image,event_venue,event_date,event_capacity,event_attendee_count) VALUES ('EVE008','First Event','Event description','ROH001','OPEN','rohit','rohits house','10/10/2020',10,0)");
            //     result = stmt2.executeUpdate("INSERT INTO post (id,title,description,creator_id,status,image,event_venue,event_date,event_capacity,event_attendee_count) VALUES ('EVE009','Second Event','Event description','ROH002','OPEN','rohit2','rohits house2','10/10/2021',8,0)");
            con.commit();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

    }


    /*
    Function called on Application startup
    Function to read data from the Posts and Reply tables and populate it to
    postCollection of UniLink.java class
     */
    public void readDatabase(UniLink unilink) {
        Set<Post> postCollection = unilink.getPostCollection();
        Connection con = null;
        Statement stmt = null;
        ResultSet result = null;
        try {
            con = getConnection("unilinkDB");
            stmt = con.createStatement();
            result = stmt.executeQuery("select * from post");  //Gets all posts from "post" table
            while (result.next()) {
                ResultSet replyResult = null;
                String currentId = result.getString("id");
                //Gets all replies from "reply" table corresponding to the post id
                replyResult = stmt.executeQuery("select * from reply where post_id ='" + currentId + "'");
                //Create objects for Event/Sale/Job and corresponding Replies and add to the Posts Collection
                if (currentId.startsWith("EVE")) {
                    Event event = new Event(currentId, result.getString("title"), result.getString("description"), result.getString("event_venue"), result.getString("event_date"), result.getInt("event_capacity"), result.getString("creator_id"), result.getString("image"));
                    event.setStatus(result.getString("status"));
                    event.setAttendee_count(Integer.parseInt(result.getString("event_attendee_count")));
                    while (replyResult.next()) {
                        Reply reply = new Reply(replyResult.getString("post_id"), replyResult.getDouble("value"), replyResult.getString("responder_id"));
                        event.getReplyList().add(reply);
                    }
                    postCollection.add(event);
                } else if (currentId.startsWith("JOB")) {
                    Job job = new Job(currentId, result.getString("title"), result.getString("description"), result.getDouble("job_proposed_price"), result.getString("creator_id"), result.getString("image"));
                    while (replyResult.next()) {
                        Reply reply = new Reply(replyResult.getString("post_id"), replyResult.getDouble("value"), replyResult.getString("responder_id"));
                        job.getReplyList().add(reply);
                    }
                    job.setStatus(result.getString("status"));
                    job.setLowest_offer(Double.parseDouble((result.getString("job_lowest_offer"))));
                    postCollection.add(job);
                } else if (currentId.startsWith("SAL")) {
                    Sale sale = new Sale(currentId, result.getString("title"), result.getString("description"), result.getDouble("sale_asking_price"), result.getDouble("sale_asking_price"), result.getString("creator_id"), result.getString("image"));
                    while (replyResult.next()) {
                        Reply reply = new Reply(replyResult.getString("post_id"), replyResult.getDouble("value"), replyResult.getString("responder_id"));
                        sale.getReplyList().add(reply);
                    }
                    sale.setStatus(result.getString("status"));
                    sale.setHighest_offer(Double.parseDouble(result.getString("sale_highest_offer")));
                    postCollection.add(sale);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        unilink.setPostCollection(postCollection);
    }

    /*
    Function called when we quit from application or is closed
    First truncates the tables "Post" and "Reply"
    Then populates Corresponding data from postCollection to "Posts" and "Replies"
    */
    public void writeDatabase(UniLink unilink) {
        Connection con = null;
        try {
            int result;
            con = getConnection("unilinkDB");
            Statement stmt = con.createStatement();
            result = stmt.executeUpdate("TRUNCATE TABLE reply");
            result = stmt.executeUpdate("TRUNCATE TABLE post");
            Set<Post> postCollection = new HashSet<>();
            postCollection = unilink.getPostCollection();
            for (Post object : postCollection) {
                String insertQuery = "null";
                if (object instanceof Event) {
                    insertQuery = "INSERT INTO post (id,title,description,creator_id,status,image,event_venue,event_date,event_capacity,event_attendee_count) VALUES ('" + object.getId() + "','" + object.getTitle() + "','" + object.getDescription() + "','" + object.getCreator_id() + "','" + object.getStatus() + "','" + object.getImage_name() + "','" + ((Event) object).getVenue() + "','" + ((Event) object).getDate() + "'," + ((Event) object).getCapacity() + "," + ((Event) object).getAttendee_count() + ")";
                    System.out.println(insertQuery);
                } else if (object instanceof Sale) {
                    insertQuery = "INSERT INTO post (id,title,description,creator_id,status,image,sale_asking_price,sale_highest_offer,sale_minimum_raise) VALUES ('" + object.getId() + "','" + object.getTitle() + "','" + object.getDescription() + "','" + object.getCreator_id() + "','" + object.getStatus() + "','" + object.getImage_name() + "'," + ((Sale) object).getAsking_price() + "," + ((Sale) object).getHighest_offer() + "," + ((Sale) object).getMinimum_raise() + ")";
                    System.out.println(insertQuery);
                } else if (object instanceof Job) {
                    insertQuery = "INSERT INTO post (id,title,description,creator_id,status,image,job_proposed_price,job_lowest_offer) VALUES ('" + object.getId() + "','" + object.getTitle() + "','" + object.getDescription() + "','" + object.getCreator_id() + "','" + object.getStatus() + "','" + object.getImage_name() + "'," + ((Job) object).getProposed_price() + "," + ((Job) object).getLowest_offer() + ")";
                    System.out.println(insertQuery);
                }
                result = stmt.executeUpdate(insertQuery);
                con.commit();
                for (Reply reply : object.getReplyList()) {
                    String insertQueryReply;
                    insertQueryReply = "INSERT INTO reply (post_id,value,responder_id) VALUES ('" + reply.getPost_id() + "'," + reply.getValue() + ",'" + reply.getResponder_id() + "')";
                    stmt = con.createStatement();
                    result = stmt.executeUpdate(insertQueryReply);
                    System.out.println(insertQueryReply);
                    con.commit();
                }
            }
            con.commit();
            //Saves all changes made to database
            result = stmt.executeUpdate("SHUTDOWN");
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}




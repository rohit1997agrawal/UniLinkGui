package model;


//The syntax is supported by recent HSQLDB 2.3.X versions.

import com.sun.xml.internal.bind.v2.TODO;
import org.hsqldb.HsqlException;

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

            //To check if table exists or not
            Statement st = con.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM INFORMATION_SCHEMA.SYSTEM_TABLES where TABLE_TYPE='TABLE'");
            if(resultSet.next())
            {
                    //Ignore Table creating and inserting hardcoded data
                System.out.println("Tables exist");

            }
            else{
                stmt = con.createStatement();

                //Create table "Post" with "id" as primary key
                result = stmt.executeUpdate("CREATE TABLE IF NOT EXISTS post (" +
                        "            id VARCHAR(10) NOT NULL, title VARCHAR(100) NOT NULL," +
                        "            description VARCHAR(100) NOT NULL, creator_id VARCHAR(50) NOT NULL, status VARCHAR(10) NOT NULL , image VARCHAR(50), event_venue VARCHAR(100) , event_date VARCHAR(20) , " +
                        "event_capacity INT , event_attendee_count INT , sale_asking_price FLOAT , sale_highest_offer FLOAT , sale_minimum_raise FLOAT , job_proposed_price FLOAT , " +
                        "            job_lowest_offer FLOAT , PRIMARY KEY (id))");
             //   System.out.println(result);

                //Create table "Reply" with auto increment id , and "post id" as foreign key
                result2 = stmt.executeUpdate("CREATE TABLE IF NOT EXISTS reply (" +
                        "           id INTEGER IDENTITY PRIMARY KEY, post_id VARCHAR(10) NOT NULL, value FLOAT NOT NULL , " +
                        "             responder_id VARCHAR(50) NOT NULL,  " +
                        "           FOREIGN KEY (post_id) REFERENCES post(id) ) ");
                con.commit();

                insertData();
            }

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

    }



    //Insert initial hardcoded data , only if table Post and Reply are created and does not exist already
    public void insertData() {
        Connection con = null;
        Statement stmt = null;
        int result = 0;
        try {
            con = getConnection("unilinkDB");
            stmt = con.createStatement();
            con.setAutoCommit(false);
            String insert1 = "INSERT  INTO post (id,title,description,creator_id,status,image,event_venue,event_date,event_capacity,event_attendee_count) VALUES ('EVE002','Dinner','Vegan dinner this weekend.','S345','OPEN','Dinner.jpg','Carleton','2020-06-20',5,2)";
            String insert2 = "INSERT  INTO post (id,title,description,creator_id,status,image,sale_asking_price,sale_highest_offer,sale_minimum_raise) VALUES ('SAL002','Designer heels','Steve Madden, size 9 US, new and in good condition.','S345','OPEN','heels.jpg',60.0,44.0,10.0)";
            String insert3 = "INSERT  INTO post (id,title,description,creator_id,status,image,sale_asking_price,sale_highest_offer,sale_minimum_raise) VALUES ('SAL001','Casio watch ','Great offer! Casio men''s watch, unused.','S123','OPEN','watch.jpg',109.0,90.0,5.0)";
            String insert4 = "INSERT  INTO post (id,title,description,creator_id,status,image,job_proposed_price,job_lowest_offer) VALUES ('JOB001','Cybersecurity graduate program','Entry level position in CBD.','S123','OPEN','Security.jpg',23000.0,19000.0)";
            String insert5 = "INSERT  INTO post (id,title,description,creator_id,status,image,job_proposed_price,job_lowest_offer) VALUES ('JOB002','Front-end developer','Entry level post based in Richmond','S345','OPEN','IT.jpg',23000.0,20000.0)";
            String insert6 = "INSERT  INTO post (id,title,description,creator_id,status,image,event_venue,event_date,event_capacity,event_attendee_count) VALUES ('EVE001','Roh''s birthday','Please come for Roh''s 23rd birthday ','S123','OPEN','Birthday_Cake.jpg','North Melbourne','2020-06-14',10,2)";
            String insert7 = "INSERT  INTO reply (post_id,value,responder_id) VALUES ('EVE002',1.0,'S678')";
            String insert8 = "INSERT  INTO reply (post_id,value,responder_id) VALUES ('EVE002',1.0,'S890')";
            String insert9 = "INSERT  INTO reply (post_id,value,responder_id) VALUES ('SAL002',34.0,'S678')";
            String insert10 = "INSERT  INTO reply (post_id,value,responder_id) VALUES ('SAL002',44.0,'S890')";
            String insert11 = "INSERT  INTO reply (post_id,value,responder_id) VALUES ('SAL001',80.0,'S678')";
            String insert12 = "INSERT  INTO reply (post_id,value,responder_id) VALUES ('SAL001',90.0,'S890')";
            String insert13 = "INSERT  INTO reply (post_id,value,responder_id) VALUES ('JOB001',20000.0,'S678')";
            String insert14 = "INSERT  INTO reply (post_id,value,responder_id) VALUES ('JOB001',19000.0,'S890')";
            String insert15 = "INSERT  INTO reply (post_id,value,responder_id) VALUES ('JOB002',21000.0,'S678')";
            String insert16 = "INSERT  INTO reply (post_id,value,responder_id) VALUES ('JOB002',20000.0,'S890')";
            String insert17 = "INSERT  INTO reply (post_id,value,responder_id) VALUES ('EVE001',1.0,'S678')";
            String insert18 = "INSERT  INTO reply (post_id,value,responder_id) VALUES ('EVE001',1.0,'S890')";
            stmt.addBatch(insert1);
            stmt.addBatch(insert2);
            stmt.addBatch(insert3);
            stmt.addBatch(insert4);
            stmt.addBatch(insert5);
            stmt.addBatch(insert6);
            stmt.addBatch(insert7);
            stmt.addBatch(insert8);
            stmt.addBatch(insert9);
            stmt.addBatch(insert10);
            stmt.addBatch(insert11);
            stmt.addBatch(insert12);
            stmt.addBatch(insert13);
            stmt.addBatch(insert14);
            stmt.addBatch(insert15);
            stmt.addBatch(insert16);
            stmt.addBatch(insert17);
            stmt.addBatch(insert18);
            //Executing the batch
            stmt.executeBatch();
            con.commit();
        } catch (HsqlException | SQLException | ClassNotFoundException e ) {
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
                    Sale sale = new Sale(currentId, result.getString("title"), result.getString("description"), result.getDouble("sale_asking_price"), result.getDouble("sale_minimum_raise"), result.getString("creator_id"), result.getString("image"));
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
                    insertQuery = "INSERT INTO post (id,title,description,creator_id,status,image,event_venue,event_date,event_capacity,event_attendee_count) VALUES ('" + object.getId() + "','" + object.getTitle().replace("'","''") + "','" + object.getDescription().replace("'","''") + "','" + object.getCreator_id() + "','" + object.getStatus() + "','" + object.getImage_name() + "','" + ((Event) object).getVenue().replace("'","''") + "','" + ((Event) object).getDate() + "'," + ((Event) object).getCapacity() + "," + ((Event) object).getAttendee_count() + ")";
                } else if (object instanceof Sale) {
                    insertQuery = "INSERT INTO post (id,title,description,creator_id,status,image,sale_asking_price,sale_highest_offer,sale_minimum_raise) VALUES ('" + object.getId() + "','" + object.getTitle().replace("'","''") + "','" + object.getDescription().replace("'","''") + "','" + object.getCreator_id() + "','" + object.getStatus() + "','" + object.getImage_name() + "'," + ((Sale) object).getAsking_price() + "," + ((Sale) object).getHighest_offer() + "," + ((Sale) object).getMinimum_raise() + ")";
                } else if (object instanceof Job) {
                    insertQuery = "INSERT INTO post (id,title,description,creator_id,status,image,job_proposed_price,job_lowest_offer) VALUES ('" + object.getId() + "','" + object.getTitle().replace("'","''") + "','" + object.getDescription().replace("'","''") + "','" + object.getCreator_id() + "','" + object.getStatus() + "','" + object.getImage_name() + "'," + ((Job) object).getProposed_price() + "," + ((Job) object).getLowest_offer() + ")";
                }
               // System.out.println(insertQuery);
                result = stmt.executeUpdate(insertQuery);
                con.commit();
                for (Reply reply : object.getReplyList()) {
                    String insertQueryReply;
                    insertQueryReply = "INSERT INTO reply (post_id,value,responder_id) VALUES ('" + reply.getPost_id() + "'," + reply.getValue() + ",'" + reply.getResponder_id() + "')";
                    stmt = con.createStatement();
                    result = stmt.executeUpdate(insertQueryReply);
                 //   System.out.println(insertQueryReply);
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




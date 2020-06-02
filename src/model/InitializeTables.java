package model;

//IMPORTAMT

//The syntax is supported by recent HSQLDB 2.3.X versions.

import com.sun.glass.ui.Window;
import model.InitializeDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class InitializeTables {


    //Function to create table "post" and "reply"
    //To be modified Later
    public static void createTable() {
        Connection con = null;
        Statement stmt = null;
        Statement stmt2 = null;
        Statement stmt3 = null;
        Statement stmt4 = null;
        int result = 0;
        int result2 = 0;
        try {
            con = InitializeDatabase.getConnection("testDB");
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
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        System.out.println("Table created successfully");
    }


    //Function to insert data initially (will not be used every time we run the program - to be modified)
    public static void insertData() {

        Connection con = null;
        Statement stmt = null;
        Statement stmt2 = null;
        int result = 0;
        try {
            con = InitializeDatabase.getConnection("testDB");
            stmt = con.createStatement();
            stmt2 = con.createStatement();
            result = stmt.executeUpdate("INSERT INTO post (id,title,description,creator_id,status,image,event_venue,event_date,event_capacity,event_attendee_count) VALUES ('EVE001','First Event','Event description','ROH001','OPEN','rohit','rohits house','10/10/2020',10,0)");
            result = stmt2.executeUpdate("INSERT INTO post (id,title,description,creator_id,status,image,event_venue,event_date,event_capacity,event_attendee_count) VALUES ('EVE002','Second Event','Event description','ROH002','OPEN','rohit2','rohits house2','10/10/2021',8,0)");
            con.commit();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        System.out.println(result + " rows effected");
        System.out.println("Rows inserted successfully");
    }



    //Function to read data from the database and populate it to the ArrayList
    //To be changed later
    public static void readDatabase(UniLink unilink)
    {
        Set<Post> postCollection = unilink.getPostCollection();
        Connection con = null;
        Statement stmt = null;
        Statement stmt2 = null;
        ResultSet result = null;

        try {
            con = InitializeDatabase.getConnection("testDB");
            stmt = con.createStatement();
            stmt2 = con.createStatement();
            result = stmt.executeQuery("select * from post");
            while(result.next()){
                ResultSet replyResult = null;
                String currentId = result.getString("id");
                replyResult = stmt2.executeQuery("select * from reply where post_id ='"+currentId+"'");
                if(currentId.startsWith("EVE"))
                {
                    Event event = new Event(currentId,result.getString("title"),result.getString("description"),result.getString("event_venue"),result.getString("event_date"),result.getInt("event_capacity"),result.getString("creator_id"),result.getString("image"));
                    while(replyResult.next()){
                        Reply reply = new Reply(replyResult.getString("post_id"),replyResult.getDouble("value"),replyResult.getString("responder_id"));
                       event.getReplyList().add(reply);
                   }
                    postCollection.add(event);

                }
                else if(currentId.startsWith("JOB"))
                {
                    Job job = new Job(currentId,result.getString("title"),result.getString("description"),result.getDouble("job_proposed_price"),result.getString("creator_id"),result.getString("image"));
                    while(replyResult.next()){
                        Reply reply = new Reply(replyResult.getString("post_id"),replyResult.getDouble("value"),replyResult.getString("responder_id"));
                        job.getReplyList().add(reply);
                    }
                    postCollection.add(job);
                }
                else if(currentId.startsWith("SAL"))
                {
                    Sale sale = new Sale(currentId,result.getString("title"),result.getString("description"),result.getDouble("sale_asking_price"),result.getDouble("sale_asking_price"),result.getString("creator_id"),result.getString("image"));
                    while(replyResult.next()){
                        Reply reply = new Reply(replyResult.getString("post_id"),replyResult.getDouble("value"),replyResult.getString("responder_id"));
                        sale.getReplyList().add(reply);
                    }
                    postCollection.add(sale);
                }

            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        //System.out.println(result + " rows effected");
        System.out.println("Rows select * successfully");


        //To hard code one Event "EVE001" with few attendees
        Event newEvent = new Event("EVE001", "Programming Study Group ", "Let's meet tonight to finish the assignment" , "RMIT Library","06/05/2020", 5, "S001","study.jpg");
        postCollection.add(newEvent);
        Reply replyEvent1 = new Reply("EVE001",1, "S002");
        newEvent.handleReply(replyEvent1);
        Reply replyEvent2 = new Reply("EVE001",1, "S003");
        newEvent.handleReply(replyEvent2);
        Reply replyEvent3 = new Reply("EVE001",1, "S004");
        newEvent.handleReply(replyEvent3);

        //To hard code one Sale with few offers
        Sale newSale = new Sale("SAL001","I phone 6S","Working condtion , with box and charger",400,20,"S005","iphone.jpg");
        postCollection.add(newSale);
        Reply replySale1 = new Reply("SAL001",200, "S006");
        newSale.handleReply(replySale1);
        Reply replySale2 = new Reply("SAL001",250, "S007");
        newSale.handleReply(replySale2);
        Reply replySale3 = new Reply("SAL001",300, "S008");
        newSale.handleReply(replySale3);

        //To hard code one Job with few offers
        Job newJob = new Job("JOB001","Changing house","Need someone to help me move my belongings to new place",200,"S009","house.jpg" );
        postCollection.add(newJob);
        Reply replyJob1 = new Reply("JOB001",200, "S010");
        newJob.handleReply(replyJob1);
        Reply replyJob2 = new Reply("JOB001",150, "S011");
        newJob.handleReply(replyJob2);
        Reply replyJob3 = new Reply("JOB001",100, "S012");
        newJob.handleReply(replyJob3);

        unilink.setPostCollection(postCollection);





    }
    public void writeDatabase(UniLink unilink)
    {
        System.out.println("ASDASDASDAASD@@@@@@@@@@@@@");
          Set<Post> postCollection = new HashSet<>();
          postCollection = unilink.getPostCollection();
        for (Post object : postCollection) {
            if(object instanceof Event)
            {
                String insertQuery = "INSERT INTO post (id,title,description,creator_id,status,image,event_venue,event_date,event_capacity,event_attendee_count) VALUES ('"+object.getId()+"','"+object.getTitle()+"','"+object.getDescription()+"','"+object.getCreator_id()+"','"+object.getStatus()+"','"+object.getImage_name()+"','"+((Event) object).getVenue()+"','"+((Event) object).getDate()+"',"+((Event) object).getCapacity()+","+((Event) object).getAttendee_count()+")";
            }
            else if(object instanceof Sale)
            {

            }
            else if(object instanceof Job)
            {

            }
        }

        }



}
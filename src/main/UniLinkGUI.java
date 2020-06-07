package main;

import controller.LoginScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.DatabaseHandling;
import model.UniLink;


public class UniLinkGUI extends Application {


    /*
    Starting point of application
    Creates database and tables(if not exists)
    Reads data from database and stores it to postCollection
    Launches the Login Screen
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Only Object creation of unilink class to access post Collection
        UniLink unilink = new UniLink();
        DatabaseHandling obj = new DatabaseHandling();


        //Create database and tables "Post" and "Reply" if it does not exist , and insert initial data
        obj.createTable();


        //Function to read database and store data in Post Collection(defined in UniLink.java)
        obj.readDatabase(unilink);

        //To launch the login screen
        FXMLLoader loader = new FXMLLoader();
        //Loads the login screeen
        loader.setLocation(getClass().getResource("/view/Login.fxml"));
        Parent root = loader.load();
        LoginScreenController controller = loader.getController();

        //To pass the Primary stage and unilink object to controller
        controller.initializeModelAndStage(primaryStage, unilink);
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.centerOnScreen();
        primaryStage.show();

        //Event handler for when application is closed, Data inserted to Database
        primaryStage.setOnCloseRequest((
                WindowEvent event1) -> {
            //writeDatabase called to insert data to database
            obj.writeDatabase(unilink);
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}

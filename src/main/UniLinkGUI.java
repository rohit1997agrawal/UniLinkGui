package main;

import com.sun.org.apache.xml.internal.security.Init;
import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InitializeDatabase;
import model.InitializeTables;
import model.UniLink;


//Dont look at MainMenu.fmxl and MainController.java

public class UniLinkGUI extends Application {



     @Override
        public void start(Stage primaryStage) throws Exception{

         UniLink unilink = new UniLink();

        //Create Database

        InitializeDatabase.createDb();

        //Create table scrips
        InitializeTables.createTable();

        //insert initial data

        InitializeTables.insertData();

        //Function to read database and store data in Post Collection(defined in UniLink.java)
         //To be modified to read from database
         InitializeTables.readDatabase(unilink);

        System.out.println("HELLO");
        System.out.println(unilink.getPostCollection().size());

        //To launch the login screen
        //Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(getClass().getResource("/view/Login.fxml"));
         Parent root = loader.load();
         MainController controller = loader.getController();
         controller.initializeModelAndStage(primaryStage,unilink);

        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.centerOnScreen();
        primaryStage.show();

         System.out.println(System.getProperty("java.version"));
         System.out.println(System.getProperty("javafx.version"));

    }


    public static void main(String[] args) {
        launch(args);
    }
}

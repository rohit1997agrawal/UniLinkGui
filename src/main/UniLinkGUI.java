package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InitializeDatabase;
import model.InitializeTables;


//Dont look at MainMenu.fmxl and MainController.java

public class UniLinkGUI extends Application {

     @Override
        public void start(Stage primaryStage) throws Exception{

        //Create Database
        InitializeDatabase.createDb();

        //Create table scrips
        InitializeTables.createTable();

        //insert initial data

        InitializeTables.insertData();

        //To launch the login screen
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.centerOnScreen();
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}

package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ConnectionTest;
import model.CreateTable;

//Dont look at MainMenu.fmxl and MainController.java

public class UniLinkGUI extends Application {

     @Override
        public void start(Stage primaryStage) throws Exception{

        //Create Database
        ConnectionTest.createDb();

        //Create table scrips
        CreateTable.createTable();

        //insert initial data

        CreateTable.insertData();


        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        primaryStage.setTitle("Rohits house");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.centerOnScreen();
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}

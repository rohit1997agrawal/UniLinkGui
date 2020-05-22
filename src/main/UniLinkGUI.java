package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.ConnectionTest;
import model.CreateTable;
import view.LoginScreen;


public class UniLinkGUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        //Create Database
        ConnectionTest.createDb();

        //Create table scrips
       // CreateTable.createTable();


        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        primaryStage.setTitle("UniLink");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.centerOnScreen();
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}

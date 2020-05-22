package controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

public class MainController {

    Alert alertBox = new Alert(Alert.AlertType.NONE);
    @FXML
    private TextField txtUserName;

    @FXML
    private Text userName;

    public void Login(javafx.event.ActionEvent actionEvent) throws Exception
    {
        if(txtUserName.getText().isEmpty())
        {
            alertBox.setAlertType(Alert.AlertType.ERROR);
            alertBox.setContentText("Username can not be left blank!");
            alertBox.show();
        }
        else{

            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            primaryStage.setTitle("MainMenu");
            primaryStage.setScene(new Scene(root, 300, 275));
            primaryStage.centerOnScreen();
           //userName.setText("YOOOOOO");
            primaryStage.show();
        }
    }


}

package controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.UniLink;

import java.awt.event.ActionEvent;

public class MainController {

    Alert alertBox = new Alert(Alert.AlertType.NONE);
    @FXML
    private TextField txtUserName;
    @FXML
    private Button loginButton;

    Stage primaryStage;

    UniLink unilink;

   public void initializeModelAndStage(Stage primaryStage,UniLink unilink)
   {
       this.unilink = unilink;
        this.primaryStage=primaryStage;

   }


    public void Login(javafx.event.ActionEvent actionEvent) throws Exception
    {
        //To display Alert if username field left blank, while logging in
        if(txtUserName.getText().isEmpty())
        {
            alertBox.setAlertType(Alert.AlertType.ERROR);
            alertBox.setContentText("Username can not be left blank!");
            alertBox.show();
        }
        //To load the Main menu , once Id is entered
        else{



            //To initialize new stage , to open Main menu
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/MainMenu.fxml"));

            Parent root = loader.load();

            ListViewController controller = loader.getController();

            controller.initializeModelAndStage(txtUserName.getText(),primaryStage,unilink);

            primaryStage.setTitle("MainMenu");
            primaryStage.setScene(new Scene(root, 950, 500));
            primaryStage.centerOnScreen();
            primaryStage.show();
        }
    }


}

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

import java.awt.event.ActionEvent;

public class MainController {

    Alert alertBox = new Alert(Alert.AlertType.NONE);
    @FXML
    private TextField txtUserName;
    @FXML
    private Button loginButton;




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

            //To close the login window
            Stage primaryStage = (Stage) loginButton.getScene().getWindow();
            primaryStage.close();

            //To initialize new stage , to open Main menu
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/MainMenu.fxml"));
            Parent root = loader.load();
            Stage mainMenuStage = new Stage();

            ListViewController controller = loader.getController();
            controller.setLoggedInUser(txtUserName.getText());

            mainMenuStage.setTitle("MainMenu");
            mainMenuStage.setScene(new Scene(root, 950, 500));
            mainMenuStage.centerOnScreen();
            mainMenuStage.show();
        }
    }


}

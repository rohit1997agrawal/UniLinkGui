package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.UniLink;
import model.exceptions.UsernameInvalidException;

import java.io.IOException;

public class LoginScreenController {

    Alert alertBox = new Alert(Alert.AlertType.NONE);
    @FXML
    private TextField txtUserName;
    @FXML
    private Button loginButton;

    Stage primaryStage;

    UniLink unilink;

    //Function to recieve and set the stage , and unilink object
    public void initializeModelAndStage(Stage primaryStage, UniLink unilink) {
        this.unilink = unilink;
        this.primaryStage = primaryStage;

    }

    /*
    Function called when user clicks on "Login" Button
    Validates username
    Opens Main Menu once username validation is successful
     */
    @FXML
    public void Login(javafx.event.ActionEvent actionEvent) {
        try {
            //To display Alert if username field is left blank or is invalid
            if (txtUserName.getText().isEmpty()) {
                throw new UsernameInvalidException("Username can not be left blank!");
            }
            //To check if username starts with "s" followed by atleast one integer from [0-9]
            //Regex used to validate that username starts with "S" and has atleast one integer
            else if (!txtUserName.getText().matches("^(?:S)[0-9]+$")) {
                throw new UsernameInvalidException("Please enter Valid username! \n Username should start with S followed by at least one integer");
            }
            //To load the Main menu , once Id is entered
            else {
                //To initialize new stage , to launch Main menu
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/MainMenu.fxml"));

                Parent root = loader.load();
                MainMenuController controller = loader.getController();

                //Passes stage, unilink object and current logged in user to Main Menu controller
                controller.initializeModelAndStage(txtUserName.getText(), primaryStage, unilink);

                primaryStage.setTitle("MainMenu");
                primaryStage.setScene(new Scene(root, 950, 500));
                primaryStage.centerOnScreen();
                primaryStage.show();
            }
        } catch(UsernameInvalidException ex) {
            alertBox.setAlertType(Alert.AlertType.ERROR);
            alertBox.setContentText(ex.getMessage());
            alertBox.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

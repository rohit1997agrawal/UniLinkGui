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
    public void Login(javafx.event.ActionEvent actionEvent) throws Exception {
        //To display Alert if username field is left blank or is invalid
        if (txtUserName.getText().isEmpty()) {
            alertBox.setAlertType(Alert.AlertType.ERROR);
            alertBox.setContentText("Username can not be left blank!");
            alertBox.show();
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
    }


}

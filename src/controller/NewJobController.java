package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Job;
import model.UniLink;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NewJobController {

    Alert alertBox = new Alert(Alert.AlertType.NONE);
    Stage primaryStage;
    File file;
    String logged_in_user;
    UniLink unilink;
    @FXML
    private TextField job_title;
    @FXML
    private TextField proposed_price;
    @FXML
    private TextArea job_description;
    @FXML
    private Button upload;
    @FXML
    private ImageView job_image;
    @FXML
    private Button submit;

    //Function to receive and set the stage , and unilink object , and logged in user name
    public void initializeModelAndStage(Stage primaryStage, UniLink unilink, String logged_in_user) {
        this.unilink = unilink;
        this.primaryStage = primaryStage;
        this.logged_in_user = logged_in_user;
    }

    @FXML
    void onCancel(ActionEvent event) {
        returnToMainMenu();
    }

    /*
   Function called when Submit Job is clicked
   Validates Job details
   If not validated , corresponding error message thrown
   If validated, new Event Created
  */
    @FXML
    void submitJob(ActionEvent event) {
        alertBox.setAlertType(Alert.AlertType.ERROR);
        try {
            double num = Double.parseDouble(proposed_price.getText());
            if (num <= 0) {
                alertBox.setContentText("Please enter a positive number for proposed price!");
                alertBox.show();
                return;
            }
        } catch (NumberFormatException e) {
            alertBox.setContentText("Please enter valid input for Proposed price");
            alertBox.show();
            return;
        }
        if (job_title.getText().isEmpty() || job_description.getText().isEmpty() || proposed_price.getText().isEmpty()) {
            alertBox.setAlertType(Alert.AlertType.ERROR);
            alertBox.setContentText("All fields are mandatory!");
            alertBox.show();
        } else {
            String fileName = "image-not-available.jpg";
            if (file != null) {
                Path from = Paths.get(file.toURI());
                Path to = Paths.get(System.getProperty("user.dir") + "/images", file.getName());

                try {
                    Files.copy(from, to);
                    fileName = file.getName();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String new_id = unilink.generateAutoIncrementId("JOB");
            Job newJob = new Job(new_id, job_title.getText(), job_description.getText(), Double.parseDouble(proposed_price.getText()), logged_in_user, fileName);
            unilink.getPostCollection().add(newJob);
            alertBox.setAlertType(Alert.AlertType.INFORMATION);
            alertBox.setContentText("New Job Created! ");
            alertBox.show();
            returnToMainMenu();
        }
    }

    /*
   Function called when user clicks on Upload image
   File chooser opens up , to choose image file to upload
   */
    @FXML
    void uploadImage(ActionEvent event) {

        final FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            Image image1 = new Image(file.toURI().toString());
            job_image.setImage(image1);

        }
    }

    //Function to open up Main Menu window
    public void returnToMainMenu() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/MainMenu.fxml"));

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MainMenuController controller = loader.getController();

        controller.initializeModelAndStage(logged_in_user, primaryStage, unilink);

        primaryStage.setTitle("MainMenu");
        primaryStage.setScene(new Scene(root, 950, 500));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

}
package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Event;
import model.UniLink;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NewEventController {

    @FXML
    private DatePicker event_date;

    @FXML
    private TextField event_title;

    @FXML
    private TextField event_venue;

    @FXML
    private TextField event_capacity;

    @FXML
    private TextArea event_description;

    @FXML
    private Button upload;

    @FXML
    private Button submit;

    @FXML
    private ImageView event_image;

    Alert alertBox = new Alert(Alert.AlertType.NONE);

    Stage primaryStage;
    File file;
    String logged_in_user;

    UniLink unilink;
    public void initializeModelAndStage(Stage primaryStage, UniLink unilink , String logged_in_user)
    {
        this.unilink = unilink;
        this.primaryStage=primaryStage;
        this.logged_in_user = logged_in_user;

    }

    @FXML
    void submitEvent(ActionEvent event) throws IOException {
        alertBox.setAlertType(Alert.AlertType.ERROR);
        try{
            int num = Integer.parseInt(event_capacity.getText());
            if(num<=0)
            {
                alertBox.setContentText("Please enter a positive number for Capacity!");
                alertBox.show();
                return;

            }
            // is an integer!
        } catch (NumberFormatException e) {
            System.out.println("INT");

            alertBox.setContentText("Please enter a valid input for Capacity!");
            alertBox.show();
            return;
        }

       if(event_title.getText().isEmpty() || event_capacity.getText().isEmpty() || event_description.getText().isEmpty() || event_venue.getText().isEmpty() || event_date.getValue() == null)
       {

           alertBox.setAlertType(Alert.AlertType.ERROR);
           alertBox.setContentText("All fields are mandatory!");
           alertBox.show();
       }

       else{
            String fileName = "image-not-available.jpg";
           if(file!=null) {
               Path from = Paths.get(file.toURI());
               Path to = Paths.get(System.getProperty("user.dir") + "/images", file.getName());

               try {
                   Files.copy(from, to);
                   fileName = file.getName();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
            String new_id = unilink.generateAutoIncrementId("EVE");
           Event newEvent = new Event(new_id,event_title.getText(),event_description.getText(),event_venue.getText(),event_date.getValue().toString(),Integer.parseInt(event_capacity.getText()),logged_in_user,fileName);
            unilink.getPostCollection().add(newEvent);
           alertBox.setAlertType(Alert.AlertType.INFORMATION );
           alertBox.setContentText("New Event Created! ");
           alertBox.show();
           returnToMainMenu();



        }



    }

    @FXML
    void uploadImage(ActionEvent event) {

        final FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
        Image image1 = new Image(file.toURI().toString());
        event_image.setImage(image1);

        }



    }
    @FXML
    void onCancel(ActionEvent event) {
        returnToMainMenu();
    }
    public void returnToMainMenu()
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/MainMenu.fxml"));

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MainMenuController controller = loader.getController();

        controller.initializeModelAndStage(logged_in_user,primaryStage,unilink);

        primaryStage.setTitle("MainMenu");
        primaryStage.setScene(new Scene(root, 950, 500));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

}

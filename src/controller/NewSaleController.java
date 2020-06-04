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
import model.Sale;
import model.UniLink;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NewSaleController {

    Alert alertBox = new Alert(Alert.AlertType.NONE);
    Stage primaryStage;
    File file;
    String logged_in_user;
    UniLink unilink;
    @FXML
    private TextField sale_title;
    @FXML
    private TextField asking_price;
    @FXML
    private TextArea sale_description;
    @FXML
    private TextField minimum_raise;
    @FXML
    private Button upload;
    @FXML
    private Button submit;
    @FXML
    private ImageView sale_image;

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
   Function called when Submit Sale is clicked
   Validates Sale details
   If not validated , corresponding error message thrown
   If validated, new Sale Created
  */
    @FXML
    void submitSale(ActionEvent event) {
        alertBox.setAlertType(Alert.AlertType.ERROR);
        try {
            double num = Double.parseDouble(asking_price.getText());
            double num2 = Double.parseDouble(minimum_raise.getText());

            if (num <= 0 || num2 <= 0) {
                alertBox.setContentText("Please enter positive number for Asking price/Minimum raise");
                alertBox.show();
                return;
            }
        } catch (NumberFormatException e) {
            alertBox.setContentText("Please enter valid input for Asking price/Minimum raise");
            alertBox.show();
            return;
        }

        if (sale_title.getText().isEmpty() || sale_description.getText().isEmpty() || minimum_raise.getText().isEmpty() || asking_price.getText().isEmpty()) {
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
            String new_id = unilink.generateAutoIncrementId("SAL");
            Sale newSale = new Sale(new_id, sale_title.getText(), sale_description.getText(), Double.parseDouble(asking_price.getText()), Double.parseDouble(minimum_raise.getText()), logged_in_user, fileName);
            unilink.getPostCollection().add(newSale);
            alertBox.setAlertType(Alert.AlertType.INFORMATION);
            alertBox.setContentText("New Sale Created! ");
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
            sale_image.setImage(image1);

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

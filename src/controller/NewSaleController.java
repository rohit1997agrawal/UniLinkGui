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
import model.exceptions.AskingPriceInvalidException;
import model.exceptions.MinimumRaiseInvalidException;
import model.exceptions.ProposedPriceInvalidException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

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
            try {
                double num = Double.parseDouble(asking_price.getText());
                if (num <= 0) {
                    throw new AskingPriceInvalidException("Please enter a positive number for asking price");
                }
            } catch (NumberFormatException e) {
                throw new AskingPriceInvalidException("Please enter a valid input for asking price");
            }
            try {
                double num2 = Double.parseDouble(minimum_raise.getText());
                if (num2 <= 0) {
                    throw new MinimumRaiseInvalidException("Please enter a positive number for minimum raise");
                }
            } catch (NumberFormatException e) {
                throw new MinimumRaiseInvalidException("Please enter a valid input for minimum raise");
            }

            if (sale_title.getText().isEmpty() || sale_description.getText().isEmpty() || minimum_raise.getText().isEmpty() || asking_price.getText().isEmpty()) {
                alertBox.setAlertType(Alert.AlertType.ERROR);
                alertBox.setContentText("All fields are mandatory!");
                alertBox.show();
            } else {
                String fileName = "no_image.png";
                if (file != null) {
                    Path from = Paths.get(file.toURI());
                    Path to = Paths.get(System.getProperty("user.dir") + "/images", file.getName());
                    Files.copy(from, to,REPLACE_EXISTING);
                    fileName = file.getName();
                }
                String new_id = unilink.generateAutoIncrementId("SAL");
                Sale newSale = new Sale(new_id, sale_title.getText(), sale_description.getText(), Double.parseDouble(asking_price.getText()), Double.parseDouble(minimum_raise.getText()), logged_in_user, fileName);
                unilink.getPostCollection().add(newSale);
                alertBox.setAlertType(Alert.AlertType.INFORMATION);
                alertBox.setContentText("New sale created! ");
                alertBox.show();
                returnToMainMenu();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (AskingPriceInvalidException | MinimumRaiseInvalidException ex) {
            alertBox.setAlertType(Alert.AlertType.ERROR);
            alertBox.setContentText(ex.getMessage());
            alertBox.show();
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
            if (image1.isError()) {
                file = null;
                alertBox.setAlertType(Alert.AlertType.ERROR);
                alertBox.setContentText("Unable to upload image! ");
                alertBox.show();
            }
            else {
                sale_image.setImage(image1);
            }

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
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(new Scene(root, 1000, 500));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

}

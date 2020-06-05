package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;
import model.exceptions.JobOfferInvalidException;
import model.exceptions.SaleOfferInvalidException;

import java.io.IOException;

public class ReplySale {

    Sale objPost;
    UniLink unilink;
    String logged_in_user;
    Alert alertBox = new Alert(Alert.AlertType.NONE);
    @FXML
    private Label reply_post_id;
    @FXML
    private TextField sale_offer;
    @FXML
    private Label highest_offer;
    @FXML
    private Label minimum_raise;
    private Stage primaryStage;

    @FXML
    void closeReply(ActionEvent event) {
        returnToMainMenu();
    }

    /*
        Function called when Submit Reply is clicked
        Validates Reply details
        If not validated , corresponding error message thr0won
        If validated, reply recorded
*/
    @FXML
    void submitReply(ActionEvent event) {
        try {
            alertBox.setAlertType(Alert.AlertType.ERROR);
            double number;
            try {
                number = Double.parseDouble(sale_offer.getText());
            } catch (NumberFormatException e) {
                throw new SaleOfferInvalidException("Please enter valid input for Sale offer");
            }
            if (number <= 0) {
                throw new SaleOfferInvalidException("Please enter positive for Sale offer");
            } else if (number > objPost.getHighest_offer()) {
                if (number >= (objPost.getHighest_offer() + objPost.getMinimum_raise())) {
                    Reply reply = new Reply(objPost.getId(), number, logged_in_user);
                    objPost.handleReply(reply);
                    if (number > objPost.getAsking_price()) {
                        alertBox.setAlertType(Alert.AlertType.INFORMATION);
                        objPost.setStatus("CLOSED");
                        alertBox.setContentText("Item Sold To you!");
                        alertBox.show();
                        returnToMainMenu();
                        return;
                    }
                    alertBox.setAlertType(Alert.AlertType.INFORMATION);
                    alertBox.setContentText("Offer has been recorded");
                    alertBox.show();
                    returnToMainMenu();
                    return;

                } else {
                    throw new SaleOfferInvalidException("Your offer not greater than current highest offer by the set minimum Raise!");
                }
            } else {
                throw new SaleOfferInvalidException("Offer lower than current highest offer!");
            }
        } catch (SaleOfferInvalidException ex) {
            alertBox.setAlertType(Alert.AlertType.ERROR);
            alertBox.setContentText(ex.getMessage());
            alertBox.show();
        }
    }


    //Function to receive and set the stage , and unilink object  logged in user name and corresponding post object
    public void initializeModelAndStage(String logged_in_user, Stage primaryStage, Post post, UniLink unilink) {
        this.unilink = unilink;
        objPost = (Sale) post;
        this.primaryStage = primaryStage;

        this.logged_in_user = logged_in_user;
        reply_post_id.setText(post.getId());
        if (objPost.getHighest_offer() == 0) {
            highest_offer.setText("No Offer");
        } else {
            highest_offer.setText(String.valueOf(objPost.getHighest_offer()));
        }
        minimum_raise.setText(String.valueOf(objPost.getMinimum_raise()));

    }

    //Function to open up Main Menu window
    private void returnToMainMenu() {
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
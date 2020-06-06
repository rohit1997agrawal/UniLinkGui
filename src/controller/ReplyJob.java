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

import java.io.IOException;

public class ReplyJob {

    Job objPost;
    UniLink unilink;
    String logged_in_user;
    Alert alertBox = new Alert(Alert.AlertType.NONE);
    @FXML
    private Label reply_post_id;
    @FXML
    private TextField job_offer;
    @FXML
    private Label lowest_offer;
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
                number = Double.parseDouble(job_offer.getText());
            } catch (NumberFormatException s) {
                throw new JobOfferInvalidException("Please enter a valid input for offer");
            }
            if (number <= 0) {
                throw new JobOfferInvalidException("Please enter a positive number for offer");
            } else if (number == 0.1) {
                Reply reply = new Reply(objPost.getId(), number, logged_in_user);
                objPost.handleReply(reply);
                alertBox.setAlertType(Alert.AlertType.INFORMATION);
                objPost.setStatus("CLOSED");
                alertBox.setContentText("Congratulations! You have received the offer! ");
                alertBox.show();
                returnToMainMenu();
            } else if (number < objPost.getLowest_offer() || objPost.getReplyList().size() == 0) {
                Reply reply = new Reply(objPost.getId(), number, logged_in_user);
                objPost.handleReply(reply);
                alertBox.setAlertType(Alert.AlertType.INFORMATION);
                alertBox.setContentText("Offer has been recorded");
                alertBox.show();
                returnToMainMenu();
            } else {
                throw new JobOfferInvalidException("Offer is higher than the current lowest offer");
            }

        } catch (JobOfferInvalidException ex) {
            alertBox.setAlertType(Alert.AlertType.ERROR);
            alertBox.setContentText(ex.getMessage());
            alertBox.show();

        }
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
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(new Scene(root, 1000, 500));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }


    //Function to receive and set the stage , and unilink object  logged in user name and corresponding post object
    public void initializeModelAndStage(String logged_in_user, Stage primaryStage, Post post, UniLink unilink) {
        this.unilink = unilink;
        objPost = (Job) post;
        this.primaryStage = primaryStage;
        this.logged_in_user = logged_in_user;
        reply_post_id.setText(post.getId());
        if (objPost.getLowest_offer() == 0) {
            lowest_offer.setText("No Offer");
        } else {
            lowest_offer.setText("$ "+String.valueOf(objPost.getLowest_offer()));
        }
    }

}



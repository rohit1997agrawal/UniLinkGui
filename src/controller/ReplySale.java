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

import java.io.IOException;

public class ReplySale {

    @FXML
    private Label reply_post_id;

    @FXML
    private TextField sale_offer;

    @FXML
    private Label highest_offer;

    @FXML
    private Label minimum_raise;

    Sale objPost;
    private Stage primaryStage;

    UniLink unilink;
    String logged_in_user;

    Alert alertBox = new Alert(Alert.AlertType.NONE);

    @FXML
    void closeReply(ActionEvent event) {
        returnToMainMenu();
    }

    @FXML
    void submitReply(ActionEvent event) {

        alertBox.setAlertType(Alert.AlertType.ERROR);
        try {
            double number = Double.parseDouble(sale_offer.getText());
            if(number<=0)
            {
                alertBox.setContentText("Please enter positive for job offer");
                alertBox.show();

            }

            else if (number > objPost.getHighest_offer())
            {
                if(number >= (objPost.getHighest_offer()+ objPost.getMinimum_raise()))
                {
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

                }
                else{
                    alertBox.setContentText("Your offer not greater than current highest offer by the set minimum Raise!");
                    alertBox.show();
                }


            }
            else {
                alertBox.setContentText("Offer lower than current highest offer!");
                alertBox.show();

            }

        }
        catch (NumberFormatException e) {
            //System.out.println("INT");

            alertBox.setContentText("Please enter valid input for Sale offer");
            alertBox.show();

        }


    }

    public void initializeModelAndStage(String logged_in_user , Stage primaryStage , Post post, UniLink unilink)
    {
        this.unilink = unilink;
        objPost = (Sale) post;
        this.primaryStage=primaryStage;

        this.logged_in_user = logged_in_user;
        reply_post_id.setText(post.getId());
        if(objPost.getHighest_offer()==0)
        {
            highest_offer.setText("No Offer");
        }
        else{
            highest_offer.setText(String.valueOf(objPost.getHighest_offer()));
        }
        minimum_raise.setText(String.valueOf(objPost.getMinimum_raise()));

    }

    private void returnToMainMenu() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/MainMenu.fxml"));

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ListViewController controller = loader.getController();


        controller.initializeModelAndStage(logged_in_user,primaryStage,unilink);

        primaryStage.setTitle("MainMenu");
        primaryStage.setScene(new Scene(root, 950, 500));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
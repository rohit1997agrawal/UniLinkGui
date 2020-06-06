package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.*;
import model.exceptions.PostClosedException;

import java.io.File;
import java.io.IOException;

public class ListCellItemController {
    Post object;
    Alert alertBox = new Alert(Alert.AlertType.NONE);
    String logged_in_user;
    @FXML
    private HBox hBox;
    @FXML
    private TextArea post_id;
    @FXML
    private ImageView post_image;
    @FXML
    private TextArea post_title;
    @FXML
    private TextArea post_description;
    @FXML
    private TextArea post_creator_id;
    @FXML
    private TextArea post_status;
    @FXML
    private TextArea post_info_1;
    @FXML
    private TextArea post_info_2;
    @FXML
    private TextArea post_info_3;
    @FXML
    private TextArea post_info_4;
    @FXML
    private Label post_info_1_label;
    @FXML
    private Label post_info_2_label;
    @FXML
    private Label post_info_3_label;
    @FXML
    private Label post_info_4_label;
    @FXML
    private Button reply_button;
    @FXML
    private Button more_details_button;
    private UniLink unilink;
    private Stage primaryStage;

    /*
    Constructed to set the stage, unilink object and current logged in user
     */

    public ListCellItemController(Stage primaryStage, UniLink unilink, String logged_in_user) {
        this.unilink = unilink;
        this.primaryStage = primaryStage;
        this.logged_in_user = logged_in_user;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ListCellItem.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    Function called when user clicks on More details of a post
    More details button only visible for posts created by current logged in user
    More details window opens up
   */
    @FXML
    void handleMoreDetails(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/MoreDetails.fxml"));
            Parent root = loader.load();
            MoreDetailsController controller = loader.getController();
            controller.initializeModelAndStage(logged_in_user, primaryStage, object, unilink);

            primaryStage.setTitle("More Details of Post");
            primaryStage.setScene(new Scene(root, 890, 514));
            primaryStage.centerOnScreen();
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
    Function called when user clicks on Reply button of any post
    Reply button not visible for posts created by current logged in user
    Alert thrown if post is already "CLOSED"
    */
    @FXML
    void handleReply(ActionEvent event) {
        try {
            alertBox.setAlertType(Alert.AlertType.ERROR);
            if (object.getStatus().equals("CLOSED")) {
                throw new PostClosedException("Sorry! Post is closed!");
            }
            if (object instanceof Event) {
                handleReplyEvent(object);
            } else if (object instanceof Sale) {
                handleReplySale(object);
            } else if (object instanceof Job) {
                handleReplyJob(object);
            }
        }
        catch(PostClosedException ex)
        {
            alertBox.setContentText(ex.getMessage());
            alertBox.show();
            return;
        }

    }

    /*
         Function called when user clicks on Reply button of Job
         ReplyJob window opens up
   */
    private void handleReplyJob(Post object) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ReplyJob.fxml"));
            Parent root = loader.load();
            ReplyJob controller = loader.getController();
            controller.initializeModelAndStage(logged_in_user, primaryStage, object, unilink);

            primaryStage.setTitle("Reply to Job");
            primaryStage.setScene(new Scene(root, 300, 275));
            primaryStage.centerOnScreen();
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
       Function called when user clicks on Reply button of Sale
       ReplySale window opens up
    */
    private void handleReplySale(Post object) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ReplySale.fxml"));
            Parent root = loader.load();
            ReplySale controller = loader.getController();
            controller.initializeModelAndStage(logged_in_user, primaryStage, object, unilink);

            primaryStage.setTitle("Reply to Sale");
            primaryStage.setScene(new Scene(root, 300, 275));
            primaryStage.centerOnScreen();
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
       Function called when user clicks on "Join event" button of Event
       Current screen refreshed , after user joins event
    */
    private void handleReplyEvent(Post object) {

        alertBox.setAlertType(Alert.AlertType.ERROR);
        for (Reply iterator : object.getReplyList()) {
            {
                if (iterator.getResponder_id().equals(logged_in_user)) {
                    alertBox.setContentText("You have already joined this event");
                    alertBox.show();
                    return;
                }
            }
        }
        alertBox.setAlertType(Alert.AlertType.INFORMATION);
        Reply reply = new Reply(object.getId(), 1, logged_in_user);
        object.handleReply(reply);
        alertBox.setContentText("You have joined this event");
        alertBox.show();
        refreshMainMenu();
    }

    /*
    Function called to set the details of each listcell according to type of Post
    Object of type Post passed, using which details are set
     */

    public void setInfo(Post object) {
        reply_button.setVisible(false);
        this.object = object;
        File file = new File("images/" + object.getImage_name());
        Image image = new Image(file.toURI().toString());
        if (image.isError()) {
             file = new File("images/no_image.png");
             image = new Image(file.toURI().toString());
        }
        post_image.setImage(image);
        post_id.setText(object.getId());
        post_creator_id.setText(object.getCreator_id());
        post_description.setText(object.getDescription());
        post_status.setText(object.getStatus());
        post_title.setText(object.getTitle());
        if (!object.getCreator_id().equals(logged_in_user)) {
            more_details_button.setVisible(false);
            reply_button.setVisible(true);
        }
        if (object instanceof Event) {
            setEventInfo(object);
        } else if (object instanceof Sale) {
            setSaleInfo(object);
        } else if (object instanceof Job) {
            setJobInfo(object);
        }
    }

    /*
        Function called to set the information related to Event
        Date,Venue, Capacity, Attendee count set
   */
    public void setEventInfo(Post object) {
        Event objPost = (Event) object;
        post_info_1.setText(objPost.getDate());

        post_info_2.setText(objPost.getVenue());
        post_info_1_label.setText("Date");
        post_info_2_label.setText("Venue");
        post_info_3_label.setText("Capacity");
        post_info_3.setText(String.valueOf(objPost.getCapacity()));
        post_info_4.setText(String.valueOf(objPost.getAttendee_count()));
        hBox.setStyle(
                "-fx-background-color: lightPink"
        );
        reply_button.setText("Join Event");

    }

    /*
       Function called to set the information related to Sale
       Minimum raise, highest offer set
    */
    public void setSaleInfo(Post object) {
        Sale objPost = (Sale) object;

        if (objPost.getHighest_offer() == 0) {
            post_info_1.setText("NO OFFER");
        } else {
            post_info_1.setText("$ "+String.valueOf(objPost.getHighest_offer()));
        }
        post_info_2.setText("$ "+String.valueOf(objPost.getMinimum_raise()));
        hBox.setStyle(
                "-fx-background-color: lightCyan"
        );
        post_info_1_label.setText("Highest Offer");
        post_info_2_label.setText("Minimum Raise");
        post_info_3_label.setVisible(false);
        post_info_4_label.setVisible(false);
        post_info_3.setVisible(false);
        post_info_4.setVisible(false);

    }

    /*
      Function called to set the information related to Job
      Proposed price, lowest offer set
  */
    public void setJobInfo(Post object) {
        Job objPost = (Job) object;

        post_info_1.setText("$ "+String.valueOf(objPost.getProposed_price()));
        if (objPost.getLowest_offer() == 0) {
            post_info_2.setText("NO OFFER");
        } else {
            post_info_2.setText("$ "+String.valueOf(objPost.getLowest_offer()));
        }

        hBox.setStyle(
                "-fx-background-color: lightYellow"
        );
        post_info_1_label.setText("Proposed Price");
        post_info_2_label.setText("Lowest Offer");
        post_info_3_label.setVisible(false);
        post_info_4_label.setVisible(false);
        post_info_3.setVisible(false);
        post_info_4.setVisible(false);

    }

    //Function called to Refresh current window , after user has joined an event
    public void refreshMainMenu() {
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

    public HBox getBox() {
        return hBox;
    }
}
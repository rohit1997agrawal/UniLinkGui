package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.*;

import java.io.File;
import java.io.IOException;

public class Data
{
    @FXML
    private HBox hBox;
    @FXML
    private Label post_id;
    @FXML
    private ImageView post_image;

    @FXML
    private Label post_title;

    @FXML
    private Label post_description;

    @FXML
    private Label post_creator_id;

    @FXML
    private Label post_status;

    @FXML
    private Label post_info_1;

    @FXML
    private Label post_info_2;

    @FXML
    private Label post_info_3;


    @FXML
    private Label post_info_4;

    @FXML
    private Button reply_button;

    @FXML
    private Button more_details_button;



    Post object;
    Alert alertBox = new Alert(Alert.AlertType.NONE);

    String logged_in_user;
    private UniLink unilink;
    private Stage primaryStage;

    public void initializeModelAndStage(Stage primaryStage, UniLink unilink ,String logged_in_user)
    {


    }


    @FXML
    void handleMoreDetails(ActionEvent event) {

    }

    @FXML
    void handleReply(ActionEvent event) {

        alertBox.setAlertType(Alert.AlertType.ERROR);

        if (object.getStatus().equals("CLOSED"))
        {
            alertBox.setContentText("Sorry! Post is closed!");
            alertBox.show();
            return;
        }

        if(object instanceof Event)
        {
            handleReplyEvent(object);
        }
        else if(object instanceof Sale)
        {
            handleReplySale(object);
        }
        else if(object instanceof Job)
        {
            handleReplyJob(object);
        }

    }

    private void handleReplyJob(Post object) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ReplyJob.fxml"));
            Parent root = loader.load();
            ReplyJob controller = loader.getController();
            controller.initializeModelAndStage(logged_in_user,primaryStage,object,unilink);

            primaryStage.setTitle("Reply to Job");
            primaryStage.setScene(new Scene(root, 300, 275));
            primaryStage.centerOnScreen();
            primaryStage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleReplySale(Post object) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ReplySale.fxml"));
            Parent root = loader.load();
            ReplySale controller = loader.getController();
            controller.initializeModelAndStage(logged_in_user,primaryStage,object,unilink);

            primaryStage.setTitle("Reply to Sale");
            primaryStage.setScene(new Scene(root, 300, 275));
            primaryStage.centerOnScreen();
            primaryStage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleReplyEvent(Post object) {

        alertBox.setAlertType(Alert.AlertType.ERROR);
        for (Reply iterator : object.getReplyList()) {
            {
                if (iterator.getResponder_id().equals(logged_in_user)) {
                    alertBox.setContentText("User already part of the Event!");
                    alertBox.show();
                    return;
                }

            }

        }
        alertBox.setAlertType(Alert.AlertType.INFORMATION);
        Reply reply = new Reply(object.getId(),1,logged_in_user);
        object.handleReply(reply);
        alertBox.setContentText("You have joined the event!");
        alertBox.show();
        refreshMainMenu();
    }


    public Data(Stage primaryStage, UniLink unilink ,String logged_in_user)
    {
        this.unilink = unilink;
        this.primaryStage=primaryStage;
        this.logged_in_user = logged_in_user;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/listCellItem.fxml"));
        fxmlLoader.setController(this);
        try
        {
            fxmlLoader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void setInfo(Post object)
    {
        reply_button.setVisible(false);
        this.object = object;
        File file = new File("images/"+object.getImage_name());
        Image image = new Image(file.toURI().toString());
        System.out.println(file.toURI().toString());
        post_image.setImage(image);
        post_id.setText(object.getId());
        post_creator_id.setText(object.getCreator_id());
        post_description.setText(object.getDescription());
        post_status.setText(object.getStatus());
        post_title.setText(object.getTitle());
        if(!object.getCreator_id().equals(logged_in_user))
        {
            System.out.println(object.getCreator_id() + "   "+logged_in_user);
            more_details_button.setVisible(false);
            reply_button.setVisible(true);
        }
        if(object instanceof Event)
        {
           setEventInfo(object);
        }
        else if(object instanceof Sale)
        {
            setSaleInfo(object);
        }
        else if(object instanceof Job)
        {
            setJobInfo(object);
        }
    }

    public void setEventInfo(Post object)
    {
        Event objPost = (Event) object;
        post_info_1.setText(objPost.getDate());
        post_info_2.setText(objPost.getVenue());
        post_info_3.setText(String.valueOf(objPost.getCapacity()));
        post_info_4.setText(String.valueOf(objPost.getAttendee_count()));
        hBox.setStyle(
                "-fx-background-color: lightPink"
        );
        reply_button.setText("Join Event");

    }
    public void setSaleInfo(Post object)
    {
        Sale objPost = (Sale) object;

        if(objPost.getHighest_offer()==0)
        {
            post_info_1.setText("NO OFFER");
        }
        else {
            post_info_1.setText(String.valueOf(objPost.getHighest_offer()));
        }
        post_info_2.setText(String.valueOf(objPost.getMinimum_raise()));
        hBox.setStyle(
                "-fx-background-color: lightCyan"
        );
        post_info_3.setStyle("-fx-border-width : 0");
        post_info_4.setStyle("-fx-border-width : 0");

    }
    public void setJobInfo(Post object)
    {
        Job objPost = (Job) object;

        post_info_1.setText(String.valueOf(objPost.getProposed_price()));
        if(objPost.getLowest_offer()==0)
        {
            post_info_2.setText("NO OFFER");
        }
        else {
            post_info_2.setText(String.valueOf(objPost.getLowest_offer()));
        }

        hBox.setStyle(
                "-fx-background-color: lightYellow"
        );
        post_info_3.setStyle("-fx-border-width : 0");
        post_info_4.setStyle("-fx-border-width : 0");

    }
    public void refreshMainMenu()
    {
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

    public HBox getBox()
    {
        return hBox;
    }
}
package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Optional;

public class MoreDetailsController {

    @FXML
    private ImageView post_image;

    @FXML
    private TextField post_id;

    @FXML
    private TextField post_creator_id;

    @FXML
    private TextArea post_title;

    @FXML
    private TextArea post_description;

    @FXML
    private TextField post_status;

    @FXML
    private Label post_info_1_label;

    @FXML
    private TextField post_info_1;

    @FXML
    private Label post_info_2_label;

    @FXML
    private TextField post_info_2;

    @FXML
    private TextField post_info_3;

    @FXML
    private Label post_info_3_label;

    @FXML
    private TextField post_info_4;

    @FXML
    private Label post_info_4_label;

    @FXML
    private TableView<Reply> replyTable;

    @FXML
    private TableColumn<Reply, String> responsder_id;

    @FXML
    private TableColumn<Reply, Double> responser_response;

    Alert alertBox = new Alert(Alert.AlertType.NONE);

    Post objPost;
    File file;

    String logged_in_user;
    Stage primaryStage;
    UniLink unilink;

    @FXML
    void closePost(ActionEvent event) {
        if(objPost.getStatus().equals("CLOSED"))
        {
            alertBox.setAlertType(Alert.AlertType.ERROR);
            alertBox.setContentText("Post is closed! No action allowed");
            alertBox.show();
        }
        else {


            objPost.setStatus("CLOSED");
            alertBox.setAlertType(Alert.AlertType.INFORMATION);
            alertBox.setContentText("Post closed successfully! ");
            alertBox.show();
            refreshCurrentView();
        }
    }

    @FXML
    void deletePost(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delet the post?",ButtonType.YES,ButtonType.NO);
        alert.setTitle("Delete Post");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.YES) {
            Iterator<Post> iterator = unilink.getPostCollection().iterator();  //Using an iterator to remove/delete the post
            while (iterator.hasNext()) {
                Post currentPost = iterator.next();
                System.out.println(currentPost.getId());

                if (currentPost.getId().equals(objPost.getId())) //To get the Post with respect to the entered Post id
                {
                    iterator.remove();
                    alertBox.setAlertType(Alert.AlertType.INFORMATION);
                    alertBox.setContentText("Post Deleted! ");
                    alertBox.show();
                    returnToMainMenu();

                }
            }

        }
//        alert.showAndWait().ifPresent(type -> {
//            if (type == ButtonType.YES) {
//                Iterator<Post> iterator = unilink.getPostCollection().iterator();  //Using an iterator to remove/delete the post
//                while (iterator.hasNext()) {
//                    Post currentPost = iterator.next();
//                    System.out.println(currentPost.getId());
//
//                    if (currentPost.getId().equals(objPost.getId())) //To get the Post with respect to the entered Post id
//                    {
//                        iterator.remove();
//                        alertBox.setAlertType(Alert.AlertType.INFORMATION);
//                        alertBox.setContentText("Post Deleted! ");
//                        alertBox.show();
//                        returnToMainMenu();
//
//                    }
//                }
//
//
//            } else if (type == ButtonType.NO) {
//
//                refreshCurrentView();
//
//            } else {
//                refreshCurrentView();
//            }
//        });

    }

    @FXML
    void returnToMainMenu(ActionEvent event) {
        returnToMainMenu();
    }

    @FXML
    void savePost(ActionEvent event) {
        alertBox.setAlertType(Alert.AlertType.ERROR);
        if(objPost.getStatus().equals("CLOSED"))
        {

            alertBox.setContentText("Post is closed! No action allowed");
            alertBox.show();
        }
        else if(objPost.getReplyList().size()>0) {
            alertBox.setAlertType(Alert.AlertType.ERROR);
            alertBox.setContentText("You can not edit any details! People have responded");
            alertBox.show();
        }
        else{
            if(objPost instanceof Event)
            {
                editEventInfo();
            }
            else if(objPost instanceof Sale)
            {
                editSaleInfo();
            }
            else if(objPost instanceof Job)
            {
                editJobInfo();
            }
        }

    }

    @FXML
    void uploadImage(ActionEvent event) {
        alertBox.setAlertType(Alert.AlertType.ERROR);
        if(objPost.getStatus().equals("CLOSED"))
        {

            alertBox.setContentText("Post is closed! No action allowed");
            alertBox.show();
        }
        else if(objPost.getReplyList().size()>0) {

            alertBox.setContentText("You can not upload new image! People have responded");
            alertBox.show();
        }
        else{
            final FileChooser fileChooser = new FileChooser();
            file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                Image image1 = new Image(file.toURI().toString());
                post_image.setImage(image1);

            }
        }


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


    public void initializeModelAndStage(String logged_in_user , Stage primaryStage , Post post, UniLink unilink)
    {
        this.unilink = unilink;
        this.objPost = post;
        this.primaryStage=primaryStage;
        this.logged_in_user = logged_in_user;

        setInfo(objPost);
        responsder_id.setCellValueFactory(new PropertyValueFactory<Reply, String>("responder_id"));
        responser_response.setCellValueFactory(new PropertyValueFactory<Reply, Double>("value"));
        ObservableList<Reply> data = FXCollections.<Reply>observableArrayList();
        data.addAll(objPost.getReplyList());

        replyTable.setItems(data);


    }

    public void setInfo(Post object)
    {
        File file = new File("images/"+object.getImage_name());
        Image image = new Image(file.toURI().toString());
        System.out.println(file.toURI().toString());
        post_image.setImage(image);
        post_id.setText(object.getId());
        post_creator_id.setText(object.getCreator_id());
        post_description.setText(object.getDescription());
        post_status.setText(object.getStatus());
        post_title.setText(object.getTitle());

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

    public void editEventInfo() {
        alertBox.setAlertType(Alert.AlertType.ERROR);
        int num;
        try {
            num = Integer.parseInt(post_info_3.getText());
            if (num <= 0) {
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

        if (post_title.getText().isEmpty() || post_info_3.getText().isEmpty() || post_description.getText().isEmpty() || post_info_1.getText().isEmpty() || post_info_2.getText().isEmpty()) {

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
//            String new_id = unilink.generateAutoIncrementId("EVE");
            //Event newEvent = new Event(new_id,event_title.getText(),event_description.getText(),event_venue.getText(),event_date.getValue().toString(),Integer.parseInt(event_capacity.getText()),logged_in_user,fileName);
            //unilink.getPostCollection().add(newEvent);
            Event objEvent = (Event) objPost;
            objEvent.setTitle(post_title.getText());
            objEvent.setDescription(post_description.getText());
            objEvent.setVenue(post_info_1.getText());
            objEvent.setDate(post_info_2.getText());
            objEvent.setImage_name(fileName);
            objEvent.setCapacity(num);


            alertBox.setAlertType(Alert.AlertType.INFORMATION);
            alertBox.setContentText("Event Details updated!");
            alertBox.show();
            refreshCurrentView();
        }
    }

    public void editSaleInfo() {
        alertBox.setAlertType(Alert.AlertType.ERROR);
        double num, num2;
        try {
             num = Double.parseDouble(post_info_1.getText());
             num2 = Double.parseDouble(post_info_2.getText());

            if (num <= 0 || num2 <= 0) {
                alertBox.setContentText("Please enter positive number for Asking price/Minimum raise");
                alertBox.show();
                return;
            }
            // is an integer!
        } catch (NumberFormatException e) {
            //System.out.println("INT");

            alertBox.setContentText("Please enter valid input for Asking price/Minimum raise");
            alertBox.show();
            return;
        }

        if (post_title.getText().isEmpty() || post_description.getText().isEmpty() || post_info_2.getText().isEmpty() || post_info_1.getText().isEmpty()) {

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
            //String new_id = unilink.generateAutoIncrementId("SAL");
           // Sale newSale = new Sale(new_id, sale_title.getText(), sale_description.getText(), Double.parseDouble(asking_price.getText()), Double.parseDouble(minimum_raise.getText()), logged_in_user, fileName);
          //  unilink.getPostCollection().add(newSale);
            alertBox.setAlertType(Alert.AlertType.INFORMATION);
            Sale objSale = (Sale) objPost;
            objSale.setTitle(post_title.getText());
            objSale.setDescription(post_description.getText());
            objSale.setAsking_price(num);
            objSale.setMinimum_raise(num2);
            objSale.setImage_name(fileName);

            alertBox.setContentText("Sale details updated! ");
            alertBox.show();
            refreshCurrentView();
        }
    }

    public void editJobInfo()
    {
        alertBox.setAlertType(Alert.AlertType.ERROR);
        double num;
        try{
             num = Double.parseDouble(post_info_1.getText());
            if(num<=0)
            {
                alertBox.setContentText("Please enter a positive number for proposed price!");
                alertBox.show();
                return;
            }
            // is an integer!
        } catch (NumberFormatException e) {
            //System.out.println("INT");

            alertBox.setContentText("Please enter valid input for Proposed price");
            alertBox.show();
            return;
        }

        if(post_title.getText().isEmpty()  || post_description.getText().isEmpty() || post_info_1.getText().isEmpty() )
        {

            alertBox.setAlertType(Alert.AlertType.ERROR);
            alertBox.setContentText("All fields are mandatory!");
            alertBox.show();
        }

        else {
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
            //String new_id = unilink.generateAutoIncrementId("JOB");
            // Job newJob = new Job(new_id,job_title.getText(),job_description.getText(),Double.parseDouble(proposed_price.getText()),logged_in_user,fileName);
            //   unilink.getPostCollection().add(newJob);
            alertBox.setAlertType(Alert.AlertType.INFORMATION);
            Job objJob = (Job) objPost;
            objJob.setTitle(post_title.getText());
            objJob.setDescription(post_description.getText());
            objJob.setProposed_price(num);
            objJob.setImage_name(fileName);

            alertBox.setContentText("Job details updated! ");
            alertBox.show();
            refreshCurrentView();
        }
    }
    public void setEventInfo(Post object)
    {
        Event objPost = (Event) object;

        post_info_1_label.setText("Date");
        post_info_2_label.setText("Venue");
        post_info_3_label.setText("Capacity");
        post_info_4_label.setText("Attendee Count");
        post_info_1.setText(objPost.getDate());
        post_info_2.setText(objPost.getVenue());
        post_info_3.setText(String.valueOf(objPost.getCapacity()));
        post_info_4.setText(String.valueOf(objPost.getAttendee_count()));

        post_info_4.setEditable(false);
        if(object.getReplyList().size()>0)
        {
            post_title.setEditable(false);
            post_description.setEditable(false);
            post_info_1.setEditable(false);
            post_info_2.setEditable(false);
            post_info_3.setEditable(false);
        }


    }
    public void setSaleInfo(Post object)
    {
        Sale objPost = (Sale) object;

        post_info_1_label.setText("Asking Price");
        post_info_2_label.setText("Minimum Raise");
        post_info_3_label.setText("Highest offer");
        if(objPost.getHighest_offer()==0)
        {
            post_info_3.setText("NO OFFER");
        }
        else {
            post_info_3.setText(String.valueOf(objPost.getHighest_offer()));
        }
        post_info_1.setText(String.valueOf(objPost.getAsking_price()));
        post_info_2.setText(String.valueOf(objPost.getMinimum_raise()));
        post_info_3.setEditable(false);
        post_info_4.setVisible(false);
        post_info_4_label.setVisible(false);
        if(object.getReplyList().size()>0)
        {
            post_title.setEditable(false);
            post_description.setEditable(false);
            post_info_1.setEditable(false);
            post_info_2.setEditable(false);
        }



    }
    public void setJobInfo(Post object)
    {
        Job objPost = (Job) object;

        post_info_1_label.setText("Proposed Price");
        post_info_2_label.setText("Lowest Offer");

        post_info_1.setText(String.valueOf(objPost.getProposed_price()));
        if(objPost.getLowest_offer()==0)
        {
            post_info_2.setText("NO OFFER");
        }
        else {
            post_info_2.setText(String.valueOf(objPost.getLowest_offer()));
        }
        post_info_2.setEditable(false);

        post_info_3.setVisible(false);
        post_info_3_label.setVisible(false);

        post_info_4.setVisible(false);
        post_info_4_label.setVisible(false);
        if(object.getReplyList().size()>0)
        {
            post_title.setEditable(false);
            post_description.setEditable(false);
            post_info_1.setEditable(false);
        }



    }


    public void refreshCurrentView()
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/MoreDetails.fxml"));
            Parent root = loader.load();
            MoreDetailsController controller = loader.getController();
            controller.initializeModelAndStage(logged_in_user,primaryStage,objPost,unilink);

            primaryStage.setTitle("More Details of Post");
            primaryStage.setScene(new Scene(root, 716, 480));
            primaryStage.centerOnScreen();
            primaryStage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}

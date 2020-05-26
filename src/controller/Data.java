package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import model.Event;
import model.Job;
import model.Post;
import model.Sale;

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

    public Data()
    {
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
    }
    public void setSaleInfo(Post object)
    {
        Sale objPost = (Sale) object;

        post_info_1.setText(String.valueOf(objPost.getHighest_offer()));
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
        post_info_2.setText(String.valueOf(objPost.getLowest_offer()));
        hBox.setStyle(
                "-fx-background-color: lightYellow"
        );
        post_info_3.setStyle("-fx-border-width : 0");
        post_info_4.setStyle("-fx-border-width : 0");

    }

    public HBox getBox()
    {
        return hBox;
    }
}
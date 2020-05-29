package controller;

import javafx.scene.control.ListCell;
import javafx.stage.Stage;
import model.Post;
import model.UniLink;

public class ListViewCell extends ListCell<Post>
{

    String logged_in_user;
    private UniLink unilink;
    private Stage primaryStage;

    public ListViewCell(Stage primaryStage, UniLink unilink, String logged_in_user) {
        this.unilink = unilink;
        this.primaryStage=primaryStage;

        this.logged_in_user = logged_in_user;
    }


    @Override
    public void updateItem(Post object, boolean empty)
    {
        super.updateItem(object,empty);
        System.out.println("SOP");
        System.out.println(logged_in_user);
        if(object != null && !empty)
        {
            Data data = new Data(primaryStage,unilink,logged_in_user);

            data.setInfo(object);
            setGraphic(data.getBox());
        }
    }
}
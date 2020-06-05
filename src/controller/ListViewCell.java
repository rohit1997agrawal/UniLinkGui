package controller;

import javafx.scene.control.ListCell;
import javafx.stage.Stage;
import model.Post;
import model.UniLink;

public class ListViewCell extends ListCell<Post> {

    String logged_in_user;
    private UniLink unilink;
    private Stage primaryStage;

    public ListViewCell(Stage primaryStage, UniLink unilink, String logged_in_user) {
        this.unilink = unilink;
        this.primaryStage = primaryStage;
        this.logged_in_user = logged_in_user;
    }


    //Function called to update each List view Cell , according to Event type
    @Override
    public void updateItem(Post object, boolean empty) {
        super.updateItem(object, empty);
        if (object != null && !empty) {
            //List Cell item controller called , which renders each list cell and modifies listcell according to event type
            ListCellItemController data = new ListCellItemController(primaryStage, unilink, logged_in_user);

            data.setInfo(object);
            //setGraphic() method present in the  ListCell() , will set the items of the ListCell
            setGraphic(data.getBox());
        }
    }
}
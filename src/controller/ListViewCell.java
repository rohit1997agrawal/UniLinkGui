package controller;

import javafx.scene.control.ListCell;
import model.Post;

public class ListViewCell extends ListCell<Post>
{
    @Override
    public void updateItem(Post object, boolean empty)
    {
        super.updateItem(object,empty);
        if(object != null && !empty)
        {
            Data data = new Data();
            data.setInfo(object.getTitle());
            setGraphic(data.getBox());
        }
    }
}
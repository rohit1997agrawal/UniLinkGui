package view;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Post;

 class CustomListCell extends ListCell<Post> {
    private HBox content;
    private Text name;
    private Text price;

    public CustomListCell() {
        super();
        name = new Text();
        price = new Text();
        VBox vBox = new VBox(name, price);
        content = new HBox(new Label("[Graphic]"), vBox);
        content.setSpacing(10);
    }


}

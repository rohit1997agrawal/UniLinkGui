package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.Post;
import model.UniLink;


public class MenuController {
    @FXML
    private ImageView post_image;

    @FXML
    private TextField post_id;

    @FXML
    private TextField post_title;

    @FXML
    private TextField post_description;

    @FXML
    private TextField post_creator_id;

    @FXML
    private TextField post_status;

    @FXML
    private TextField post_info_1;

    @FXML
    private TextField post_info_2;

    @FXML
    private TextField post_info_3;


    @FXML
    public void initialize() {
        System.out.println("second");
        ObservableList<Post> data = FXCollections.observableArrayList();
        data.addAll(UniLink.getPostCollection());
        final ListView<Post> listView = new ListView<Post>(data);
        listView.setCellFactory(new Callback<ListView<Post>, ListCell<Post>>() {
            @Override
            public ListCell<Post> call(ListView<Post> listView) {
                return new CustomListCell();
            }
        });

    }
    private class CustomListCell extends ListCell<Post> {
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

        @Override
        protected void updateItem(Post item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null && !empty) { // <== test for null item and empty parameter
                name.setText(item.getTitle());
                price.setText(String.format("%d $", item.getDescription()));
                setGraphic(content);
            } else {
                setGraphic(null);
            }
        }
    }
}



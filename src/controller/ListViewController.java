package controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.util.Callback;
import model.Post;
import model.UniLink;

import java.io.IOException;
import java.util.Set;

public class ListViewController {
    @FXML
    private ListView<Post> listView;
    private Set<Post> postSet;
    ObservableList<Post> observableList = FXCollections.observableArrayList();

    public ListViewController()
    {
////        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainMenu.fxml"));
////        fxmlLoader.setController(this);
//        try
//        {
//            Parent parent = (Parent)fxmlLoader.load();
//            Scene scene = new Scene(parent, 400.0 ,500.0);
//        }
//        catch (IOException e)
//        {
//            throw new RuntimeException(e);
//        }
    }
    @FXML
    void initialize() {
        assert listView != null : "fx:id=\"listView\" was not injected: check your FXML file 'CustomList.fxml'.";

        setListView();
    }

    public void setListView()
    {

        observableList.setAll(UniLink.getPostCollection());
        listView.setItems(observableList);
        listView.setCellFactory(new Callback<ListView<Post>, javafx.scene.control.ListCell<Post>>()
        {


            @Override
            public ListCell<Post> call(ListView<Post> listView)
            {
                return new ListViewCell();
            }
        });
    }

}

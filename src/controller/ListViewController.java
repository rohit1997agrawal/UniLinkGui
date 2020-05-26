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
import java.util.stream.Collectors;

public class ListViewController {
    @FXML
    private ListView<Post> listView;

    @FXML
    private ChoiceBox<String> filter_type;

    @FXML
    private ChoiceBox<String> filter_status;

    @FXML
    private ChoiceBox<String> filter_creator;

    private Set<Post> postSet;
    ObservableList<Post> observableList = FXCollections.observableArrayList();
    ObservableList<String> availableTypes = FXCollections.observableArrayList("All","Event", "Sale","Job");
    ObservableList<String> availableStatus = FXCollections.observableArrayList("All","Open","Closed");
    ObservableList<String> availableCreator = FXCollections.observableArrayList("All","My Posts");


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

        setChoiceBox();
        setListView(UniLink.getPostCollection());
    }
    public void setChoiceBox() {
        filter_type.setItems(availableTypes);
        filter_type.getSelectionModel().select("All");
        filter_status.setItems(availableStatus);
        filter_status.getSelectionModel().select("All");
        filter_creator.setItems(availableCreator);
        filter_creator.getSelectionModel().select("All");


        filter_type.setOnAction((event) -> {
          String selectedType = filter_type.getSelectionModel().getSelectedItem();
            Set<Post> selectedTypeCollection = UniLink.getPostCollection().stream()
                    .filter(p -> p.getId().startsWith(selectedType.toUpperCase().substring(0,3))).collect(Collectors.toSet());
            setListView(selectedTypeCollection);
        });


        filter_creator.setOnAction((event) -> {
            String selectedPerson = filter_creator.getSelectionModel().getSelectedItem();
            System.out.println("ComboBox Action (selected: " + selectedPerson + ")");
        });

        filter_status.setOnAction((event) -> {
            String selectedPerson = filter_status.getSelectionModel().getSelectedItem();
            System.out.println("ComboBox Action (selected: " + selectedPerson + ")");
        });
    }



    public void setListView(Set<Post> postCollection)
    {



        observableList.setAll(postCollection);
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

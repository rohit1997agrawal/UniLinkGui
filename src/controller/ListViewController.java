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

    @FXML
    private Label logged_in_user;

    private Set<Post> postCollection;

  // private  String logged_in_user;

    ObservableList<Post> observableList = FXCollections.observableArrayList();
    ObservableList<String> availableTypes = FXCollections.observableArrayList("All","Event", "Sale","Job");
    ObservableList<String> availableStatus = FXCollections.observableArrayList("All","Open","Closed");
    ObservableList<String> availableCreator = FXCollections.observableArrayList("All","My Posts");


    public void setLoggedInUser(String username)
    {
        logged_in_user.setText(username);
        System.out.println(logged_in_user);

    }
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
        postCollection = UniLink.getPostCollection();
        setChoiceBox();
        setListView(postCollection);

    }
    public void setChoiceBox() {
        filter_type.setItems(availableTypes);
        filter_type.getSelectionModel().select("All");
        filter_status.setItems(availableStatus);
        filter_status.getSelectionModel().select("All");
        filter_creator.setItems(availableCreator);
        filter_creator.getSelectionModel().select("All");
        filter_type.setOnAction((event) -> {
                    setFilter();
        });
        filter_creator.setOnAction((event) -> {
                    setFilter();
        });
        filter_status.setOnAction((event) -> {
                    setFilter();
        });


    }

    public void setFilter()
    {
            String selectedType = filter_type.getSelectionModel().getSelectedItem();
            String selectedCreator = filter_creator.getSelectionModel().getSelectedItem();
            String selectedStatus = filter_status.getSelectionModel().getSelectedItem();
            filterTypeHandler(selectedType,selectedCreator,selectedStatus);
    }

    public void filterTypeHandler(String selectedType , String selectedCreator, String selectedStatus)
    {
        Set<Post> filteredPostCollection;
        if(selectedType.equals("All") && selectedCreator.equals("All") && selectedStatus.equals("All"))
        {
            setListView(postCollection);
        }
        else if(selectedCreator.equals("All") && selectedStatus.equals("All"))
        {
         filteredPostCollection = postCollection.stream()
                    .filter(p -> p.getId().startsWith(selectedType.toUpperCase().substring(0, 3))).collect(Collectors.toSet());
            setListView(filteredPostCollection);
        }
        else if((selectedType.equals("All") && selectedCreator.equals("All")))
        {
            filteredPostCollection = postCollection.stream()
                    .filter(p -> p.getStatus().equals(selectedStatus.toUpperCase())).collect(Collectors.toSet());
            setListView(filteredPostCollection);
        }
        else if (selectedType.equals("All") && selectedStatus.equals("All"))
        {
            filteredPostCollection = postCollection.stream()
                    .filter(p -> p.getCreator_id().equals(logged_in_user.getText())).collect(Collectors.toSet());
            setListView(filteredPostCollection);
        }
        else if(selectedStatus.equals("All"))
        {
           filteredPostCollection = postCollection.stream()
                    .filter(p -> p.getCreator_id().equals(logged_in_user.getText()) && p.getId().startsWith(selectedType.toUpperCase().substring(0, 3))).collect(Collectors.toSet());
            setListView(filteredPostCollection);
        }
        else if(selectedCreator.equals("All"))
        {
           filteredPostCollection = postCollection.stream()
                    .filter(p ->  p.getStatus().equals(selectedStatus.toUpperCase()) && p.getId().startsWith(selectedType.toUpperCase().substring(0, 3))).collect(Collectors.toSet());
            setListView(filteredPostCollection);
        }
        else if(selectedType.equals("All"))
        {
            filteredPostCollection = postCollection.stream()
                    .filter(p ->  p.getStatus().equals(selectedStatus.toUpperCase()) && p.getCreator_id().equals(logged_in_user.getText())).collect(Collectors.toSet());
            setListView(filteredPostCollection);
        }
        else{
             filteredPostCollection = postCollection.stream()
                    .filter(p -> p.getCreator_id().equals(logged_in_user.getText()) && p.getId().startsWith(selectedType.toUpperCase().substring(0, 3)) &&  p.getStatus().equals(selectedStatus.toUpperCase())).collect(Collectors.toSet());
            setListView(filteredPostCollection);
        }
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

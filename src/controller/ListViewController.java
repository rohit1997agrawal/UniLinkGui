package controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import model.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
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

    @FXML
    private Button logout;

    Stage primaryStage;
    UniLink unilink;
    File file;

    DirectoryChooser directoryChooser = new DirectoryChooser();
    Alert alertBox = new Alert(Alert.AlertType.NONE);



    @FXML
    void newEvent(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/NewEvent.fxml"));

        Parent root = loader.load();
        NewEventController controller = loader.getController();
        controller.initializeModelAndStage(primaryStage,unilink,logged_in_user.getText());
        primaryStage.setTitle("New Event ");
        primaryStage.setScene(new Scene(root, 600  , 400));
        primaryStage.centerOnScreen();
        primaryStage.show();

    }


    @FXML
    void newJob(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/NewJob.fxml"));

        Parent root = loader.load();
        NewJobController controller = loader.getController();
        controller.initializeModelAndStage(primaryStage,unilink,logged_in_user.getText());
        primaryStage.setTitle("New Job");
        primaryStage.setScene(new Scene(root, 600  , 400));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    @FXML
    void newSale(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/NewSale.fxml"));

        Parent root = loader.load();
        NewSaleController controller = loader.getController();
        controller.initializeModelAndStage(primaryStage,unilink,logged_in_user.getText());
        primaryStage.setTitle("New Sale");
        primaryStage.setScene(new Scene(root, 600  , 400));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    @FXML
    void logOut(ActionEvent event) {
//        Stage loginStage = (Stage) logout.getScene().getWindow();
//        loginStage.close();

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/Login.fxml"));
            Parent root = loader.load();
            MainController controller = loader.getController();
            controller.initializeModelAndStage(primaryStage, unilink);
            primaryStage.setTitle("Login");
            primaryStage.setScene(new Scene(root, 300, 275));
            primaryStage.centerOnScreen();
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void exportData(ActionEvent event) {
            directoryChooser.setInitialDirectory(new File("src"));
            File selectedDirectory = directoryChooser.showDialog(primaryStage);
            System.out.println(selectedDirectory.getAbsolutePath());
            FileHandling obj = new FileHandling();
            if(obj.exportData(selectedDirectory.getAbsolutePath(),unilink))
            {
                alertBox.setAlertType(Alert.AlertType.INFORMATION);
                alertBox.setContentText("Data exported successfully to "+selectedDirectory.getAbsolutePath());
            }
            else{
                alertBox.setAlertType(Alert.AlertType.ERROR);
                alertBox.setContentText("Error while exporting");
            }
        alertBox.show();


    }

    @FXML
    void importData(ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            System.out.println(file.getPath());
            FileHandling obj = new FileHandling();
            if(obj.importData(file.getPath(),unilink))
            {
                alertBox.setAlertType(Alert.AlertType.INFORMATION);
                alertBox.setContentText("Data imported successfully");
                refreshMainMenu();

            }
            else{
                alertBox.setAlertType(Alert.AlertType.ERROR);
                alertBox.setContentText("Error while importing");
            }
            alertBox.show();


        }
    }

    @FXML
    void quitApp(ActionEvent event) {

        primaryStage.close();
    }


    private Set<Post> postCollection;



    ObservableList<Post> observableList = FXCollections.observableArrayList();
    ObservableList<String> availableTypes = FXCollections.observableArrayList("All","Event", "Sale","Job");
    ObservableList<String> availableStatus = FXCollections.observableArrayList("All","Open","Closed");
    ObservableList<String> availableCreator = FXCollections.observableArrayList("All","My Posts");


    public void initializeModelAndStage(String username , Stage primaryStage , UniLink unilink)
    {
        this.unilink = unilink;
        this.primaryStage=primaryStage;
        logged_in_user.setText(username);
        System.out.println(logged_in_user);

        postCollection =  unilink.getPostCollection();

        setChoiceBox();
        setListView(postCollection);



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
        System.out.println("HELLO");
      //  System.out.println(unilink.getPostCollection().size());
        assert listView != null : "fx:id=\"listView\" was not injected: check your FXML file 'CustomList.fxml'.";
//        postCollection =  unilink.getPostCollection();
//
//        setChoiceBox();
//        setListView(postCollection);

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
                return new ListViewCell(primaryStage,unilink,logged_in_user.getText());
            }
        });
    }

//    public void handleCloseWindow()
//    {
//        InitializeTables obj = new InitializeTables();
//        obj.writeDatabase();
//    }

    public void refreshMainMenu()
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/MainMenu.fxml"));

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ListViewController controller = loader.getController();

        controller.initializeModelAndStage(logged_in_user.getText(),primaryStage,unilink);

        primaryStage.setTitle("MainMenu");
        primaryStage.setScene(new Scene(root, 950, 500));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

}

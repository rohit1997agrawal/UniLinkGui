package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.*;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public class MainMenuController {
    Stage primaryStage;
    UniLink unilink;
    File file;
    DirectoryChooser directoryChooser = new DirectoryChooser();
    Alert alertBox = new Alert(Alert.AlertType.NONE);
    ObservableList<Post> observableList = FXCollections.observableArrayList();
    ObservableList<String> availableTypes = FXCollections.observableArrayList("All", "Event", "Sale", "Job");
    ObservableList<String> availableStatus = FXCollections.observableArrayList("All", "Open", "Closed");
    ObservableList<String> availableCreator = FXCollections.observableArrayList("All", "My Posts");
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
    private Set<Post> postCollection;

    @FXML
    void developerScreen(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/DeveloperInformation.fxml"));

        Parent root = loader.load();
        Stage newWindow = new Stage();
        newWindow.setTitle("Developer Information");
        newWindow.setScene(new Scene(root, 582, 241));
        newWindow.centerOnScreen();
        newWindow.show();

    }

    /*
    Function called when user clicks on New Event button
    New window opens up to create New event
     */
    @FXML
    void newEvent(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/NewEvent.fxml"));

        Parent root = loader.load();
        NewEventController controller = loader.getController();
        controller.initializeModelAndStage(primaryStage, unilink, logged_in_user.getText());
        primaryStage.setTitle("New Event ");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.centerOnScreen();
        primaryStage.show();

    }

    /*
     Function called when user clicks on New Job button
     New window opens up to create New Job
      */
    @FXML
    void newJob(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/NewJob.fxml"));

        Parent root = loader.load();
        NewJobController controller = loader.getController();
        controller.initializeModelAndStage(primaryStage, unilink, logged_in_user.getText());
        primaryStage.setTitle("New Job");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    /*
   Function called when user clicks on New Sale button
   New window opens up to create New Job
    */
    @FXML
    void newSale(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/NewSale.fxml"));

        Parent root = loader.load();
        NewSaleController controller = loader.getController();
        controller.initializeModelAndStage(primaryStage, unilink, logged_in_user.getText());
        primaryStage.setTitle("New Sale");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    /*
   Function called when user clicks on log out button
   Login screen opens up
    */
    @FXML
    void logOut(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Login.fxml"));
        Parent root = loader.load();
        LoginScreenController controller = loader.getController();
        controller.initializeModelAndStage(primaryStage, unilink);
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    /*
   Function called when user clicks Export Data
   Directory chooser opens up , to select folder to save "Posts.txt" text file
   exportData function of FileHandling.java called to write into the file
    */
    @FXML
    void exportData(ActionEvent event) {
        directoryChooser.setInitialDirectory(new File("src"));
        File selectedDirectory = directoryChooser.showDialog(primaryStage);
        System.out.println(selectedDirectory.getAbsolutePath());
        FileHandling obj = new FileHandling();
        if (obj.exportData(selectedDirectory.getAbsolutePath(), unilink)) {
            alertBox.setAlertType(Alert.AlertType.INFORMATION);
            alertBox.setContentText("Data exported successfully to " + selectedDirectory.getAbsolutePath());
        } else {
            alertBox.setAlertType(Alert.AlertType.ERROR);
            alertBox.setContentText("Error while exporting");
        }
        alertBox.show();
    }

    /*
       Function called when user clicks on Import Data
       File chooser opens up , to select the text file
       importData function of FileHandling.java called to read data from the file
   */
    @FXML
    void importData(ActionEvent event) throws IOException {
        final FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            System.out.println(file.getPath());
            FileHandling obj = new FileHandling();
            if (obj.importData(file.getPath(), unilink)) {
                alertBox.setAlertType(Alert.AlertType.INFORMATION);
                alertBox.setContentText("Data imported successfully");
                refreshMainMenu();
            } else {
                alertBox.setAlertType(Alert.AlertType.ERROR);
                alertBox.setContentText("Error while importing");
            }
            alertBox.show();
        }
    }

    /*
     Function called when user clicks on Quit Application
     writeDatabase called to insert data to database from postCollection
     Stage closed
    */
    @FXML
    void quitApp(ActionEvent event) {
        DatabaseHandling obj = new DatabaseHandling();
        obj.writeDatabase(unilink);
        primaryStage.close();
    }

    //Function to receive and set the stage , and unilink object , and logged in user name
    public void initializeModelAndStage(String username, Stage primaryStage, UniLink unilink) {
        this.unilink = unilink;
        this.primaryStage = primaryStage;
        logged_in_user.setText(username);
        System.out.println(logged_in_user);
        postCollection = unilink.getPostCollection();
        //To set the values of Choice box for Filters
        setChoiceBox();
        //To render the listview based on values of postCollection
        setListView(postCollection);
    }

    public void setChoiceBox() {
        filter_type.setItems(availableTypes);
        filter_type.getSelectionModel().select("All");
        filter_status.setItems(availableStatus);
        filter_status.getSelectionModel().select("All");
        filter_creator.setItems(availableCreator);
        filter_creator.getSelectionModel().select("All");
        //Event handler for all 3 filters
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

    //Called when any one of the filter is changed
    public void setFilter() {
        String selectedType = filter_type.getSelectionModel().getSelectedItem();
        String selectedCreator = filter_creator.getSelectionModel().getSelectedItem();
        String selectedStatus = filter_status.getSelectionModel().getSelectedItem();
        filterTypeHandler(selectedType, selectedCreator, selectedStatus);
    }

    /*
    To filter and render list of posts according to combination of all three filters
    Made use of Stream and Filters, to filter list of posts
     */
    public void filterTypeHandler(String selectedType, String selectedCreator, String selectedStatus) {
        Set<Post> filteredPostCollection;
        if (selectedType.equals("All") && selectedCreator.equals("All") && selectedStatus.equals("All")) {
            setListView(postCollection);
        } else if (selectedCreator.equals("All") && selectedStatus.equals("All")) {
            filteredPostCollection = postCollection.stream()
                    .filter(p -> p.getId().startsWith(selectedType.toUpperCase().substring(0, 3))).collect(Collectors.toSet());
            setListView(filteredPostCollection);
        } else if ((selectedType.equals("All") && selectedCreator.equals("All"))) {
            filteredPostCollection = postCollection.stream()
                    .filter(p -> p.getStatus().equals(selectedStatus.toUpperCase())).collect(Collectors.toSet());
            setListView(filteredPostCollection);
        } else if (selectedType.equals("All") && selectedStatus.equals("All")) {
            filteredPostCollection = postCollection.stream()
                    .filter(p -> p.getCreator_id().equals(logged_in_user.getText())).collect(Collectors.toSet());
            setListView(filteredPostCollection);
        } else if (selectedStatus.equals("All")) {
            filteredPostCollection = postCollection.stream()
                    .filter(p -> p.getCreator_id().equals(logged_in_user.getText()) && p.getId().startsWith(selectedType.toUpperCase().substring(0, 3))).collect(Collectors.toSet());
            setListView(filteredPostCollection);
        } else if (selectedCreator.equals("All")) {
            filteredPostCollection = postCollection.stream()
                    .filter(p -> p.getStatus().equals(selectedStatus.toUpperCase()) && p.getId().startsWith(selectedType.toUpperCase().substring(0, 3))).collect(Collectors.toSet());
            setListView(filteredPostCollection);
        } else if (selectedType.equals("All")) {
            filteredPostCollection = postCollection.stream()
                    .filter(p -> p.getStatus().equals(selectedStatus.toUpperCase()) && p.getCreator_id().equals(logged_in_user.getText())).collect(Collectors.toSet());
            setListView(filteredPostCollection);
        } else {
            filteredPostCollection = postCollection.stream()
                    .filter(p -> p.getCreator_id().equals(logged_in_user.getText()) && p.getId().startsWith(selectedType.toUpperCase().substring(0, 3)) && p.getStatus().equals(selectedStatus.toUpperCase())).collect(Collectors.toSet());
            setListView(filteredPostCollection);
        }
    }

    //Function to populate listview , setCellFactory() method called on the ListView which returns ListCell
    public void setListView(Set<Post> postCollection) {
        observableList.setAll(postCollection);
        listView.setItems(observableList);
        listView.setCellFactory(new Callback<ListView<Post>, javafx.scene.control.ListCell<Post>>() {
            @Override
            public ListCell<Post> call(ListView<Post> listView) {
                return new ListViewCell(primaryStage, unilink, logged_in_user.getText());
            }
        });
    }


    //Function called to Refresh current window , after data is imported from file
    public void refreshMainMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/MainMenu.fxml"));
        Parent root = null;
        root = loader.load();
        MainMenuController controller = loader.getController();
        controller.initializeModelAndStage(logged_in_user.getText(), primaryStage, unilink);
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(new Scene(root, 1000, 500));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

}

package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.ConnectionTest;
import model.CreateTable;

public class LoginScreen {

    Stage primaryStage;
    GridPane grid = new GridPane();
    Text welcomeText = new Text("Log In");
    Label userName = new Label("Username:");
    TextField userNameTextField = new TextField();
    Button loginButton = new Button("Login");
    HBox hbBtn = new HBox(10);
    Scene scene = new Scene(grid, 300, 275);

    public LoginScreen (Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void displayLoginScreen()
    {

        primaryStage.setTitle("UniLink");

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        welcomeText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(welcomeText, 0, 0);

        grid.add(userName, 0, 1);

        userNameTextField.setPromptText("Username");
        grid.add(userNameTextField, 1, 1);

        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(loginButton);
        grid.add(hbBtn, 1, 4);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}

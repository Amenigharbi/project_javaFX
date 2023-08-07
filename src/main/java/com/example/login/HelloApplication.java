package com.example.login;
import com.example.login.GestionEtudiantsApp;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Définition du titre de la fenêtre
        primaryStage.setTitle("Login");

        // Création des éléments d'interface
        Text sceneTitle = new Text("Welcome");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
        sceneTitle.setFill(Color.WHITE);
        Label userName = new Label("Username:");
        userName.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        userName.setTextFill(Color.WHITE);
        TextField userTextField = new TextField();
        Label password = new Label("Password:");
        password.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        password.setTextFill(Color.WHITE);
        PasswordField passwordField = new PasswordField();
        Button signInBtn = new Button("Sign in");
        signInBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        Button signUpBtn = new Button("Sign up");
        signUpBtn.setStyle("-fx-background-color: #008CBA; -fx-text-fill: white; -fx-font-weight: bold;");
        Label actionTarget = new Label();

        // Configuration du layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setStyle("-fx-background-color: #2196F3;");
        grid.add(sceneTitle, 0, 0, 2, 1);
        grid.add(userName, 0, 1);
        grid.add(userTextField, 1, 1);
        grid.add(password, 0, 2);
        grid.add(passwordField, 1, 2);
        grid.add(signInBtn, 1, 3);
        grid.add(signUpBtn, 2, 3);
        grid.add(actionTarget, 1, 4, 2, 1);


        // Définition de l'action à exécuter lors du clic sur le bouton "Sign in"
        signInBtn.setOnAction(e -> {
            if (userTextField.getText().equals("admin") && passwordField.getText().equals("password")) {
                actionTarget.setTextFill(Color.GREEN);
                actionTarget.setText("Login successful!");
            } else {
                actionTarget.setTextFill(Color.FIREBRICK);
                actionTarget.setText("Invalid username or password.");
            }
        });

        //  Définition de l'action à exécuter lors du clic sur le bouton "Sign up"
        signUpBtn.setOnAction(e -> {
            try {
                GestionBus app = new GestionBus();
                Stage stage = new Stage();
                app.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });



        // Création de la scène et affichage de la fenêtre
        Scene scene = new Scene(grid, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
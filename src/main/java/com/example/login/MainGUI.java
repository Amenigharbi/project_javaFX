package com.example.login;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainGUI extends Application {

    private static final String DB_URL = "jdbc:mysql://127.0.0.1/gestion_etud";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static Connection c;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Connexion à la base de données
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connection succeed");
        }
        catch (SQLException e) {
            System.out.println("erreur connection"+e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        Label matriculeLabel = new Label("Matricule :");
        matriculeLabel.setStyle("-fx-font: 16px Arial;");
        TextField matriculeField = new TextField();
        matriculeField.setStyle("-fx-text-fill: blue; -fx-background-color: white; -fx-font: 16px Arial;");
        Button checkButton = new Button("Vérifier");
        checkButton.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: lightgrey; -fx-font: 16px Arial;");
        Label resultatLabel = new Label();
        resultatLabel.setStyle("-fx-font: 16px Arial; -fx-text-fill: blue;");

        checkButton.setOnAction(event -> {
            handleButtonAction(matriculeField.getText(), resultatLabel);
        });

        VBox root = new VBox(10, matriculeLabel, matriculeField, checkButton, resultatLabel);
        root.setPadding(new Insets(10));
        primaryStage.setScene(new Scene(root, 700, 200));
        primaryStage.show();
    }

    private static void handleButtonAction(String matricule, Label resultatLabel) {
        try {
            Socket s = new Socket("127.0.0.1", 9001);
            PrintWriter pw = new PrintWriter(s.getOutputStream());
            pw.println(matricule);
            pw.flush();
            BufferedReader br = null; // 7alit l socket en mode lecture
            try {
                br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String ligne = null; // 9ra l matricule li baathou l'étudiant
            try {
                ligne = br.readLine();
                resultatLabel.setText("L'horaire du bus avec la matricule " + matricule + " est : "+ ligne);


            } catch (IOException e) {
                throw new RuntimeException(e);

            }
            try {
// Préparer la requête SQL pour vérifier si la matricule existe dans la table
                String sql = "SELECT Horaire FROM bus WHERE matricule = ?";
                PreparedStatement statement = c.prepareStatement(sql);
                statement.setString(1, ligne);

// Exécuter la requête SQL et récupérer le résultat
                ResultSet resultSet = statement.executeQuery();

// Vérifier si la matricule existe et afficher l'horaire du bus associé
                if (resultSet.next()) {
                    String horaire = resultSet.getString("Horaire");
                    System.out.println("L'horaire du bus avec la matricule " + ligne + " est : " + horaire);

                } else {
                    System.out.println("La matricule " + ligne + " n'existe pas dans la table bus.");
                }

// Fermer les ressources
                resultSet.close();
                statement.close();
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }


            br.close();
            pw.close();
            s.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }}

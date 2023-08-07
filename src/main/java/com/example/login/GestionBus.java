package com.example.login;


import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GestionBus extends Application {

    // Configuration de la connexion à la base de données
    private static final String nonDriver="com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://127.0.0.1/gestion_etud";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    // Liste des bus à afficher dans la table
    private ObservableList<Bus>buses = FXCollections.observableArrayList();

    // Éléments d'interface
    private TableView<Bus>table = new TableView<>();
    private TextField matriculeInput = new TextField();
    private TextField stationInput = new TextField();
    private TextField capaciteInput = new TextField();
    private TextField horaire = new TextField();
    static Connection c;

    @Override
    public void start(Stage primaryStage) throws SQLException, ClassNotFoundException {
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



// Définition du titre de la fenêtre
        primaryStage.setTitle("Gestion des Bus");


// Création des colonnes de la table
        TableColumn<Bus, Integer> Matcol= new TableColumn<>("Matricule Bus");
        Matcol.setCellValueFactory(cellData -> cellData.getValue().getMatricule().asObject());
        TableColumn<Bus, String> statCol = new TableColumn<>("Station Bus");
        statCol.setCellValueFactory(cellData -> cellData.getValue().getStation());
        TableColumn<Bus, Integer> capCol = new TableColumn<>("Capacité Bus");
        capCol.setCellValueFactory(cellData -> cellData.getValue().getCapacite().asObject());
        TableColumn<Bus, String> HoraireCol = new TableColumn<>("Horaire Bus");
        HoraireCol.setCellValueFactory(cellData -> cellData.getValue().getHoraire());
        table.setItems(buses);

// Ajout des colonnes à la table
        table.getColumns().addAll(Matcol, statCol, capCol,HoraireCol);

// Configuration du layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.add(table, 0, 0, 4, 1);
        buses.addAll(getBus());

// Configuration des champs de saisie
        Label Mat = new Label("Matricule bus :");
        Label Stat = new Label("Station bus :");
        Label Cap = new Label("Capacité bus :");
        Label Hor = new Label("Horaire bus :");
        grid.add(Mat, 0, 1);
        grid.add(matriculeInput, 1, 1);
        grid.add(Stat, 0, 2);
        grid.add(stationInput, 1, 2);
        grid.add(Cap , 2, 1);
        grid.add(capaciteInput, 3, 1);
        grid.add(Hor, 2, 2);
        grid.add(horaire, 3, 2);
        Image image = new Image("https://img.freepik.com/premium-vector/school-bus-cartoon_184560-13.jpg");
        ImageView imageView = new ImageView(image);
// Ajout de l'image au GridPane
        grid.add(imageView, 4, 0, 1, 4);

        Mat.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Mat.setTextFill(Color.BLUE);
        Stat.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Stat.setTextFill(Color.BLUE);
        Cap.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Cap.setTextFill(Color.BLUE);
        Hor.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Hor.setTextFill(Color.BLUE);
        // Définition de la police de caractères pour la table
        Font tableFont = Font.font("Segoe UI", FontWeight.BOLD, 14);
        table.setStyle("-fx-font: "+tableFont.getSize()+"px \""+tableFont.getFamily()+"\";");

// Définition de la couleur de fond pour la table
        table.setStyle("-fx-background-color: #F5F5F5;");

// Définition des bordures pour la table
        table.setStyle("-fx-border-color: #ddd; -fx-border-width: 1px;");

// Définition des propriétés pour chaque colonne
        Matcol.setStyle("-fx-alignment: CENTER;");
        statCol.setStyle("-fx-alignment: CENTER-LEFT;");
        capCol.setStyle("-fx-alignment: CENTER-LEFT;");
        HoraireCol.setStyle("-fx-alignment: CENTER-LEFT;");


// Configuration des boutons
        Button addButton = new Button("Ajouter");
        addButton.setOnAction(e-> {
// Récupération des données saisies
            String stat = stationInput.getText();
            String cap = capaciteInput.getText();
            String mat = matriculeInput.getText();
            String hor = horaire.getText();


// Vérification de la validité des données saisies
            if (stat.isEmpty() || cap.isEmpty() || mat.isEmpty()|| hor.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs.");
                alert.showAndWait();
            } else {


                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

                    Statement stmt;
                    stmt = conn.createStatement();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


                try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
// Insertion de la bus dans la base de données

                    PreparedStatement stmt = con.prepareStatement("INSERT INTO bus (matricule,station,capacite,horaire) VALUES ( ?, ?,?,?)");
                    stmt.setString(1, mat);
                    stmt.setString(2, stat);
                    stmt.setString(3, cap);
                    stmt.setString(4, hor);

                    stmt.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


// Mise à jour de la liste des bus affichés dans la table
                buses.clear();
                buses.addAll(getBus());

// Réinitialisation des champs de saisie
                matriculeInput.setText("");
                stationInput.setText("");
                capaciteInput.setText("");
                horaire.setText("");

            }
        });

        Button editButton = new Button("Modifier");
        editButton.setOnAction(e -> {
// Récupération de la bus sélectionnée dans la table
            Bus bus = table.getSelectionModel().getSelectedItem();

            if (bus == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner un bus dans la table.");
                alert.showAndWait();
            } else {// Ouverture de la fenêtre de modification de l'étudiant
                ModifierBusDialog dialog = new ModifierBusDialog(bus);
                Optional<Bus> result = dialog.showAndWait();
                if (result.isPresent()) {
                    Bus BusData = result.get();
                    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

                        PreparedStatement stmt = conn.prepareStatement("update bus set station=?,capacite=?,horaire=? where matricule=?");
                        stmt.setString(1, BusData.getStation().getValue());
                        stmt.setInt(2,BusData.getCapacite().getValue());
                        stmt.setString(3, BusData.getHoraire().getValue());
                        stmt.setInt(4, BusData.getMatricule().getValue());


                        stmt.executeUpdate();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    // Mise à jour de la liste des bus affichés dans la table
                    buses.clear();
                    buses.addAll(getBus());
                }


            }
        });

        Button deleteButton = new Button("Supprimer");
        deleteButton.setOnAction(e -> {
// Récupération de bus sélectionné dans la table
            Bus bus = table.getSelectionModel().getSelectedItem();

            if (bus == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner un bus dans la table.");
                alert.showAndWait();
            } else {
// Suppression de bus dans la base de données
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    PreparedStatement stmt = conn.prepareStatement("DELETE FROM bus WHERE matricule = ?");

                    stmt.setInt(1,bus.getMatricule().getValue());

                    stmt.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

// Mise à jour de la liste des bus affichés dans la table
                buses.clear();
                buses.addAll(getBus());
            }
        });

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(10);
        Button etudBtn = new Button("Gestion Etudiant");
        Font font = new Font("Arial", 24);
        addButton.setStyle("-fx-background-color: #87CEEB; -fx-border-color: blue; -fx-border-width: 2px; -fx-text-fill: blue;");
        editButton.setStyle("-fx-background-color: #87CEEB; -fx-border-color: blue; -fx-border-width: 2px; -fx-text-fill: blue;");
        deleteButton.setStyle("-fx-background-color: #87CEEB; -fx-border-color: blue; -fx-border-width: 2px; -fx-text-fill: blue;");
        etudBtn.setStyle("-fx-background-color: #87CEEB; -fx-border-color: blue; -fx-border-width: 2px; -fx-text-fill: blue;");
        addButton.setFont(font);
        editButton.setFont(font);
        deleteButton.setFont(font);
        etudBtn.setFont(font);


        hbox.getChildren().addAll(addButton, editButton, deleteButton,etudBtn);
        grid.add(hbox, 0, 3, 4, 1);
        etudBtn.setOnAction(e -> {
            try {
                GestionEtudiantsApp app = new GestionEtudiantsApp();
                Stage stage = new Stage();
                app.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private static List<Bus>getBus() {
        List<Bus> buses = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bus");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("matricule");
                String stat= rs.getString("station");
                int capa = rs.getInt("capacite");
                String hor= rs.getString("horaire");



                buses.add(new Bus(id, stat, capa,hor));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return buses;
    }

    public static void main(String[] args) {
        launch(args);
    }}

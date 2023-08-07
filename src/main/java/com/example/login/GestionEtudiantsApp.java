package com.example.login;

import javafx.application.Application;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GestionEtudiantsApp extends Application {

    // Configuration de la connexion à la base de données
    private static final String nonDriver="com.mysql.cj.jdbc.Driver";

    private static final String DB_URL = "jdbc:mysql://127.0.0.1/gestion_etud";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    // Liste des étudiants à afficher dans la table
    private ObservableList<Etudiant> etudiants = FXCollections.observableArrayList();

    // Éléments d'interface
    private TableView<Etudiant> table = new TableView<>();
    private TextField nomInput = new TextField();
    private TextField prenomInput = new TextField();
    private TextField adresseInput = new TextField();
    private TextField telephoneInput = new TextField();
    private TextField matInput = new TextField();
    static Connection c;
    @Override
    public void start(Stage primaryStage) throws SQLException {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connection succed");
        }
        catch (SQLException e) {
            System.out.println("erreur connection"+e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        // Définition du titre de la fenêtre
        primaryStage.setTitle("Gestion des étudiants");

        // Création des colonnes de la table
        TableColumn<Etudiant, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> cellData.getValue().getId().asObject());
        TableColumn<Etudiant, String> nomCol = new TableColumn<>("Nom");
        nomCol.setCellValueFactory(cellData -> cellData.getValue().getNom());
        TableColumn<Etudiant, String> prenomCol = new TableColumn<>("Prénom");
        prenomCol.setCellValueFactory(cellData -> cellData.getValue().getPrenom());
        TableColumn<Etudiant, String> adresseCol = new TableColumn<>("Adresse");
        adresseCol.setCellValueFactory(cellData -> cellData.getValue().getAdresse());
        TableColumn<Etudiant, String> telephoneCol = new TableColumn<>("Téléphone");
        telephoneCol.setCellValueFactory(cellData -> cellData.getValue().getTelephone());
        TableColumn<Etudiant, Integer> MatCol = new TableColumn<>("MatBus");
        MatCol.setCellValueFactory(cellData -> cellData.getValue().getBus().asObject());
        table.setItems(etudiants);

        // Ajout des colonnes à la table
        table.getColumns().addAll(idCol, nomCol, prenomCol, adresseCol, telephoneCol,MatCol);

        // Configuration du layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.add(table, 0, 0, 4, 1);
        etudiants.addAll(getEtudiants());
        // Configuration des champs de saisie
        Label Nom = new Label("Nom :");
        Label Prenom = new Label("Prenom :");
        Label adr = new Label("Adresse :");
        Label tel = new Label("Telephone :");
        Label mat = new Label("Matricule Bus :");
        grid.add(Nom, 0, 1);
        grid.add(nomInput, 1, 1);
        grid.add(Prenom, 0, 2);
        grid.add(prenomInput, 1, 2);
        grid.add(adr, 2, 1);
        grid.add(adresseInput, 3, 1);
        grid.add(tel, 2, 2);
        grid.add(telephoneInput, 3, 2);
        grid.add(mat, 2, 3);
        grid.add(matInput, 3, 3);
        // Création de l'image

        Image image = new Image("https://e7.pngegg.com/pngimages/270/229/png-clipart-boy-and-girl-wearing-white-dress-shirt-art-illustration-student-teacher-drawing-illustration-cartoon-student-cartoon-character-child.png");
        ImageView imageView = new ImageView(image);
// Ajout de l'image au GridPane
        grid.add(imageView, 4, 0, 1, 4);

        Nom.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Nom.setTextFill(Color.BLUE);
        Prenom.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Prenom.setTextFill(Color.BLUE);
        adr.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        adr.setTextFill(Color.BLUE);
        tel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        tel.setTextFill(Color.BLUE);
        mat.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        mat.setTextFill(Color.BLUE);
        // Définition de la police de caractères pour la table
        Font tableFont = Font.font("Segoe UI", FontWeight.BOLD, 14);
        table.setStyle("-fx-font: "+tableFont.getSize()+"px \""+tableFont.getFamily()+"\";");

// Définition de la couleur de fond pour la table
        table.setStyle("-fx-background-color: #F5F5F5;");

// Définition des bordures pour la table
        table.setStyle("-fx-border-color: #ddd; -fx-border-width: 1px;");

// Définition des propriétés pour chaque colonne
        idCol.setStyle("-fx-alignment: CENTER;");
        nomCol.setStyle("-fx-alignment: CENTER-LEFT;");
        prenomCol.setStyle("-fx-alignment: CENTER-LEFT;");
        adresseCol.setStyle("-fx-alignment: CENTER-LEFT;");
        telephoneCol.setStyle("-fx-alignment: CENTER;");
        MatCol.setStyle("-fx-alignment: CENTER;");


        // Configuration des boutons
        Button addButton = new Button("Ajouter");
        addButton.setOnAction(e->{
            // Récupération des données saisies
            String nom = nomInput.getText();
            String prenom = prenomInput.getText();
            String adresse = adresseInput.getText();
            String telephone = telephoneInput.getText();
            String Matricule = matInput.getText();

            // Vérification de la validité des données saisies
            if (nom.isEmpty() || prenom.isEmpty() || adresse.isEmpty() || telephone.isEmpty()|| Matricule.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs.");
                alert.showAndWait();
            } else {
                // Insertion de l'étudiant dans la base de données
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

                    PreparedStatement stmt = conn.prepareStatement("INSERT INTO etudiant (nom, prenom, addresse, telephone,matbus) VALUES (?, ?, ?, ?,?)");
                    stmt.setString(1, nom);
                    stmt.setString(2, prenom);
                    stmt.setString(3, adresse);
                    stmt.setString(4, telephone);
                    stmt.setString(5, Matricule);
                    stmt.executeUpdate();
                } catch (SQLException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Veuillez verifier le champ matricule bus.");
                    alert.showAndWait();
                }

                // Mise à jour de la liste des étudiants affichés dans la table
                etudiants.clear();
                etudiants.addAll(getEtudiants());

                // Réinitialisation des champs de saisie
                nomInput.setText("");
                prenomInput.setText("");
                adresseInput.setText("");
                telephoneInput.setText("");
                matInput.setText("");
            }
        });

        Button editButton = new Button("Modifier");
        editButton.setOnAction(e -> {
            // Récupération de l'étudiant sélectionné dans la table
            Etudiant etudiant = table.getSelectionModel().getSelectedItem();

            if (etudiant == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner un étudiant dans la table.");
                alert.showAndWait();
            } else {
                // Ouverture de la fenêtre de modification de l'étudiant
                ModifierEtudiantDialog dialog = new ModifierEtudiantDialog(etudiant);
                Optional<Etudiant> result = dialog.showAndWait();
                if (result.isPresent()) {
                    Etudiant etudiantData = result.get();
                    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

                        Statement stmt;
                        stmt = conn.createStatement();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

                        PreparedStatement stmt = conn.prepareStatement("update etudiant set nom=?,prenom=?,addresse=?,telephone=?,matbus=? where id=?");
                        stmt.setString(1, etudiantData.getNom().getValue());
                        stmt.setString(2, etudiantData.getPrenom().getValue());
                        stmt.setString(3, etudiantData.getAdresse().getValue());
                        stmt.setString(4, etudiantData.getTelephone().getValue());
                        stmt.setInt(5, etudiantData.getBus().getValue());
                        stmt.setInt(6, etudiantData.getId().getValue());
                        stmt.executeUpdate();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    // Mise à jour de la liste des étudiants affichés dans la table
                    etudiants.clear();
                    etudiants.addAll(getEtudiants());
                }


            }
        });

        Button deleteButton = new Button("Supprimer");
        deleteButton.setOnAction(e -> {
            // Récupération de l'étudiant sélectionné dans la table
            Etudiant etudiant = table.getSelectionModel().getSelectedItem();

            if (etudiant == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner un étudiant dans la table.");
                alert.showAndWait();
            } else {
                // Suppression de l'étudiant dans la base de données
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    PreparedStatement stmt = conn.prepareStatement("DELETE FROM etudiant WHERE id = ?");
                    stmt.setInt(1, etudiant.getId().getValue());
                    stmt.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                // Mise à jour de la liste des étudiants affichés dans la table
                etudiants.clear();
                etudiants.addAll(getEtudiants());
            }
        });

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(10);
        Button busBtn = new Button("Gestion Bus");
        Button comBtn = new Button("Communication serveur");
        Font font = new Font("Arial", 24);
        addButton.setStyle("-fx-background-color: #87CEEB; -fx-border-color: blue; -fx-border-width: 2px; -fx-text-fill: blue;");
        editButton.setStyle("-fx-background-color: #87CEEB; -fx-border-color: blue; -fx-border-width: 2px; -fx-text-fill: blue;");
        deleteButton.setStyle("-fx-background-color: #87CEEB; -fx-border-color: blue; -fx-border-width: 2px; -fx-text-fill: blue;");
        busBtn.setStyle("-fx-background-color: #87CEEB; -fx-border-color: blue; -fx-border-width: 2px; -fx-text-fill: blue;");
        comBtn.setStyle("-fx-background-color: #87CEEB; -fx-border-color: blue; -fx-border-width: 2px; -fx-text-fill: blue;");
        addButton.setFont(font);
        editButton.setFont(font);
        deleteButton.setFont(font);
        busBtn.setFont(font);
        comBtn.setFont(font);

        hbox.getChildren().addAll(addButton, editButton, deleteButton,busBtn,comBtn );
        grid.add(hbox, 0, 4, 5, 1);
       busBtn.setOnAction(e -> {
            try {
                GestionBus app = new GestionBus();
                Stage stage = new Stage();
                app.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        comBtn.setOnAction(e -> {
            try {
                MainGUI app = new MainGUI();
                Stage stage = new Stage();
                app.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });


        Scene scene = new Scene(grid);
        scene.setFill(Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private static List<Etudiant> getEtudiants() {
        List<Etudiant> etudiants = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM etudiant");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String adresse = rs.getString("addresse");
                String telephone = rs.getString("telephone");
                Integer Bus = rs.getInt("matbus");

                etudiants.add(new Etudiant(id, nom, prenom, adresse, telephone,Bus));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return etudiants;
    }

    public static void main(String[] args) {
        launch(args);
    }}



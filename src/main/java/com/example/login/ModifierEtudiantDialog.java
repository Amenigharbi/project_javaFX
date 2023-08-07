package com.example.login;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.Optional;

public class ModifierEtudiantDialog extends Dialog<Etudiant> {
    private TextField IdInput;
    private TextField nomInput;
    private TextField prenomInput;
    private TextField adresseInput;
    private TextField telephoneInput;
    private TextField matInput;

    public ModifierEtudiantDialog(Etudiant etudiant) {
        setTitle("Modifier un étudiant");
        setHeaderText("Modifiez les informations de l'étudiant.");

        ButtonType modifierButtonType = new ButtonType("Modifier");

        getDialogPane().getButtonTypes().addAll(modifierButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        IdInput = new TextField();
        IdInput.setText(etudiant.getId().getValue().toString());
        nomInput = new TextField();
        nomInput.setText(etudiant.getNom().getValue());
        prenomInput = new TextField();
        prenomInput.setText(etudiant.getPrenom().getValue());
        adresseInput = new TextField();
        adresseInput.setText(etudiant.getAdresse().getValue());
        telephoneInput = new TextField();
        telephoneInput.setText(etudiant.getTelephone().getValue());
        matInput = new TextField();
        matInput.setText(etudiant.getBus().getValue().toString());

        grid.add(new Label("Id:"), 0, 0);
        grid.add(IdInput, 1, 0);
        grid.add(new Label("Nom:"), 0, 1);
        grid.add(nomInput, 1, 1);
        grid.add(new Label("Prenom:"), 0, 2);
        grid.add(prenomInput, 1, 2);
        grid.add(new Label("Adresse:"), 0, 3);
        grid.add(adresseInput, 1, 3);
        grid.add(new Label("Telephone:"), 0, 4);
        grid.add(telephoneInput, 1, 4);
        grid.add(new Label("mat bus:"), 0, 5);
        grid.add(matInput, 1, 5);

        getDialogPane().setContent(grid);

        setResultConverter(dialogButton -> {
            if (dialogButton == modifierButtonType) {
                // Vérification de la validité des données saisies
                if (nomInput.getText().isEmpty() || prenomInput.getText().isEmpty() || adresseInput.getText().isEmpty() || telephoneInput.getText().isEmpty() || matInput.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Veuillez remplir tous les champs.");
                    alert.showAndWait();
                    return null;
                } else {
                    Etudiant newData = new Etudiant(Integer.parseInt(IdInput.getText()),nomInput.getText(),prenomInput.getText(),adresseInput.getText(),telephoneInput.getText(),Integer.parseInt(matInput.getText()));
                    return newData;
                }
            }
            return null;
        });
    }
}

package com.example.login;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class ModifierBusDialog extends Dialog<Bus>{
    private TextField IdMatricule;
    private TextField stationInput;
    private TextField capaciteInput;
    private TextField horaire;


    public ModifierBusDialog(Bus mat) {
        setTitle("Modifier un bus ");
        setHeaderText("Modifiez les informations du bus.");

        ButtonType modifierButtonType = new ButtonType("Modifier");

        getDialogPane().getButtonTypes().addAll(modifierButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        IdMatricule = new TextField();
        IdMatricule.setText(String.valueOf(mat.getMatricule().getValue()));
        stationInput = new TextField();
        stationInput.setText(mat.getStation().getValue());
        capaciteInput = new TextField();
        capaciteInput.setText(String.valueOf(mat.getCapacite().getValue()));
        horaire = new TextField();
        horaire.setText(String.valueOf(mat.getHoraire().getValue()));


        grid.add(new Label("Matricule:"), 0, 0);
        grid.add(IdMatricule, 1, 0);
        grid.add(new Label("Station Bus:"), 0, 1);
        grid.add(stationInput, 1, 1);
        grid.add(new Label("capacité Bus:"), 0, 2);
        grid.add(capaciteInput, 1, 2);
        grid.add(new Label("Horaire Bus:"), 0, 3);
        grid.add(horaire, 1, 3);


        getDialogPane().setContent(grid);

        setResultConverter(dialogButton -> {
            if (dialogButton == modifierButtonType) {
// Vérification de la validité des données saisies
                if (IdMatricule.getText().isEmpty() || stationInput.getText().isEmpty() || capaciteInput.getText().isEmpty()||horaire.getText().isEmpty() ) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Veuillez remplir tous les champs.");
                    alert.showAndWait();
                    return null;
                } else {
                   Bus newData = new Bus (Integer.parseInt(IdMatricule.getText()),stationInput.getText(),Integer.parseInt(capaciteInput.getText()),horaire.getText());
                    return newData;
                }
            }
            return null;
        });
    }
}



















package com.example.login;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Etudiant {
    private SimpleIntegerProperty id;
    private SimpleStringProperty nom;
    private SimpleStringProperty prenom;
    private SimpleStringProperty adresse;
    private SimpleStringProperty telephone;
    private SimpleIntegerProperty bus;

    public Etudiant(int id, String nom, String prenom, String adresse, String telephone,Integer bus) {
        this.id = new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.adresse = new SimpleStringProperty(adresse);
        this.telephone = new SimpleStringProperty(telephone);
        this.bus = new SimpleIntegerProperty(bus);
    }

    public SimpleIntegerProperty getId() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public SimpleStringProperty getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public SimpleStringProperty getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
    }

    public SimpleStringProperty getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse.set(adresse);
    }

    public SimpleStringProperty getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone.set(telephone);
    }

    public SimpleIntegerProperty getBus() {
        return bus;
    }

    public void setBus(Integer bus) {
        this.bus.set(bus);
    }
}

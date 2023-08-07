package com.example.login;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Bus {
    private SimpleIntegerProperty matricule;
    private SimpleStringProperty station;
    private SimpleIntegerProperty capacite;
    private SimpleStringProperty horaire;



    public Bus(int matricule, String station, int capacite,String horaire ) {
        this.matricule = new SimpleIntegerProperty(matricule);
        this.station = new SimpleStringProperty(station);
        this.capacite = new SimpleIntegerProperty(capacite);
        this.horaire = new SimpleStringProperty(horaire);


    }

    public SimpleIntegerProperty getMatricule() {

        return matricule;
    }

    public void setMatricule(int matricule) {

        this.matricule.set(matricule);
    }

    public SimpleStringProperty getStation() {

        return station;
    }

    public void setStation(String Libelle) {

        this.station.set(Libelle);
    }

    public SimpleIntegerProperty getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite.set(capacite);
    }



    public SimpleStringProperty getHoraire() {
        return horaire;
    }

    public void setHoraire(String horaire) {
        this.horaire.set(horaire );
    }}
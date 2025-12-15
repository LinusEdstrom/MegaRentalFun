package com.Edstrom.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Member {


    //attribut

    private int id;        //removed final so I can have empty constructor with Json/jackson
    private String name;
    private StatusLevel statusLevel;
    @JsonIgnore
    private final ObservableList<Rental> rentalHistory = FXCollections.observableArrayList();

    //konstruktorer

    public Member(){

    }

    public Member(int id,String name, StatusLevel statusLevel) {
        this.id = id;
        this.name = name;
        this.statusLevel = statusLevel;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {this.id = id;}  // Jacksoon will use this for deserializing.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StatusLevel getStatusLevel() {
        return statusLevel;
    }
    public void setStatusLevel(StatusLevel statusLevel) {
        this.statusLevel = statusLevel;
    }
    public ObservableList<Rental> getRentalHistory() {
        return rentalHistory;
    }

    public void addRentalToHistory(Rental rental) {
        rentalHistory.add(rental);
    }

}


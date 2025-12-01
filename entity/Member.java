package com.Edstrom.entity;

import java.util.ArrayList;
import java.util.List;

public class Member {


    //attribut

    private static long nextId = 1;
    private final long id;
    private String name;
    private String statusLevel;
    // private List<Rental> history;

    //konstruktorer

    public Member(String name, String statusLevel) {
        this.id = nextId++;
        this.name = name;
        this.statusLevel = statusLevel;
       // this.history = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatusLevel() {
        return statusLevel;
    }

    public void setStatusLevel(String statusLevel) {
        this.statusLevel = statusLevel;
    }

    /*public List<Rental> getHistory() {
        return history;
    }

    public void addRentalToHistory(Rental rental) {
        history.add(rental);
    }



    @Override
    public String toString() {
        return "[" + id +"] " + name + " (" + statusLevel + " - " + history.size() + " rentals  )";
    }

     */

}


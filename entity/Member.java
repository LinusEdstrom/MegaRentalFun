package com.Edstrom.entity;

import java.util.ArrayList;
import java.util.List;

public class Member {


    //attribut

    private int id;        //removed final so I can have empty constructor with Json/jackson
    private String name;
    private String statusLevel;
    // private List<Rental> history;

    //konstruktorer

    public Member(){

    }

    public Member(int id,String name, String statusLevel) {
        this.id = id;
        this.name = name;
        this.statusLevel = statusLevel;
       // this.history = new ArrayList<>();
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


package com.Edstrom.entity;



public abstract class Item {

    //attribute

    protected int id;
    protected String title;
    protected double basePrice;
    private boolean rented;

    public Item(){}


    public Item(int id, String title, double basePrice) {
        this.id = id;
        this.title = title;
        this.basePrice = basePrice;
        this.rented = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle()
    {return title;}

    public void setTitle(String title){this.title = title;}

    public double getBasePrice(){
        return basePrice;}

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public boolean isRented() {return rented;}

    public void rentOut(){rented = true;}

    public void returnItem(){rented = false;}

    //public abstract double getPricePerDay();    //En override som gick till Dvd, för att få return basePrice
}





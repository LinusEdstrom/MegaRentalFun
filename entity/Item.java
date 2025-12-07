package com.Edstrom.entity;



public abstract class Item {

    //attribute

    protected int id;
    protected String title;
    protected double basePrice;
    private boolean available = true;

    public Item(){}


    public Item(int id, String title, double basePrice) {
        this.id = id;
        this.title = title;
        this.basePrice = basePrice;

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

    public boolean isAvailable() {return available;}

    public void setAvailable(boolean available){this.available = available;}

    //public abstract double getPricePerDay();    //En override som gick till Dvd, för att få return basePrice
}





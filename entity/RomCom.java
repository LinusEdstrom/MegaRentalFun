package com.Edstrom.entity;

public class RomCom extends Item {

    // attribute

    private int length;
    private int cheeziness;
    private int hunks;

    //constructor

    public RomCom() {}

    public RomCom(int id, String title, double price, int length, int cheeziness, int hunks) {
        super(id, title, price);
        this.length = length;
        this.cheeziness = cheeziness;
        this.hunks = hunks;
    }

    public int getLength(){return length;}
    public void setLength(int length){
        this.length = length;
    }

    public void setCheeziness(int cheeziness) {
        this.cheeziness = cheeziness;}

    public void setHunks(int hunks) {
        this.hunks = hunks;}

    public int getCheeziness(){return cheeziness;}
    public int getHunks(){return hunks;}


    }





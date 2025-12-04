package com.Edstrom.entity;

public class Action extends Item {

    //attribute

    private int length;
    private int explosions;
    private int coolOneliners;

    public Action() {
        super();
    }

    public Action(int id, String title, double basePrice, int length, int explosions, int coolOneliners){
        super(id,title, basePrice);
        this.length = length;
        this.explosions = explosions;
        this.coolOneliners = coolOneliners;
    }
    // Den här ska nog bort

    public double getBasePrice (){
        return basePrice;
    }
    public int getLength(){return length;}
    public void setLength(int length){this.length = length;}
    public int getExplosions() {return explosions;}
    public void setExplosions(int explosions) {this.explosions = explosions;}
    public int getCoolOneliners() {return coolOneliners;}
    public void setCoolOneliners(int coolOneliners) {this.coolOneliners = coolOneliners;}
}
    /*@Override
    public double getPricePerDay () {
    return basePrice;
}
 fångar upp overriden som gick till Dvd förut.
     */



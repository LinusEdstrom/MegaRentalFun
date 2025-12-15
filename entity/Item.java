package com.Edstrom.entity;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

//Lägger in till subtyper så Json fattar
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RomCom.class, name = "RomCom"),
        @JsonSubTypes.Type(value = Action.class, name = "Action")

})

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

}





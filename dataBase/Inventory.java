package com.Edstrom.dataBase;

import com.Edstrom.entity.Action;
import com.Edstrom.entity.Item;
import com.Edstrom.entity.RomCom;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private final List<Item> inventoryList = new ArrayList<>();

    /*public Item findByTitle(String title){
        String search = title.trim();
        for(Item item : inventoryList) {
            if (item.getTitle().equalsIgnoreCase(title))
                return item;
        }
        return null;
    }

    public Action createAndAddAction(String title, double price, int length, int explosions, int coolOneliners){
        Action action = new Action(title, price, length, explosions, coolOneliners);
        inventoryList.add(action);
        return action;
    }
    public RomCom createAndAddRomCom(String title, double price, int length, int cheeziness, int hunks){
        RomCom romCom = new RomCom(title, price, length, cheeziness, hunks);
        inventoryList.add(romCom);
        return romCom;
    }
    public List<Action> actionList() {
        List<Action> actionList = new ArrayList<>();
        for (Item item : inventoryList) {
            if (item instanceof Action action) {
                actionList.add(action);}
        }
        return actionList;
    }

    public List<RomCom> romComList() {
        List<RomCom> romComList = new ArrayList<>();
        for (Item item : inventoryList) {
            if (item instanceof RomCom romCom) {
                romComList.add(romCom); }
        }
        return romComList;
    }

     */

}


package com.Edstrom.dataBase;

import com.Edstrom.entity.Action;
import com.Edstrom.entity.Item;
import com.Edstrom.entity.Member;
import com.Edstrom.entity.RomCom;
import com.fasterxml.jackson.core.type.TypeReference;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Inventory extends PersistenceLayer {

    private final ObservableList<Item> inventoryList = FXCollections.observableArrayList();
    private final File jsonItems = new File("inventoryList.json");

    public Inventory() {
        loadFromFile();
    }
    // Listener
    //items.addListener(ListChangeListener<Item>) changer -> saveToFile());

    public ObservableList<Item> getItems() {
        return inventoryList;
    }

    public void addItem(Item newItem) {
        if (newItem == null) {
            throw new IllegalArgumentException("Item can't be null");
        }
        boolean existsId = inventoryList.stream().anyMatch(items -> items.getId() == newItem.getId());
        if (existsId) {
            throw new IllegalArgumentException("Item with Id " + newItem.getId() + "allready exists in inventory"
            );
        }
        inventoryList.add(newItem);
    }
    private void loadFromFile(){
        if(!jsonItems.exists()) {
            // Exception}
            return;
        }
        try {
            List<Item> items = mapper.readValue(jsonItems, new TypeReference<List<Item>>(){});
            inventoryList.addAll(items);
        }catch(IOException e) { //Exceptions
        }

    }
}
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




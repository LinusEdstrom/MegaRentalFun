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
        saveItemFile();
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
    private void saveItemFile(){
        try {
            mapper.writeValue(jsonItems, new ArrayList<>(inventoryList));
        }catch (IOException e){
            System.out.println("Crash and burn" + e.getMessage());
        }
    }
}





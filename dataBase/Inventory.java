package com.Edstrom.dataBase;

import com.Edstrom.entity.Action;
import com.Edstrom.entity.Item;
import com.Edstrom.entity.Member;
import com.Edstrom.entity.RomCom;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    public ObservableList<Item> getItems() {
        return inventoryList;
    }

    public void addItem(Item newItem) {
        inventoryList.add(newItem);
        saveItemFile();
    }

    private void loadFromFile() {
        System.out.println("Loading inventory from " + jsonItems.getAbsolutePath());
        if (!jsonItems.exists()) {

            try {
                jsonItems.createNewFile();
                mapper.writeValue(jsonItems, new ArrayList<>());

            } catch (IOException e) { //Exceptions
                e.printStackTrace();
            }
            return;
        }
        try {
            List<Item> fileItems = mapper.readValue(jsonItems, new TypeReference<List<Item>>() {
            });
            inventoryList.setAll(fileItems);
        } catch (IOException e) {
            System.out.println("Failed to read items from file");
            e.printStackTrace();
        }
    }

    public void saveItemFile() {
        try {
            // Build array with type field
            ArrayNode nodeSubObject = mapper.createArrayNode();
            for(Item savedItem : inventoryList){
                ObjectNode savedObjectNode = mapper.valueToTree(savedItem); // Item becomes Node.
                String typeName = savedItem.getClass().getSimpleName();
                savedObjectNode.put("type", typeName);
                nodeSubObject.add(savedObjectNode);
            }
            mapper.writeValue(jsonItems, nodeSubObject);
        } catch (IOException e) {
            System.out.println("Crash and burn" + e.getMessage());
            e.printStackTrace();
        }
    }
    }






package com.Edstrom.service;

import com.Edstrom.dataBase.Inventory;
import com.Edstrom.entity.Item;
import com.Edstrom.entity.Member;
import javafx.collections.ObservableList;

public class RentalService {

private final Inventory inventory;

    public RentalService(Inventory inventory) {
        this.inventory = inventory;
    }

    public ObservableList<Item> getItems() {return inventory.getItems();
    }
    public void addItem(Item newItem) {
        if (newItem == null){
            throw new IllegalArgumentException("Where is the item ?");
        }
        inventory.addItem(newItem);
        }

    }



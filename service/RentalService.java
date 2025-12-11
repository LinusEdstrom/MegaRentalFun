package com.Edstrom.service;

import com.Edstrom.dataBase.Inventory;
import com.Edstrom.entity.Item;
import com.Edstrom.entity.Member;
import com.Edstrom.entity.Rental;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class RentalService {

private ObservableList<Rental> activeRentals = FXCollections.observableArrayList();

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
    public Rental rentItem(Member member, Item item, int days) {
        if(member == null || item == null) {
            throw new IllegalArgumentException("Member or Item is null");
        }
        if(!item.isAvailable()){
            return null;
        }
        LocalDate rentingDate = LocalDate.now();

        Rental rentalMirr = new Rental(member, item, rentingDate, days);
        member.getRentalHistory().add(rentalMirr);
        activeRentals.add(rentalMirr);

        item.setAvailable(false);

        return rentalMirr;
    }
    public boolean returnRental(Rental returnedRental) {
        if(returnedRental == null || !activeRentals.contains(returnedRental)) {
            return false;
        }
        Item returnedItem = returnedRental.getItem();
        if (returnedItem != null) {
            returnedItem.setAvailable(true);
        }
        activeRentals.remove(returnedRental);
        return true;

    }
    public ObservableList<Rental> getActiveRentals() {
        return activeRentals;
    }


    }



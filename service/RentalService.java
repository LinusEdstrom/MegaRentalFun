package com.Edstrom.service;

import com.Edstrom.dataBase.Inventory;
import com.Edstrom.entity.*;
import com.Edstrom.exceptions.AlreadyExistsException;
import com.Edstrom.exceptions.InvalidItemDataException;
import com.Edstrom.exceptions.MissingSubTypeException;
import com.Edstrom.exceptions.NumberOverZeroException;
import com.Edstrom.pricing.Premium;
import com.Edstrom.pricing.PricePolicy;
import com.Edstrom.pricing.Standard;
import com.Edstrom.pricing.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

public class RentalService {

    private ObservableList<Rental> activeRentals = FXCollections.observableArrayList();

    private final Inventory inventory;

    public RentalService(Inventory inventory) {
        this.inventory = inventory;
    }

    public ObservableList<Item> getItems() {
        return inventory.getItems();
    }

    public void addItem(int id, String title, double basePrice, String subType,
                        int valueExtra1, int valueExtra2, int valueExtra3) {

        boolean exists = inventory.getItems().stream().anyMatch(item -> item.getId() == id);
        if (exists) {
            throw new AlreadyExistsException("Item with ID " + id + " already exists");
        }
        if (title == null || title.isEmpty()) {
            throw new InvalidItemDataException("All fields must be filled correctly");
        }
        if (subType == null) {
            throw new MissingSubTypeException("Please choose what kind of movie it is");
        }
        if (id <= 0 || basePrice <= 0 || valueExtra1 <= 0 || valueExtra2 < 0 || valueExtra3 < 0) {
            throw new NumberOverZeroException("Numeric fields has to be positive");
        }
        Item newItem;
        switch (subType) {
            case "Action":
                newItem = new Action(id, title, basePrice, valueExtra1, valueExtra2, valueExtra3);
                break;
            case "RomCom":
                newItem = new RomCom(id, title, basePrice, valueExtra1, valueExtra2, valueExtra3);
                break;
            default:
                throw new InvalidItemDataException(" This is not a valid type " + subType);
        }
        inventory.getItems().add(newItem);
        inventory.saveItemFile();
    }

        private PricePolicy getPolicy(Member rentMember){
        switch (rentMember.getStatusLevel()){

            case PREMIUM:
                return new Premium();
            case STUDENT:
                return new Student();
            default:
                return new Standard();
        }
        }

    public Rental rentItem(Member member, Item item, int days) {
        if(member == null || item == null) {
            throw new IllegalArgumentException("Member or Item is null");
        }
        if(!item.isAvailable()){
            return null;
        }
        LocalDate rentingDate = LocalDate.now();

        PricePolicy policy = getPolicy(member);
        double totalPrice = policy.priceCalculator(item.getBasePrice(), days);

        Rental rentalMirr = new Rental(member, item, rentingDate, days, totalPrice);
        member.getRentalHistory().add(rentalMirr);
        activeRentals.add(rentalMirr);

        item.setAvailable(false);
        inventory.getItems().remove(item);

        return rentalMirr;
    }
    public boolean returnRental(Rental returnedRental) {
        if(returnedRental == null || !activeRentals.contains(returnedRental)) {
            return false;
        }
        Item returnedItem = returnedRental.getItem();
        if (returnedItem != null) {
            returnedItem.setAvailable(true);

            ObservableList<Item> items = inventory.getItems();
            if(!items.contains(returnedItem)){
                items.add(returnedItem);
            }
            inventory.saveItemFile();
        }

        activeRentals.remove(returnedRental);
        return true;

    }
    public ObservableList<Rental> getActiveRentals() {
        return activeRentals;
    }
    public double getTotalRevenue(List<Member> membersHistoryMember) {
        if (membersHistoryMember == null || membersHistoryMember.isEmpty()) return 0.0;

        return membersHistoryMember.stream()
                .flatMap(member -> member.getRentalHistory().stream())
                .mapToDouble(Rental::getTotalPrice)
                .sum();
    }

    }



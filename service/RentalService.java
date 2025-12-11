package com.Edstrom.service;

import com.Edstrom.dataBase.Inventory;
import com.Edstrom.entity.Item;
import com.Edstrom.entity.Member;
import com.Edstrom.entity.Rental;
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

    public ObservableList<Item> getItems() {return inventory.getItems();
    }
    public void addItem(Item newItem) {
        if (newItem == null){
            throw new IllegalArgumentException("Where is the item ?");
        }
        inventory.addItem(newItem);
        }
        private PricePolicy getPolicy(Member rentMember){
        switch (rentMember.getStatusLevel().toLowerCase()){

            case "premium":
                return new Premium();
            case "student":
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
    public double getTotalRevenue(List<Member> membersHistoryMember) {
        if (membersHistoryMember == null || membersHistoryMember.isEmpty()) return 0.0;

        return membersHistoryMember.stream()
                .flatMap(member -> member.getRentalHistory().stream())
                .mapToDouble(Rental::getTotalPrice)
                .sum();
    }

    }



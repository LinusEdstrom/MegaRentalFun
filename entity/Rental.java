package com.Edstrom.entity;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;

public class Rental {


    // Testar göra rental med observable värden.
    private final ObjectProperty<Member> rentingMember = new SimpleObjectProperty<>();
    private final ObjectProperty<Item> rentedItem = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> rentingDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> returnDate = new SimpleObjectProperty<>();
    private final int days;
    private final double totalPrice;

    //Bygg Rentalen
    public Rental(Member rentingMember, Item rentedItem, LocalDate rentingDate, int days) {
        this.rentingMember.set(rentingMember);
        this.rentedItem.set(rentedItem);
        this.rentingDate.set(rentingDate);
        this.days = days;
        this.returnDate.set(rentingDate.plusDays(days));
        this.totalPrice = rentedItem.getBasePrice() * days;
    }
    //Get and Set
    //Member
    public Member getMember(){return rentingMember.get();}
    public ObjectProperty<Member> rentingMemberProperty(){return rentingMember;}
    public void setMember(Member activeMember){rentingMember.set(activeMember);}
    //Item
    public Item getItem(){return rentedItem.get();}
    public ObjectProperty<Item> rentedItemProperty(){return rentedItem;}
    public void setItem(Item activeItem){rentedItem.set(activeItem);}
    //RentingDate
    public LocalDate getRentingDate(){return rentingDate.get();}
    public ObjectProperty<LocalDate> rentingDateProperty(){return rentingDate;}
    public void setRentingDate(LocalDate startDate){rentingDate.set(startDate);}
    //this.returnDate.set(startDate.plusDays(days)); //Behövs ju bara om days ska gå ändra
    //days
    public int getDays(){return days;}
    //ReturnDate
    public LocalDate getReturnDate(){return returnDate.get();}
    public ObjectProperty<LocalDate> returnDateProperty(){return returnDate;}
    public void setReturnDate(LocalDate stopDate){returnDate.set(stopDate);}
    //TotalPrice
    public double getTotalPrice(){return totalPrice;}


}

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

    //Bygg Rentalen
    public Rental(Member rentingMember, Item rentedItem, LocalDate rentingDate, LocalDate returnDate) {
        this.rentingMember.set(rentingMember);
        this.rentedItem.set(rentedItem);
        this.rentingDate.set(rentingDate);
        this.returnDate.set(returnDate);
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
    //ReturnDate
    public LocalDate getReturnDate(){return returnDate.get();}
    public ObjectProperty<LocalDate> returnDateProperty(){return returnDate;}
    public void setReturnDate(LocalDate stopDate){returnDate.set(stopDate);}


}

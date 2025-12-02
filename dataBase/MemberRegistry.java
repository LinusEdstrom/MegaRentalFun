package com.Edstrom.dataBase;

import com.Edstrom.entity.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;


public class MemberRegistry {


    private final ObservableList<Member> memberList = FXCollections.observableArrayList();

    public MemberRegistry(){

            memberList.add(new Member("FiaStina", "Student"));
            memberList.add(new Member("LisaLasse", "Student"));
            memberList.add(new Member("GulliGunnar", "Standard"));
            memberList.add(new Member("StekarJanne", "Standard"));
            memberList.add(new Member("ClaustHauler", "Premium"));

        }

    public ObservableList<Member> getMembers(){
        return memberList;
    }

    public void saveMember(Member member){
        memberList.add(member);
    }
    public void deleteMember(Member member) {
        memberList.remove(member);
    }


}




package com.Edstrom.service;

import com.Edstrom.dataBase.MemberRegistry;
import com.Edstrom.entity.Member;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class MembershipService {

    private final MemberRegistry memberRegistry;

    public MembershipService(MemberRegistry memberRegistry) {
        this.memberRegistry = memberRegistry;
    }

    public void addMember(String name, String statusLevel, ObservableList<Member> tableItems) {
        // Här ska det in fett med Exception try catch stuff
    /*if(name == null || name.isEmpty() || statusLevel == null || statusLevel.isEmpty()) {
        return;}
    }

     */
        Member newMember = new Member(name, statusLevel);
        //tableItems.add(newMember); // Blev ju dubbleter när de sparas dubbel vilket ju inte behövs när
        //ObservableList uppdaterar UI på studs. Heja heja!!!
        memberRegistry.saveMember(newMember);
    }
    public void deleteMember(ObservableList<Member> selectedMember, ObservableList<Member> allMembers){
    // In här med nå feta Exceptions
    selectedMember.forEach(member ->{
        allMembers.remove(member);
        memberRegistry.deleteMember(member);
            });
    }
    public ObservableList<Member> getMembers(){
        return memberRegistry.getMembers();
    }
}

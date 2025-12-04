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

    public Member addMember(int id, String name, String statusLevel) {

        // Här ska det in fett med Exception try catch stuff
         if(name == null || name.isEmpty() || statusLevel == null || statusLevel.isEmpty()) {
             return null;
         }
        Member newMember = new Member(id, name.trim(), statusLevel.trim());
        //addedMember.add(newMember); // Blev ju dubbleter när de sparas dubbel vilket ju inte behövs när
        //ObservableList uppdaterar UI på studs. Heja heja!!!

        try {
             memberRegistry.saveMember(newMember);

        }catch (Exception e) {
            e.printStackTrace();

        }
        return null;//save to registry Json file.
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

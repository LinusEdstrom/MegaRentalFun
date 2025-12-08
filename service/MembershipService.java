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

    public void addMember(int id, String name, String statusLevel) {

        try{
            // Här ska det in fett med Exception try catch stuff
         if(name == null || name.isEmpty() || statusLevel == null || statusLevel.isEmpty()) {
           throw new IllegalArgumentException(" Error in writing somewhere");
         }
         boolean existsId = memberRegistry.getMembers().stream().anyMatch(member -> member.getId() == id);
         if(existsId){
             throw new IllegalArgumentException("That Id allready exists");
         }
        Member newMember = new Member(id, name.trim(), statusLevel.trim());
             memberRegistry.saveMember(newMember);

        }catch (Exception e) {
            throw new RuntimeException("Failed to save member + e.getMessage");
        }
    }
    public void deleteMember(ObservableList<Member> selectedMember, ObservableList<Member> allMembers){
    // In här med nå feta Exceptions
    if (selectedMember == null || selectedMember.isEmpty()) {
        throw new IllegalArgumentException("U have to select a member to delete!");
        }
        new ArrayList<>(selectedMember).forEach(deletedMember ->{
            allMembers.remove(deletedMember);
            memberRegistry.removeMemberById(deletedMember.getId());//Bara trams att göra en stream för det här.
        });
    }
    public ObservableList<Member> getMembers(){
        return memberRegistry.getMembers();
    }
    public void updateMember(Member member){
        memberRegistry.updateMember(member);
    }
}

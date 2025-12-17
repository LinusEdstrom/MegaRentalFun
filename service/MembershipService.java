package com.Edstrom.service;

import com.Edstrom.dataBase.MemberRegistry;
import com.Edstrom.entity.Member;
import com.Edstrom.entity.StatusLevel;
import com.Edstrom.exceptions.AlreadyExistsException;
import com.Edstrom.exceptions.InvalidMemberDataException;
import com.Edstrom.exceptions.MissingStatusException;
import com.Edstrom.exceptions.NumberOverZeroException;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;

public class MembershipService {

    private final MemberRegistry memberRegistry;

    public MembershipService(MemberRegistry memberRegistry) {
        this.memberRegistry = memberRegistry;
    }

    public void addMember(int id, String name, StatusLevel statusLevel) {

        if (id <= 0) {
            throw new NumberOverZeroException("number must be more then 0");
        }
        if (name == null || name.isEmpty()) {
            throw new InvalidMemberDataException(" Error in writing somewhere");
        }
        boolean existsId = memberRegistry.getMembers()
                .stream()
                .anyMatch(member -> member.getId() == id);
        if (existsId) {
            throw new AlreadyExistsException("That Id allready exists"); //alReadyExistsException
        }
        if (statusLevel == null) {
            throw new MissingStatusException("Please choose a status");
        }
        try {
            Member newMember = new Member(id, name.trim(), statusLevel);
            memberRegistry.saveMember(newMember);
        } catch (IOException e) {
            throw new RuntimeException("Error, member not saved" + e);  //IOExceptions should never show in UI
        }
    }

    public void deleteMember(ObservableList<Member> selectedMember) {
    if (selectedMember == null || selectedMember.isEmpty()) {
        throw new InvalidMemberDataException("U have to select a member to delete!");
        }
        ObservableList<Member> allMembers = memberRegistry.getMembers();
        new ArrayList<>(selectedMember).forEach(deletedMember ->{
            allMembers.remove(deletedMember);
            memberRegistry.removeMemberById(deletedMember.getId());
        });
        memberRegistry.saveMemberFile();
    }
    public ObservableList<Member> getMembers(){
        return memberRegistry.getMembers();
    }
    public void updateMember(Member member){
        memberRegistry.updateMember(member);
    }
}

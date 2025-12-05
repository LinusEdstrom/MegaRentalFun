package com.Edstrom.dataBase;

import com.Edstrom.entity.Member;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MemberRegistry extends PersistenceLayer {

    private final ObservableList<Member> memberList = FXCollections.observableArrayList();
    private final File jsonMembers = new File("memberList.json");


    public MemberRegistry(){
        loadMemberFile();
        }

    public ObservableList<Member> getMembers(){
        return memberList;
    }

    public void saveMember(Member member){
        memberList.add(member);
        saveMemberFile();
    }
    public void deleteMember(Member member) {
        memberList.remove(member);
        saveMemberFile();
    }
    private void loadMemberFile()   {
        if(!jsonMembers.exists()) return;   //Behövs ju bara i början eller om nån nallat listan, voi voi

        try{
            List<Member> members = mapper.readValue(jsonMembers, new TypeReference<List<Member>>(){});
            memberList.clear();
            memberList.addAll(members);
        } catch (IOException e){
            System.out.println("The members are gone!! " + e.getMessage());
        }
    }
    private void saveMemberFile(){
        try {
            mapper.writeValue(jsonMembers, new ArrayList<>(memberList));
        }catch (IOException e){
            System.out.println("Crash and burn" + e.getMessage());
        }
    }

    }







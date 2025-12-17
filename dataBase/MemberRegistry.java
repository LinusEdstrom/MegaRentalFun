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
    //instance
    private static MemberRegistry instance;

    private final ObservableList<Member> memberList = FXCollections.observableArrayList();
    private final File jsonMembers = new File("memberList.json");

    public MemberRegistry(){
        loadMemberFile();
        }
    // Gör den till en instance
     public static MemberRegistry getInstance(){
        if(instance == null){
            instance = new MemberRegistry();
         }
        return instance;
     }

    public ObservableList<Member> getMembers(){
        return memberList;
    }

    public void saveMember(Member member) throws IOException{
        memberList.add(member);
        saveMemberFile();
    }
    public void deleteMember(Member member) {
        memberList.remove(member);
        saveMemberFile();
    }
    public boolean removeMemberById(int id){
        Member deletedMember = memberList.stream()
                .filter(delMember -> delMember.getId() == id)
                .findFirst()
                .orElse(null);

        if(deletedMember == null) {
            System.out.println("No member with id " + id);
            return false;
        }
        boolean removed = memberList.remove(deletedMember);
        saveMemberFile();
        return true;
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
    public void saveMemberFile(){
        try {
            mapper.writeValue(jsonMembers, new ArrayList<>(memberList));
        }catch (IOException e){
            System.out.println("Crash and burn" + e.getMessage());
        }
    }
    public void updateMember(Member updateMember){
        int index = memberList.indexOf(updateMember);
        if(index >= 0) {
            memberList.set(index, updateMember);
            saveMemberFile();
        }
    }

    }







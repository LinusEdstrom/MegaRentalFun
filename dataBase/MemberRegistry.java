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


public class MemberRegistry {

    private static final String FILE_PATH = "memberList.json";
    private final ObservableList<Member> memberList = FXCollections.observableArrayList();
    private final File jsonMembers = new File("memberList.json");
    private final ObjectMapper mapper = new ObjectMapper();


    public MemberRegistry(){
        System.out.println("JSON file path: " + jsonMembers.getAbsolutePath());
        loadMemberFile();

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
    private void loadMemberFile() {
        mapper.enable( SerializationFeature.INDENT_OUTPUT);
        try {
            List<Member> members = mapper.readValue(jsonMembers, new TypeReference<List<Member>>() {
            });
            memberList.clear();
            memberList.addAll(members);

            // Fixar jackson som tjafsar med deserialisering
            long maxId = members.stream().mapToLong(Member::getId).max().orElse(0L);
            Member.setNextId(maxId +1); // Sätter memberns original id, STÖKIGT!!!
        } catch (IOException e) {
        }        //Känns lite tomtigt o ha catch här
    }
    private void saveMemberFile(){
        try{
        mapper.writeValue(new File(FILE_PATH), new ArrayList<>(memberList));
        } catch (IOException e){} // Tomteställe två, borde kanske ha nåt fint heina ?
        }

    }







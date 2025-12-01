package com.Edstrom.dataBase;

import com.Edstrom.entity.Member;
import java.util.ArrayList;
import java.util.List;


public class MemberRegistry {

    public MemberRegistry() {}

    private List<Member> memberList = new ArrayList<>();

    /*public void addMember(Member member) {memberList.put(member.getId(), member);
    }
    
    public List<Member> listAllMembers() {return new ArrayList<>(this.memberList.getName());
    }

    Metoder


    public Member findById(long id){return memberList.get(id);}

    public Member findByName(String name) {
        if (name == null) return null; {System.out.println("Member not found");}
        for (Member member :memberList.values()) {
            if (member.getName().equalsIgnoreCase(name)) {
                return member;
            }
        }
        return null;
    }



     */
}




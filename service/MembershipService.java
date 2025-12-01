package com.Edstrom.service;

import com.Edstrom.dataBase.MemberRegistry;
import com.Edstrom.entity.Member;

import java.util.ArrayList;
import java.util.List;

public class MembershipService {

    private final MemberRegistry memberRegistry;

    public MembershipService(MemberRegistry memberRegistry) {
        this.memberRegistry = memberRegistry;
    }

    /*public Member registerMember(String name, String statusLevel) {
        Member addMember = new Member(name, statusLevel);
        memberRegistry.addMember(addMember);
        return addMember;
    }

    public List<Member> listAllMembers() {
        return memberRegistry.listAllMembers();
    }

     */




}


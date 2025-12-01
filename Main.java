package com.Edstrom;

import com.Edstrom.dataBase.MemberRegistry;
import com.Edstrom.service.MembershipService;

public class Main {
    public static void main(String[] args) {

        MemberRegistry memberRegistry = new MemberRegistry();
        MembershipService membershipService = new MembershipService(memberRegistry);
        fillMemberList(membershipService);

    }
    private static void fillMemberList(MembershipService membershipService) {
        membershipService.registerMember("Ofelia", "standard");
        membershipService.registerMember("Lisa", "premium");
        membershipService.registerMember("Thor", "student");
        membershipService.registerMember("KentJesus", "student");
        membershipService.registerMember("Majken", "standard");a
    }
    /*private static void listAllMembers(MembershipService membershipService) {
        for (Member allMembers : membershipService.listAllMembers())
            System.out.println(allMembers.getId() + " - " + allMembers.getName() + " - " + allMembers.getStatusLevel());
    }

     */
}

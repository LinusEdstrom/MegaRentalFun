package com.Edstrom.entity;

public enum StatusLevel {
    STANDARD("Standard"),
    STUDENT("Student"),
    PREMIUM("Premium");

    private final String showStatus;

    StatusLevel(String showStatus){
        this.showStatus = showStatus;
    }
    @Override
    public String toString(){
        return showStatus;
    }

    /*@Override
    public String toString(){
    String s = name().toLowerCase();
    return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

     */

}

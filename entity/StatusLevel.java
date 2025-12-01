package com.Edstrom.entity;

public enum StatusLevel {
    STANDARD,
    STUDENT,
    PREMIUM;

    @Override
    public String toString(){
    String s = name().toLowerCase();
    return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

}

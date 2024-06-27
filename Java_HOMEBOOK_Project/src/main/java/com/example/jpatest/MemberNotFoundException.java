package com.example.jpatest;
public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException(String id) {
        super("Member not found with id " + id);
    }
}
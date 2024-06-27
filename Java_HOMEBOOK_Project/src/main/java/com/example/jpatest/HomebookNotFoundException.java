package com.example.jpatest;
public class HomebookNotFoundException extends RuntimeException {

    public HomebookNotFoundException(Long serialNo) {
        super("Homebook not found with serialNo " + serialNo);
    }
}
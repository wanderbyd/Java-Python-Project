package com.example.jpatest;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class HomebookDateEditor extends PropertyEditorSupport {

    private final SimpleDateFormat dateFormat;

    public HomebookDateEditor() {
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        try {
            Date parsedDate = dateFormat.parse(text);
            setValue(parsedDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use yyyy-MM-dd.");
        }
    }
}


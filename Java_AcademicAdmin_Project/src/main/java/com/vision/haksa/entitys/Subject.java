package com.vision.haksa.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Subjects")
public class Subject {
    @Id
    @Column(name = "subjectcode")
    private String subjectcode;
    
    @Column(name = "subjectname")
    private String subjectname;
    
    @Column(name = "textbookname")
    private String textbookname;

	public String getSubjectcode() {
		return subjectcode;
	}

	public void setSubjectcode(String subjectcode) {
		this.subjectcode = subjectcode;
	}

	public String getSubjectname() {
		return subjectname;
	}

	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
	}

	public String getTextbookname() {
		return textbookname;
	}

	public void setTextbookname(String textbookname) {
		this.textbookname = textbookname;
	}

	
}
package com.vision.haksa.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Students")
public class Student {
    @Id
    @Column(name = "ssn")
    private String ssn;
    
    @Column(name = "fullname")
    private String fullname;
    
    @Column(name = "phonenumber")
    private String phonenumber;
    
    @Column(name = "businessregistrationnumber")
    private String businessregistrationnumber;

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getBusinessregistrationnumber() {
		return businessregistrationnumber;
	}

	public void setBusinessregistrationnumber(String businessregistrationnumber) {
		this.businessregistrationnumber = businessregistrationnumber;
	}
 }
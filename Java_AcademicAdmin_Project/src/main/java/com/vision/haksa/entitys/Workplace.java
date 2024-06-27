package com.vision.haksa.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Workplaces")
public class Workplace {
    @Id
    @Column(name = "businessregistrationnumber")
    private String businessregistrationnumber;
    
    @Column(name = "companyname")
    private String companyname;
    
    @Column(name = "companyaddress")
    private String companyaddress;
    
    @Column(name = "phonenumber")
    private String phonenumber;
    
    @Column(name = "faxnumber")
    private String faxnumber;

	public String getBusinessregistrationnumber() {
		return businessregistrationnumber;
	}

	public void setBusinessregistrationnumber(String businessregistrationnumber) {
		this.businessregistrationnumber = businessregistrationnumber;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getCompanyaddress() {
		return companyaddress;
	}

	public void setCompanyaddress(String companyaddress) {
		this.companyaddress = companyaddress;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getFaxnumber() {
		return faxnumber;
	}

	public void setFaxnumber(String faxnumber) {
		this.faxnumber = faxnumber;
	}

	

    
}
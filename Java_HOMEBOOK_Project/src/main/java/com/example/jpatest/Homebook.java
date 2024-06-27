
package com.example.jpatest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Date;

@Entity

@Table(name = "homebook")
public class Homebook {

	
	
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HOMEBOOK_SEQ")
    @SequenceGenerator(name = "HOMEBOOK_SEQ", sequenceName = "HOMEBOOK_SEQ", allocationSize = 1)
    @Column(name = "serialno")
    private Long serialNo;

    @Column(name = "day")
    private Date day; 
    
    @Column(name = "section")
    private String section;
    
    @Column(name = "accounttitle")
    private String accountTitle;

    @Column(name = "remark")
    private String remark;

    @Column(name = "revenue")
    private Double revenue;

    @Column(name = "expense")
    private Double expense;

    @Column(name = "mid")
    private String memberId;

    public Homebook() {
    }
    public Homebook(Long serialNo, Date day, String section, String accountTitle, String remark, Double revenue, Double expense, String memberId) {
        this.serialNo = serialNo;
        this.day = day;
        this.section = section;
        this.accountTitle = accountTitle;
        this.remark = remark;
        this.revenue = revenue;
        this.expense = expense;
        this.memberId = memberId;
    }

    public Long getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Long serialNo) {
        this.serialNo = serialNo;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
    	this.day = day;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getAccountTitle() {
        return accountTitle;
    }

    public void setAccountTitle(String accountTitle) {
        this.accountTitle = accountTitle;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    public Double getExpense() {
        return expense;
    }

    public void setExpense(Double expense) {
        this.expense = expense;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return "Homebook [serialNo=" + serialNo + ", day=" + day + ", section=" + section + ", accountTitle=" + accountTitle
                + ", remark=" + remark + ", revenue=" + revenue + ", expense=" + expense + ", memberId=" + memberId + "]";
    }
}



package com.example.jpatest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "members")
public class Member {
    
    @Id
    @Column(name = "MEMBERID")
    private String memberId;

    @Column(name = "JOINDATE")
    private LocalDate joinDate; 
    
    @Column(name = "PASSWORD")
    private String password;
    
    @Column(name = "PHONE")
    private String phone;

    @Column(name = "ISWITHDRAW")
    private String isWithdraw;

    public Member() {
    }

    public Member(String memberId, LocalDate joinDate, String password, String phone, String isWithdraw) {
        this.memberId = memberId;
        if (joinDate==null) joinDate = LocalDate.now();
    	this.joinDate = joinDate;
		this.password = password;
        this.phone = phone;
        this.isWithdraw = isWithdraw;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getJoinDate2() {
    	if (joinDate==null) joinDate = LocalDate.now();
    	return joinDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    
    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }
    

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsWithdraw() {
        return isWithdraw;
    }

    public void setIsWithdraw(String isWithdraw) {
        this.isWithdraw = isWithdraw;
    }

	@Override
	public String toString() {
		return "Member [memberId=" + memberId + ", joinDate=" + joinDate + ", password=" + password + ", phone=" + phone
				+ ", isWithdraw=" + isWithdraw + "]";
	}
    
}
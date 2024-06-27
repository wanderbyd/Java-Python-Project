package com.vision.haksa.entitys;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Courses")
public class Course {
    @Id
    @Column(name = "coursecode")
    private String coursecode;// 카멜표기법 사용하지 말것~ COURSE_CODE 
    
    @Column(name = "coursename")
    private String coursename;
    
    @Column(name = "tuitionfee")
    private BigDecimal tuitionfee;
    
    @Column(name = "textbookname")
    private String textbookname;
    
    @Column(name = "startdate")
    private Date startdate;
    
    @Column(name = "coursetype")
    private String coursetype;
    
    @Column(name = "classdays")
    private int classdays;
    
    @Column(name = "subjectcode")
    private String subjectcode;
    
    @Column(name = "enrolledstudents")
    private int enrolledstudents;
    
    @Column(name = "lectureroomnumber")
    private String lectureroomnumber;
    
    //----------------- 추가한 부분 ---------------- 
    @OneToMany(mappedBy = "course")
    private List<CourseInstructor> instructors;
    //--------------------------------------------
    
	public String getCoursecode() {
		return coursecode;
	}

	public void setCoursecode(String coursecode) {
		this.coursecode = coursecode;
	}

	public String getCoursename() {
		return coursename;
	}

	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}

	public BigDecimal getTuitionfee() {
		return tuitionfee;
	}

	public void setTuitionfee(BigDecimal tuitionfee) {
		this.tuitionfee = tuitionfee;
	}

	public String getTextbookname() {
		return textbookname;
	}

	public void setTextbookname(String textbookname) {
		this.textbookname = textbookname;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public String getCoursetype() {
		return coursetype;
	}

	public void setCoursetype(String coursetype) {
		this.coursetype = coursetype;
	}

	public int getClassdays() {
		return classdays;
	}

	public void setClassdays(int classdays) {
		this.classdays = classdays;
	}

	public String getSubjectcode() {
		return subjectcode;
	}

	public void setSubjectcode(String subjectcode) {
		this.subjectcode = subjectcode;
	}

	public int getEnrolledstudents() {
		return enrolledstudents;
	}

	public void setEnrolledstudents(int enrolledstudents) {
		this.enrolledstudents = enrolledstudents;
	}

	public String getLectureroomnumber() {
		return lectureroomnumber;
	}

	public void setLectureroomnumber(String lectureroomnumber) {
		this.lectureroomnumber = lectureroomnumber;
	}

	public List<CourseInstructor> getInstructors() {
		return instructors;
	}

	public void setInstructors(List<CourseInstructor> instructors) {
		this.instructors = instructors;
	}
    

	
	
	
 
}
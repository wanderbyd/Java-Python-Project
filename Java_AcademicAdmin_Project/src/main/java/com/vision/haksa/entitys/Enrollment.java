package com.vision.haksa.entitys;
import java.math.BigDecimal;
import java.util.Objects;
import com.vision.haksa.compkeys.EnrollmentId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@IdClass(EnrollmentId.class) // 복합 키 클래스를 지정
@Table(name = "Enrollments")
public class Enrollment {
	
    @Id
    @Column(name = "coursecode")
    private String coursecode;
 
    @Id
    @Column(name = "ssn")
    private String ssn;

    
    @Column(name = "evaluation")
    private int evaluation;
    
    @Column(name = "tuitionfee")
    private BigDecimal tuitionfee;
    
    @Column(name = "attendance1")
    private int attendance1;
    
    @Column(name = "attendance2")
    private int attendance2;
    
    @Column(name = "attendance3")
    private int attendance3;
    
    @Column(name = "attendance4")
    private int attendance4;
    
    @Column(name = "attendance5")
    private int attendance5;
    
    @ManyToOne
    @JoinColumn(name = "coursecode", referencedColumnName = "coursecode", insertable = false, updatable = false)
    public Course course;

    @ManyToOne
    @JoinColumn(name = "ssn", referencedColumnName = "ssn", insertable = false, updatable = false)
    public Student student;
	
    public Enrollment() {
	}
	public Enrollment(String coursecode, String ssn, int evaluation, BigDecimal tuitionfee, int attendance1,
			int attendance2, int attendance3, int attendance4, int attendance5) {
		super();
		this.coursecode = coursecode;
		this.ssn = ssn;
		this.evaluation = evaluation;
		this.tuitionfee = tuitionfee;
		this.attendance1 = attendance1;
		this.attendance2 = attendance2;
		this.attendance3 = attendance3;
		this.attendance4 = attendance4;
		this.attendance5 = attendance5;
	}
	public String getCoursecode() {
		return coursecode;
	}
	public void setCoursecode(String coursecode) {
		this.coursecode = coursecode;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public int getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(int evaluation) {
		this.evaluation = evaluation;
	}
	public BigDecimal getTuitionfee() {
		return tuitionfee;
	}
	public void setTuitionfee(BigDecimal tuitionfee) {
		this.tuitionfee = tuitionfee;
	}
	public int getAttendance1() {
		return attendance1;
	}
	public void setAttendance1(int attendance1) {
		this.attendance1 = attendance1;
	}
	public int getAttendance2() {
		return attendance2;
	}
	public void setAttendance2(int attendance2) {
		this.attendance2 = attendance2;
	}
	public int getAttendance3() {
		return attendance3;
	}
	public void setAttendance3(int attendance3) {
		this.attendance3 = attendance3;
	}
	public int getAttendance4() {
		return attendance4;
	}
	public void setAttendance4(int attendance4) {
		this.attendance4 = attendance4;
	}
	public int getAttendance5() {
		return attendance5;
	}
	public void setAttendance5(int attendance5) {
		this.attendance5 = attendance5;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	@Override
	public int hashCode() {
		return Objects.hash(attendance1, attendance2, attendance3, attendance4, attendance5, course, coursecode,
				evaluation, ssn, student, tuitionfee);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Enrollment other = (Enrollment) obj;
		return attendance1 == other.attendance1 && attendance2 == other.attendance2 && attendance3 == other.attendance3
				&& attendance4 == other.attendance4 && attendance5 == other.attendance5
				&& Objects.equals(course, other.course) && Objects.equals(coursecode, other.coursecode)
				&& evaluation == other.evaluation && Objects.equals(ssn, other.ssn)
				&& Objects.equals(student, other.student) && Objects.equals(tuitionfee, other.tuitionfee);
	}

}
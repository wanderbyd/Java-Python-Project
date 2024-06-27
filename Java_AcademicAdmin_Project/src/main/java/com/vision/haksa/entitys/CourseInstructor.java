package com.vision.haksa.entitys;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import com.vision.haksa.compkeys.CourseInstructorId;

@Entity
@IdClass(CourseInstructorId.class)
@Table(name = "Courseinstructors")
public class CourseInstructor {
	@Id
    @Column(name = "coursecode")
    private String coursecode;
    
	@Id
    @Column(name = "teacherid")
    private String teacherid;
    
    @Id
    @Column(name = "lecturedate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date lecturedate;
    
    
    private String lecturetime;
    
    private int lectureevaluation;
    
    
    @ManyToOne
    @JoinColumn(name = "coursecode", referencedColumnName = "coursecode", insertable = false, updatable = false)
    public Course course;

    @ManyToOne
    @JoinColumn(name = "teacherid", referencedColumnName = "teacherid", insertable = false, updatable = false)
    public Teacher teacher;
    
    
    public CourseInstructor() {
        // 기본 생성자
    }

    public CourseInstructor(String coursecode, String teacherid, Date lecturedate) {
        this.coursecode = coursecode;
        this.teacherid = teacherid;
        this.lecturedate = lecturedate;
    }
    
    
    
	public String getCoursecode() {
		return coursecode;
	}

	public void setCoursecode(String coursecode) {
		this.coursecode = coursecode;
	}

	public String getTeacherid() {
		return teacherid;
	}

	public void setTeacherid(String teacherid) {
		this.teacherid = teacherid;
	}

	public Date getLecturedate() {
		return lecturedate;
	}

	public void setLecturedate(Date lecturedate) {
		if(lecturedate==null) {
			lecturedate = new Date();
		}
		Date dateOnly = new Date(lecturedate.getTime());
		
		this.lecturedate = dateOnly;
	
	}

	public String getLecturetime() {
		return lecturetime;
	}

	public void setLecturetime(String lecturetime) {
		this.lecturetime = lecturetime;
	}

	public int getLectureevaluation() {
		return lectureevaluation;
	}

	public void setLectureevaluation(int lectureevaluation) {
		this.lectureevaluation = lectureevaluation;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@Override
	public String toString() {
		return "CourseInstructor [coursecode=" + coursecode + ", teacherid=" + teacherid + ", lecturedate="
				+ lecturedate + ", lecturetime=" + lecturetime + ", lectureevaluation=" + lectureevaluation
				+ ", course=" + course + ", teacher=" + teacher + "]";
	}
	
	
 }
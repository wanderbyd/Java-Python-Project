package com.vision.haksa.compkeys;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@IdClass(CourseInstructorId.class)
public class CourseInstructorId implements Serializable {

    @Id
    @Column(name = "coursecode")
    private String coursecode;

    @Id
    @Column(name = "teacherid")
    private String teacherid;

    @Id
    @Column(name = "lecturedate")
    private Date lecturedate;

    public CourseInstructorId() {
    }

    public CourseInstructorId(String coursecode, String teacherid, Date lecturedate) {
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
		this.lecturedate = lecturedate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(coursecode, lecturedate, teacherid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CourseInstructorId other = (CourseInstructorId) obj;
		return Objects.equals(coursecode, other.coursecode) && Objects.equals(lecturedate, other.lecturedate)
				&& Objects.equals(teacherid, other.teacherid);
	}

    
}
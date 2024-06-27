package com.vision.haksa.entitys;
import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vision.haksa.compkeys.CourseInstructorId;

public interface CourseInstructorRepository extends JpaRepository<CourseInstructor, CourseInstructorId> {
	// 직접 쿼리를 사용하여 복합키로 엔티티 조회
    @Query("SELECT ci FROM CourseInstructor ci WHERE ci.coursecode = :coursecode AND ci.teacherid = :teacherid AND ci.lecturedate = :lecturedate")
    CourseInstructor findById(@Param("coursecode") String coursecode, @Param("teacherid") String teacherid, @Param("lecturedate") Date lecturedate);

    // 직접 쿼리를 사용하여 복합키로 엔티티 삭제
    @Query("DELETE FROM CourseInstructor ci WHERE ci.coursecode = :coursecode AND ci.teacherid = :teacherid AND ci.lecturedate = :lecturedate")
    void deleteById(@Param("coursecode") String coursecode, @Param("teacherid") String teacherid, @Param("lecturedate") Date lecturedate);
    
    void deleteByCoursecodeAndTeacheridAndLecturedate(String coursecode, String teacherid, Date lecturedate);
    
}
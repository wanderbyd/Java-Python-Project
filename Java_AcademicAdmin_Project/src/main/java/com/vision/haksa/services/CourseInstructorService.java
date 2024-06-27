package com.vision.haksa.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.vision.haksa.compkeys.CourseInstructorId;
import com.vision.haksa.entitys.CourseInstructor;
import com.vision.haksa.entitys.CourseInstructorRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CourseInstructorService {
	
	@Autowired
    private CourseInstructorRepository courseInstructorRepository;

    public CourseInstructor saveCourseInstructor(CourseInstructor courseInstructor) {
        return courseInstructorRepository.save(courseInstructor);
    }

    public List<CourseInstructor> getAllCourseInstructors() {
        return courseInstructorRepository.findAll();
    }

    // 복합키로 조회
    public CourseInstructor findCourseInstructor(String coursecode, String teacherid, Date lecturedate) {
        return courseInstructorRepository.findById(coursecode, teacherid, lecturedate);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteCourseInstructor(String coursecode, String teacherid, Date lecturedate) {
        courseInstructorRepository.deleteByCoursecodeAndTeacheridAndLecturedate(coursecode, teacherid, lecturedate);
    }
}
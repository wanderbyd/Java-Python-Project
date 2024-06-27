package com.vision.haksa.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vision.haksa.compkeys.EnrollmentId;
import com.vision.haksa.entitys.Enrollment;
import com.vision.haksa.entitys.EnrollmentRepository;

import java.util.List;

@Service
public class EnrollmentService9 {
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public Enrollment saveEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Enrollment getEnrollmentById(String coursecode, String ssn) {
        //return enrollmentRepository.findById(id).orElse(null);
    	return enrollmentRepository.findById(new EnrollmentId(coursecode, ssn)).orElse(null);
    	
    }

    public void deleteEnrollment(String coursecode, String ssn) {
    	
    	EnrollmentId id = new EnrollmentId(coursecode, ssn);
        enrollmentRepository.deleteById(id);
    }
}
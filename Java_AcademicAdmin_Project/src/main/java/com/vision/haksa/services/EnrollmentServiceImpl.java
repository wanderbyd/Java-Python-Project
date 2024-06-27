package com.vision.haksa.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vision.haksa.compkeys.EnrollmentId;
import com.vision.haksa.entitys.Enrollment;
import com.vision.haksa.entitys.EnrollmentRepository;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

	private final EnrollmentRepository enrollmentRepository;
	
	@Autowired
    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    @Override
    public Enrollment getEnrollmentById(EnrollmentId id) {
        return enrollmentRepository.findById(id).orElse(null);
    }

    @Override
    public void createEnrollment(Enrollment enrollment) {
        enrollmentRepository.save(enrollment);
    }

    @Override
    public void updateEnrollment(EnrollmentId id, Enrollment enrollment) {
        if (enrollmentRepository.existsById(id)) {
            enrollmentRepository.save(enrollment);
        }
    }

    @Override
    public void deleteEnrollment(EnrollmentId id) {
        enrollmentRepository.deleteById(id);
    }

}

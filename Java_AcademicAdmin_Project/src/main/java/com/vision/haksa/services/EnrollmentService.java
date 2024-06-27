package com.vision.haksa.services;
import java.util.List;

import com.vision.haksa.compkeys.EnrollmentId;
import com.vision.haksa.entitys.Enrollment;

public interface EnrollmentService {
    List<Enrollment> getAllEnrollments();

    Enrollment getEnrollmentById(EnrollmentId id);

    void createEnrollment(Enrollment enrollment);

    void updateEnrollment(EnrollmentId id, Enrollment enrollment);

    void deleteEnrollment(EnrollmentId id);
}
package com.vision.haksa.entitys;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, String> {
	Subject findBySubjectcode(String code);
}
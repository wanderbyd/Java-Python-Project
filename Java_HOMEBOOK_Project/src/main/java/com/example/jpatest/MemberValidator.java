package com.example.jpatest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MemberValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Member.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "joinDate", "joinDate.required", "Join date is required");
        // 추가적인 검사 룰을 정의할 수 있음
     // joinDate 필드의 값을 LocalDateTime으로 파싱하여 형식을 확인
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        try {
            LocalDateTime joinDate = LocalDateTime.parse(((Member)target).getJoinDate2(), formatter);
        } catch (DateTimeParseException e) {
            errors.rejectValue("joinDate", "joinDate.invalidFormat", "Invalid date format. Please use yyyy-MM-dd HH:mm:ss");
        }
    }

	
}
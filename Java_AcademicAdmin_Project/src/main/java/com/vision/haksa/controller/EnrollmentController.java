package com.vision.haksa.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.vision.haksa.compkeys.EnrollmentId;
import com.vision.haksa.entitys.Enrollment;

import com.vision.haksa.services.CourseService;
import com.vision.haksa.services.EnrollmentService;
import com.vision.haksa.services.StudentService;

@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {
	private final EnrollmentService enrollmentService;
	private final CourseService courseService;
	private final StudentService studentService;

	public EnrollmentController(EnrollmentService enrollmentService, CourseService courseService,
			StudentService studentService) {
		this.enrollmentService = enrollmentService;
		this.courseService = courseService;
		this.studentService = studentService;
	}

	@GetMapping
	public String listEnrollments(Model model, @RequestParam(name = "sortBy", required = false) String sortBy) {
List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
	    
	    if (sortBy == null || sortBy.isEmpty()) {
	        model.addAttribute("enrollments", enrollments);
	        return "enrollment/list";
	    }

	    Comparator<Enrollment> comparator = null;

	    switch (sortBy) {
	        case "coursecode":
	            comparator = Comparator.comparing(enrollment -> enrollment.getCoursecode(), Comparator.nullsLast(Comparator.naturalOrder()));
	            break;
	        case "ssn":
	            comparator = Comparator.comparing(enrollment -> enrollment.getSsn(), Comparator.nullsLast(Comparator.naturalOrder()));
	            break;
	        case "evaluation":
	            comparator = Comparator.comparing(enrollment -> enrollment.getEvaluation(), Comparator.nullsLast(Comparator.naturalOrder()));
	            break;
	        case "tuitionfee":
	            comparator = Comparator.comparing(enrollment -> enrollment.getTuitionfee(), Comparator.nullsLast(Comparator.naturalOrder()));
	            break;
	        case "attendance1":
	            comparator = Comparator.comparing(enrollment -> enrollment.getAttendance1(), Comparator.nullsLast(Comparator.naturalOrder()));
	            break;
	        case "attendance2":
	            comparator = Comparator.comparing(enrollment -> enrollment.getAttendance2(), Comparator.nullsLast(Comparator.naturalOrder()));
	            break;
	        case "attendance3":
	            comparator = Comparator.comparing(enrollment -> enrollment.getAttendance3(), Comparator.nullsLast(Comparator.naturalOrder()));
	            break;
	        case "attendance4":
	            comparator = Comparator.comparing(enrollment -> enrollment.getAttendance4(), Comparator.nullsLast(Comparator.naturalOrder()));
	            break;
	        case "attendance5":
	            comparator = Comparator.comparing(enrollment -> enrollment.getAttendance5(), Comparator.nullsLast(Comparator.naturalOrder()));
	            break;
	        default:
	            // No sorting, use the original order
	            break;
	    }

	    if (comparator != null) {
	        enrollments = enrollments.stream()
	            .sorted(comparator)
	            .collect(Collectors.toList());
	    }

	    model.addAttribute("enrollments", enrollments);
	    return "enrollment/list";
	
		//model.addAttribute("enrollments", enrollmentService.getAllEnrollments());
		//return "enrollment/list";
	}

	@GetMapping("/{coursecode}/{ssn}")
	public String showEnrollment(@PathVariable("coursecode") String coursecode, @PathVariable("ssn") String ssn,
			Model model) {
		EnrollmentId id = new EnrollmentId(coursecode, ssn);
		Enrollment enrollment = enrollmentService.getEnrollmentById(id);
		model.addAttribute("enrollment", enrollment);
		return "enrollment/detail";
	}

	@GetMapping("/new")
	public String createEnrollmentForm(Model model) {
		model.addAttribute("enrollment", new Enrollment());
		model.addAttribute("courses", courseService.getAllCourses());
		model.addAttribute("students", studentService.getAllStudents());
		return "enrollment/form";
	}

	@PostMapping("/save")
	public String saveEnrollment(@ModelAttribute Enrollment enrollment,Model model) {
	
		try {
			enrollmentService.createEnrollment(enrollment);
			model.addAttribute("successMessage", "success! ");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("successMessage", "fail, retry!");
		}
	    return "success"; 
	}

	@GetMapping("/{coursecode}/{ssn}/edit")
	public String editEnrollmentForm(@PathVariable("coursecode") String coursecode, @PathVariable("ssn") String ssn,
			Model model) {
		EnrollmentId id = new EnrollmentId(coursecode, ssn);
		Enrollment enrollment = enrollmentService.getEnrollmentById(id);
		model.addAttribute("enrollment", enrollment);

		// You should load the list of courses and students here and add them to the
		// model
		model.addAttribute("courses", courseService.getAllCourses());
		model.addAttribute("students", studentService.getAllStudents());

		return "enrollment/edit";
	}

	@PostMapping("/{coursecode}/{ssn}/edit")
	public String editEnrollment(@PathVariable("coursecode") String coursecode, @PathVariable("ssn") String ssn,
			@ModelAttribute Enrollment enrollment,Model model) {
		
		try {
			EnrollmentId id = new EnrollmentId(coursecode, ssn);
			enrollmentService.updateEnrollment(id, enrollment);
			model.addAttribute("successMessage", "success! ");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("successMessage", "fail, retry!");
		}
	    return "success"; 
	
	}

	@PostMapping("/{coursecode}/{ssn}/delete")
	public String deleteEnrollment(@PathVariable("coursecode") String coursecode, @PathVariable("ssn") String ssn,Model model) {
		
		try {
			EnrollmentId id = new EnrollmentId(coursecode, ssn);
			enrollmentService.deleteEnrollment(id);
			model.addAttribute("successMessage", "success! ");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("successMessage", "fail, retry!");
		}
	    return "success"; 
	
	}
}
package com.vision.haksa.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.vision.haksa.entitys.Student;
import com.vision.haksa.entitys.Workplace;
import com.vision.haksa.services.StudentService;
import com.vision.haksa.services.WorkplaceService;

@Controller
@RequestMapping("/students")
public class StudentController {

	private final StudentService studentService;
	private final WorkplaceService workplaceService;

	@Autowired
	public StudentController(StudentService studentService, WorkplaceService workplaceService) {
		this.studentService = studentService;
		this.workplaceService = workplaceService;
	}
	@GetMapping("")
	public String listStudents(Model model, @RequestParam(name = "sortBy", required = false) String sortBy) {
	    List<Student> students = studentService.getAllStudents();
	    
	    if (sortBy == null || sortBy.isEmpty()) {
	        model.addAttribute("students", students);
	        return "student/list";
	    }

	    Comparator<Student> comparator = null;

	    switch (sortBy) {
	        case "name":
	            comparator = Comparator.comparing(student -> student.getFullname(), Comparator.nullsLast(Comparator.naturalOrder()));
	            break;
	        case "ssn":
	            comparator = Comparator.comparing(student -> student.getSsn(), Comparator.nullsLast(Comparator.naturalOrder()));
	            break;
	        case "businessRegNum":
	            comparator = Comparator.comparing(student -> student.getBusinessregistrationnumber(), Comparator.nullsLast(Comparator.naturalOrder()));
	            break;
	        default:
	            // No sorting, use the original order
	            break;
	    }

	    if (comparator != null) {
	        students = students.stream()
	            .sorted(comparator)
	            .collect(Collectors.toList());
	    }

	    model.addAttribute("students", students);
	    return "student/list";
	}


  
	@GetMapping("/{ssn}")
	public String viewStudent(@PathVariable String ssn, Model model) {
		Student student = studentService.getStudentById(ssn);
		if (student == null) {
			return "error"; 
		}
		model.addAttribute("student", student);
		return "student/detail";  
	}

	@GetMapping("/add")
	public String createStudentForm(Model model) {
		List<Workplace> workplaces = workplaceService.getAllWorkplaces();
		model.addAttribute("student", new Student());
		model.addAttribute("workplaces", workplaces);
		return "student/form";  
	}

	@GetMapping("/edit/{ssn}")
	public String editStudentForm(@PathVariable String ssn, Model model) {
		Student student = studentService.getStudentById(ssn);
		if (student == null) {
			return "error"; 
		}
		model.addAttribute("student", student);
		return "student/edit"; 
	}
	
	@PostMapping("/create")
	public String createStudent(@ModelAttribute Student student, Model model) {
	    try {
			studentService.saveStudent(student);
			model.addAttribute("successMessage", "success!");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("successMessage", "fail, retry!");
		}
	    return "success";  
	}
	
	
	


	
	@PostMapping("/update/{ssn}")
	public String updateStudent(@PathVariable String ssn, @ModelAttribute Student student,Model model) {

		try {
			student.setSsn(ssn); 
			studentService.saveStudent(student);
			model.addAttribute("successMessage", "success!");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("successMessage", "fail, retry!");
		}
	    return "success"; 
	}

	@PostMapping("/delete/{ssn}")
	public String deleteStudent(@PathVariable String ssn,Model model) {
		try {
			studentService.deleteStudent(ssn);
			model.addAttribute("successMessage", "success! ");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("successMessage", "fail, retry!");
		}
	    return "success"; 
	}

}
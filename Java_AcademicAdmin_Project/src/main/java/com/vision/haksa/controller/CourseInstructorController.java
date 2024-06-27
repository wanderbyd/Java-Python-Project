package com.vision.haksa.controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.vision.haksa.compkeys.CourseInstructorId;
import com.vision.haksa.entitys.Course;
import com.vision.haksa.entitys.CourseInstructor;
import com.vision.haksa.entitys.Teacher;
import com.vision.haksa.services.CourseInstructorService;
import com.vision.haksa.services.CourseService;
import com.vision.haksa.services.TeacherService;

@Controller
@RequestMapping("/courseinstructors")
public class CourseInstructorController {

	private final CourseInstructorService courseInstructorService;
    private final CourseService courseService;
    private final TeacherService teacherService;
    
    @Autowired // 자동으로 di가 일어남 (defendancy injection)
    public CourseInstructorController(CourseInstructorService courseInstructorService, CourseService courseService, TeacherService teacherService) {
        this.courseInstructorService = courseInstructorService;
        this.courseService = courseService;
        this.teacherService = teacherService;
    }
    

    @GetMapping("")
    public String listCourseInstructors(Model model,@RequestParam(name = "sortBy", required = false) String sortBy) {
    	List<CourseInstructor> courseInstructors = courseInstructorService.getAllCourseInstructors();

		if (sortBy == null || sortBy.isEmpty()) {
			model.addAttribute("courseInstructors", courseInstructors);
			return "courseinstructor/list";
		}

		Comparator<CourseInstructor> comparator = null;

		switch (sortBy) {
		case "coursecode":
			comparator = Comparator.comparing(courseinstructor -> courseinstructor.getCoursecode(),
					Comparator.nullsLast(Comparator.naturalOrder()));
			break;
		case "teacherid":
			comparator = Comparator.comparing(courseinstructor -> courseinstructor.getTeacherid(),
					Comparator.nullsLast(Comparator.naturalOrder()));
			break;
		case "lecturedate":
			comparator = Comparator.comparing(courseinstructor -> courseinstructor.getLecturedate(),
					Comparator.nullsLast(Comparator.naturalOrder()));
			break;
		case "lecturetime":
			comparator = Comparator.comparing(courseinstructor -> courseinstructor.getLecturetime(),
					Comparator.nullsLast(Comparator.naturalOrder()));
			break;
		case "lectureevaluation":
			comparator = Comparator.comparing(courseinstructor -> courseinstructor.getLectureevaluation(),
					Comparator.nullsLast(Comparator.naturalOrder()));
			break;
		default:
			break;
		}
		if (comparator != null) {
			courseInstructors = courseInstructors.stream().sorted(comparator).collect(Collectors.toList());
		}
		 model.addAttribute("courseInstructors", courseInstructors);
    	//model.addAttribute("courseInstructors", courseInstructorService.getAllCourseInstructors());
        return "/courseinstructor/list";
    }

    @GetMapping("/new")
    public String newCourseInstructorForm(Model model) {
    	CourseInstructor courseInstructor = new CourseInstructor();
        model.addAttribute("courseInstructor", new CourseInstructor());
        
     // Get the list of courses and teachers
        List<Course> courses = courseService.getAllCourses();
        List<Teacher> teachers = teacherService.getAllTeachers();
   

        model.addAttribute("courses", courses);
        model.addAttribute("teachers", teachers);

        
        
        return "courseinstructor/form";
    }

    @PostMapping("/new")
    public String saveCourseInstructor(@ModelAttribute CourseInstructor courseInstructor,Model model) {
		try {
			  courseInstructorService.saveCourseInstructor(courseInstructor);
			model.addAttribute("successMessage", "success! ");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("successMessage", "fail, retry!");
		}
	    return "success"; 
    }

    @GetMapping("/{coursecode}/{teacherid}/{lecturedate}")
    public String viewCourseInstructor(
        @PathVariable String coursecode,
        @PathVariable String teacherid,
        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date lecturedate,
        Model model
    ) {
        CourseInstructor courseInstructor = courseInstructorService.findCourseInstructor(coursecode, teacherid, lecturedate);
        model.addAttribute("courseInstructor", courseInstructor);
        return "courseinstructor/detail";
    }

    @GetMapping("/edit/{coursecode}/{teacherid}/{lecturedate}")
    public String editCourseInstructorForm(
        @PathVariable String coursecode,
        @PathVariable String teacherid,
        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date lecturedate,
        Model model
    ) {
        CourseInstructor courseInstructor = courseInstructorService.findCourseInstructor(coursecode, teacherid, lecturedate);
        model.addAttribute("courseInstructor", courseInstructor);
        return "courseinstructor/edit";
    }


	@PostMapping("/update/{coursecode}/{teacherid}/{lecturedate}")
	public String updateCourseInstructor(@PathVariable String coursecode,@PathVariable String teacherid,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date lecturedate, @ModelAttribute CourseInstructor courseinstructor,Model model) {
	
		try {
			courseinstructor.setCoursecode(coursecode);
			courseinstructor.setTeacherid(teacherid);
			courseinstructor.setLecturedate(lecturedate);
			
			courseInstructorService.saveCourseInstructor(courseinstructor);
			model.addAttribute("successMessage", "success! ");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("successMessage", "fail, retry!");
		}
	    return "success"; 
	}
    
    

    @PostMapping("/delete/{coursecode}/{teacherid}/{lecturedate}")
    public String deleteCourseInstructor(
        @PathVariable String coursecode,
        @PathVariable String teacherid,
        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date lecturedate,Model model) {
		try {
			  courseInstructorService.deleteCourseInstructor(coursecode, teacherid, lecturedate);
			model.addAttribute("successMessage", "success! ");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("successMessage", "fail, retry!");
		}
	    return "success"; 
	}
    
}


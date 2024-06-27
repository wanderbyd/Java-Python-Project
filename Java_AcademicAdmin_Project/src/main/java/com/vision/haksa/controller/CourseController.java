package com.vision.haksa.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.vision.haksa.entitys.Course;
import com.vision.haksa.entitys.LectureRoom;
import com.vision.haksa.entitys.Student;
import com.vision.haksa.entitys.Subject;
import com.vision.haksa.services.CourseService;
import com.vision.haksa.services.LectureRoomService;
import com.vision.haksa.services.SubjectService;

@Controller
@RequestMapping("/courses")
public class CourseController {

	private final CourseService courseService;
	private final SubjectService subjectService;
	private final LectureRoomService lectureRoomService;

	@Autowired
	public CourseController(CourseService courseService, SubjectService subjectService,
			LectureRoomService lectureRoomService) {
		this.courseService = courseService;
		this.subjectService = subjectService;
		this.lectureRoomService = lectureRoomService;
	}



	@GetMapping("")
	public String listCourses(Model model, @RequestParam(name = "sortBy", required = false) String sortBy) {
		List<Course> courses = courseService.getAllCourses();

		if (sortBy == null || sortBy.isEmpty()) {
			model.addAttribute("courses", courses);
			return "course/list";
		}

		Comparator<Course> comparator = null;

		switch (sortBy) {
		case "coursecode":
			comparator = Comparator.comparing(course -> course.getCoursecode(),
					Comparator.nullsLast(Comparator.naturalOrder()));
			break;
		case "coursename":
			comparator = Comparator.comparing(course -> course.getCoursename(),
					Comparator.nullsLast(Comparator.naturalOrder()));
			break;
		case "tuitionfee":
			comparator = Comparator.comparing(course -> course.getTuitionfee(),
					Comparator.nullsLast(Comparator.naturalOrder()));
			break;
		case "textbookname":
			comparator = Comparator.comparing(course -> course.getTextbookname(),
					Comparator.nullsLast(Comparator.naturalOrder()));
			break;
		case "startdate":
			comparator = Comparator.comparing(course -> course.getStartdate(),
					Comparator.nullsLast(Comparator.naturalOrder()));
			break;
		case "coursetype":
			comparator = Comparator.comparing(course -> course.getCoursetype(),
					Comparator.nullsLast(Comparator.naturalOrder()));
			break;
		case "classdays":
			comparator = Comparator.comparing(course -> course.getClassdays(),
					Comparator.nullsLast(Comparator.naturalOrder()));
			break;
		case "subjectcode":
			comparator = Comparator.comparing(course -> course.getSubjectcode(),
					Comparator.nullsLast(Comparator.naturalOrder()));
			break;
		case "enrolledstudents":
			comparator = Comparator.comparing(course -> course.getEnrolledstudents(),
					Comparator.nullsLast(Comparator.naturalOrder()));
			break;
		case "lectureroomnumber":
			comparator = Comparator.comparing(course -> course.getLectureroomnumber(),
					Comparator.nullsLast(Comparator.naturalOrder()));
			break;
		
		default:
		
			break;
		}

		if (comparator != null) {
			courses = courses.stream().sorted(comparator).collect(Collectors.toList());
		}

		model.addAttribute("courses", courses);
		return "course/list";
	}

	@GetMapping("/{coursecode}")
	public String viewCourse(@PathVariable String coursecode, Model model) {
		Course course = courseService.getCourseById(coursecode);
		if (course == null) {
			// Handle case where course is not found
			return "error"; // You can create an error page
		}
		model.addAttribute("course", course);
		return "course/detail";
	}

	@GetMapping("/add")
	public String createCourseForm(Model model) {
		List<Subject> subjects = subjectService.getAllSubjects();
		List<LectureRoom> lecturerooms = lectureRoomService.getAllLectureRooms();
		model.addAttribute("course", new Course());
		model.addAttribute("subjects", subjects);
		model.addAttribute("lecturerooms", lecturerooms);
		return "course/form";
	}

	@PostMapping("/create")
	public String createCourse(@ModelAttribute Course course, Model model) {
		try {
			courseService.saveCourse(course);
			model.addAttribute("successMessage", "success!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("successMessage", "fail, retry!");
		}
		return "success";
		// return "redirect:/courses";
	}

	// 강좌 삭제 액션
	// @RequestMapping(value = "/{coursecode}", method = RequestMethod.DELETE)
	@PostMapping("{coursecode}")
	public String deleteCourse(@PathVariable String coursecode, Model model) {

		try {
			courseService.deleteCourse(coursecode);
			model.addAttribute("successMessage", "success!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("successMessage", "fail, retry!");
		}
		return "success";

		
	}

	@GetMapping("/edit/{coursecode}")
	public String editCourseForm(@PathVariable String coursecode, Model model) {
		Course course = courseService.getCourseById(coursecode);
		if (course == null) {
			return "error"; // 에러 페이지로 리다이렉트 또는 처리
		}
		model.addAttribute("course", course);
		return "course/edit";
	}

	@PostMapping("/update/{coursecode}")
	public String updateCourse(@PathVariable String coursecode, @ModelAttribute Course course, Model model) {
		// return "redirect:/courses";
		try {
			course.setCoursecode(coursecode);
			courseService.saveCourse(course);

			model.addAttribute("successMessage", "success!");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("successMessage", "fail, retry!");
		}
		return "success";
	}

}
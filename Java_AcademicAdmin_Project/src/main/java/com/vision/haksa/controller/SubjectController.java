package com.vision.haksa.controller;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.vision.haksa.entitys.Subject;
import com.vision.haksa.services.SubjectService;

@Controller
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("")
    public String listSubjects(Model model,@RequestParam(name = "sortBy", required = false) String sortBy) {
        List<Subject> subjects = subjectService.getAllSubjects();   
        
        if (sortBy == null || sortBy.isEmpty()) {
	        model.addAttribute("subjects", subjects);
	        return "subject/list";
	    }

	    Comparator<Subject> comparator = null;

	    switch (sortBy) {
	        case "subjectcode":
	            comparator = Comparator.comparing(subject -> subject.getSubjectcode(), Comparator.nullsLast(Comparator.naturalOrder()));
	            break;
	        case "subjectname":
	            comparator = Comparator.comparing(subject -> subject.getSubjectname(), Comparator.nullsLast(Comparator.naturalOrder()));
	            break;
	        case "textbookname":
	            comparator = Comparator.comparing(subject -> subject.getTextbookname(), Comparator.nullsLast(Comparator.naturalOrder()));
	            break;
	        default:
	            // No sorting, use the original order
	            break;
	    }

	    if (comparator != null) {
	        subjects = subjects.stream()
	            .sorted(comparator)
	            .collect(Collectors.toList());
	    }

	    model.addAttribute("subjects", subjects);
	    return "subject/list";
	}
        
        

    @GetMapping("/{subjectcode}")
    public String viewSubject(@PathVariable String subjectcode, Model model) {
        Subject subject = subjectService.getSubjectByCode(subjectcode);
        if (subject == null) {
            return "error";
        }
        model.addAttribute("subject", subject);
        return "subject/detail";
    }

    @GetMapping("/add")
    public String createSubjectForm(Model model) {
        return "subject/form";
    }

    @PostMapping("/create")
    public String createSubject(@ModelAttribute Subject subject,Model model) {
     	try {
     		subjectService.saveSubject(subject);
			model.addAttribute("successMessage", "success! ");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("successMessage", "fail, retry!");
		}
	    return "success"; 
    }

    @GetMapping("/edit/{subjectcode}")
    public String editSubjectForm(@PathVariable String subjectcode, Model model) {
        Subject subject = subjectService.getSubjectByCode(subjectcode);
        if (subject == null) {
            return "error";
        }
        model.addAttribute("subject", subject);
        return "subject/edit";
    }

    @PostMapping("/update/{subjectcode}")
    public String updateSubject(@PathVariable String subjectcode, @ModelAttribute Subject subject, Model model) {

    	try {
    	    subject.setSubjectcode(subjectcode);
            subjectService.saveSubject(subject);
			model.addAttribute("successMessage", "success! ");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("successMessage", "fail, retry!");
		}
	    return "success"; 
        
        
    }

    @PostMapping("/delete/{subjectcode}")
    public String deleteSubject(@PathVariable String subjectcode, Model model) {
    	
    	
    	try {
    		 subjectService.deleteSubject(subjectcode);
			model.addAttribute("successMessage", "success! ");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("successMessage", "fail, retry!");
		}
	    return "success"; 
    
    }
}
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

import com.vision.haksa.entitys.Student;
import com.vision.haksa.entitys.Workplace;
import com.vision.haksa.services.StudentService;
import com.vision.haksa.services.WorkplaceService;

@Controller
@RequestMapping("/workplaces")
public class WorkplaceController {
	private final WorkplaceService workplaceService;

    @Autowired
    public WorkplaceController(WorkplaceService workplaceService) {
        this.workplaceService = workplaceService;
    }

    
    @GetMapping("")
	public String listWorkplaces(Model model, @RequestParam(name = "sortBy", required = false) String sortBy) {
	    List<Workplace> workplaces = workplaceService.getAllWorkplaces();
	    
	    if (sortBy == null || sortBy.isEmpty()) {
	        model.addAttribute("workplaces", workplaces);
	        return "workplace/list";
	    }

	    Comparator<Workplace> comparator = null;

	    switch (sortBy) {
	        case "regno":
	            comparator = Comparator.comparing(workplace -> workplace.getBusinessregistrationnumber(), Comparator.nullsLast(Comparator.naturalOrder()));
	            break;
	        case "cname":
	            comparator = Comparator.comparing(workplace -> workplace.getCompanyname(), Comparator.nullsLast(Comparator.naturalOrder()));
	            break;
	        
	        default:
	            // No sorting, use the original order
	            break;
	    }

	    if (comparator != null) {
	    	workplaces = workplaces.stream()
	            .sorted(comparator)
	            .collect(Collectors.toList());
	    }

	    model.addAttribute("workplaces", workplaces);
	    return "workplace/list";
	}
    
    
    
    

    @GetMapping("/{businessregistrationnumber}")
    public String viewWorkplace(@PathVariable String businessregistrationnumber, Model model) {
        Workplace workplace = workplaceService.getWorkplaceById(businessregistrationnumber);
        if (workplace == null) {
            return "error"; // 에러 페이지로 리다이렉트 또는 처리
        }
        model.addAttribute("workplace", workplace);
        return "workplace/detail"; // detail.html에 대한 뷰로 이동
    }

    @GetMapping("/add")
    public String createWorkplaceForm(Model model) {
        // model.addAttribute("student", new Student());
        return "workplace/form"; 
    }


        
    @GetMapping("/edit/{businessregistrationnumber}")
    public String editWorkplaceForm(@PathVariable String businessregistrationnumber, Model model) {
    	Workplace workplace = workplaceService.getWorkplaceById(businessregistrationnumber);
        if (workplace == null) {
            // 학생이 존재하지 않는 경우 처리
            return "error"; // 에러 페이지로 리다이렉트 또는 처리
        }
        model.addAttribute("workplace", workplace);
        return "workplace/edit"; 
    }

    @PostMapping("/create")
    public String createWorkplace(@ModelAttribute Workplace workplace,Model model) {
    	try {
			workplaceService.saveWorkplace(workplace);
			model.addAttribute("successMessage", "success!");
		} catch (Exception e) {
		
			e.printStackTrace();
			model.addAttribute("successMessage", "fail, retry!");
		}
    	return "success"; 
    }
    @PostMapping("/update/{businessregistrationnumber}")
    public String updateWorkplace(@PathVariable String businessregistrationnumber, @ModelAttribute Workplace workplace,Model model) {
    	try {
			workplace.setBusinessregistrationnumber(businessregistrationnumber); 
			workplaceService.saveWorkplace(workplace);
			model.addAttribute("successMessage", "success!");
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("successMessage", "fail, retry!");
		}
        return "success"; // 학생 목록 페이지로 리다이렉트
    }
    
    @PostMapping("/delete/{businessregistrationnumber}")
    public String deleteWorkplace(@PathVariable String businessregistrationnumber,Model model) {
    	try {
			workplaceService.deleteWorkplace(businessregistrationnumber);
			model.addAttribute("successMessage", "success!");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("successMessage", "fail, retry!");
		}
		return "success"; 
    }
    
    
    
    @GetMapping("/form")
    public String getForm(Model model) {
        Workplace workplace = new Workplace();
        model.addAttribute("workplace", workplace);
        return "workplace/form";
    }

}

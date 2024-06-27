package com.example.jpatest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.example.jpatest.HomebookDateEditor;

import jakarta.servlet.http.HttpSession;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/homebook")
public class HomebookController {

    private final HomebookService homebookService;

    public HomebookController(HomebookService homebookService) {
        this.homebookService = homebookService;
    }
    
    @Autowired
    private HomebookDateEditor dateEditor;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, dateEditor);
        }
  
	@GetMapping("/")
	public String index(HttpSession session,Model model) {
		Member loggedInMember = (Member) session.getAttribute("loggedInMember");
		List<Homebook> homebooks = homebookService.getAllHomebooks();
		List<Homebook> filteredHomebooks = homebooks.stream()
                .filter(homebook -> homebook.getMemberId().equals(loggedInMember.getMemberId()))
                .collect(Collectors.toList());
		model.addAttribute("homebooks", filteredHomebooks);
		return "index_homebook";
	}
    

    @GetMapping("/{serialNo}")
    public String viewHomebook(@PathVariable("serialNo") Long serialNo, Model model) {
        Homebook homebook = homebookService.getHomebookById(serialNo);
        if (homebook == null) {
            throw new HomebookNotFoundException(serialNo);
        }
        model.addAttribute("homebook", homebook);
        return "detail_homebook";
    }

    @PostMapping("/{serialNo}")
    public ResponseEntity<Homebook> updateHomebook(@PathVariable("serialNo") Long serialNo, @RequestBody Homebook homebook) {
        Homebook updatedHomebook = homebookService.updateHomebook(serialNo, homebook);
        return ResponseEntity.ok().body(updatedHomebook);
    }

    @PostMapping("/edit/{serialNo}")
    public String editHomebookSubmit(@PathVariable("serialNo") Long serialNo, @ModelAttribute("homebook") Homebook homebook) {
        Homebook updatedHomebook = homebookService.updateHomebook(serialNo, homebook);
        return "redirect:/homebook/";
    }



    @PostMapping("/delete/{serialNo}")
    public String deleteHomebook(@PathVariable Long serialNo) {
        homebookService.deleteHomebook(serialNo);
        return "redirect:/homebook/";
    }

    @GetMapping("/new")
    public String newHomebook(Model model) {
        model.addAttribute("homebook", new Homebook());
        return "new_homebook";
    }
    
    @PostMapping("/add")
    public String addHomebook(@ModelAttribute Homebook homebook, Model model, HttpSession session) {
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        homebook.setMemberId(loggedInMember.getMemberId()); 
        homebookService.createHomebook(homebook);
        List<Homebook> homebooks = homebookService.getAllHomebooks()
                .stream()
                .filter(hb -> hb.getMemberId().equals(loggedInMember.getMemberId()))
                .collect(Collectors.toList());
        
        model.addAttribute("homebooks", homebooks);
        
        return "index_homebook";
    }


    @GetMapping("/edit/{serialNo}")
    public String showEditForm(@PathVariable("serialNo") Long serialNo, Model model) {
        Homebook homebook = homebookService.getHomebookById(serialNo);
        model.addAttribute("homebook", homebook);
        return "edit_homebook";
    }
}

